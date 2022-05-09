package com.bankaccount.application.resource;

import com.bankaccount.application.dto.OperationCommandDto;
import com.bankaccount.domain.model.BankAccount;
import com.bankaccount.domain.model.OperationCommand;
import com.bankaccount.domain.usecases.bankaccount.BankAccountUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Bank Account resource")
public class BankAccountResourceTest {

    public static final Long BANK_ACCOUNT_NUMBER = 123456789L;

    private OperationCommandDto depositOperationCommandDto;
    private OperationCommandDto withdrawOperationCommandDto;
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Mock
    private BankAccountUpdate bankAccountUpdate;

    @InjectMocks
    private BankAccountResource bankAccountResource;

    @Captor
    private ArgumentCaptor<OperationCommand> operationCommandArgumentCaptor;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(bankAccountResource, "mapperFacade", new DefaultMapperFactory.Builder().build().getMapperFacade());
        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountResource).build();
        objectMapper = new ObjectMapper();
        depositOperationCommandDto = OperationCommandDto.builder().amount(100L).description("Deposit of 100 EUR").build();
        withdrawOperationCommandDto = OperationCommandDto.builder().amount(100L).description("Withdraw of 100 EUR").build();
    }

    @Test
    public void should_deposit_a_bank_account() throws Exception {
        when(bankAccountUpdate.deposit(any(), any())).thenReturn(BankAccount.builder().accountNumber(BANK_ACCOUNT_NUMBER).build());

        mockMvc.perform(put("/bank-account/{accountNumber}/deposit", BANK_ACCOUNT_NUMBER)
                .content(objectMapper.writeValueAsString(depositOperationCommandDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(BANK_ACCOUNT_NUMBER));

        Mockito.verify(bankAccountUpdate, Mockito.times(1)).deposit(eq(BANK_ACCOUNT_NUMBER), operationCommandArgumentCaptor.capture());
        Assertions.assertThat(operationCommandArgumentCaptor.getValue()).isNotNull();
        Assertions.assertThat(operationCommandArgumentCaptor.getValue().getAmount()).isEqualTo(100L);
        Assertions.assertThat(operationCommandArgumentCaptor.getValue().getDescription()).isEqualTo("Deposit of 100 EUR");
    }

    @Test
    public void should_withdraw_a_bank_account() throws Exception {
        when(bankAccountUpdate.withdraw(any(), any())).thenReturn(BankAccount.builder().accountNumber(BANK_ACCOUNT_NUMBER).build());

        mockMvc.perform(put("/bank-account/{accountNumber}/withdraw", BANK_ACCOUNT_NUMBER)
                .content(objectMapper.writeValueAsString(withdrawOperationCommandDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(BANK_ACCOUNT_NUMBER));

        verify(bankAccountUpdate, Mockito.times(1)).withdraw(eq(BANK_ACCOUNT_NUMBER), operationCommandArgumentCaptor.capture());
        Assertions.assertThat(operationCommandArgumentCaptor.getValue()).isNotNull();
        Assertions.assertThat(operationCommandArgumentCaptor.getValue().getAmount()).isEqualTo(100L);
        Assertions.assertThat(operationCommandArgumentCaptor.getValue().getDescription()).isEqualTo("Withdraw of 100 EUR");
    }
}
