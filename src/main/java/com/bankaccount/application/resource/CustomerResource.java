package com.bankaccount.application.resource;

import com.bankaccount.application.dto.CustomerDto;
import com.bankaccount.domain.usecases.customer.CustomerFinder;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerResource {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CustomerFinder customerFinder;

    @GetMapping("/{identifier}")
    public CustomerDto findCustomer(@PathVariable Long identifier) {
        return mapperFacade.map(customerFinder.findCustomer(identifier), CustomerDto.class);
    }

}

