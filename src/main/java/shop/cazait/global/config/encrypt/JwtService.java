package shop.cazait.global.config.encrypt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import static shop.cazait.global.error.status.ErrorStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final long ACCESS_TOKEN_VALID_TIME = 30 * 1 * 60 * 1000L;   // 30분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주


    /**
     * 토큰 만료 검증을 위한 테스트 시간
     **/
//    private final long ACCESS_TOKEN_VALID_TIME = 1 * 30 * 1000L;   // 30초
//    private final long REFRESH_TOKEN_VALID_TIME = 1 * 60 * 1000L;   // 일분
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //토큰을 만들 때 공통적인 요소들을 만드는 함수 //호출 후 compact 작업이 필요하다
    public JwtBuilder makeCommonTokenSource(Date now, Date expirationDate) {
        System.out.println("Now = " + now);
        System.out.println("Expiration Date = " + expirationDate);
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key);

    }

    //accessToken 발행 함수
    public String createJwt(Long userIdx) {
        log.info("Created token userIdx = " + userIdx);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);

        return makeCommonTokenSource(now, expirationDate)
                .claim("userIdx", userIdx)
                .compact();
    }

    //refreshToken 발행 함수
    public String createRefreshToken() {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);
        return makeCommonTokenSource(now, expirationDate)
                .compact();
    }

    //헤더에서의 토큰을 추출하는 함수
    public String getJwtFromHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    //토큰을 파싱하기 위한 함수
    private Jws<Claims> parseJwt(String token) {
        Jws<Claims> claims;
        claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.substring(token.indexOf(" ")).trim());
        return claims;
    }

    //API 실행 시, 전송한 토큰의 user ID로 요청을 진행했는 지 검증하기 위해 토큰의 user ID를 추출하는 함수
//    public Long getUserIdx() throws UserException {
//        // JWT 추출
//        String token = getJwtFromHeader();
//
//        // JWT parsing
//        Jws<Claims> claims = parseTokenWithAllException(token);
//
//        // userIdx 추출
//        return claims.getBody().get("userIdx", Long.class);
//    }

    //만료, 잘못된 , 없는 토큰 일시 모든 예외처리 발생함수
    public Jws<Claims> parseTokenWithAllException(String token) throws UserException {
        try {
            Jws<Claims> parsedToken = parseJwt(token);
            return parsedToken;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired UserID : " + exception.getClaims().get("userIdx"));
            throw new UserException(EXPIRED_JWT);
        } catch (JwtException exception) {
            log.error("RefreshToken Tampered.");
            throw new UserException(INVALID_JWT);
        } catch (IllegalArgumentException exception) {
            log.error("Token is null.");
            throw new UserException(EMPTY_JWT);
        } catch ( NullPointerException exception) {
            log.error("Token is null.");
            throw new UserException(EMPTY_JWT);
        }

    }

    //1. 만료된 토큰을 파라미터로 받은 후 userID를 반환하는 함수 // 토큰 재발급 시 이용됨
    //2. 만료되지 않더라도 토큰 안에 있는 userID와의 검증을 위해 userID 반환
    public Long getUserIdx(String token) throws UserException {
        log.info("getUseridx token info = " + token);

        // JWT parsing
        Jws<Claims> claims;
        try {
            claims = parseJwt(token);
            log.info("Access expireTime = " + claims.getBody().getExpiration());
        } catch (ExpiredJwtException exception) {
            Long userIdx = exception.getClaims().get("userIdx", Long.class);
            return userIdx;
        } catch (JwtException exception) {
            log.error("Token tampered.");
            throw new UserException(INVALID_JWT);
        } catch (IllegalArgumentException exception) {
            log.error("Token is null.");
            throw new UserException(EMPTY_JWT);
        }

        return claims.getBody().get("userIdx", Long.class);
    }

    //액세스 토큰이 유효한 지 검증하는 함수 //인터셉터에서 모든 예외가 발생
//    public boolean isValidAccessToken(String token) throws UserException {
//        log.info("isValidAccessToken is : " + token);
//
//        Jws<Claims> accessClaims = parseTokenWithAllException(token);
//        log.info("Access expireTime: " + accessClaims.getBody().getExpiration());
//        log.info("Access userId: " + accessClaims.getBody().get("userIdx", Long.class));
//        return true;
//    }
//
//    // 유저 정보가 수정이 되는 API에서, 전송된 refreshtoken을 검증 할 때 쓰임//모든 예외 발생
//    public boolean isValidRefreshToken(String token) throws UserException {
//        log.info("isValidRefreshToken: " + token);
//
//        Jws<Claims> refreshClaims = parseTokenWithAllException(token);
//        log.info("Access expireTime: " + refreshClaims.getBody().getExpiration());
//        return true;
//    }

    //accessToken과 refreshToken 모두 검증하는 함수 // 만료, 잘못된 , 없는 토큰 일시 모든 예외처리 발생함수
    //1.인터셉터에서의 모든 API accessToken 검증
    //2.유저정보 수정단계에서 refreshToken 검증
    //Access userId의 로그에서 숫자가 추출되면 1(accessToken), Null인 경우는 2번(refreshToken)의 경우이다.
    public boolean isValidToken(String token) throws UserException {
        log.info("isValidAccessToken is = " + token);

        Jws<Claims> accessClaims = parseTokenWithAllException(token);
        log.info("Access expire time = " + accessClaims.getBody().getExpiration());
        log.info("Access userId =  " + accessClaims.getBody().get("userIdx", Long.class));
        return true;
    }

    /**
     * 재발급 API
     * 재발급 시에는 만료된 토큰에서 예외처리가 일어나지 않는다.
     **/
    //accesstoken 재발급
    public boolean isValidAccessTokenInRefresh(String token) throws UserException {
        log.info("isValidAccessToken is = " + token);
        Jws<Claims> accessClaims;
        try {
            accessClaims = parseJwt(token);
            log.info("Access expireTime = " + accessClaims.getBody().getExpiration());
            log.info("Access userId = " + accessClaims.getBody().get("userIdx", Long.class));
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired UserID = " + exception.getClaims().get("userIdx"));
            return false;
        } catch (JwtException exception) {
            log.error("accessToken Tampered.");
            throw new UserException(INVALID_JWT);
        } catch (IllegalArgumentException exception) {
            log.error("Token is null.");
            throw new UserException(EMPTY_JWT);
        }
    }

    //refreshToken 재발급
    public boolean isValidRefreshTokenInRefresh(String token) throws UserException {
        log.info("isValidRefreshToken = " + token);
        Jws<Claims> refreshClaims;
        try {
            refreshClaims = parseJwt(token);
            log.info("Access expire time = " + refreshClaims.getBody().getExpiration());
            return true;
        } catch (ExpiredJwtException exception) {
            return false;
        } catch (JwtException exception) {
            log.error("refreshToken Tampered.");
            throw new UserException(INVALID_JWT);
        } catch (IllegalArgumentException exception) {
            log.error("refreshToken is null.");
            throw new UserException(EMPTY_JWT);
        }
    }
    public void isValidAccessTokenId(Long userIdxFromController) throws UserException {
        String jwtFromHeader = getJwtFromHeader();
        Long userIdxFromJwt= getUserIdx(jwtFromHeader);
        log.info("userIdxFromJwt =  "+userIdxFromJwt);
        if (!userIdxFromController.equals(userIdxFromJwt)) {
            throw new UserException(INVALID_REQUEST);
        }
    }
}



//        return Jwts.builder()
//                .setHeaderParam("type", "jwt")
//                .setIssuedAt(now)
//                .setExpiration(expirationDate)
//                .signWith(key)
//                .compact();