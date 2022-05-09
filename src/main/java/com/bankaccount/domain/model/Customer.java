package com.bankaccount.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private Long identifier;
    private String firstName;
    private String lastName;
    private String email;
    private List<BankAccount> accounts;
}
