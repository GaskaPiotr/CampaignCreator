package io.github.gaskapiotr.campaignapi.controller;

import io.github.gaskapiotr.campaignapi.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping
    public ResponseEntity<Set<String>> getAllKeywords() {
        return ResponseEntity.ok(keywordService.getAllKeywordNames());
    }
}
