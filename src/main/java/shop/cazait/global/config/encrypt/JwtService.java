package shop.cazait.global.config.encrypt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.error.exception.BaseException;

import static shop.cazait.global.error.status.ErrorStatus.*;

@Slf4j
@Service
public class JwtService {
    private final long ACCESS_TOKEN_VALID_TIME = 1 * 60 * 1000L;   // 1분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createJwt(Long userIdx) {
        log.info("created Token userIdx: "+userIdx);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

//    public String getJwt(){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        return request.getHeader("X-ACCESS-TOKEN");
//    }

    public Long getUserIdx(String token) throws UserException {
        log.info("getUseridx token info: "+token);
        // JWT 추출
        //String accessToken = getJwt();
        if (token == null || token.length() == 0) {
            throw new UserException(EMPTY_JWT);
        }

        // JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        }
        catch (ExpiredJwtException exception) {
            Long userIdx = exception.getClaims().get("userIdx", Long.class);
            return userIdx;
        }
        catch (JwtException exception) {
            log.error("Token tampered");
            throw new UserException(INVALID_JWT);
        }
        catch (NullPointerException exception) {
            log.error("Token is null");
            throw new UserException(EMPTY_JWT);
        }

        // userIdx 추출
        return claims.getBody().get("userIdx", Long.class);
    }

    public boolean isValidAccessToken(String token) throws UserException {
        log.info("isValidAccessToken is : " + token);
        try {
            Jws<Claims> accessClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            log.info("Access expireTime: " + accessClaims.getBody().getExpiration());
            log.info("Access userId: " + accessClaims.getBody().get("userIdx", Long.class));
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired UserID : " + exception.getClaims().get("userIdx"));
            throw new UserException(EXPIRED_JWT);
        } catch (JwtException exception) {
            log.error("Token Tampered");
            throw new UserException(INVALID_JWT);
        } catch (NullPointerException exception) {
            log.error("Token is null");
            throw new UserException(EMPTY_JWT);
        }
    }

    public boolean isValidAccessTokenInRefresh(String token) throws UserException {
        log.info("isValidAccessToken is : " + token);
        try {
            Jws<Claims> accessClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            log.info("Access expireTime: " + accessClaims.getBody().getExpiration());
            log.info("Access userId: " + accessClaims.getBody().get("userIdx", Long.class));
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired UserID : " + exception.getClaims().get("userIdx"));
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            throw new UserException(INVALID_JWT);
        } catch (NullPointerException exception) {
            log.error("Token is null");
            throw new UserException(EMPTY_JWT);
        }
    }

    public boolean isValidRefreshTokenInRefresh(String token) throws UserException {
        log.info("isValidRefreshToken: "+token);
        try {
            Jws<Claims> accessClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            log.info("Access expireTime: " + accessClaims.getBody().getExpiration());
            return true;
        }
        catch (ExpiredJwtException exception) {
            log.error("Token Expired UserID : " + exception.getClaims().get("userIdx"));
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            throw new UserException(INVALID_JWT);
        } catch (NullPointerException exception) {
            log.error("Token is null");
            throw new UserException(EMPTY_JWT);
        }
    }
}
