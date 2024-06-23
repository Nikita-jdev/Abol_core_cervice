package org.core_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.core_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserController")
@RestController
@RequiredArgsConstructor
@RequestMapping("${server.user_version}")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Создаём пользователя",
            description = "Создаём имя пользователя и пароль, имя проверяем на уникальность"
    )
    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody String userName, String password) {
        return userService.createUser(userName, password);
    }
}

