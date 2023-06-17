package com.ngemba.securecapita.repository.implementation;

import com.ngemba.securecapita.domain.Role;
import com.ngemba.securecapita.exception.ApiException;
import com.ngemba.securecapita.repository.RoleRepository;
import com.ngemba.securecapita.rowmapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static com.ngemba.securecapita.enumeration.RoleType.ROLE_USER;
import static com.ngemba.securecapita.query.RoleQuery.*;
import static java.util.Map.*;
import static java.util.Objects.requireNonNull;

/**
 * The RoleRepositoryImpl class provides an implementation for the RoleRepository interface
 * and interacts with the database to perform CRUD operations on the Role table.
 * This class also handles role assignment and updates to a User.
 *
 * @see com.ngemba.securecapita.repository.RoleRepository
 */

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {

    // JDBC template for executing SQL queries
    private final NamedParameterJdbcTemplate jdbc;

    /**
     * Creates a new Role in the database
     * @param data The role to be created
     * @return The created role
     */
    @Override
    public Role create(Role data) {
        return null;
    }

    /**
     * Lists Roles in the database with pagination
     * @param page the page number
     * @param pageSize the size of the page
     * @return Collection of roles
     */
    @Override
    public Collection<Role> List(int page, int pageSize) {
        return null;
    }

    /**
     * Retrieves a Role from the database by id
     * @param id The role id to be retrieved
     * @return The retrieved role
     */
    @Override
    public Role get(Long id) {
        return null;
    }

    /**
     * Updates a Role in the database
     * @param data The role to be updated
     * @return The updated role
     */
    @Override
    public Role update(Role data) {
        return null;
    }

    /**
     * Deletes a Role from the database by id
     * @param id The role id to be deleted
     * @return True if deletion is successful, False otherwise
     */
    @Override
    public Boolean delete(Long id) {
        return null;
    }

    /**
     * Assigns a Role to a User in the database
     * @param userId The user id to be assigned a role
     * @param roleName The name of the role to be assigned
     * @throws ApiException if no role found by roleName or error occurred
     */
    @Override
    public void addRoleToUser(Long userId, String roleName) {
        try{
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleName), new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException exception){
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        }catch (Exception exception){
            throw new ApiException("An error occurred. Exception from adding role to user.");
        }

    }

    /**
     * Retrieves a Role from the database by user id
     * @param userId The user id
     * @return The retrieved role
     */
    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    /**
     * Retrieves a Role from the database by user email
     * @param email The user email
     * @return The retrieved role
     */
    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    /**
     * Updates a User's Role in the database
     * @param userId The user id
     * @param roleName The name of the new role
     */
    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
