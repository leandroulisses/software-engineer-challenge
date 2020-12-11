package com.picpay.user.domain;

import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void findByKeyword(String keyword) {
        
    }
}
