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
public class PostLoginRes {

    @Schema(description = "회원 id", example = "1")
    private Long id;

    @Schema(description = "이메일", example = "12345@gmail.com")
    private String email;

    @Schema(description = "jwt token")
    private String jwtToken;

    @Schema(description = "refresh token")
    private String refreshToken;

    @Schema(description = "유저인지 마스터인지", example = "USER/MASTER")
    @NotBlank
    private Role role;

    public static PostLoginRes of(User user, String jwtToken, String refreshToken, Role role){
        return  PostLoginRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .jwtToken(jwtToken)
                .refreshToken(refreshToken)
                .role(role)
                .build();
    }

    public static PostLoginRes of(Master master, String jwtToken, String refreshToken, Role role) {
        return  PostLoginRes.builder()
                .id(master.getId())
                .email(master.getEmail())
                .jwtToken(jwtToken)
                .refreshToken(refreshToken)
                .role(role)
                .build();
    }
}