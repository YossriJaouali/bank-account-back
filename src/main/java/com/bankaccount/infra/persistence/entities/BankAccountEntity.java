package com.bankaccount.infra.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccountEntity {
    @Id
    private Long accountNumber;
    private double balance;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccount")
    private List<OperationEntity> operations;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
