package shop.cazait.domain.master.dto.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.master.entity.Master;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "Master 계정 가입", description = "email, password, nickname을 담고있는 Request 객체")
public class PostMasterReq {

    @ApiModelProperty(value = "Master 이메일")
    private  String email;

    @ApiModelProperty(value = "Master 패스워드")
    private String password;

    @ApiModelProperty(value = "Master 닉네임")
    private  String nickname;

    public Master toEntity() {
        return Master.builder()
                .email(getEmail())
                .password(getPassword())
                .nickname(getNickname())
                .build();
    }

}
