package com.bankaccount.infra.persistence;

import com.bankaccount.domain.model.Customer;
import com.bankaccount.domain.usecases.customer.CustomerPort;
import com.bankaccount.infra.persistence.entities.CustomerEntity;
import com.bankaccount.infra.persistence.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CustomerAdapter implements CustomerPort {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findCustomer(Long identifier) {
        log.info("Retrieve the customer which its identifier is : {}", identifier);
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(identifier);
        return customerEntityOptional.map(customerEntity -> mapperFacade.map(customerEntity, Customer.class)).orElse(null);
    }
}
