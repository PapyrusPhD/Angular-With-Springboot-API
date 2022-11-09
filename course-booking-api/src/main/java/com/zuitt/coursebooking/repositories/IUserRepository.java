package com.zuitt.coursebooking.repositories;

import com.zuitt.coursebooking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users WHERE email = ?1 LIMIT 1", nativeQuery = true)
    User findByEmail(String email);
}
