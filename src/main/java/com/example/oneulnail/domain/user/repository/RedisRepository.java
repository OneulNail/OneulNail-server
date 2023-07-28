package com.example.oneulnail.domain.user.repository;

import com.example.oneulnail.domain.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisRepository extends CrudRepository<RefreshToken,Long> {
    RefreshToken findByRefreshToken(String refreshToken);

}
