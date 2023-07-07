package com.example.oneulnail.user.repository;

import com.example.oneulnail.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByPhoneNum(String phone_num);

}