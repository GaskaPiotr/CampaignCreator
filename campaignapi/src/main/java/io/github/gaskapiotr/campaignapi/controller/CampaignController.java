package io.github.gaskapiotr.campaignapi.controller;

import io.github.gaskapiotr.campaignapi.dto.CampaignRequest;
import io.github.gaskapiotr.campaignapi.dto.CampaignResponse;
import io.github.gaskapiotr.campaignapi.model.CampaignStatus;
import io.github.gaskapiotr.campaignapi.service.CampaignService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;

    @GetMapping
    public ResponseEntity<List<CampaignResponse>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponse> getCampaignById(
            @PathVariable @Positive(message = "ID must be positive") Long id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<CampaignStatus>> getCampaignStatuses() {
        return ResponseEntity.ok(
                List.of(CampaignStatus.values())
        );
    }

    @PostMapping
    public ResponseEntity<CampaignResponse> createCampaign(@Valid @RequestBody CampaignRequest request) {
        CampaignResponse createdCampaign = campaignService.createCampaign(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCampaign.id())
                .toUri();

        return ResponseEntity.created(location).body(createdCampaign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponse> updateCampaign(
            @PathVariable @Positive(message = "ID must be positive") Long id,
            @Valid @RequestBody CampaignRequest request
    ) {
        return ResponseEntity.ok(campaignService.updateCampaign(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(
            @PathVariable @Positive(message = "ID must be positive") Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }
}
