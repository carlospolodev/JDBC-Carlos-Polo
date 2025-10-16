package com.parcial.dos.parcialdos.account.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

  
    private Long id;    
    private String accountNumber;    
    private String ownerName;    
    private BigDecimal balance = BigDecimal.ZERO;   
    private Boolean active = true;

    public Account() {}

    public Account(String accountNumber, String ownerName, BigDecimal balance, Boolean active) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance == null ? BigDecimal.ZERO : balance;
        this.active = active == null ? true : active;
    }

    }
