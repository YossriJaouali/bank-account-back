package com.bankaccount.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {
    private Long accountNumber;
    private double balance;

    public void withdraw(double amtToWithdraw) {
        balance = BigDecimal.valueOf(balance).subtract(BigDecimal.valueOf(amtToWithdraw)).doubleValue();
    }

    public void deposit(double amtToDeposit) {
        balance = BigDecimal.valueOf(balance).add(BigDecimal.valueOf(amtToDeposit)).doubleValue();
    }
}
