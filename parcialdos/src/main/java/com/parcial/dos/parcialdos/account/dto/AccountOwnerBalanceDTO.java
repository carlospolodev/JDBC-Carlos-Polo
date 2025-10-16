package com.parcial.dos.parcialdos.account.dto;

import java.math.BigDecimal;

public class AccountOwnerBalanceDTO {

    public AccountOwnerBalanceDTO() {}

    public AccountOwnerBalanceDTO(String dueno, BigDecimal balanceActual) {
        this.dueno = dueno;
        this.balanceActual = balanceActual;
    }
}
