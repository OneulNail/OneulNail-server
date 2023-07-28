package com.example.oneulnail.domain.likePost.service;

import com.example.oneulnail.domain.likePost.dto.request.LikePostRegisterReqDto;
import com.example.oneulnail.domain.likePost.entity.LikePost;
import com.example.oneulnail.domain.likePost.repository.LikePostRepository;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.post.entity.Post;
import com.example.oneulnail.domain.post.mapper.PostMapper;
import com.example.oneulnail.domain.post.service.PostService;
import com.example.oneulnail.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LikePostService {

    private final LikePostRepository likePostRepository;
    private final PostMapper postMapper;
    private final PostService postService;

    @Transactional
    public void register(User foundUser, LikePostRegisterReqDto registerReqDto) {
        Post foundPost = findPostAndAddLikeCount(registerReqDto);
        saveNewLikePost(foundPost, foundUser);
    }

    @Transactional(readOnly = true)
    public Slice<PostInfoResDto> findPostsByUserId(User foundUser, Pageable pageable) {
        Slice<Post> posts = likePostRepository.findPostsByUserIdSlice(foundUser.getId(), pageable);
        return posts.map(post -> postMapper.postEntityToPostInfo(post));
    }

    private Post findPostAndAddLikeCount(LikePostRegisterReqDto registerReqDto) {
        Post post = postService.findById(registerReqDto.getPostId());
        post.addLikeCount();
        return post;
    }

    private void saveNewLikePost(Post post, User user) {
        LikePost newLikePost = LikePost.builder()
                .post(post)
                .user(user)
                .build();
        likePostRepository.save(newLikePost);
    }
}
