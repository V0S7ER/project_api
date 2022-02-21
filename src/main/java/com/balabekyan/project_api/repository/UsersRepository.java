package com.balabekyan.project_api.repository;

import com.balabekyan.project_api.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    long countByEmail(String email);
    Optional<User> findByIdAndPassword(Long id, String password);
}
