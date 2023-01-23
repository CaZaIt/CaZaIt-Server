package shop.cazait.domain.master.dto.get;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetMasterRes {
	@ApiModelProperty(value = "마스터 계정 ID")
	private Long id;

	@ApiModelProperty(value = "마스터 계정 이메일")
	private String email;

	@ApiModelProperty(value = "마스터 계정 이름")
	private String nickname;

}
