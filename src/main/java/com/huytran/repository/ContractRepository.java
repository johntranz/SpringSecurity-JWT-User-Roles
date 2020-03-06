package com.huytran.repository;

import com.huytran.models.Contract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Integer> {

    @Query("SELECT c FROM Contract c WHERE c.contractCode= ?1")
    Contract findContractCode(String contractCode);
}
