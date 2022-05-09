package com.bankaccount.domain.usecases.customer;

import com.bankaccount.domain.model.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FindCustomer implements CustomerFinder {

    private CustomerPort customerPort;

    public FindCustomer(CustomerPort customerPort) {
        this.customerPort = customerPort;
    }

    @Override
    public Customer findCustomer(Long identifier) {
        log.info("Retrieve the customer which its identifier is : {}", identifier);
        return customerPort.findCustomer(identifier);
    }
}
