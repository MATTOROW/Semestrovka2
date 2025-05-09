package ru.itis.semesterwork.second.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.repository.AccountRepository;
import ru.itis.semesterwork.second.service.IconStorageService;


@RestController
@RequiredArgsConstructor
public class ImageController {

    private final AccountRepository accountRepository;
    private final IconStorageService iconStorageService;

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        Resource resource = iconStorageService.loadIcon(filename);
        if (resource != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, iconStorageService.contentType(filename))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
