package shop.cazait.domain.master.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.master.entity.Master;

@Schema(description = "마스터 정보 Request : 회원 가입에 필요한 마스터 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MasterCreateInDTO {

	@NotBlank(message = "마스터 아이디를 입력해주세요.")
	@Schema(description = "로그인 아이디", example = "cazait1234")
	private String accountNumber;

	@NotBlank(message = "마스터 비밀번호를 입력해주세요.")
	@Schema(description = "비밀번호", example = "abc12345#!")
	private String password;

	@NotBlank(message = "마스터 전화번호를 입력해주세요.")
	@Schema(description = "휴대폰 번호", example = "01012345678")
	private String phoneNumber;

	@NotBlank(message = "마스터 닉네임을 입력해주세요.")
	@Schema(description = "마스터 닉네임", example = "master")
	private String nickname;

	public Master encryptMasterPassword(String encryptedMasterPassword) {
		return Master.builder()
				.accountNumber(getAccountNumber())
				.password(encryptedMasterPassword)
				.phoneNumber(getPhoneNumber())
				.nickname(getNickname())
				.build();
	}

}