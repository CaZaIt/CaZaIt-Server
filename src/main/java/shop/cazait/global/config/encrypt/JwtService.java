package shop.cazait.global.config.encrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.cazait.global.error.exception.BaseException;
import static shop.cazait.global.error.status.ErrorStatus.EMPTY_JWT;
import static shop.cazait.global.error.status.ErrorStatus.INVALID_JWT;

@Service
public class JwtService {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String createJwt(Long userIdx){
        System.out.println("userIdx = " + userIdx);
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx",userIdx)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 3600000))
                .signWith(key)
                .compact();
    }

    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    public Long getUserIdx() throws BaseException{

        // JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
        }
      catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // userIdx 추출
        return claims.getBody().get("userIdx",Long.class);  // jwt 에서 userIdx를 추출합니다.
    }
}
