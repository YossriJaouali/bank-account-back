package com.bankaccount.infra.persistence.repository;

import com.bankaccount.infra.persistence.entities.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    Page<OperationEntity> findAllByBankAccountAccountNumberOrderByCreationDateDesc(Long accountNumber, Pageable pageable);
}
