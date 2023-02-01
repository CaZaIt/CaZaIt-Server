package shop.cazait.domain.master.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.master.entity.Master;

@Getter
public class PostMasterLogInReq {

	@ApiModelProperty(value = "회원 id", example = "1")
	private Long id;
	@ApiModelProperty(value = "이메일", example = "Cazait@gmail.com")
	private String email;
	@ApiModelProperty(value = "비밀번호", example = "abcde!12345")
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
