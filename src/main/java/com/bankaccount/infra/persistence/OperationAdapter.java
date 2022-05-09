package com.bankaccount.infra.persistence;

import com.bankaccount.domain.model.Operation;
import com.bankaccount.domain.model.Page;
import com.bankaccount.domain.usecases.operation.OperationPort;
import com.bankaccount.infra.persistence.entities.OperationEntity;
import com.bankaccount.infra.persistence.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OperationAdapter implements OperationPort {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Page<Operation> findAllOperations(Long accountNumber, int pageIndex, int pageSize) {
        log.info("Retrieve the paginated operations of the account : {}", accountNumber);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        org.springframework.data.domain.Page<OperationEntity> operationEntityPage = operationRepository
                .findAllByBankAccountAccountNumberOrderByCreationDateDesc(accountNumber, pageable);
        Page<Operation> operationPage = new Page<>();
        operationPage.setContent(mapperFacade.mapAsList(operationEntityPage.getContent(), Operation.class));
        operationPage.setTotal(operationEntityPage.getTotalElements());
        operationPage.setNumberOfPages(operationEntityPage.getTotalPages());
        operationPage.setPage(operationEntityPage.getNumber());
        operationPage.setPageSize(operationEntityPage.getSize());
        return operationPage;
    }
}
