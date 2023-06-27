package shop.cazait.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.auth.Role;

import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;

@Schema(description = "유저 로그인 Response : 로그인 완료된 유저 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserAuthenticateOutDTO {

    @Schema(description = "회원 id", example = "1")
    private Long id;

    @Schema(description = "이메일", example = "12345@gmail.com")
    private String email;

    @Schema(description = "access token")
    private String accessToken;

    @Schema(description = "refresh token")
    private String refreshToken;

    @Schema(description = "유저인지 마스터인지", example = "USER/MASTER")
    @NotBlank
    private String role;

    public static UserAuthenticateOutDTO of(User user, String accessToken, String refreshToken, String role){
        return  UserAuthenticateOutDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(role)
                .build();
    }

    public static UserAuthenticateOutDTO of(Master master, String accessToken, String refreshToken, String role) {
        return  UserAuthenticateOutDTO.builder()
                .id(master.getId())
                .email(master.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(role)
                .build();
    }
}