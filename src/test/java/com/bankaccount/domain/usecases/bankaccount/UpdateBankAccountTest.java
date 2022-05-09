package com.bankaccount.domain.usecases.bankaccount;

import com.bankaccount.domain.exception.BankAccountNotFoundException;
import com.bankaccount.domain.exception.InsufficientBalanceException;
import com.bankaccount.domain.model.BankAccount;
import com.bankaccount.domain.model.OperationCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Bank Account service")
public class UpdateBankAccountTest {

    public static final Long BANK_ACCOUNT_NUMBER = 130876L;

    private BankAccount bankAccount;

    @Mock
    private BankAccountPort bankAccountPort;

    @InjectMocks
    private UpdateBankAccount updateBankAccount;

    @BeforeEach
    public void setUp() {
        bankAccount = BankAccount.builder().accountNumber(1234L).balance(100.0).build();
    }

    @Test
    public void should_withdraw_bank_account() {
        OperationCommand operationCommand = OperationCommand.builder().amount(30.5).description("Withdraw of 30.5 EUR").build();
        when(bankAccountPort.findBankAccount(BANK_ACCOUNT_NUMBER)).thenReturn(bankAccount);

        updateBankAccount.withdraw(BANK_ACCOUNT_NUMBER, operationCommand);

        Assertions.assertThat(bankAccount.getBalance()).isEqualTo(69.5);
    }

    @Test
    public void should_throw_an_exception_when_the_amount_to_withdraw_is_equal_to_0() {
        OperationCommand operationCommand = OperationCommand.builder().amount(0).description("Withdraw of 0 EUR").build();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> updateBankAccount.withdraw(BANK_ACCOUNT_NUMBER, operationCommand));

        assertEquals("The amount should be greater than 0", exception.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_the_bank_account_is_not_found() {
        OperationCommand operationCommand = OperationCommand.builder().amount(100).description("Withdraw of 100 EUR").build();
        when(bankAccountPort.findBankAccount(BANK_ACCOUNT_NUMBER)).thenReturn(null);

        Exception exception = assertThrows(BankAccountNotFoundException.class,
                () -> updateBankAccount.withdraw(BANK_ACCOUNT_NUMBER, operationCommand));

        assertEquals("The bank account is not found", exception.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_the_balance_is_insufficient() {
        OperationCommand operationCommand = OperationCommand.builder().amount(2_000).description("Withdraw of 100 EUR").build();
        when(bankAccountPort.findBankAccount(BANK_ACCOUNT_NUMBER)).thenReturn(bankAccount);

        Exception exception = assertThrows(InsufficientBalanceException.class,
                () -> updateBankAccount.withdraw(BANK_ACCOUNT_NUMBER, operationCommand));

        assertEquals("The amount should not be greater than the balance", exception.getMessage());
    }

    @Test
    public void should_deposit_bank_account() {
        OperationCommand operationCommand = OperationCommand.builder().amount(100.02).description("Deposit of 100.02 EUR").build();
        when(bankAccountPort.findBankAccount(BANK_ACCOUNT_NUMBER)).thenReturn(bankAccount);

        updateBankAccount.deposit(BANK_ACCOUNT_NUMBER, operationCommand);

        Assertions.assertThat(bankAccount.getBalance()).isEqualTo(200.02);
    }

    @Test
    public void should_update_balance_in_no_multithreading_case() {
        OperationCommand withdrawOperationCommand = OperationCommand.builder().amount(10.07).description("Withdraw of 10.07 EUR").build();
        OperationCommand depositOperationCommand = OperationCommand.builder().amount(100.98).description("Deposit of 100.98 EUR").build();
        when(bankAccountPort.findBankAccount(BANK_ACCOUNT_NUMBER)).thenReturn(bankAccount);

        IntStream.range(0, 10).forEach(i -> {
            if (i % 2 == 0) {
                updateBankAccount.deposit(BANK_ACCOUNT_NUMBER, depositOperationCommand);
            } else {
                updateBankAccount.withdraw(BANK_ACCOUNT_NUMBER, withdrawOperationCommand);
            }
        });

        Assertions.assertThat(bankAccount.getBalance()).isEqualTo(554.55);
    }

    @Test
    public void should_update_balance_in_multithreading_case() throws InterruptedException {
        int numberOfThreads = 10;
        OperationCommand withdrawOperationCommand = OperationCommand.builder().amount(10.07).description("Withdraw of 10.07 EUR").build();
        OperationCommand depositOperationCommand = OperationCommand.builder().amount(100.98).description("Deposit of 100.98 EUR").build();
        when(bankAccountPort.findBankAccount(BANK_ACCOUNT_NUMBER)).thenReturn(bankAccount);
        ExecutorService exec = Executors.newFixedThreadPool(10);

        IntStream.range(0, numberOfThreads).forEach(i -> exec.execute(() -> {
            if (i % 2 == 0) {
                updateBankAccount.deposit(BANK_ACCOUNT_NUMBER, depositOperationCommand);
            } else {
                updateBankAccount.withdraw(BANK_ACCOUNT_NUMBER, withdrawOperationCommand);
            }
        }));
        exec.shutdown();
        exec.awaitTermination(50, TimeUnit.SECONDS);

        Assertions.assertThat(bankAccount.getBalance()).isEqualTo(554.55);
    }

}
