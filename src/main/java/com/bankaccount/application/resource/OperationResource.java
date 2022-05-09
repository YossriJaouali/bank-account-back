package com.bankaccount.application.resource;

import com.bankaccount.application.dto.OperationDto;
import com.bankaccount.domain.model.Operation;
import com.bankaccount.domain.model.Page;
import com.bankaccount.domain.usecases.operation.OperationFinder;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank-account")
public class OperationResource {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private OperationFinder operationFinder;

    @GetMapping("/{accountNumber}/operations")
    public Page<OperationDto> findAllOperations(@PathVariable Long accountNumber, @RequestParam int pageIndex, @RequestParam int pageSize) {
        return mapperFacade.map(operationFinder.findAllOperations(accountNumber, pageIndex, pageSize),
                new TypeBuilder<Page<Operation>>() {}.build(), new TypeBuilder<Page<OperationDto>>() {}.build());
    }

}

