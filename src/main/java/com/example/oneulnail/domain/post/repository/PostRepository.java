package com.example.oneulnail.domain.post.repository;

import com.example.oneulnail.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p")
    Slice<Post> findAllSlice(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.shop.id = :shopId")
    Slice<Post> findAllByShopIdSlice(@Param("shopId") Long shopId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.category = :category")
    Slice<Post> findAllByCategorySlice(@Param("category") String category, Pageable pageable);
}
