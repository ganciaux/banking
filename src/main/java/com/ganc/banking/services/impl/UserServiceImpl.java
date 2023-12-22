package com.ganc.banking.services.impl;

import com.ganc.banking.dto.AccountDto;
import com.ganc.banking.dto.UserDto;
import com.ganc.banking.models.User;
import com.ganc.banking.repositories.UserRepository;
import com.ganc.banking.services.AccountService;
import com.ganc.banking.services.UserService;
import com.ganc.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private AccountService accountService;
    private final ObjectsValidator<UserDto> validator;
    public UserServiceImpl(UserRepository userRepository, ObjectsValidator<UserDto> validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user=UserDto.toEntity(dto);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                //.map(u -> UserDto.fromEntity(u))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user found by provided id: "+id) );
    }

    @Override
    public void delete(Integer id) {
        //todo check before delete
        userRepository.deleteById(id);
    }

    @Override
    public Integer validateAccount(Integer id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user found for account validation: id: "+id) );
        user.setActive(true);
        AccountDto account = AccountDto.builder()
                .user(UserDto.fromEntity(user)).build();
        accountService.save(account);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Integer invalidateAccount(Integer id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user found for account validation: id: "+id) );
        user.setActive(false);
        userRepository.save(user);
        return user.getId();
    }
}
