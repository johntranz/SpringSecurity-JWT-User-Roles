package com.huytran.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Column
    private String name;

    @NotNull(message = "Birthday is mandatory")
    @Column(name = "dob", columnDefinition = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd//")
    private Date dayOfBirthday;

    @NotBlank
    @Column(name = "ssn", length = 13)
    @Size(min = 9, max = 13, message = "Must be greater than or equal to 9, But but less than 13")
    private String socialSecurityNumber;

    @Column(name = "customer_code", length = 10)
    @Pattern(regexp = "CUS\\d{7}", message = "start with CUS and followed by seven digits, this is the only value")
    private String customerCode;

    @Column(columnDefinition = "TINYINT")
    @Min(value = 1, message = "Value is between 1 and 3")
    @Max(value = 3, message = "Value is between 1 and 3")
    private Byte type;

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private Set<Contract> listContracts;

    public Set<Contract> getContractEntities() {
        return listContracts;
    }

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDayOfBirthday() {
        return dayOfBirthday;
    }

    public void setDayOfBirthday(Date dayOfBirthday) {
        this.dayOfBirthday = dayOfBirthday;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dayOfBirthday=" + dayOfBirthday +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", type=" + type +
                ", contractEntities=" + listContracts +
                '}';
    }
}
