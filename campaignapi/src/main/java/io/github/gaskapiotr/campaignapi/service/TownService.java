package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.dto.TownResponse;
import io.github.gaskapiotr.campaignapi.model.Town;
import io.github.gaskapiotr.campaignapi.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TownService {
    private final TownRepository townRepository;

    public List<TownResponse> getAllTowns() {
        return townRepository.findAllByOrderByNameAsc().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TownResponse mapToResponse(Town entity) {
        return new TownResponse(
                entity.getId(),
                entity.getName()
        );
    }

    Town getTownEntityById(Long id) {
        return townRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Town not found in database"));
    }
}
