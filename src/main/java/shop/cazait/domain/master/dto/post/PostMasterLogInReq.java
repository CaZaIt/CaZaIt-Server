//package shop.cazait.domain.master.dto.post;
//
//import javax.validation.constraints.NotBlank;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import shop.cazait.domain.master.entity.Master;
//
//@Schema(description = "마스터 로그인 Request : 로그인할 마스터 계정 정보")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PostMasterLogInReq {
//
//	@Schema(description = "이메일", example = "master@gmail.com")
//	@NotBlank(message = "Master 이메일을 입력해주세요.")
//	private String email;
//
//	@Schema(description = "비밀번호", example = "abcde!12345")
//	@NotBlank(message = "Master 비밀번호를 입력해주세요.")
//	private String password;
//
//	@Builder
//	public PostMasterLogInReq(String email, String password) {
//		this.email = email;
//		this.password = password;
//	}
//
//	public Master toEntity() {
//		return Master.builder()
//			.email(email)
//			.password(password)
//			.build();
//	}
//
//}
