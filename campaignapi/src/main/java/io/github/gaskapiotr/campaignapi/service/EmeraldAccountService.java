package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.dto.EmeraldAccountResponse;
import io.github.gaskapiotr.campaignapi.exception.InsufficientFundsException;
import io.github.gaskapiotr.campaignapi.model.EmeraldAccount;
import io.github.gaskapiotr.campaignapi.repository.EmeraldAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmeraldAccountService {
    private final EmeraldAccountRepository emeraldAccountRepository;

    public EmeraldAccountResponse getAccount() {
        EmeraldAccount emeraldAccount = getAccountEntity();
        return new EmeraldAccountResponse(emeraldAccount.getId(), emeraldAccount.getBalance());
    }

    private EmeraldAccount getAccountEntity() {
        return emeraldAccountRepository.findAccount()
                .orElseThrow(() -> new IllegalStateException("Emerald account not found in database"));
    }

    @Transactional
    void deductFunds(BigDecimal amount) {
        EmeraldAccount emeraldAccount = getAccountEntity();
        BigDecimal balance = emeraldAccount.getBalance();

        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException("Insufficient funds. Required: " + amount + ", Available: " + balance);
        }

        emeraldAccount.setBalance(balance.subtract(amount));
    }

    @Transactional
    public void depositFunds(BigDecimal amount) {
        EmeraldAccount account = getAccountEntity();
        account.setBalance(account.getBalance().add(amount));
    }
}
