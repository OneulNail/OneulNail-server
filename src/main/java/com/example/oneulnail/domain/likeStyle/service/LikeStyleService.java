package com.example.oneulnail.domain.likeStyle.service;

import com.example.oneulnail.domain.likeStyle.dto.request.LikeStyleRegisterReqDto;
import com.example.oneulnail.domain.likeStyle.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.domain.likeStyle.entity.LikeStyle;
import com.example.oneulnail.domain.likeStyle.repository.LikeStyleRepository;
import com.example.oneulnail.domain.likeStyle.mapper.LikeStyleMapper;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.config.security.oauth2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LikeStyleService {
    private final LikeStyleRepository likeStyleRepository;
    private final LikeStyleMapper likeStyleMapper;
    private final AuthService authService;

    @Transactional
    public LikeStyleRegisterResDto register(HttpServletRequest request, LikeStyleRegisterReqDto requestDto){
        String email = authService.extractEmailFromJwt(request);
        User foundUser = authService.findUserByEmail(email);
        LikeStyle newLikeStyle = buildLikeStyle(foundUser, requestDto);
        return saveAndReturnResponse(newLikeStyle);
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
