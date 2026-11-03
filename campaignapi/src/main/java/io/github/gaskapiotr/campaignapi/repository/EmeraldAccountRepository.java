package io.github.gaskapiotr.campaignapi.repository;

import io.github.gaskapiotr.campaignapi.model.EmeraldAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmeraldAccountRepository extends JpaRepository<EmeraldAccount, Long> {
}
