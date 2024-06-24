package org.core_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.core_service.service.ImjService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Tag(name = "ImjController")
@RestController
@RequiredArgsConstructor
@RequestMapping("${server.imj_version}")
public class ImjController {

    private final ImjService imjService;

    @Value("${img_size}")
    private int imjMaxSize;

    @Operation(
            summary = "Добавляем изображения",
            description = "Разрешённые форматы jpg и png весом до 10 Мб"
    )
    @PostMapping("/imj")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> addImj(@RequestParam MultipartFile[] imj, Long userId) {
        List<MultipartFile> imjList = Arrays.stream(imj)
                .filter(i -> i.getName().endsWith(".jpg")
                             || i.getName().endsWith(".png")
                             || i.getSize() < imjMaxSize)
                .toList();
        return imjService.addImj(imjList, userId);
    }
}
