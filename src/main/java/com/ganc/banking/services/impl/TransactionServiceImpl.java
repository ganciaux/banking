package com.ganc.banking.services.impl;

import com.ganc.banking.dto.ContactDto;
import com.ganc.banking.dto.TransactionDto;
import com.ganc.banking.models.Contact;
import com.ganc.banking.models.Transaction;
import com.ganc.banking.models.TransactionType;
import com.ganc.banking.repositories.TransactionRepository;
import com.ganc.banking.services.TransactionService;
import com.ganc.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;
    private final ObjectsValidator<TransactionDto> validator;

    public TransactionServiceImpl(TransactionRepository repository, ObjectsValidator<TransactionDto> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Integer save(TransactionDto dto) {
        validator.validate(dto);
        Transaction transaction= TransactionDto.toEntity(dto);
        transaction.setAmount(transaction.getAmount().multiply(BigDecimal.valueOf(transactionType(transaction.getType()))));
        return repository.save(transaction).getId();
    }

    @Override
    public List<TransactionDto> findAll() {
        return repository.findAll()
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findById(Integer id) {
        return repository.findById(id)
                .map(TransactionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No transaction found by provided id: "+id) );
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private int transactionType(TransactionType type){
        return TransactionType.TRANSFERT==type ? -1 : 1;
    }

    @Override
    public List<TransactionDto> findAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }
}
