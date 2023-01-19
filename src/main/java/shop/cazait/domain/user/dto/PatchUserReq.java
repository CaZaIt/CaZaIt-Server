package shop.cazait.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ApiModel(value = "PatchUserReq/회원 수정 정보",description = "수정되어야 할 새로운 회원 정보 dto")
public class PatchUserReq {
    @ApiModelProperty(value = "이메일", example = "12345@gmail.com")
    private String email;
    @ApiModelProperty(value = "비밀번호", example = "12345#!@#")
    private String password;
    @ApiModelProperty(value = "닉네임", example = "토마스")
    private String nickname;

    @Builder
    public PatchUserReq(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
