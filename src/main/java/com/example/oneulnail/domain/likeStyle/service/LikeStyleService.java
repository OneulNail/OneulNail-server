package com.example.oneulnail.domain.likeStyle.service;

import com.example.oneulnail.domain.likeStyle.dto.request.LikeStyleRegisterReqDto;
import com.example.oneulnail.domain.likeStyle.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.domain.likeStyle.entity.LikeStyle;
import com.example.oneulnail.domain.likeStyle.repository.LikeStyleRepository;
import com.example.oneulnail.domain.likeStyle.mapper.LikeStyleMapper;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.post.service.PostService;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeStyleService {
    private final LikeStyleRepository likeStyleRepository;
    private final LikeStyleMapper likeStyleMapper;
    private final PostService postService;

    @Transactional
    public LikeStyleRegisterResDto register(User foundUser, LikeStyleRegisterReqDto requestDto){
        LikeStyle newLikeStyle = buildLikeStyle(foundUser, requestDto);
        return saveAndReturnResponse(newLikeStyle);
    }

    @Transactional(readOnly = true)
    public List<PostInfoResDto> findTopThreePostsForEachStyle(User foundUser) {
        LikeStyle foundLikeStyle = likeStyleRepository.findLikeStyleByUser(foundUser)
                .orElseThrow(() -> new NotFoundException("선호스타일이 없습니다."));

        List<PostInfoResDto> allPosts = new ArrayList<>();
        allPosts.addAll(postService.findTopThreePostsByStyle(foundLikeStyle.getStyle1()).getContent());
        allPosts.addAll(postService.findTopThreePostsByStyle(foundLikeStyle.getStyle2()).getContent());
        allPosts.addAll(postService.findTopThreePostsByStyle(foundLikeStyle.getStyle3()).getContent());
        return allPosts;
    }

    private LikeStyle buildLikeStyle(User foundUser, LikeStyleRegisterReqDto likeStyleRegisterReqDto){
        return LikeStyle.builder()
                .style1(likeStyleRegisterReqDto.getStyle1())
                .style2(likeStyleRegisterReqDto.getStyle2())
                .style3(likeStyleRegisterReqDto.getStyle3())
                .user(foundUser)
                .build();
    }

    private LikeStyleRegisterResDto saveAndReturnResponse(LikeStyle likeStyle){
        LikeStyle registeredLikeStyle = likeStyleRepository.save(likeStyle);
        return likeStyleMapper.likeStyleRegisterEntityToDto(registeredLikeStyle);
    }
}
