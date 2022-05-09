package com.bankaccount.application.resource;

import com.bankaccount.domain.model.Customer;
import com.bankaccount.domain.usecases.customer.CustomerFinder;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Customer resource")
public class CustomerResourceTest {

    public static final Long CUSTOMER_IDENTIFIER = 130876L;

    private MockMvc mockMvc;

    @Mock
    private CustomerFinder customerFinder;

    @InjectMocks
    private CustomerResource customerResource;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(customerResource, "mapperFacade", new DefaultMapperFactory.Builder().build().getMapperFacade());
        mockMvc = MockMvcBuilders.standaloneSetup(customerResource).build();
    }

    @Test
    public void should_retrieve_operations_for_a_bank_account() throws Exception {
        Customer customer = Customer.builder().identifier(CUSTOMER_IDENTIFIER).firstName("John").lastName("Joe").build();
        when(customerFinder.findCustomer(CUSTOMER_IDENTIFIER)).thenReturn(customer);

        mockMvc.perform(get("/customer/{identifier}", CUSTOMER_IDENTIFIER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.identifier").value(CUSTOMER_IDENTIFIER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Joe"));

        verify(customerFinder).findCustomer(CUSTOMER_IDENTIFIER);
    }

}

