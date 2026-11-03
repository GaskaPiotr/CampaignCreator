package io.github.gaskapiotr.campaignapi.dto;

import io.github.gaskapiotr.campaignapi.model.CampaignStatus;

import java.math.BigDecimal;
import java.util.Set;

public record CampaignResponse(
        Long id,
        String name,
        Set<String> keywords,
        BigDecimal bidAmount,
        BigDecimal campaignFund,
        CampaignStatus status,
        String townName,
        Double radiusKm
) {}
