package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.dto.TownResponse;
import io.github.gaskapiotr.campaignapi.model.Town;
import io.github.gaskapiotr.campaignapi.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TownService {
    private final TownRepository townRepository;

    public Set<TownResponse> getAllTowns() {
        return townRepository.findAll().stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    private TownResponse entityToResponse(Town entity) {
        return new TownResponse(
                entity.getId(),
                entity.getName()
        );
    }
}
