package com.bankaccount.domain.usecases.bankaccount;

import com.bankaccount.domain.model.BankAccount;
import com.bankaccount.domain.model.OperationCommand;

public interface BankAccountPort {
    BankAccount findBankAccount(Long bankAccountNumber);

    BankAccount updateBankAccount(BankAccount bankAccount, OperationCommand operationCommand);
}
