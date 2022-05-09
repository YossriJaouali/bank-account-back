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
            BankAccountEntity bankAccountEntity = BankAccountEntity.builder().accountNumber(123456789L).balance(1_000).customer(customerEntity).build();
            final BankAccountEntity bankAccount = bankAccountRepository.save(bankAccountEntity);
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
        };
    }

}
