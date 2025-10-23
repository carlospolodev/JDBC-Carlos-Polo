package com.parcial.dos.parcialdos.service;

import com.parcial.dos.parcialdos.dto.*;
import com.parcial.dos.parcialdos.entity.Account;
import com.parcial.dos.parcialdos.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountResponseDTO create(AccountRequestDTO dto) {
        Account account = new Account(
                dto.getNumeroCuenta(),
                dto.getDueno(),
                dto.getBalanceActual(),
                true
        );
        Account saved = accountRepository.save(account);
        return new AccountResponseDTO(
                saved.getId(),
                saved.getAccountNumber(),
                saved.getOwnerName(),
                saved.getBalance(),
                saved.getActive()
        );
    }

    @Override
    public List<AccountResponseDTO> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(acc -> new AccountResponseDTO(
                        acc.getId(),
                        acc.getAccountNumber(),
                        acc.getOwnerName(),
                        acc.getBalance(),
                        acc.getActive()))
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDTO getById(Long id) {
        Optional<Account> opt = accountRepository.findById(id);
        if (opt.isEmpty()) return null;

        Account acc = opt.get();
        return new AccountResponseDTO(
                acc.getId(),
                acc.getAccountNumber(),
                acc.getOwnerName(),
                acc.getBalance(),
                acc.getActive()
        );
    }

    @Override
    public String updateBalance(Long id, BigDecimal nuevoBalance) {
        Optional<Account> opt = accountRepository.findById(id);
        if (opt.isEmpty()) {
            return "Cuenta no encontrada";
        }

        Account acc = opt.get();
        BigDecimal balanceAnterior = acc.getBalance();
        acc.setBalance(nuevoBalance);
        accountRepository.save(acc);

        return "La cuenta " + acc.getAccountNumber() +
                " fue actualizada: balanceAnterior=" + balanceAnterior +
                ", balanceActual=" + nuevoBalance;
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountOwnerBalanceDTO getByAccountNumber(String numeroCuenta) {
        Optional<Account> opt = accountRepository.findByAccountNumber(numeroCuenta);
        if (opt.isEmpty()) return null;

        Account acc = opt.get();
        return new AccountOwnerBalanceDTO(acc.getOwnerName(), acc.getBalance());
    }
}
