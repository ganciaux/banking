package com.ganc.banking.services.impl;

import com.ganc.banking.dto.AddressDto;
import com.ganc.banking.dto.UserDto;
import com.ganc.banking.models.Address;
import com.ganc.banking.repositories.AddressRepository;
import com.ganc.banking.services.AddressService;
import com.ganc.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;
    private final ObjectsValidator<AddressDto> validator;

    public AddressServiceImpl(AddressRepository repository, ObjectsValidator<AddressDto> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Integer save(AddressDto dto) {
        validator.validate(dto);
        Address address = AddressDto.toEntity(dto);
        return repository.save(address).getId();
    }

    @Override
    public List<AddressDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AddressDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto findById(Integer id) {
        return repository.findById(id)
                .map(AddressDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No address found by provided id: "+id) );
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
