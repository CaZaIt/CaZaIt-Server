package shop.cazait.domain.master.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.master.dto.request.MasterUpdateInDTO;
import shop.cazait.domain.master.entity.Master;

import java.util.UUID;

@Schema(description = "마스터 수정 Response : 수정한 마스터 계정 정보")
@Getter
@Builder
public class MasterUptateOutDTO {

	@Schema(description = "마스터 계정 ID")
	private UUID id;
	@Schema(description = "마스터 로그인 아이디")
	private String idNumber;

	@Schema(description = "마스터 패스워드")
	private String password;

	@Schema(description = "마스터 휴대전화번호")
	private String phoneNumber;

	@Schema(description = "마스터 닉네임")
	private String nickname;

	public static MasterUptateOutDTO of(Master master){
		return MasterUptateOutDTO.builder()
				.id(master.getId())
				.idNumber(master.getIdNumber())
				.password(master.getPassword())
				.phoneNumber(master.getPhoneNumber())
				.nickname(master.getNickname())
				.build();
	}
}
