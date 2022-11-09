package com.niit.jap.repository;

import com.niit.jap.Domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer,Integer> {
    @Query("{'product.productName':{$in:[?0]}}")
     List<Customer>findAllCustomerFromProductName(String productName);


}
