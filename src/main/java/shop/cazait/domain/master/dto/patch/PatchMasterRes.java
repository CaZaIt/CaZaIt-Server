package shop.cazait.domain.master.dto.patch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "마스터 수정 Response : 수정한 마스터 계정 정보")
@Getter
@Builder
public class PatchMasterRes {

	@Schema(description = "마스터 계정 ID")
	private Long id;

	@Schema(description = "Master 이메일")
	private String email;

	@Schema(description = "Master 패스워드")
	private String password;

	@Schema(description = "Master 닉네임")
	private String nickname;

}
