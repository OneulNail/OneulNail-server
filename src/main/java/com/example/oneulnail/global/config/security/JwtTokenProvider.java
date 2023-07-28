package com.example.oneulnail.global.config.security;

import com.example.oneulnail.domain.user.dto.response.SignInResDto;
import com.example.oneulnail.domain.user.mapper.SignMapper;
import com.example.oneulnail.global.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static com.example.oneulnail.global.constants.BaseResponseStatus.*;

/**
 * JWT 토큰을 생성하고 유효성을 검증하는 컴포넌트 클래스 JWT 는 여러 암호화 알고리즘을 제공하고 알고리즘과 비밀키를 가지고 토큰을 생성
 * <p>
 * claim 정보에는 토큰에 부가적으로 정보를 추가할 수 있음 claim 정보에 회원을 구분할 수 있는 값을 세팅하였다가 토큰이 들어오면 해당 값으로 회원을 구분하여 리소스
 * 제공
 * <p>
 * JWT 토큰에 expire time을 설정할 수 있음
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService; // Spring Security 에서 제공하는 서비스 레이어
    private final SignMapper signMapper;

    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";
    private final long accessTokenValidMillisecond = 1000L * 60 * 30; // 30분 토큰 유효

    private final long refreshTokenValidMillisecond = 7 * 24 * 60 * 60 * 1000L; // 7일 토큰 유효



    //     SecretKey 에 대해 인코딩
    @PostConstruct
    protected void init() {
        System.out.println(secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(secretKey);
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    // JWT 토큰 생성
    public SignInResDto createToken(String phone_num) {
        Claims claims = Jwts.claims().setSubject(phone_num);

        Date now = new Date();
        String accessToken = Jwts.builder()
                .setIssuer("issuer") // 발급자 설정
                .setAudience("audience") // 수신자 설정
                .claim("type", TYPE_ACCESS)
                .setClaims(claims)
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        String refreshToken = Jwts.builder()
                .claim("type", TYPE_REFRESH)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond)) //토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        LOGGER.info("[createToken] JWT 토큰 생성 완료");
        return signMapper.signInEntityToDto(accessToken,refreshToken);

    }

    // AccessToken 갱신
    public SignInResDto createAccessToken(String phone_num) {
        Claims claims = Jwts.claims().setSubject(phone_num);

        Date now = new Date();
        String accessToken = Jwts.builder()
                .setIssuer("issuer") // 발급자 설정
                .setAudience("audience") // 수신자 설정
                .claim("type", TYPE_ACCESS)
                .setClaims(claims)
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        LOGGER.info("[createToken] AccessToken 갱신 완료");
        return signMapper.signInAccessEntityToDto(accessToken);

    }

    // JWT 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
                userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    // JWT 토큰에서 회원 구별 정보 추출
    public String getUsername(String token) {
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                .getSubject();
        LOGGER.info("토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }


    /**
     * HTTP Request Header 에 설정된 토큰 값을 가져옴
     *
     * @param request Http Request Header
     * @return String type Token 값
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // JWT 토큰의 유효성 + 만료일 체크
    public boolean validateToken(ServletRequest servletRequest, String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            LOGGER.info("토큰 유효 체크 완료");
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            servletRequest.setAttribute("exception","ExpiredJwtException");
            LOGGER.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            servletRequest.setAttribute("exception","UnsupportedJwtException");
            LOGGER.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            servletRequest.setAttribute("exception","IllegalArgumentException");
            LOGGER.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
        }

    public boolean isRefreshToken(String token) {
        String type = (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("type");
        return type.equals(TYPE_REFRESH);
    }
}
