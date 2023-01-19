package shop.cazait.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;
@Getter
@ApiModel(value = "PatchUserRes/회원 수정 정보",description = "수정 완료된 회원 정보 dto")
public class PatchUserRes {
    @ApiModelProperty(value = "회원 id", example = "1")
    private Long id;
    @ApiModelProperty(value = "비밀번호", example = "12345#!@#")
    private String password;
    @ApiModelProperty(value = "이메일", example = "12345@gmail.com")
    private String email;
    @ApiModelProperty(value = "닉네임", example = "토마스")
    private String nickname;

    @Builder
    public PatchUserRes(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static PatchUserRes of(User user) {
        return PatchUserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
    }
}
