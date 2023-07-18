package com.example.oneulnail.domain.likePost.service;

import com.example.oneulnail.domain.likePost.dto.request.LikePostRegisterReqDto;
import com.example.oneulnail.domain.likePost.entity.LikePost;
import com.example.oneulnail.domain.likePost.repository.LikePostRepository;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.post.entity.Post;
import com.example.oneulnail.domain.post.mapper.PostMapper;
import com.example.oneulnail.domain.post.service.PostService;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.config.security.oauth2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LikePostService {

    private final LikePostRepository likePostRepository;
    private final PostMapper postMapper;
    private final PostService postService;
    private final AuthService authService;

    @Transactional
    public void register(HttpServletRequest request, LikePostRegisterReqDto registerReqDto) {
        String userEmail = extractUserEmailFromRequest(request);
        User foundUser = findUserByEmail(userEmail);
        Post foundPost = findPostAndAddLikeCount(registerReqDto);

        saveNewLikePost(foundPost, foundUser);
    }

    @Transactional(readOnly = true)
    public Slice<PostInfoResDto> findPostsByUserId(HttpServletRequest request, Pageable pageable) {
        String userEmail = extractUserEmailFromRequest(request);
        User foundUser = findUserByEmail(userEmail);
        Slice<Post> posts = likePostRepository.findPostsByUserIdSlice(foundUser.getId(), pageable);
        return posts.map(post -> postMapper.postEntityToPostInfo(post));
    }

    private String extractUserEmailFromRequest(HttpServletRequest request) {
        return authService.extractEmailFromJwt(request);
    }

    private User findUserByEmail(String email) {
        return authService.findUserByEmail(email);
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
