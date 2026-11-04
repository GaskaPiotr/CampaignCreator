package io.github.gaskapiotr.campaignapi.controller;

import io.github.gaskapiotr.campaignapi.dto.TownResponse;
import io.github.gaskapiotr.campaignapi.service.TownService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/towns")
@RequiredArgsConstructor
public class TownController {
    private final TownService townService;

    @GetMapping
    public ResponseEntity<List<TownResponse>> getAllTowns() {
        return ResponseEntity.ok(townService.getAllTowns());
    }
}
