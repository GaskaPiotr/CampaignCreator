package io.github.gaskapiotr.campaignapi.service;

import io.github.gaskapiotr.campaignapi.exception.InsufficientFundsException;
import io.github.gaskapiotr.campaignapi.model.EmeraldAccount;
import io.github.gaskapiotr.campaignapi.repository.EmeraldAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmeraldAccountServiceTest {
    @Mock
    private EmeraldAccountRepository emeraldAccountRepository;

    @InjectMocks
    private EmeraldAccountService emeraldAccountService;

    @Test
    @DisplayName("Should deduct funds when balance is sufficient")
    void shouldDeductFundsWhenBalanceIsSufficient() {
        // Arrange
        EmeraldAccount emeraldAccount = new EmeraldAccount();
        emeraldAccount.setBalance(new BigDecimal("100.00"));
        when(emeraldAccountRepository.findAccount()).thenReturn(Optional.of(emeraldAccount));

        // Act
        emeraldAccountService.deductFunds(new BigDecimal("30.00"));

        // Assert
        assertThat(emeraldAccount.getBalance()).isEqualByComparingTo("70.00");
    }

    @Test
    @DisplayName("Should throw InsufficientFundsException when balance is too low")
    void shouldThrowExceptionWhenBalanceIsTooLow() {
        // Arrange
        EmeraldAccount emeraldAccount = new EmeraldAccount();
        emeraldAccount.setBalance(new BigDecimal("50.00"));
        when(emeraldAccountRepository.findAccount()).thenReturn(Optional.of(emeraldAccount));

        // Act & Assert
        assertThatThrownBy(() -> emeraldAccountService.deductFunds(new BigDecimal("60.00")))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient funds");
    }

    @Test
    @DisplayName("Should deposit funds and increase balance")
    void shouldDepositFundsAndIncreaseBalance() {
        // Arrange
        EmeraldAccount emeraldAccount = new EmeraldAccount();
        emeraldAccount.setBalance(new BigDecimal("100.00"));
        when(emeraldAccountRepository.findAccount()).thenReturn(Optional.of(emeraldAccount));

        // Act
        emeraldAccountService.depositFunds(new BigDecimal("45.00"));

        // Assert
        assertThat(emeraldAccount.getBalance()).isEqualByComparingTo("145.00");
    }

}