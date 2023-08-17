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
public class ExtKakaoUserInfoOutDTO {
    private ExtKakaoUserAccountOutDTO extKakaoUserAccountOutDTO;
    long id;

    public static ExtKakaoUserInfoOutDTO fail() {
        return null;
    }
}
