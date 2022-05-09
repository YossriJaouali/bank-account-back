package com.bankaccount.domain.usecases.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Bank Account service")
public class FindCustomerTest {

    public static final Long CUSTOMER_IDENTIFIER = 130876L;

    @Mock
    private CustomerPort customerPort;

    @InjectMocks
    private FindCustomer findCustomer;

    @Test
    public void should_find_customer_by_its_identifier() {
        findCustomer.findCustomer(CUSTOMER_IDENTIFIER);

        verify(customerPort).findCustomer(CUSTOMER_IDENTIFIER);
    }
}
