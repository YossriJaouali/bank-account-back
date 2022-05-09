package com.bankaccount.domain.usecases.operation;

import com.bankaccount.domain.model.Operation;
import com.bankaccount.domain.model.Page;

public interface OperationPort {
    Page<Operation> findAllOperations(Long accountNumber, int pageIndex, int pageSize);
}
