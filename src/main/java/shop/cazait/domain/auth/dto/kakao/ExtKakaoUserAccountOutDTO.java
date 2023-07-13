package shop.cazait.domain.auth.dto.kakao;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class ExtKakaoUserAccountOutDTO {
    private ExtKakaoUserProfileOutDTO extKakaoUserProfileOutDTO;
    private String gender;
    private String birthday;
    private String email;
}