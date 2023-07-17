package com.example.oneulnail.domain.post.repository;

import com.example.oneulnail.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p")
    Slice<Post> findAllSlice(Pageable pageable);
}
