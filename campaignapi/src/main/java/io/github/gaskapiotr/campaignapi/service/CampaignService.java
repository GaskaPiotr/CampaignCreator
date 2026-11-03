package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.dto.CampaignResponse;
import io.github.gaskapiotr.campaignapi.model.Campaign;
import io.github.gaskapiotr.campaignapi.model.Keyword;
import io.github.gaskapiotr.campaignapi.repository.CampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public List<CampaignResponse> getAll() {
        return campaignRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CampaignResponse getById(Long id) {
        return campaignRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found in database"));
    }

    private CampaignResponse mapToResponse(Campaign entity) {
        return new CampaignResponse(
                entity.getId(),
                entity.getName(),
                entity.getKeywords().stream()
                        .map(Keyword::getName)
                        .collect(Collectors.toSet()),
                entity.getBidAmount(),
                entity.getCampaignFund(),
                entity.getStatus(),
                entity.getTownName(),
                entity.getRadiusKm()
        );
    }
}
