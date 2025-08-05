package com.smarthealth.user_service.repository;

import com.smarthealth.user_service.dto.UserResponse;
import com.smarthealth.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Override
    Optional<User> findById(Long id);
}
