package shop.cazait.domain.auth.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExtKakaoUserTokenOutDTO {

    private String tokenType;
    private String accessToken;
    private String refreshToken;

    private long expiresIn;
    private long refreshTokenExpiresIn;

    public static ExtKakaoUserTokenOutDTO fail() {
        return new ExtKakaoUserTokenOutDTO(null, null);
    }
    private ExtKakaoUserTokenOutDTO(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
