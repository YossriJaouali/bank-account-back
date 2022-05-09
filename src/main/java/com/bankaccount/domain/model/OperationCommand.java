package com.bankaccount.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationCommand {
    private double amount;
    private String description;
    private OperationType operationType;
}
