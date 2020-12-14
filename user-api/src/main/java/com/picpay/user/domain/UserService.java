package com.picpay.user.domain;

import com.picpay.user.dto.UserDTO;
import org.springframework.data.domain.PageImpl;

public interface UserService {

    PageImpl<UserDTO> findByKeyword(String keyword, Integer pageNumber);

}
