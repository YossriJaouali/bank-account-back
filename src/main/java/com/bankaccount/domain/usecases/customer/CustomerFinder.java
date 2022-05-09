package com.bankaccount.domain.usecases.customer;

import com.bankaccount.domain.model.Customer;

public interface CustomerFinder {
    Customer findCustomer(Long identifier);
}
