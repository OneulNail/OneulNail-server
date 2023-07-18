package com.example.oneulnail.domain.likePost.repository;

import com.example.oneulnail.domain.likePost.entity.LikePost;
import com.example.oneulnail.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    @Query("SELECT lp.post FROM LikePost lp JOIN lp.user u WHERE u.id = :userId")
    Slice<Post> findPostsByUserIdSlice(@Param("userId") Long userId, Pageable pageable);
}
