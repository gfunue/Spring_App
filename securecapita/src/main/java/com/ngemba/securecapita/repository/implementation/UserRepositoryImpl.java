package com.ngemba.securecapita.repository.implementation;

import com.ngemba.securecapita.domain.Role;
import com.ngemba.securecapita.domain.User;
import com.ngemba.securecapita.exception.ApiException;
import com.ngemba.securecapita.query.UserQuery;
import com.ngemba.securecapita.repository.RoleRepository;
import com.ngemba.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.UUID;

import static com.ngemba.securecapita.enumeration.RoleType.ROLE_USER;
import static com.ngemba.securecapita.enumeration.VerificationType.ACCOUNT;
import static com.ngemba.securecapita.query.UserQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        // Check if email is unique
        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0)
            throw new ApiException("Email already in use. Please use a different email");
        // Save new user
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(UserQuery.INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            // Add role to user
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            // Send verification url
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            // Save url in verification table
            jdbc.update(UserQuery.INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "url", verificationUrl));
            // Send email to user with verification url
            //emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            // Return the newly created user
            return user;
            // If any errors, throw exception message
    } catch (DataAccessException dae){
        //log.error(exception.getMessage());
        //throw new ApiException("An error occurred. Pleas try again.");
        log.error("A database error occurred", dae);
        throw new ApiException("A database error occurred: " + dae.getMessage(), dae);
    } catch (Exception exception){
        log.error("An unexpected error occurred", exception);
        throw new ApiException("An unexpected error occurred: " + exception.getMessage(), exception);
    }
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

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verify/" + type + "/" + key).toUriString();
    }

}
