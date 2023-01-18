package shop.cazait.domain.master.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.cazait.domain.master.entity.Master;

@Getter
@Setter
@Builder
public class PostMasterRes {

    @ApiModelProperty(value = "마스터 계정 ID")
    private  Long id;

    @ApiModelProperty(value = "마스터 계정 이메일")
    private  String email;

    @ApiModelProperty(value = "마스터 계정 이름")
    private  String nickname;

    static public PostMasterRes toDto(Master master) {
        return PostMasterRes.builder()
                .email(master.getEmail())
                .nickname(master.getNickname())
                .build();
    }

}
