package shop.cazait.domain.master.dto.patch;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "마스터 수정 Request : 수정할 마스터 계정 정보")
@AllArgsConstructor
@Getter
public class PatchMasterReq {

	@Schema(description = "Master 이메일")
	@NotBlank(message = "수정하고자 하는 마스터 계정의 이메일을 입력해주세요.")
	private String email;

	@Schema(description = "Master 패스워드")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
	@NotBlank(message = "수정하고자 하는 마스터 계정의 패스워드를 입력해주세요.")
	private String password;

	@Schema(description = "Master 닉네임")
	@NotBlank(message = "수정하고자 하는 마스터 계정의 닉네임을 입력해주세요.")
	private String nickname;

}
