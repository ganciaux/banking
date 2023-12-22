package com.ganc.banking.services.impl;

import com.ganc.banking.dto.AddressDto;
import com.ganc.banking.dto.ContactDto;
import com.ganc.banking.dto.TransactionDto;
import com.ganc.banking.dto.UserDto;
import com.ganc.banking.models.Address;
import com.ganc.banking.models.Contact;
import com.ganc.banking.models.User;
import com.ganc.banking.repositories.ContactRepository;
import com.ganc.banking.services.ContactService;
import com.ganc.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository repository;
    private final ObjectsValidator<ContactDto> validator;

    public ContactServiceImpl(ContactRepository repository, ObjectsValidator<ContactDto> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Integer save(ContactDto dto) {
        validator.validate(dto);
        Contact contact= ContactDto.toEntity(dto);
        return repository.save(contact).getId();
    }

    @Override
    public List<ContactDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ContactDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto findById(Integer id) {
        return repository.findById(id)
                .map(ContactDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No contact found by provided id: "+id) );
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<ContactDto> findAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(ContactDto::fromEntity)
                .collect(Collectors.toList());
    }
}
