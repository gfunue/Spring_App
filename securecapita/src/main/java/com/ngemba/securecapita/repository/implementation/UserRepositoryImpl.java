package com.ngemba.securecapita.repository.implementation;

import com.ngemba.securecapita.domain.User;
import com.ngemba.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {
    private final NamedParameterJdbcTemplate jdbc;
    @Override
    public User create(User data) {
        // Check if email is unique
        // Save new user
        // Add role to user
        // Send verification url
        // Save url in verification table
        // Send email to user with verification url
        // Return the newly created user
        // If any errors, throw exception message
        return null;
    }

    @Override
    public Collection<User> List(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
