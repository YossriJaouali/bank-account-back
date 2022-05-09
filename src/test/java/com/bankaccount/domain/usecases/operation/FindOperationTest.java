package com.bankaccount.domain.usecases.operation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Operation service")
public class FindOperationTest {

    public static final Long BANK_ACCOUNT_NUMBER = 130876L;

    @Mock
    private OperationPort operationPort;

    @InjectMocks
    private FindOperation findOperation;

    @Test
    public void should_find_customer_by_its_identifier() {
        findOperation.findAllOperations(BANK_ACCOUNT_NUMBER,0,5);

        verify(operationPort).findAllOperations(BANK_ACCOUNT_NUMBER,0,5);
    }
}
