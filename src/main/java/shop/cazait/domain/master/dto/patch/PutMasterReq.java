package shop.cazait.domain.master.dto.patch;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PutMasterReq {

	@ApiModelProperty(value = "Master 이메일")
	private String email;

	@ApiModelProperty(value = "Master 패스워드")
	private String password;

	@ApiModelProperty(value = "Master 닉네임")
	private String nickname;

}
