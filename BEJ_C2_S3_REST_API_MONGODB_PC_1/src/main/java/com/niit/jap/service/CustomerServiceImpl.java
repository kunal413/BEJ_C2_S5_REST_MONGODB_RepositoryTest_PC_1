package com.niit.jap.service;

import com.niit.jap.Domain.Customer;
import com.niit.jap.exception.CustomerNotFoundException;
import com.niit.jap.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService{
     private CustomerRepository customerRepository;
     public CustomerServiceImpl(CustomerRepository customerRepository){
         this.customerRepository=customerRepository;
     }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomerData() throws Exception {
        return customerRepository.findAll();
    }

    @Override
    public boolean deleteCustomer(int customerId) throws ClassNotFoundException {
         if (customerRepository.findById(customerId).isEmpty()){
             throw new ClassNotFoundException();
         }
         else {
             customerRepository.deleteById(customerId);
             return true;
         }


    }

    @Override
    public List<Customer> getAllCustomerByProductName(String productName) throws CustomerNotFoundException {
         if (customerRepository.findAllCustomerFromProductName(productName).isEmpty()){
             throw new CustomerNotFoundException();
         }

        return customerRepository.findAllCustomerFromProductName(productName);
    }
}
