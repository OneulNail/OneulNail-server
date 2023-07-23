package com.example.oneulnail.domain.post.service;

import com.example.oneulnail.domain.post.dto.request.PostRegisterReqDto;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.post.dto.response.PostRegisterResDto;
import com.example.oneulnail.domain.post.entity.Post;
import com.example.oneulnail.domain.post.mapper.PostMapper;
import com.example.oneulnail.domain.post.repository.PostRepository;
import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.shop.service.ShopService;
import com.example.oneulnail.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ShopService shopService;
    private final PostMapper postMapper;

    @Transactional
    public PostRegisterResDto register(PostRegisterReqDto postRegisterReqDto) {
        Shop foundShop = shopService.findById(postRegisterReqDto.getShopId());
        Post newPost = buildPost(postRegisterReqDto, foundShop);

        return saveAndReturnResponse(newPost);
    }

    private Post buildPost(PostRegisterReqDto postRegisterReqDto, Shop shop) {
        return Post.builder()
                .shop(shop)
                .name(postRegisterReqDto.getName())
                .likeCount(0)
                .imgUrl(postRegisterReqDto.getImgUrl())
                .price(postRegisterReqDto.getPrice())
                .content(postRegisterReqDto.getContent())
                .category(postRegisterReqDto.getCategory())
                .build();
    }

    private PostRegisterResDto saveAndReturnResponse(Post post) {
        Post registeredPost = postRepository.save(post);
        return postMapper.postRegisterEntityToDto(registeredPost);
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post Not Found"));
    }

    @Transactional(readOnly = true)
    public PostInfoResDto findDtoById(Long postId) {
        Post foundPost = findById(postId);
        return postMapper.postEntityToPostInfo(foundPost);
    }

    @Transactional(readOnly = true)
    public Slice<PostInfoResDto> findAll(Pageable pageable) {
        Slice<Post> posts = postRepository.findAllSlice(pageable);
        return posts.map(post -> postMapper.postEntityToPostInfo(post));
    }

    @Transactional(readOnly = true)
    public Slice<PostInfoResDto> findAllByShopId(Long shopId, Pageable pageable) {
        Slice<Post> posts = postRepository.findAllByShopIdSlice(shopId, pageable);
        return posts.map(post -> postMapper.postEntityToPostInfo(post));
    }

    @Transactional(readOnly = true)
    public Slice<PostInfoResDto> findAllByCategory(String category, Pageable pageable) {
        Slice<Post> posts = postRepository.findAllByCategorySlice(category, pageable);
        return posts.map(post -> postMapper.postEntityToPostInfo(post));
    }

    @Transactional(readOnly = true)
    public Slice<PostInfoResDto> findTopThreePostsByStyle(String style) {
        Pageable topThree = PageRequest.of(0, 3);
        Slice<Post> posts = postRepository.findTopThreePostsByStyle(style, topThree);
        return posts.map(post -> postMapper.postEntityToPostInfo(post));
    }
}
