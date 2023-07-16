package shop.cazait.domain.master.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Schema(description = "마스터 수정 Request : 수정할 마스터 계정 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MasterUpdateInDTO {

	@Schema(description = "마스터 로그인 아이디")
	@NotBlank(message = "수정하고자 하는 마스터 계정의 아이디를 입력해주세요.")
	private String accountNumber;

	@Schema(description = "마스터 패스워드")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
	@NotBlank(message = "수정하고자 하는 마스터 계정의 패스워드를 입력해주세요.")
	private String password;

	@Schema(description = "마스터 전화번호")
	@NotBlank(message = "수정하고자 하는 마스터 계정의 휴대폰 전화번호를 입력해주세요.")
	private String phoneNumber;

	@Schema(description = "마스터 닉네임")
	@NotBlank(message = "수정하고자 하는 마스터 계정의 닉네임을 입력해주세요.")
	private String nickname;


	public MasterUpdateInDTO encryptMasterUpdateDTO(String encryptedPassword){
		this.password = encryptedPassword;

		return this;
	}

}
