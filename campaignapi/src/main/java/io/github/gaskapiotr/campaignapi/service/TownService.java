package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.model.Town;
import io.github.gaskapiotr.campaignapi.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TownService {
    private final TownRepository townRepository;

    public Set<Town> getAllTowns() {
        return new HashSet<>(townRepository.findAll());
    }
}
