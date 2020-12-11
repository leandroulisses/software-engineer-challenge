package com.picpay.user.domain;

import com.picpay.user.dto.CreateUserDTO;

import java.util.List;

public interface UserService {

    void findByKeyword(String keyword);

    void save(List<CreateUserDTO> users);

}
