package com.example.oneulnail.user.repository;

import com.example.oneulnail.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User getByUid(String uid);

    User getByPhoneNum(String phone_num);

}