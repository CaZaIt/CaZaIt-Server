package shop.cazait.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ExternalDocs;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ApiModel(value = "PostUserReq/유저정보",description = "회원 가입시 필요한 dto")
public class PostUserReq {
    @ApiModelProperty(value = "회원 id", example = "1")
    private Long id;

    @ApiModelProperty(value = "이메일", example = "12345@gmail.com")
    @Email(message = "이메일 형식을 지키세요.")
    private String email;

    @ApiModelProperty(value = "비밀번호", example = "abc12345#!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
    private String password;

    @NotNull(message="닉네임을 입력하세요.")
    @ApiModelProperty(value = "닉네임", example = "토마스")
    private String nickname;

    @Builder
    public PostUserReq(String email, String password, String nickname){
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
