package com.ganc.banking.services;

import com.ganc.banking.dto.TransactionDto;
import com.ganc.banking.models.Transaction;

import java.util.List;

public interface TransactionService extends AbstractService<TransactionDto> {
    List<TransactionDto>  findAllByUserId(Integer userId);
}
