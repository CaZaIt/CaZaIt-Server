package shop.cazait.domain.master.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.master.entity.Master;

@Schema(description = "마스터 로그인 Request : 로그인할 마스터 계정 정보")
@Getter
public class PostMasterLogInReq {

	@Schema(description = "이메일", example = "Cazait@gmail.com")
	private String email;

	@Schema(description = "비밀번호", example = "abcde!12345")
	private String password;

	@Builder
	public PostMasterLogInReq(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public Master toEntity() {
		return Master.builder()
			.email(email)
			.password(password)
			.build();
	}

}
