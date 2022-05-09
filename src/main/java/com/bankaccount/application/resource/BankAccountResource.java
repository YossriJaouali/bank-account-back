package com.bankaccount.application.resource;

import com.bankaccount.application.dto.BankAccountDto;
import com.bankaccount.application.dto.OperationCommandDto;
import com.bankaccount.domain.model.OperationCommand;
import com.bankaccount.domain.usecases.bankaccount.BankAccountUpdate;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank-account")
public class BankAccountResource {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private BankAccountUpdate bankAccountUpdate;

    @PutMapping("/{accountNumber}/deposit")
    public BankAccountDto deposit(@PathVariable Long accountNumber, @RequestBody OperationCommandDto operationCommandDto) {
        return mapperFacade.map(bankAccountUpdate.deposit(accountNumber,
                mapperFacade.map(operationCommandDto, OperationCommand.class)), BankAccountDto.class);
    }

    @PutMapping("/{accountNumber}/withdraw")
    public BankAccountDto withdraw(@PathVariable Long accountNumber, @RequestBody OperationCommandDto operationCommandDto) {
        return mapperFacade.map(bankAccountUpdate.withdraw(accountNumber,
                mapperFacade.map(operationCommandDto, OperationCommand.class)), BankAccountDto.class);
    }

}
