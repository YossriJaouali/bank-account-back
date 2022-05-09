package com.bankaccount.application.resource;

import com.bankaccount.domain.model.Operation;
import com.bankaccount.domain.model.Page;
import com.bankaccount.domain.usecases.operation.OperationFinder;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Operation resource")
public class OperationResourceTest {

    public static final Long BANK_ACCOUNT_NUMBER = 130876L;

    private MockMvc mockMvc;

    @Mock
    private OperationFinder operationFinder;

    @InjectMocks
    private OperationResource operationResource;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(operationResource, "mapperFacade", new DefaultMapperFactory.Builder().build().getMapperFacade());
        mockMvc = MockMvcBuilders.standaloneSetup(operationResource).build();
    }

    @Test
    public void should_retrieve_operations_for_a_bank_account() throws Exception {
        Page<Operation> operationPage = new Page<>();
        operationPage.setPageSize(5);
        operationPage.setPage(0);
        operationPage.setContent(Stream.of(Operation.builder().amount(344).build(),
                Operation.builder().amount(123).build()).collect(Collectors.toList()));
        when(operationFinder.findAllOperations(BANK_ACCOUNT_NUMBER, 0, 5)).thenReturn(operationPage);

        mockMvc.perform(get("/bank-account/{accountNumber}/operations?pageIndex=0&pageSize=5", BANK_ACCOUNT_NUMBER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());

        verify(operationFinder).findAllOperations(BANK_ACCOUNT_NUMBER, 0, 5);
    }

}

