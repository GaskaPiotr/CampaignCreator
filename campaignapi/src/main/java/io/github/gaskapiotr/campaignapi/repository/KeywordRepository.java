package io.github.gaskapiotr.campaignapi.repository;

import io.github.gaskapiotr.campaignapi.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    @Query("SELECT k.name FROM Keyword k ORDER BY k.name ASC")
    Set<String> findAllNames();

    Set<Keyword> findAllByNameIn(Set<String> names);
}
