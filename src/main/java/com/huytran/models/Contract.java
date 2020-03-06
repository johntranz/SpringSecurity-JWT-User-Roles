package com.huytran.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;


@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id", nullable = true)
    private int customerId;

    @Column(name = "contract_code", length = 10)
    @Pattern(regexp = "CTR\\d{7}", message = "start with CTR and followed by seven digits, this is the only value")
    private String contractCode;

    @NotNull(message = "Day from is mandatory")
    @Column(name = "contract_from", columnDefinition = "DATE")
    private Date contractFrom;

    @Column(name = "contract_to", columnDefinition = "DATE")
    private Date contractTo;

    @ManyToOne(optional = false )
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Customer customer;

    public Contract() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Date getContractFrom() {
        return contractFrom;
    }

    public void setContractFrom(Date contractFrom) {
        this.contractFrom = contractFrom;
    }

    public Date getContractTo() {
        return contractTo;
    }

    public void setContractTo(Date contractTo) {
        this.contractTo = contractTo;
    }

    @Override
    public String toString() {
        return "ContractEntity{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", contractCode='" + contractCode + '\'' +
                ", contractFrom=" + contractFrom +
                ", contractTo=" + contractTo +
                ", customerEntity=" + customer +
                '}';
    }
}
