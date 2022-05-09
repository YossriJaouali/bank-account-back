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
@Table(name = "CUSTOMER")
public class CustomerEntity {
    @Id
    private Long identifier;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<BankAccountEntity> accounts;
}
