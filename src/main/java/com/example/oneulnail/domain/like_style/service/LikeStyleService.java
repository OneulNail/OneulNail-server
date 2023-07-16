package com.example.oneulnail.domain.like_style.service;

import com.example.oneulnail.domain.like_style.dto.request.LikeStyleRegisterReqDto;
import com.example.oneulnail.domain.like_style.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.domain.like_style.entity.LikeStyle;
import com.example.oneulnail.domain.like_style.repository.LikeStyleRepository;
import com.example.oneulnail.domain.like_style.mapper.LikeStyleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeStyleService {
    private final LikeStyleRepository likeStyleRepository;
    private final LikeStyleMapper likeStyleMapper;

    public LikeStyleRegisterResDto register(LikeStyleRegisterReqDto requestDto){
        LikeStyle newLikeStyle = buildLikeStyle(requestDto);
        return saveAndReturnResponse(newLikeStyle);
    }

    private LikeStyle buildLikeStyle(LikeStyleRegisterReqDto likeStyleRegisterReqDto){
        return LikeStyle.builder()
                .style1(likeStyleRegisterReqDto.getStyle1())
                .style2(likeStyleRegisterReqDto.getStyle2())
                .style3(likeStyleRegisterReqDto.getStyle3())
                .build();
    }

    private LikeStyleRegisterResDto saveAndReturnResponse(LikeStyle likeStyle){
        LikeStyle registerdLikeStyle = likeStyleRepository.save(likeStyle);
        return likeStyleMapper.likeStyleRegisterEntityToDto(registerdLikeStyle);
    }
}
