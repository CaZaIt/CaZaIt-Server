package shop.cazait.domain.master.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import shop.cazait.domain.master.entity.Master;

public class PostMasterLogInRes {
	@ApiModelProperty(value = "회원 id", example = "1")
	private Long id;
	@ApiModelProperty(value = "이메일", example = "12345@gmail.com")
	private String email;

	@ApiModelProperty(value = "jwt token")
	private String jwtToken;

	@ApiModelProperty(value = "refresh token")
	private String refreshToken;

	@Builder
	public PostMasterLogInRes(Long id, String email, String jwtToken, String refreshToken) {
		this.id = id;
		this.email = email;
		this.jwtToken = jwtToken;
		this.refreshToken = refreshToken;
	}

	public static PostMasterLogInRes of(Master master, String jwtToken, String refreshToken) {
		return PostMasterLogInRes.builder()
			.id(master.getId())
			.email(master.getEmail())
			.jwtToken(jwtToken)
			.refreshToken(refreshToken)
			.build();
	}

}
