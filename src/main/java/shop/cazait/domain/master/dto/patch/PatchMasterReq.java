package shop.cazait.domain.master.dto.patch;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PatchMasterReq {

	@ApiModelProperty(value = "Master 이메일")
	@NotBlank(message = "수정하고자 하는 마스터계정 이메일을 입력해주세요.")
	private String email;

	@ApiModelProperty(value = "Master 패스워드")
	@NotBlank(message = "수정하고자 하는 마스터 계정 패스워드를 입력해주세요.")
	private String password;

	@ApiModelProperty(value = "Master 닉네임")
	@NotBlank(message = "수정하고자 하는 닉네임을 입력해주세요.")
	private String nickname;

}
