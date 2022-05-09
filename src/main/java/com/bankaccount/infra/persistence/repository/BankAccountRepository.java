package com.bankaccount.infra.persistence.repository;

import com.bankaccount.infra.persistence.entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    @Modifying
    @Query("update BankAccountEntity ba set ba.balance =:balance where ba.accountNumber=:accountNumber")
    void update(@Param("accountNumber") Long accountNumber, @Param("balance") double balance);
}
