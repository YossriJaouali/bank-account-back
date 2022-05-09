package com.bankaccount.domain.usecases.operation;

import com.bankaccount.domain.model.Operation;
import com.bankaccount.domain.model.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FindOperation implements OperationFinder {

    private OperationPort operationPort;

    public FindOperation(OperationPort operationPort) {
        this.operationPort = operationPort;
    }

    @Override
    public Page<Operation> findAllOperations(Long accountNumber, int pageIndex, int pageSize) {
        log.info("Retrieve the operations of the account : {}", accountNumber);
        return operationPort.findAllOperations(accountNumber, pageIndex, pageSize);
    }
}
