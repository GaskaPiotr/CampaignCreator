package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.model.Keyword;
import io.github.gaskapiotr.campaignapi.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public Set<String> getAllKeywordNames() {
        return keywordRepository.findAll().stream()
                .map(Keyword::getName)
                .collect(Collectors.toSet());
    }
}
