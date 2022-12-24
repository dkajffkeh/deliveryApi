package com.barogo.domain.auth.repository;

import com.barogo.domain.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findFirstByUserId(String userId);
}
