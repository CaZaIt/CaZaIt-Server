package shop.cazait.domain.master.dto.request;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.Secret;

@Schema(description = "마스터 정보 Request : 회원 가입에 필요한 마스터 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MasterCreateInDTO {

	@NotBlank(message = "Master 아이디를 입력해주세요.")
	@Schema(description = "아이디", example = "cazait1234")
	private String idNumber;

	@NotBlank(message = "Master 비밀번호를 입력해주세요.")
	@Schema(description = "비밀번호", example = "abcde!12345")
	private String password;

	@NotBlank(message = "Master 전화번호를 입력해주세요.")
	@Schema(description = "휴대폰 번호", example = "01012345678")
	private String phoneNumber;

	@NotBlank(message = "Master 닉네임을 입력해주세요.")
	@Schema(description = "Master 닉네임", example = "master")
	private String nickname;

	public Master toEntity()
		throws
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		return Master.builder()
				.idNumber(getIdNumber())
				.password(new AES128(Secret.MASTER_INFO_PASSWORD_KEY).encrypt(getPassword()))
				.phoneNumber(getPhoneNumber())
				.nickname(getNickname())
				.build();
	}

}