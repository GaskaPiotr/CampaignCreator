package io.github.gaskapiotr.campaignapi.repository;

import io.github.gaskapiotr.campaignapi.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    @Query("SELECT k.name FROM Keyword k")
    Set<String> findAllNames();
}
