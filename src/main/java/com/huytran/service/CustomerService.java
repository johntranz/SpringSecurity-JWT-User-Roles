package com.huytran.service;

import com.huytran.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Integer id);

    Customer createCustomer(Customer entity);

    Customer updateCustomer(Customer entity);

    void deleteCustomerById(Integer id);

}
