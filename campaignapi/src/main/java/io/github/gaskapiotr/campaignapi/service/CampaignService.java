package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.dto.CampaignRequest;
import io.github.gaskapiotr.campaignapi.dto.CampaignResponse;
import io.github.gaskapiotr.campaignapi.model.Campaign;
import io.github.gaskapiotr.campaignapi.model.Keyword;
import io.github.gaskapiotr.campaignapi.repository.CampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final EmeraldAccountService emeraldAccountService;
    private final KeywordService keywordService;
    private final TownService townService;

    public List<CampaignResponse> getAllCampaigns() {
        return campaignRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CampaignResponse getCampaignById(Long id) {
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

    @Transactional
    public CampaignResponse createCampaign(CampaignRequest campaignRequest) {
        emeraldAccountService.deductFunds(campaignRequest.campaignFund());

        Campaign campaign = mapToEntity(campaignRequest);
        Campaign savedCampaign = campaignRepository.save(campaign);

        return mapToResponse(savedCampaign);
    }

    private Campaign mapToEntity(CampaignRequest request) {
        Campaign campaign = new Campaign();
        updateEntityFromRequest(campaign, request);
        return campaign;
    }

    @Transactional
    public CampaignResponse updateCampaign(Long id, CampaignRequest campaignRequest) {
        Campaign campaign = getCampaignEntityById(id);

        adjustCampaignFund(campaign.getCampaignFund(), campaignRequest.campaignFund());

        updateEntityFromRequest(campaign, campaignRequest);

        return mapToResponse(campaign);
    }

    private void updateEntityFromRequest(Campaign campaign, CampaignRequest request) {
        campaign.setName(request.name());
        campaign.setKeywords(keywordService.getKeywordsByNames(request.keywords()));
        campaign.setBidAmount(request.bidAmount());
        campaign.setCampaignFund(request.campaignFund());
        campaign.setStatus(request.status());
        campaign.setTown(request.townId() != null ? townService.getTownEntityById(request.townId()) : null);
        campaign.setRadiusKm(request.radiusKm());
    }

    private void adjustCampaignFund(BigDecimal oldFund, BigDecimal newFund) {
        BigDecimal difference = newFund.subtract(oldFund);

        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            emeraldAccountService.deductFunds(difference);
        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
            emeraldAccountService.depositFunds(difference.abs());
        }
    }

    @Transactional
    public void deleteCampaign(Long id) {
        Campaign campaign = getCampaignEntityById(id);

        emeraldAccountService.depositFunds(campaign.getCampaignFund());

        campaignRepository.delete(campaign);
    }

    private Campaign getCampaignEntityById(Long id) {
       return campaignRepository.findById(id)
                       .orElseThrow(() -> new EntityNotFoundException("Campaign not found with id: " + id));
    }
}
