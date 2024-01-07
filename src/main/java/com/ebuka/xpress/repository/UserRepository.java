package com.ebuka.xpress.repository;

import com.ebuka.xpress.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their unique ID.
     *
     * @param userId The unique ID of the user
     * @return An Optional containing the User object if found, or an empty Optional if not found
     */
    Optional<User> findUserById(Long userId);

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user
     * @return An Optional containing the User object if found, or an empty Optional if not found
     */
    Optional<User> findUserByEmail(String email);
}
