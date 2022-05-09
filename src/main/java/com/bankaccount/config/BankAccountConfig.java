package com.bankaccount.config;

import com.bankaccount.domain.usecases.bankaccount.BankAccountPort;
import com.bankaccount.domain.usecases.bankaccount.BankAccountUpdate;
import com.bankaccount.domain.usecases.bankaccount.UpdateBankAccount;
import com.bankaccount.domain.usecases.customer.CustomerFinder;
import com.bankaccount.domain.usecases.customer.CustomerPort;
import com.bankaccount.domain.usecases.customer.FindCustomer;
import com.bankaccount.domain.usecases.operation.FindOperation;
import com.bankaccount.domain.usecases.operation.OperationFinder;
import com.bankaccount.domain.usecases.operation.OperationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankAccountConfig {

    @Bean
    public BankAccountUpdate bankAccountUpdate(BankAccountPort bankAccountPort) {
        return new UpdateBankAccount(bankAccountPort);
    }

    @Bean
    public OperationFinder operationFinder(OperationPort operationPort) {
        return new FindOperation(operationPort);
    }

    @Bean
    public CustomerFinder customerFinder(CustomerPort customerPort) {
        return new FindCustomer(customerPort);
    }


}
