package shop.cazait.domain.master.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import shop.cazait.domain.master.entity.Master;

public class PostMasterLogInRes {
	@ApiModelProperty(value = "회원 id", example = "1")
	private Long id;
	@ApiModelProperty(value = "이메일", example = "12345@gmail.com")
	private String email;

	@Builder
	public PostMasterLogInRes(Long id, String email) {
		this.id = id;
		this.email = email;
	}

	public static PostMasterLogInRes of(Master master) {
		return PostMasterLogInRes.builder()
			.id(master.getId())
			.email(master.getEmail())
			.build();
	}

}
