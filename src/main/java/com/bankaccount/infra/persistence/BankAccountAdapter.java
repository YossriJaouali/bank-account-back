package com.bankaccount.infra.persistence;

import com.bankaccount.domain.model.BankAccount;
import com.bankaccount.domain.model.OperationCommand;
import com.bankaccount.domain.usecases.bankaccount.BankAccountPort;
import com.bankaccount.infra.persistence.entities.BankAccountEntity;
import com.bankaccount.infra.persistence.entities.OperationEntity;
import com.bankaccount.infra.persistence.repository.BankAccountRepository;
import com.bankaccount.infra.persistence.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@Slf4j
public class BankAccountAdapter implements BankAccountPort {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public BankAccount findBankAccount(Long accountNumber) {
        log.info("Find a bank account which its identifier is : {} ", accountNumber);
        Optional<BankAccountEntity> bankAccountOptional = bankAccountRepository.findById(accountNumber);
        return bankAccountOptional.map(bankAccountEntity -> mapperFacade.map(bankAccountEntity, BankAccount.class)).orElse(null);
    }

    @Override
    @Transactional
    public BankAccount updateBankAccount(BankAccount bankAccount, OperationCommand operationCommand) {
        log.info("Execute an operation ({}) for the bank account : {} ", operationCommand.getOperationType().name(), bankAccount.getAccountNumber());
        bankAccountRepository.update(bankAccount.getAccountNumber(), bankAccount.getBalance());
        OperationEntity operation = OperationEntity.builder()
                .bankAccount(mapperFacade.map(bankAccount, BankAccountEntity.class))
                .amount(operationCommand.getAmount())
                .operationType(operationCommand.getOperationType())
                .description(operationCommand.getDescription())
                .creationDate(OffsetDateTime.now())
                .build();
        operationRepository.save(operation);
        return mapperFacade.map(bankAccount, BankAccount.class);
    }
}
