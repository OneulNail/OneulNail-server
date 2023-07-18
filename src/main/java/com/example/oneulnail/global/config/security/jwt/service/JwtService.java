package com.example.oneulnail.global.config.security.jwt.service;

import com.example.oneulnail.domain.user.repository.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    /**
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
     */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("issuer") // 발급자 설정
                .setAudience("audience") // 수신자 설정
                .setSubject(ACCESS_TOKEN_SUBJECT) // 토큰의 주제를 설정합니다.
                .setExpiration(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰의 만료 시간을 설정합니다.
                .claim(EMAIL_CLAIM, email) // 클레임을 추가합니다. 여기서는 이메일을 추가하였습니다.
                .signWith(SignatureAlgorithm.HS512, secretKey) // 토큰을 서명합니다. 사용할 알고리즘과 서명에 사용할 키를 설정합니다.
                .compact(); // 최종적으로 토큰을 생성하고 반환합니다.
    }

    /**
     * RefreshToken 생성
     * RefreshToken은 Claim에 email도 넣지 않으므로 withClaim() X
     */
    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("issuer") // 발급자 설정
                .setAudience("audience") // 수신자 설정
                .setSubject(REFRESH_TOKEN_SUBJECT) // 토큰의 주제를 설정합니다.
                .setExpiration(new Date(now.getTime() + refreshTokenExpirationPeriod)) // 토큰의 만료 시간을 설정합니다.
                .signWith(SignatureAlgorithm.HS512, secretKey) // 토큰을 서명합니다. 사용할 알고리즘과 서명에 사용할 키를 설정합니다.
                .compact(); // 최종적으로 토큰을 생성하고 반환합니다.
    }

    /**
     * AccessToken 헤더에 실어서 보내기
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * AccessToken에서 Email 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<String> extractEmail(String accessToken) {
        try {
            Claims claims = Jwts.parser()
                    .requireIssuer("issuer") // 발급자 확인
                    .requireAudience("audience") // 수신자 확인
                    .setSigningKey(secretKey) // 토큰을 검증할 때 사용할 서명 키를 설정합니다.
                    .parseClaimsJws(accessToken) // 토큰을 파싱하여 클레임을 추출합니다.
                    .getBody(); // 추출한 클레임을 반환합니다.
            return Optional.ofNullable(claims.get(EMAIL_CLAIM, String.class)); // 추출한 클레임 중에서 이메일 값을 가져옵니다.
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    /**
     * RefreshToken 헤더 설정
     */
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
    @Transactional(readOnly = true)
    public void updateRefreshToken(String email, String refreshToken) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey) // 토큰을 검증할 때 사용할 서명 키를 설정합니다.
                    .requireIssuer("issuer") // 발급자(issuer) 검증을 요구합니다.
                    .requireAudience("audience") // 대상 청중(audience) 검증을 요구합니다.
                    .parseClaimsJws(token); // 토큰을 파싱하고 검증합니다.
            return true; // 검증에 성공하면 토큰이 유효합니다.
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false; // 검증에 실패하면 토큰이 유효하지 않습니다.
        }
    }
}
