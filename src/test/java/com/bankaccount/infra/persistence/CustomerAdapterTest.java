package com.bankaccount.infra.persistence;

import com.bankaccount.domain.model.Customer;
import com.bankaccount.domain.usecases.customer.CustomerPort;
import com.bankaccount.infra.persistence.entities.CustomerEntity;
import com.bankaccount.infra.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@ComponentScan(basePackages = {"com.bankaccount"})
@DisplayName("Test the persistence operations of the customer")
public class CustomerAdapterTest {

    public static final Long CUSTOMER_IDENTIFIER = 9876512L;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerPort customerPort;

    @Test
    public void should_find_customer_by_identifier() {
        customerRepository.save(CustomerEntity.builder().identifier(CUSTOMER_IDENTIFIER)
                .firstName("John").lastName("Joe").build());

        Customer customer = customerPort.findCustomer(CUSTOMER_IDENTIFIER);

        assertNotNull(customer);
        assertEquals(CUSTOMER_IDENTIFIER, customer.getIdentifier());
        assertEquals("John", customer.getFirstName());
        assertEquals("Joe", customer.getLastName());
    }
}
