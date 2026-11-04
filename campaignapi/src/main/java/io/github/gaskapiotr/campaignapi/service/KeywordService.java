package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.model.Keyword;
import io.github.gaskapiotr.campaignapi.repository.KeywordRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return keywordRepository.findAllNames();
    }

    Set<Keyword> getKeywordsByNames(Set<String> names) {
        Set<Keyword> foundKeywords = keywordRepository.findAllByNameIn(names);

        if (foundKeywords.size() != names.size()) {
            throwEntityNotFoundException(foundKeywords, names);
        }

        return foundKeywords;
    }

    private void throwEntityNotFoundException(Set<Keyword> foundKeywords, Set<String> names) {
        Set<String> foundNames = foundKeywords.stream()
                .map(Keyword::getName)
                .collect(Collectors.toSet());

        Set<String> missingNames = names.stream()
                .filter(name -> !foundNames.contains(name))
                .collect(Collectors.toSet());

        throw new EntityNotFoundException("Keywords not found: " + missingNames);
    }
}
