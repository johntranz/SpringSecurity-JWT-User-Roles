package com.huytran.service.impl;


import com.huytran.exception.BadRequestException;
import com.huytran.exception.RecordNotFoundException;
import com.huytran.models.Customer;
import com.huytran.repository.CustomerRepository;
import com.huytran.service.CustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> t = (List<Customer>) repository.findAll();
        return (List<Customer>) repository.findAll();

    }

    @Override
    public Customer getCustomerById(Integer id) throws RecordNotFoundException {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException("No customer record exist for given id", id));
    }

    @Override
    public Customer createCustomer(Customer entity) {
        if (repository.findSocialSecurityNumber(entity.getSocialSecurityNumber()) == null) {
            entity.setId(null);
            do {
                entity.setCustomerCode("CUS" + RandomStringUtils.randomNumeric(7));
            } while (repository.findCustomerCode(entity.getCustomerCode()) != null);
            entity = repository.save(entity);
            return entity;
        } else {
            throw new BadRequestException("Value Social Security Number is exist");
        }
    }

    @Override
    public Customer updateCustomer(Customer entity) {
        Optional<Customer> customer = repository.findById(entity.getId());
        if (customer.isPresent()) {
            Customer newEntity = customer.get();
            newEntity.setName(entity.getName());
            newEntity.setDayOfBirthday(entity.getDayOfBirthday());
            newEntity.setCustomerCode(customer.get().getCustomerCode());
            newEntity.setType(newEntity.getType());

            if (customer.get().getSocialSecurityNumber().equals(entity.getSocialSecurityNumber())) {
                newEntity = repository.save(newEntity);
                return newEntity;

            } else {
                if (repository.findSocialSecurityNumber(entity.getSocialSecurityNumber()) == null) {
                    newEntity.setSocialSecurityNumber(entity.getSocialSecurityNumber());
                    newEntity = repository.save(newEntity);
                    return newEntity;
                } else {
                    throw new BadRequestException("Value Social Security Number is exist");
                }
            }
        } else {
            return null;
        }
    }

    @Override
    public void deleteCustomerById(Integer id) {
        Optional<Customer> customer = repository.findById(id);
        if (customer.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No customer record exist for given id", id);
        }
    }
}
