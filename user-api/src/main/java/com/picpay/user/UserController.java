package com.picpay.user;

import com.picpay.security.authentication.domain.RoleNameConstants;
import com.picpay.user.domain.UserService;
import com.picpay.user.dto.UserDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(RoleNameConstants.USER)
    public PageImpl<UserDTO> find(@RequestParam String keyword, @RequestParam Integer pageNumber) {
        return userService.findByKeyword(keyword, pageNumber);
    }

}
