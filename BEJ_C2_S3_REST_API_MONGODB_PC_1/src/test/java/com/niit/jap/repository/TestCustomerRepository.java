package com.niit.jap.repository;
import com.niit.jap.Domain.Customer;
import com.niit.jap.Domain.Product;
import com.niit.jap.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CustomerRepositoryTest {


    @Autowired
    private CustomerRepository customerRepository;
    private Product product;
    private Customer customer;

    @BeforeEach
    void setUp() {
        product = new Product(12, "California cloth ", "best one");
        customer = new Customer(1001, "Jonny", 38493483, product);
    }
    @AfterEach
    void tearDown(){
        product =null;
        customer =null;
        customerRepository.deleteAll();
    }
    @Test
    @DisplayName("Test case for saving customer object")
    void saveCustomerData(){
        customerRepository.save(customer);
        Customer customer1 = customerRepository.findById(customer.getCustomerId()).get();
        assertNotNull(customer1);
        assertEquals(customer.getCustomerId(),customer1.getCustomerId());
    }
    @Test
    @DisplayName("test case for fetching all details about customer")
    void fetchingAllDetailsOfCustomer(){
        customerRepository.insert(customer);
       Product product =new Product(121,"shirt","good Quality");
       Customer customer1 = new Customer(101,"kunal",8673288212l,product);
       customerRepository.insert(customer1);
        List<Customer> list = customerRepository.findAll();
        assertEquals(2, list.size());
        assertEquals("kunal", list.get(1).getCustomerName());

    }
    @Test
    @DisplayName("test case for deleting customer")
    void deleteCustomerById(){
        customerRepository.insert(customer);
        Customer customer1 = customerRepository.findById(customer.getCustomerId()).get();
        customerRepository.delete(customer1);
        assertEquals(Optional.empty(), customerRepository.findById(customer.getCustomerId()));


    }
}
