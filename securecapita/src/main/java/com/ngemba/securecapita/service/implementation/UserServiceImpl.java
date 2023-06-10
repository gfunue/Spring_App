package com.ngemba.securecapita.service.implementation;

import com.ngemba.securecapita.domain.User;
import com.ngemba.securecapita.dto.UserDTO;
import com.ngemba.securecapita.dtomapper.UserDTOMapper;
import com.ngemba.securecapita.repository.UserRepository;
import com.ngemba.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
