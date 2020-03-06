package com.huytran.service;

import com.huytran.models.Contract;

import java.util.List;

public interface ContractService {

    List<Contract> getAllContracts();

    Contract getContractById(Integer id);

    Contract createContract(Contract entity);

    Contract updateContract(Contract entity);

    void deleteContractById(Integer id);
}
