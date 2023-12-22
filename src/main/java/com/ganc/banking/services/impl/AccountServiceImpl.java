package com.ganc.banking.services.impl;

import com.ganc.banking.dto.AccountDto;
import com.ganc.banking.exceptions.OperationNonPermittedException;
import com.ganc.banking.models.Account;
import com.ganc.banking.repositories.AccountRepository;
import com.ganc.banking.services.AccountService;
import com.ganc.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final ObjectsValidator<AccountDto> validator;

    public AccountServiceImpl(AccountRepository repository, ObjectsValidator<AccountDto> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Integer save(AccountDto dto) {
        /*
        if (dto.getId()!=null){
            throw new OperationNonPermittedException("Account cannot be updated",
                    "Save account",
                    "Account",
                    "Updated not permitted");
        }*/
        validator.validate(dto);
        Account account=AccountDto.toEntity(dto);
        boolean userHasAlreadyAnAccount = repository.findByUserId(account.getUser().getId()).isPresent();
        if (userHasAlreadyAnAccount){
            throw new OperationNonPermittedException("The selected user has already an active account",
                    "Create account",
                    "Account service",
                    "Account creation");
        }
        if (dto.getId() == null) {
            account.setIban(generateIban());
        }
        return repository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Integer id) {
        return repository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Account was not found: "+ id));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private String generateIban(){
        String iban = Iban.random(CountryCode.FR).toFormattedString();

        if(repository.findByIban(iban).isPresent())
            generateIban();

        return iban;
    }
}
