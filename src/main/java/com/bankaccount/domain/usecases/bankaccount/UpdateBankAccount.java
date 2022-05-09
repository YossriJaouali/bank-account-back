package com.bankaccount.domain.usecases.bankaccount;

import com.bankaccount.domain.exception.BankAccountNotFoundException;
import com.bankaccount.domain.exception.InsufficientBalanceException;
import com.bankaccount.domain.model.BankAccount;
import com.bankaccount.domain.model.OperationCommand;
import com.bankaccount.domain.model.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
public class UpdateBankAccount implements BankAccountUpdate {

    private BankAccountPort bankAccountPort;

    public UpdateBankAccount(BankAccountPort bankAccountPort) {
        this.bankAccountPort = bankAccountPort;
    }

    @Override
    public synchronized BankAccount withdraw(Long accountNumberToWithdraw, OperationCommand operationCommand) {
        log.info("Withdraw {} for the bank account {}", operationCommand.getAmount(), accountNumberToWithdraw);
        Assert.isTrue(operationCommand.getAmount() > 0, "The amount should be greater than 0");

        BankAccount bankAccount = bankAccountPort.findBankAccount(accountNumberToWithdraw);
        if (bankAccount == null) {
            throw new BankAccountNotFoundException("The bank account is not found");
        } else if (bankAccount.getBalance() < operationCommand.getAmount()) {
            throw new InsufficientBalanceException("The amount should not be greater than the balance");
        }
        bankAccount.withdraw(operationCommand.getAmount());
        operationCommand.setOperationType(OperationType.WITHDRAW);
        return bankAccountPort.updateBankAccount(bankAccount, operationCommand);
    }

    @Override
    public synchronized BankAccount deposit(Long accountNumberToDeposit, OperationCommand operationCommand) {
        log.info("Deposit {} for the bank account {}", operationCommand.getAmount(), accountNumberToDeposit);
        Assert.isTrue(operationCommand.getAmount() > 0, "The amount should be greater than 0");

        BankAccount bankAccount = bankAccountPort.findBankAccount(accountNumberToDeposit);
        bankAccount.deposit(operationCommand.getAmount());
        operationCommand.setOperationType(OperationType.DEPOSIT);
        return bankAccountPort.updateBankAccount(bankAccount, operationCommand);
    }
}
