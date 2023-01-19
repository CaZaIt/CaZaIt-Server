package shop.cazait.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;
@Getter
@ApiModel(value = "DeleteUserRes/회원 삭제 정보",description = "삭제 완료된 회원 정보 dto")
public class DeleteUserRes {
    @ApiModelProperty(value = "회원 id", example = "1")
    private Long id;
    @ApiModelProperty(value = "비밀번호", example = "12345#!@#")
    private String password;
    @ApiModelProperty(value = "이메일", example = "12345@gmail.com")
    private String email;
    @ApiModelProperty(value = "닉네임", example = "토마스")
    private String nickname;

    @Builder
    public DeleteUserRes(Long id, String password, String email, String nickname) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public static DeleteUserRes of(User user){
        return DeleteUserRes
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }
}
