package com.bankaccount.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationCommandDto {
    private double amount;
    private String description;
}
