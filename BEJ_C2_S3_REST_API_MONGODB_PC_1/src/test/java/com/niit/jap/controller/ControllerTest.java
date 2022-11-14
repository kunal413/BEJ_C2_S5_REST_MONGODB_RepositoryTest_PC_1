package com.niit.jap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.jap.Domain.Customer;
import com.niit.jap.Domain.Product;
import com.niit.jap.exception.CustomerAlreadyExistsException;
import com.niit.jap.service.CustomerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    List<Customer> customerList;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CustomerServiceImpl customerService;
    @InjectMocks
    private CustomerController customerController;
    private Customer customer1, customer2;
    private Product product1, product2;

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }

    @BeforeEach
    void setUp() {
        product1 = new Product(101, "kunalproduction", "is best one");
        customer1 = new Customer(1001, "Johny", 544353, product1);
        product2 = new Product(102, "laptop", "good");
        customer2 = new Customer(1002, "Harry", 564565, product2);
        customerList = Arrays.asList(customer1, customer2);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @AfterEach
    void tearDown() {
        customer1 = null;
        customer2 = null;
    }

    @Test
    public void givenCustomerToSaveReturnSavedCustomer() throws Exception {
        when(customerService.saveCustomer(any())).thenReturn(customer1);
        mockMvc.perform(post("/customerdata/api/customer").contentType(MediaType.APPLICATION_JSON).content(jsonToString(customer1))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(customerService, times(1)).saveCustomer(any());

    }

    @Test
    public void givenCustomerToSaveReturnSavedCustomerFailure() throws Exception {
        when(customerService.saveCustomer(any())).thenThrow(CustomerAlreadyExistsException.class);
        mockMvc.perform(post("/customerdata/api/customer").contentType(MediaType.APPLICATION_JSON).content(jsonToString(customer1))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(customerService, times(1)).saveCustomer(any());

    }

    @Test
    public void givenCustomerIdDeleteCustomer() throws Exception {
        when(customerService.deleteCustomer(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/customerdata/api/customer/1001").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(customerService, times(1)).deleteCustomer(anyInt());

    }

}
