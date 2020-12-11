package com.picpay.user;

import com.picpay.security.authentication.domain.RoleNameConstants;
import com.picpay.user.domain.UserService;
import com.picpay.user.dto.CreateUserDTO;
import com.picpay.user.dto.UserDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed(RoleNameConstants.INTEGRATION)
    public void save(@RequestBody @Valid List<CreateUserDTO> users) {
        userService.save(users);
    }

}
