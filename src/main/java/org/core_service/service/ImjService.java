package org.core_service.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core_service.entity.KafkaEventAddImj;
import org.core_service.entity.KafkaEventGetImj;
import org.core_service.entity.User;
import org.core_service.exeption.DataValidationException;
import org.core_service.mapper.UserMapper;
import org.core_service.producer.KafkaProducerAddImj;
import org.core_service.producer.KafkaProducerGetImg;
import org.core_service.repository.UserRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImjService {

    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    private final KafkaProducerAddImj kafkaProducerAddImj;
    private final UserMapper userMapper;
    private final KafkaProducerGetImg kafkaProducerGetImg;

    @Async("executor")
    public ResponseEntity<String> addImj(List<MultipartFile> imjList, Long userId) {
        User user = getUser(userId);
        String bucketName = user.getUserName();

        long totalSize = imjList.parallelStream()
                .mapToLong(MultipartFile::getSize)
                .sum();

        imjList.parallelStream()
                .forEach(imj -> {
                    try {
                        amazonS3.putObject(
                                bucketName,
                                imj.getName(),
                                imj.getInputStream(),
                                null);
                    } catch (IOException e) {
                        log.error("Failed to save in MinIO");
                        throw new RuntimeException(Arrays.toString(e.getStackTrace()));
                    }
                });

        KafkaEventAddImj kafkaEventAddImj = new KafkaEventAddImj(
                bucketName, totalSize, userMapper.toDto(user));
        kafkaProducerAddImj.sendMessage(kafkaEventAddImj);
        return ResponseEntity.status(201).body("Файлы загружены");
    }

    public ResponseEntity<InputStreamResource> getImj(String imjName, Long userId) {
        User user = getUser(userId);
        S3Object amazonS3Object = amazonS3.getObject(user.getUserName(), imjName);
        try {
            KafkaEventGetImj kafkaEventGetImj = new KafkaEventGetImj(
                    imjName,
                    amazonS3Object.getObjectMetadata().getContentLength(),
                    userMapper.toDto(user));
            kafkaProducerGetImg.sendMessage(kafkaEventGetImj);

            return ResponseEntity.ok()
                    .body(new InputStreamResource(
                            new ByteArrayInputStream(
                                    amazonS3Object.getObjectContent().readAllBytes())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataValidationException(
                        "User not found: " + userId));
    }
}
