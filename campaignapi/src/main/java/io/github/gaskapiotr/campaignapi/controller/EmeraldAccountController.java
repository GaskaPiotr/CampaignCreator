package io.github.gaskapiotr.campaignapi.controller;

import io.github.gaskapiotr.campaignapi.dto.EmeraldAccountResponse;
import io.github.gaskapiotr.campaignapi.service.EmeraldAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emerald-account")
@RequiredArgsConstructor
public class EmeraldAccountController {
    private final EmeraldAccountService emeraldAccountService;

    @GetMapping
    public ResponseEntity<EmeraldAccountResponse> getEmeraldAccount() {
        return ResponseEntity.ok(emeraldAccountService.getAccount());
    }
}
