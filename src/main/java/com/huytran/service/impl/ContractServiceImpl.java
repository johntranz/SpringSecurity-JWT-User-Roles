package com.huytran.service.impl;

import com.huytran.exception.BadRequestException;
import com.huytran.exception.RecordNotFoundException;
import com.huytran.models.Contract;
import com.huytran.repository.ContractRepository;
import com.huytran.service.ContractService;
import com.huytran.service.CustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository repository;

    @Autowired
    CustomerService customerService;

    @Override
    public List<Contract> getAllContracts() {
        return (List<Contract>) repository.findAll();
    }

    @Override
    public Contract getContractById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException("No contract record exist for given id", id));
    }

    @Override
    public Contract createContract(Contract entity) {
        if (customerService.getCustomerById(entity.getCustomerId()) != null) {
            entity.setId(null);
            do {
                entity.setContractCode("CTR" + RandomStringUtils.randomNumeric(7));
            } while (repository.findContractCode(entity.getContractCode()) != null);
            if (entity.getContractTo() == null) {
                entity = repository.save(entity);
                return entity;
            } else {
                long differenceTimes = entity.getContractTo().getTime() - entity.getContractFrom().getTime();
                if (TimeUnit.MILLISECONDS.toDays(differenceTimes) >= 180) {
                    entity.setContractTo(entity.getContractTo());

                    entity = repository.save(entity);
                    return entity;
                } else {
                    throw new BadRequestException("Minimum start and end time difference is 6 months");
                }
            }
        } else {
            throw new BadRequestException("Value CustomerId is exist");
        }
    }

    @Override
    public Contract updateContract(Contract entity) {
        Optional<Contract> contract = repository.findById(entity.getId());
        if (contract.isPresent()) {
            if (customerService.getCustomerById(entity.getCustomerId()) != null) {
                Contract newContract = contract.get();
                newContract.setCustomerId(entity.getCustomerId());
                newContract.setContractCode(contract.get().getContractCode());
                newContract.setContractFrom(entity.getContractFrom());
                if (entity.getContractTo() == null) {
                    newContract.setContractTo(entity.getContractTo());

                    entity = repository.save(newContract);
                    return entity;
                } else {
                    long differenceTimes = entity.getContractTo().getTime() - entity.getContractFrom().getTime();
                    if (TimeUnit.MILLISECONDS.toDays(differenceTimes) >= 180) {
                        newContract.setContractTo(entity.getContractTo());

                        entity = repository.save(newContract);
                        return entity;
                    } else {
                        throw new BadRequestException("Minimum start and end time difference is 6 months");
                    }
                }

            } else {
                throw new BadRequestException("Value CustomerId is exist");
            }
        } else {
            throw new BadRequestException("This object does not exist to edit !");
        }
    }

    @Override
    public void deleteContractById(Integer id) {
        Optional<Contract> contract = repository.findById(id);
        if (contract.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No contract record exist for given id", id);
        }
    }
}
