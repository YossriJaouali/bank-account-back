package com.bankaccount.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long identifier;
    private String firstName;
    private String lastName;
    private String email;
    private List<BankAccountDto> accounts;
}
