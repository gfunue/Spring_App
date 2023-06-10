package com.ngemba.securecapita.service;

import com.ngemba.securecapita.domain.User;
import com.ngemba.securecapita.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
}
