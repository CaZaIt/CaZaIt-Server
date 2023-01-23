package shop.cazait.domain.master.dto.patch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PutMasterRes {

	@ApiModelProperty(value = "마스터 계정 ID")
	private Long id;

	@ApiModelProperty(value = "Master 이메일")
	private String email;

	@ApiModelProperty(value = "Master 패스워드")
	private String password;

	@ApiModelProperty(value = "Master 닉네임")
	private String nickname;

}
