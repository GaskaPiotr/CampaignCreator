package io.github.gaskapiotr.campaignapi.repository;

import io.github.gaskapiotr.campaignapi.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
}
