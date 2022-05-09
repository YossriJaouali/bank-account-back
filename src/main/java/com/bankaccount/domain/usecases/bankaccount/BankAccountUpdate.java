package com.bankaccount.domain.usecases.bankaccount;

import com.bankaccount.domain.model.BankAccount;
import com.bankaccount.domain.model.OperationCommand;

public interface BankAccountUpdate {
    BankAccount withdraw(Long accountNumberToWithdraw, OperationCommand operationCommand);

    BankAccount deposit(Long accountNumberToDeposit, OperationCommand operationCommand);
}
