package com.picpay.user;

import com.picpay.user.domain.UserService;
import com.picpay.user.dto.CreateUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public void find(String keyword) {
        userService.findByKeyword(keyword);
    }


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(List<CreateUserDTO> users) {
        userService.save(users);
    }

}
