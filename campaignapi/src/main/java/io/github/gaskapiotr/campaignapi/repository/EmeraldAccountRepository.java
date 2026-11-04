package io.github.gaskapiotr.campaignapi.repository;

import io.github.gaskapiotr.campaignapi.model.EmeraldAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmeraldAccountRepository extends JpaRepository<EmeraldAccount, Long> {
    @Query("SELECT a FROM EmeraldAccount a")
    Optional<EmeraldAccount> findAccount();
}
