package com.example.oneulnail.domain.likeStyle.repository;

import com.example.oneulnail.domain.likeStyle.entity.LikeStyle;
import com.example.oneulnail.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeStyleRepository extends JpaRepository<LikeStyle, Long>{

    @Query("SELECT ls from LikeStyle ls where ls.user = :user")
    Optional<LikeStyle> findLikeStyleByUser(@Param("user") User user);
}
