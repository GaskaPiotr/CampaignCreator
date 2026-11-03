package io.github.gaskapiotr.campaignapi.dto;

import java.math.BigDecimal;

public record EmeraldAccountResponse(
        Long id,
        BigDecimal balance
) {}
