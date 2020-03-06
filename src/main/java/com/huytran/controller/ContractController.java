package com.huytran.controller;

import com.huytran.models.Contract;
import com.huytran.service.impl.ContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/contracts")
public class ContractController {

    @Autowired
    private ContractServiceImpl contractService;

    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() throws ReflectiveOperationException {
        List<Contract> list = contractService.getAllContracts();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Contract>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable("id") Integer id)  {
        Contract entity = contractService.getContractById(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Contract>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(@Valid @RequestBody Contract entity)  {
        Contract created = contractService.createContract(entity);

        return new ResponseEntity<Contract>(created, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable("id") Integer id, @RequestBody @Valid Contract entity)  {
        Contract updated = contractService.updateContract(entity);

        return new ResponseEntity<Contract>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Contract> deleteContractById(@PathVariable("id") Integer id)  {
        contractService.deleteContractById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
