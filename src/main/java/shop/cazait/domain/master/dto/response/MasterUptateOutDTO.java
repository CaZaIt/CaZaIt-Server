package shop.cazait.domain.master.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Schema(description = "마스터 수정 Response : 수정한 마스터 계정 정보")
@Getter
@Builder
public class MasterUptateOutDTO {

	@Schema(description = "마스터 계정 ID")
	private UUID id;
	@Schema(description = "Master 이메일")
	private String email;

	@Schema(description = "Master 패스워드")
	private String password;

	@Schema(description = "Master 닉네임")
	private String nickname;

}
