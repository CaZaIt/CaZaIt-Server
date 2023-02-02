package shop.cazait.domain.master.dto.get;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.master.entity.Master;

@Schema(description = "마스터 조회 Response : 마스터 계정 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetMasterRes {
	@Schema(description = "마스터 계정 ID")
	private Long id;

	@Schema(description = "마스터 계정 이메일")
	private String email;

	@Schema(description = "마스터 계정 이름")
	private String nickname;

	public static GetMasterRes of(Master master) {
		return GetMasterRes.builder()
			.id(master.getId())
			.email(master.getEmail())
			.nickname(master.getNickname())
			.build();
	}

}
