package org.core_service.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.core_service.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImjService {

    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;

    public ResponseEntity<String> addImj(List<MultipartFile> imjList, Long userId) {
        amazonS3.putObject(userRepository.findById(userId))
    }
}
