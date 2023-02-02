package shop.cazait.domain.master.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.master.entity.Master;

@Schema(description = "마스터 정보 Response : 회원 가입한 마스터 계정 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostMasterRes {

	@Schema(description = "마스터 계정 ID")
	private Long id;

	@Schema(description = "마스터 계정 이메일")
	private String email;

	@Schema(description = "마스터 계정 이름")
	private String nickname;

	static public PostMasterRes of(Master master) {
		return PostMasterRes.builder()
			.email(master.getEmail())
			.nickname(master.getNickname())
			.build();
	}

}
