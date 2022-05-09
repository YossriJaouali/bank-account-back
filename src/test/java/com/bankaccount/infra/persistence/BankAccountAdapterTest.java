package com.bankaccount.infra.persistence;

import com.bankaccount.domain.model.BankAccount;
import com.bankaccount.domain.model.OperationCommand;
import com.bankaccount.domain.model.OperationType;
import com.bankaccount.domain.usecases.bankaccount.BankAccountPort;
import com.bankaccount.infra.persistence.entities.BankAccountEntity;
import com.bankaccount.infra.persistence.entities.CustomerEntity;
import com.bankaccount.infra.persistence.repository.BankAccountRepository;
import com.bankaccount.infra.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan(basePackages = {"com.bankaccount"})
@DisplayName("Test the persistence operations of the bank account")
public class BankAccountAdapterTest {

    public static final Long BANK_ACCOUNT_NUMBER = 130876L;
    public static final Long CUSTOMER_IDENTIFIER = 9876512L;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountPort banAccountPort;


    @BeforeEach
    public void setUp() {
        prepareData();
    }

    @Test
    public void should_find_bank_account_by_its_number() {
        BankAccount persistedBankAccount = banAccountPort.findBankAccount(BANK_ACCOUNT_NUMBER);

        assertEquals(BANK_ACCOUNT_NUMBER, persistedBankAccount.getAccountNumber());
        assertEquals(1_600, persistedBankAccount.getBalance());
    }

    @Test
    public void should_update_bank_account() {
        BankAccount bankAccountToUpdate = BankAccount.builder().accountNumber(BANK_ACCOUNT_NUMBER).balance(1_500).build();
        OperationCommand withdrawOperationCommand = OperationCommand.builder().amount(100L)
                .description("Withdraw of 100 EUR").operationType(OperationType.WITHDRAW).build();

        BankAccount updatedBankAccount = banAccountPort.updateBankAccount(bankAccountToUpdate, withdrawOperationCommand);

        assertEquals(BANK_ACCOUNT_NUMBER, updatedBankAccount.getAccountNumber());
        assertEquals(1_500, updatedBankAccount.getBalance());
    }

    private void prepareData() {
        CustomerEntity customerEntity = customerRepository.save(CustomerEntity.builder().identifier(CUSTOMER_IDENTIFIER)
                .firstName("John").lastName("Joe").build());
        BankAccountEntity bankAccountEntity = BankAccountEntity.builder().accountNumber(BANK_ACCOUNT_NUMBER).balance(1_600).customer(customerEntity).build();
        bankAccountRepository.save(bankAccountEntity);
    }
}

