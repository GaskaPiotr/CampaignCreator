package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.dto.CampaignRequest;
import io.github.gaskapiotr.campaignapi.dto.CampaignResponse;
import io.github.gaskapiotr.campaignapi.exception.InsufficientFundsException;
import io.github.gaskapiotr.campaignapi.model.Campaign;
import io.github.gaskapiotr.campaignapi.model.CampaignStatus;
import io.github.gaskapiotr.campaignapi.model.Keyword;
import io.github.gaskapiotr.campaignapi.model.Town;
import io.github.gaskapiotr.campaignapi.repository.CampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {
    @Mock
    private CampaignRepository campaignRepository;
    @Mock
    private KeywordService keywordService;
    @Mock
    private TownService townService;
    @Mock
    private EmeraldAccountService emeraldAccountService;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    @DisplayName("Should create campaign and deduct funds when request is valid")
    void shouldCreateCampaignWhenRequestIsValid() {
        // Arrange
        CampaignRequest campaignRequest = new CampaignRequest(
                "Winter Promo",
                Set.of("skiing", "winter"),
                new BigDecimal("2.50"),
                new BigDecimal("100.00"),
                CampaignStatus.ON,
                1L,
                15.0
        );

        Town town = new Town("Kraków");
        Set<Keyword> keywords = Set.of(new Keyword("skiing"), new Keyword("winter"));

        when(townService.getTownEntityById(1L)).thenReturn(town);
        when(keywordService.getKeywordsByNames(campaignRequest.keywords())).thenReturn(keywords);

        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocation -> {
            Campaign c = invocation.getArgument(0);
            c.setId(10L);
            return c;
        });

        // Act
        CampaignResponse campaignResponse = campaignService.createCampaign(campaignRequest);

        // Assert
        assertThat(campaignResponse).isNotNull();
        assertThat(campaignResponse.id()).isEqualTo(10L);
        assertThat(campaignResponse.name()).isEqualTo("Winter Promo");
        verify(emeraldAccountService).deductFunds(new BigDecimal("100.00"));
        verify(campaignRepository).save(any(Campaign.class));
    }

    @Test
    @DisplayName("Should not save campaign if balance deduction throws InsufficientFundsException")
    void shouldNotSaveCampaignWhenInsufficientFunds() {
        // Arrange
        CampaignRequest campaignRequest = new CampaignRequest(
                "Summer Sale",
                Set.of("beach"),
                new BigDecimal("1.00"),
                new BigDecimal("500.00"),
                CampaignStatus.ON,
                null,
                10.0
        );

        doThrow(new InsufficientFundsException("Insufficient funds"))
                .when(emeraldAccountService).deductFunds(campaignRequest.campaignFund());

        // Act & Assert
        assertThatThrownBy(() -> campaignService.createCampaign((campaignRequest)))
                .isInstanceOf(InsufficientFundsException.class);

        verify(campaignRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should deduct difference when updating campaign with increased budget")
        void shouldDeductDifferenceWhenBudgetIncreased() {
        // Arrange
        Long campaignId = 1L;
        Campaign existingCampaign = new Campaign();
        existingCampaign.setId(campaignId);
        existingCampaign.setCampaignFund(new BigDecimal("100.00"));

        CampaignRequest updateRequest = new CampaignRequest(
                "Updated Name",
                Set.of("promo"),
                new BigDecimal("2.00"),
                new BigDecimal("150.00"),
                CampaignStatus.ON,
                null,
                20.0
        );

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(existingCampaign));
        when(keywordService.getKeywordsByNames(updateRequest.keywords())).thenReturn(Set.of(new Keyword("promo")));

        // Act
        CampaignResponse response = campaignService.updateCampaign(campaignId, updateRequest);

        // Assert
        assertThat(response.campaignFund()).isEqualByComparingTo("150.00");
        verify(emeraldAccountService).deductFunds(new BigDecimal("50.00"));
        verify(emeraldAccountService, never()).depositFunds(any());
    }

    @Test
    @DisplayName("Should refund difference when updating campaign with decreased budget")
    void shouldRefundDifferenceWhenBudgetDecreased() {
        // Arrange
        Long campaignId = 1L;
        Campaign existingCampaign = new Campaign();
        existingCampaign.setId(campaignId);
        existingCampaign.setCampaignFund(new BigDecimal("100.00"));

        CampaignRequest updateRequest = new CampaignRequest(
                "Updated Name",
                Set.of("promo"),
                new BigDecimal("2.00"),
                new BigDecimal("60.00"),
                CampaignStatus.ON,
                null,
                20.0
        );

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(existingCampaign));
        when(keywordService.getKeywordsByNames(updateRequest.keywords())).thenReturn(Set.of(new Keyword("promo")));

        // Act
        CampaignResponse response = campaignService.updateCampaign(campaignId, updateRequest);

        // Assert
        assertThat(response.campaignFund()).isEqualByComparingTo("60.00");
        verify(emeraldAccountService).depositFunds(new BigDecimal("40.00"));
        verify(emeraldAccountService, never()).deductFunds(any());
    }

    @Test
    @DisplayName("Should refund full fund and delete entity on deleteCampaign")
    void shouldRefundAndRefundOnDelete() {
        // Arrange
        Long campaignId = 7L;
        Campaign existingCampaign = new Campaign();
        existingCampaign.setId(campaignId);
        existingCampaign.setCampaignFund(new BigDecimal("250.00"));

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(existingCampaign));

        // Act
        campaignService.deleteCampaign(campaignId);

        // Assert
        verify(emeraldAccountService).depositFunds(new BigDecimal("250.00"));
        verify(campaignRepository).delete(existingCampaign);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException on delete when campaign does not exist")
    void shouldThrowExceptionWhenDeletingNonExistentCampaign() {
        // Arrange
        when(campaignRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> campaignService.deleteCampaign(99L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(emeraldAccountService, never()).depositFunds(any());
        verify(campaignRepository, never()).delete(any());
    }
}