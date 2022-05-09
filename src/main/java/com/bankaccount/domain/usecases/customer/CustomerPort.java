package com.bankaccount.domain.usecases.customer;

import com.bankaccount.domain.model.Customer;

public interface CustomerPort {
    Customer findCustomer(Long identifier);
}
