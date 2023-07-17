package shop.cazait.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import shop.cazait.domain.master.model.entity.Master;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Schema(description = "유저 로그인 Response : 로그인 완료된 유저 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserAuthenticateOutDTO {

    private static final String userRole = "user";

    private static final String masterRole = "master";

    @Schema(description = "회원 id")
    private UUID id;

    @Schema(description = "로그인한 아이디", example = "cazait1234")
    private String accountNumber;

    @Schema(description = "access token")
    private String accessToken;

    @Schema(description = "refresh token")
    private String refreshToken;

    @Schema(description = "유저인지 마스터인지", example = "user/master")
    @NotBlank
    private String role;

    public static UserAuthenticateOutDTO of(User user, String accessToken){
        return  UserAuthenticateOutDTO.builder()
                .id(user.getId())
                .accountNumber(user.getAccountNumber())
                .accessToken(accessToken)
                .refreshToken(user.getRefreshToken())
                .role(userRole)
                .build();
    }

    public static UserAuthenticateOutDTO of(Master master, String accessToken) {
        return  UserAuthenticateOutDTO.builder()
                .id(master.getId())
                .accountNumber(master.getAccountNumber())
                .accessToken(accessToken)
                .refreshToken(master.getRefreshToken())
                .role(masterRole)
                .build();
    }
}