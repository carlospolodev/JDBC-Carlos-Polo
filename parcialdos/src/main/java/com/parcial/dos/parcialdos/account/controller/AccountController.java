package com.parcial.dos.parcialdos.account.controller;

import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;
import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.entity.Account;
import com.parcial.dos.parcialdos.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // 🟢 POST: Crear cuenta
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO dto) {
        Account account = new Account(
                dto.getNumeroCuenta(),
                dto.getDueno(),
                dto.getBalanceActual(),
                true
        );
        Account saved = accountRepository.save(account);

        AccountResponseDTO response = new AccountResponseDTO(
                saved.getId(),
                saved.getAccountNumber(),
                saved.getOwnerName(),
                saved.getBalance(),
                saved.getActive()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 🟡 GET: Obtener todas las cuentas
    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        List<AccountResponseDTO> accounts = accountRepository.findAll().stream()
                .map(a -> new AccountResponseDTO(
                        a.getId(),
                        a.getAccountNumber(),
                        a.getOwnerName(),
                        a.getBalance(),
                        a.getActive()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    // 🔵 GET: Obtener cuenta por ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long id) {
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Account acc = optional.get();
        AccountResponseDTO response = new AccountResponseDTO(
                acc.getId(),
                acc.getAccountNumber(),
                acc.getOwnerName(),
                acc.getBalance(),
                acc.getActive()
        );
        return ResponseEntity.ok(response);
    }

    // 🟣 PUT: Actualizar balance
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBalance(@PathVariable Long id, @RequestBody AccountRequestDTO dto) {
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada");
        }

        Account account = optional.get();
        BigDecimal balanceAnterior = account.getBalance();
        account.setBalance(dto.getBalanceActual());
        accountRepository.save(account);

        String mensaje = String.format(
                "La cuenta %s fue actualizada: balanceAnterior=%.2f, balanceActual=%.2f",
                account.getAccountNumber(),
                balanceAnterior,
                account.getBalance()
        );

        return ResponseEntity.ok(mensaje);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 🟢 GET: Buscar por número de cuenta
    @GetMapping("/by-number/{numeroCuenta}")
    public ResponseEntity<AccountOwnerBalanceDTO> getByAccountNumber(@PathVariable String numeroCuenta) {
        Optional<Account> optional = accountRepository.findByAccountNumber(numeroCuenta);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Account account = optional.get();
        AccountOwnerBalanceDTO dto = new AccountOwnerBalanceDTO(
                account.getOwnerName(),
                account.getBalance()
        );
        return ResponseEntity.ok(dto);
    }
}
