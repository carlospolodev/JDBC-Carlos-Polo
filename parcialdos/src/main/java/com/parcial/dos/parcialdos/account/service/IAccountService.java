package com.parcial.dos.parcialdos.service;

import com.parcial.dos.parcialdos.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.dto.AccountResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {

    AccountResponseDTO create(AccountRequestDTO dto);

    List<AccountResponseDTO> getAll();

    AccountResponseDTO getById(Long id);

    String updateBalance(Long id, BigDecimal nuevoBalance);

    void delete(Long id);

    AccountOwnerBalanceDTO getByAccountNumber(String numeroCuenta);
}
