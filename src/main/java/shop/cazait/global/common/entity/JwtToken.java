package shop.cazait.global.common.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String key;

}
