package com.bankaccount.infra.persistence;

import com.bankaccount.domain.model.Operation;
import com.bankaccount.domain.model.OperationType;
import com.bankaccount.domain.model.Page;
import com.bankaccount.domain.usecases.operation.OperationPort;
import com.bankaccount.infra.persistence.entities.BankAccountEntity;
import com.bankaccount.infra.persistence.entities.CustomerEntity;
import com.bankaccount.infra.persistence.entities.OperationEntity;
import com.bankaccount.infra.persistence.repository.BankAccountRepository;
import com.bankaccount.infra.persistence.repository.CustomerRepository;
import com.bankaccount.infra.persistence.repository.OperationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.OffsetDateTime;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ComponentScan(basePackages = {"com.bankaccount"})
@DisplayName("Test the persistence operations of the history of operations")
public class OperationAdapterTest {

    public static final Long BANK_ACCOUNT_NUMBER = 130876L;
    public static final Long CUSTOMER_IDENTIFIER = 987655L;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationPort operationPort;

    @Test
    public void should_find_paginated_operations() {
        CustomerEntity customerEntity = customerRepository.save(CustomerEntity.builder().identifier(CUSTOMER_IDENTIFIER).firstName("Yossri").lastName("JAOUALI").build());
        BankAccountEntity bankAccountEntity = BankAccountEntity.builder().accountNumber(BANK_ACCOUNT_NUMBER).balance(1_000).customer(customerEntity).build();
        BankAccountEntity bankAccount = bankAccountRepository.save(bankAccountEntity);
        IntStream.range(0, 30).forEach(i -> {
            OperationEntity operationEntity;
            if (i % 2 == 0) {
                operationEntity = OperationEntity.builder().bankAccount(bankAccount).description("Deposit of money")
                        .amount(23393.98).operationType(OperationType.DEPOSIT).creationDate(OffsetDateTime.now()).build();
            } else {
                operationEntity = OperationEntity.builder().bankAccount(bankAccount).description("Withdraw of money")
                        .amount(5598.87).operationType(OperationType.WITHDRAW).creationDate(OffsetDateTime.now()).build();
            }
            operationRepository.save(operationEntity);
        });

        Page<Operation> operationPage = operationPort.findAllOperations(BANK_ACCOUNT_NUMBER, 0, 5);

        assertNotNull(operationPage);
        assertEquals(30, operationPage.getTotal());
        assertEquals(6, operationPage.getNumberOfPages());
        assertEquals(5, operationPage.getPageSize());
        assertEquals(0, operationPage.getPage());
        Assertions.assertThat(operationPage.getContent()).hasSize(5);
    }
}
