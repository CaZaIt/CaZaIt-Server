package shop.cazait.domain.master.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.master.model.entity.Master;

import java.util.UUID;

@Schema(description = "마스터 정보 Response : 회원 가입한 마스터 계정 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MasterCreateOutDTO {

	@Schema(description = "마스터 계정 ID")
	private UUID id;

	@Schema(description = "마스터 로그인 아이디")
	private String accountNumber;

	@Schema(description = "마스터 계정 이름")
	private String nickname;

	@Schema(description = "마스터 계정 전화번호")
	private String phoneNumber;

	static public MasterCreateOutDTO of(Master master) {
		return MasterCreateOutDTO.builder()
				.id(master.getId())
				.accountNumber(master.getAccountNumber())
				.phoneNumber(master.getPhoneNumber())
				.nickname(master.getNickname())
				.build();
	}

}
