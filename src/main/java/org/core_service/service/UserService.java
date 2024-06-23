package org.core_service.service;

import lombok.RequiredArgsConstructor;
import org.core_service.mapper.UserMapper;
import org.core_service.entity.Role;
import org.core_service.entity.User;
import org.core_service.producer.KafkaProducerUserCreate;
import org.core_service.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaProducerUserCreate kafkaProducerUserCreate;

    @Transactional
    public ResponseEntity<String> createUser(String userName, String password) {
        if (getUser(userName) != null) {
            return ResponseEntity.status(409).body("Имя пользователя" + userName + "занято");
        }
        User user = User.builder()
                .userName(userName)
                .password(password)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        kafkaProducerUserCreate.sendMessage(userMapper.toDto(user));
        return ResponseEntity.status(201).body("Аккаунт создан");
    }

    private User getUser(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }
}
