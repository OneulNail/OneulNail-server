package com.example.oneulnail.domain.post.service;

import com.example.oneulnail.domain.post.dto.request.PostRegisterReqDto;
import com.example.oneulnail.domain.post.dto.response.PostFindOneResDto;
import com.example.oneulnail.domain.post.dto.response.PostListResDto;
import com.example.oneulnail.domain.post.dto.response.PostRegisterResDto;
import com.example.oneulnail.domain.post.entity.Post;
import com.example.oneulnail.domain.post.mapper.PostMapper;
import com.example.oneulnail.domain.post.repository.PostRepository;
import com.example.oneulnail.domain.shop.dto.response.ShopListResDto;
import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.shop.service.ShopService;
import com.example.oneulnail.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ShopService shopService;
    private final PostMapper postMapper;

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

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post Not Found"));
    }

    public PostFindOneResDto findDtoById(Long postId) {
        Post foundPost = findById(postId);
        return postMapper.postFindOneEntityToDto(foundPost);
    }

    public Slice<PostListResDto> findAll(Pageable pageable) {
        Slice<Post> posts = postRepository.findAllSlice(pageable);
        return posts.map(post -> postMapper.postListEntityToDto(post));
    }
}
