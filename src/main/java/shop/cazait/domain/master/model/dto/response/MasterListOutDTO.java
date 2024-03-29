package shop.cazait.domain.master.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.master.model.entity.Master;

import java.util.UUID;

@Schema(description = "마스터 조회 Response : 마스터 계정 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MasterListOutDTO {
	@Schema(description = "마스터 계정 ID")
	private UUID id;

	@Schema(description = "마스터 로그인 아이디")
	private String accountName;


	@Schema(description = "마스터 계정 전화번호")
	private String phoneNumber;

	@Schema(description = "마스터 계정 이름")
	private String nickname;

	public static MasterListOutDTO of(Master master) {
		return MasterListOutDTO.builder()
				.id(master.getId())
				.accountName(master.getAccountName())
				.phoneNumber(master.getPhoneNumber())
				.nickname(master.getNickname())
				.build();
	}

}
