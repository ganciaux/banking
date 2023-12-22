package com.ganc.banking.services;

import com.ganc.banking.dto.UserDto;

public interface UserService extends AbstractService<UserDto> {
    Integer validateAccount(Integer id);

    Integer invalidateAccount(Integer id);
}
