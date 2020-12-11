package com.picpay.user.domain;

import com.picpay.user.dto.CreateUserDTO;
import com.picpay.user.dto.UserDTO;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface UserService {

    PageImpl<UserDTO> findByKeyword(String keyword, Integer pageNumber);

    void save(List<CreateUserDTO> users);

}
