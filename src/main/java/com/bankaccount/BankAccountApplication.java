package com.bankaccount;

import com.bankaccount.domain.model.OperationType;
import com.bankaccount.infra.persistence.entities.BankAccountEntity;
import com.bankaccount.infra.persistence.entities.CustomerEntity;
import com.bankaccount.infra.persistence.entities.OperationEntity;
import com.bankaccount.infra.persistence.repository.BankAccountRepository;
import com.bankaccount.infra.persistence.repository.CustomerRepository;
import com.bankaccount.infra.persistence.repository.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.OffsetDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class BankAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }


    @Bean
    public CommandLineRunner initialize(BankAccountRepository bankAccountRepository,
                                        OperationRepository operationRepository,
                                        CustomerRepository customerRepository) {
        return args -> {
            CustomerEntity customerEntity = customerRepository.save(CustomerEntity.builder().identifier(123456789L).firstName("Yossri").lastName("JAOUALI").build());
            BankAccountEntity bankAccountEntity1 = BankAccountEntity.builder().accountNumber(123456789L).balance(1_000).customer(customerEntity).build();
            final BankAccountEntity bankAccount1 = bankAccountRepository.save(bankAccountEntity1);
            IntStream.range(0, 30).forEach(i -> {
                OperationEntity operationEntity;
                if (i % 2 == 0) {
                    operationEntity = OperationEntity.builder().bankAccount(bankAccount1).description("Deposit of money")
                            .amount(98763.87+ i * 20).operationType(OperationType.DEPOSIT).creationDate(OffsetDateTime.now()).build();
                } else {
                    operationEntity = OperationEntity.builder().bankAccount(bankAccount1).description("Withdraw of money")
                            .amount(349876+ i * 20).operationType(OperationType.WITHDRAW).creationDate(OffsetDateTime.now()).build();
                }
                operationRepository.save(operationEntity);
            });

            BankAccountEntity bankAccountEntity2 = BankAccountEntity.builder().accountNumber(123768789L).balance(3_000_000).customer(customerEntity).build();
            final BankAccountEntity bankAccount2 = bankAccountRepository.save(bankAccountEntity2);
            IntStream.range(0, 10).forEach(i -> {
                OperationEntity operationEntity;
                if (i % 2 == 0) {
                    operationEntity = OperationEntity.builder().bankAccount(bankAccount2).description("Deposit of money")
                            .amount(12567.98 + i * 20).operationType(OperationType.DEPOSIT).creationDate(OffsetDateTime.now()).build();
                } else {
                    operationEntity = OperationEntity.builder().bankAccount(bankAccount2).description("Withdraw of money")
                            .amount(5598.87 + i * 20).operationType(OperationType.WITHDRAW).creationDate(OffsetDateTime.now()).build();
                }
                operationRepository.save(operationEntity);
            });

            BankAccountEntity bankAccountEntity3 = BankAccountEntity.builder().accountNumber(398456789L).balance(1_000).customer(customerEntity).build();
            final BankAccountEntity bankAccount3 = bankAccountRepository.save(bankAccountEntity3);
            IntStream.range(0, 15).forEach(i -> {
                OperationEntity operationEntity;
                if (i % 2 == 0) {
                    operationEntity = OperationEntity.builder().bankAccount(bankAccount3).description("Deposit of money")
                            .amount(8765.87 + i * 20).operationType(OperationType.DEPOSIT).creationDate(OffsetDateTime.now()).build();
                } else {
                    operationEntity = OperationEntity.builder().bankAccount(bankAccount3).description("Withdraw of money")
                            .amount(23478 + i * 20).operationType(OperationType.WITHDRAW).creationDate(OffsetDateTime.now()).build();
                }
                operationRepository.save(operationEntity);
            });
        };
    }

}
