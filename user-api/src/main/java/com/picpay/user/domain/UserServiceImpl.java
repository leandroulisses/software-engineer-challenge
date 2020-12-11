package com.picpay.user.domain;

import com.picpay.user.dto.CreateUserDTO;
import com.picpay.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {

    private static final int PAGE_SIZE = 15;
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PageImpl<UserDTO> findByKeyword(String keyword, Integer pageNumber) {
        keyword = normalize(keyword);
        JpaSort sort = JpaSort.unsafe(Sort.Direction.DESC, "ur.rank", "name");
        PageRequest request = PageRequest.of(pageNumber, PAGE_SIZE, sort);
        Page<User> page = userRepository.findByKeyword(keyword, request);
        List<UserDTO> content = page.getContent().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getUsername()))
                .collect(Collectors.toList());
        return new PageImpl<>(content, page.getPageable(), page.getSize());
    }

    private String normalize(String word) {
        return word.toLowerCase();
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
