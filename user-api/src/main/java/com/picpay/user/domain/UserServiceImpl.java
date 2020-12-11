package com.picpay.user.domain;

import com.picpay.user.dto.CreateUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void findByKeyword(String keyword) {

    }

    @Override
    public void save(List<CreateUserDTO> users) {
        List<User> usersToSave = users.stream().map(createUserDTO -> toEntity(createUserDTO))
                .collect(Collectors.toList());

        userRepository.saveAll(usersToSave);
    }

    private User toEntity(CreateUserDTO createUserDTO) {
        return new User(createUserDTO.getId(), createUserDTO.getName(), createUserDTO.getUsername());
    }

}
