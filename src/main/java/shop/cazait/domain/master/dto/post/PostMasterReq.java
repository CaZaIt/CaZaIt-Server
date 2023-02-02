package shop.cazait.domain.master.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.master.entity.Master;

@Schema(description = "마스터 정보 Request : 회원 가입에 필요한 마스터 정보")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostMasterReq {

	@Schema(description = "Master 이메일")
	private String email;

	@Schema(description = "Master 패스워드")
	private String password;

	@Schema(description = "Master 닉네임")
	private String nickname;

	public Master toEntity() {
		return Master.builder()
			.email(getEmail())
			.password(getPassword())
			.nickname(getNickname())
			.build();
	}

}
