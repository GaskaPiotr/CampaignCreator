package io.github.gaskapiotr.campaignapi.dto;

import io.github.gaskapiotr.campaignapi.model.CampaignStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;

public record CampaignRequest (
        @NotBlank(message = "Campaign name is mandatory")
        String name,

        @NotEmpty(message = "Keywords are mandatory")
        Set<@NotBlank String> keywords,

        @NotNull(message = "Bid amount is mandatory")
        @DecimalMin(value = "0.10", message = "Bid amount must be at least 0.10")
        BigDecimal bidAmount,

        @NotNull(message = "Campaign fund is mandatory")
        @Positive(message = "Campaign fund must be greater than zero")
        BigDecimal campaignFund,

        @NotNull(message = "Status is mandatory")
        CampaignStatus status,

        Long townId,

        @NotNull(message = "Radius is mandatory")
        @Positive(message = "Radius must be greater than zero")
        Double radiusKm
){}
