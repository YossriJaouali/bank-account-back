package com.bankaccount.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDto {
    private Long accountNumber;
    private double balance;
    private String customerName;
}
