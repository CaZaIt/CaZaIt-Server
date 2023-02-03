package shop.cazait.domain.master.dto.post;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.Secret;

@Schema(description = "마스터 정보 Request : 회원 가입에 필요한 마스터 정보")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostMasterReq {

	@Schema(description = "이메일", example = "master@gmail.com")
	private String email;

	@Schema(description = "비밀번호", example = "abcde!12345")
	private String password;

	@Schema(description = "Master 닉네임", example = "master")
	private String nickname;

	public Master toEntity() throws
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		return Master.builder()
			.email(getEmail())
			.password(new AES128(Secret.MASTER_INFO_PASSWORD_KEY).encrypt(getPassword()))
			.nickname(getNickname())
			.build();
	}

}
