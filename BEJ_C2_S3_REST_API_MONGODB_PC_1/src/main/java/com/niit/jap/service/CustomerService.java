package com.niit.jap.service;

import com.niit.jap.Domain.Customer;
import com.niit.jap.exception.CustomerAlreadyExistsException;
import com.niit.jap.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer) throws CustomerAlreadyExistsException;
    List<Customer>getAllCustomerData()throws Exception;
    boolean deleteCustomer(int customerId)throws CustomerNotFoundException;
    List<Customer>getAllCustomerByProductName(String productName) throws CustomerNotFoundException;
}
