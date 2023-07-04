package shop.cazait.domain.master.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.master.entity.Master;

import java.util.UUID;

@Schema(description = "마스터 조회 Response : 마스터 계정 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MasterListOutDTO {
	@Schema(description = "마스터 계정 ID")
	private UUID id;

	@Schema(description = "마스터 계정 이메일")
	private String email;

	@Schema(description = "마스터 계정 이름")
	private String nickname;

	public static MasterListOutDTO of(Master master) {
		return MasterListOutDTO.builder()
			.id(master.getId())
			.email(master.getEmail())
			.nickname(master.getNickname())
			.build();
	}

}
