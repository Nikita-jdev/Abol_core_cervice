package org.core_service.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core_service.entity.KafkaEventAddImj;
import org.core_service.entity.User;
import org.core_service.exeption.DataValidationException;
import org.core_service.mapper.UserMapper;
import org.core_service.producer.KafkaProducerAddImj;
import org.core_service.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Async("executor")
    public ResponseEntity<String> addImj(List<MultipartFile> imjList, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataValidationException("User not found: " + userId));
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
}
