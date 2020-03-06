package com.huytran.repository;

import com.huytran.models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.socialSecurityNumber = ?1")
    Customer findSocialSecurityNumber (String ssn);

    @Query("SELECT c FROM Customer c WHERE c.customerCode = ?1")
    Customer findCustomerCode (String customerCode);
}
