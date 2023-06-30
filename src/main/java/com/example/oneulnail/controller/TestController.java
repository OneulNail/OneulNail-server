package com.example.oneulnail.controller;

import com.example.oneulnail.common.entity.BaseResponse;
import com.example.oneulnail.entity.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
@RequiredArgsConstructor
@Api(tags = "Test")
public class TestController {

    @GetMapping(value = "")
    public BaseResponse<String> test(@AuthenticationPrincipal User user){
        return BaseResponse.onSuccess("성공");
    }

}
