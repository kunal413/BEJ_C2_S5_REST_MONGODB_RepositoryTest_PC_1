package com.niit.jap.controller;

import com.niit.jap.Domain.Customer;
import com.niit.jap.exception.CustomerNotFoundException;
import com.niit.jap.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customerdata/api/")
public class CustomerController {
    private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping("/customer")
    public ResponseEntity<?> insertCustomer(@RequestBody Customer customer){
        Customer customer1 = customerService.saveCustomer(customer);
        return new ResponseEntity<>(customer1, HttpStatus.CREATED);
    }
    @GetMapping("customer")
    public ResponseEntity<?> fetchAllCustomer(){
        ResponseEntity responseEntity=null;
        try {
            responseEntity =new ResponseEntity<>(customerService.getAllCustomerData(),HttpStatus.OK);
        } catch (Exception e) {
           responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @DeleteMapping("customer/{customerId}")
    public ResponseEntity<?>deleteSingleCustomer(@PathVariable("customerId") int customerId)throws CustomerNotFoundException{
        ResponseEntity responseEntity =null;
        try {
            customerService.deleteCustomer(customerId);
            responseEntity = new ResponseEntity("Successfully deleted the 1 record",HttpStatus.OK);
        }
        catch (ClassNotFoundException cnfe){
            throw  new CustomerNotFoundException();
        }catch (Exception exception){
            responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("customers/{productName}")
    public ResponseEntity<?> fetchByCustomerProductName(@PathVariable String productName){
        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity<>(customerService.getAllCustomerByProductName(productName),HttpStatus.FOUND);
        } catch (Exception e) {
           responseEntity = new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  responseEntity;
    }

}
