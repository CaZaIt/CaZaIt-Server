//package shop.cazait.domain.master.dto.post;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import shop.cazait.domain.master.entity.Master;
//
//@Schema(description = "마스터 로그인 Response : 로그인한 마스터 계정 정보")
//@Getter
//@Builder(access = AccessLevel.PRIVATE)
//public class PostMasterLogInRes {
//
//	@Schema(description = "회원 id", example = "1")
//	private Long id;
//
//	@Schema(description = "이메일", example = "12345@gmail.com")
//	private String email;
//
//	@Schema(description = "jwt token")
//	private String jwtToken;
//
//	@Schema(description = "refresh token")
//	private String refreshToken;
//
//	@Builder
//	public PostMasterLogInRes(Long id, String email, String jwtToken, String refreshToken) {
//		this.id = id;
//		this.email = email;
//		this.jwtToken = jwtToken;
//		this.refreshToken = refreshToken;
//	}
//
//	public static PostMasterLogInRes of(Master master, String jwtToken, String refreshToken) {
//		return PostMasterLogInRes.builder()
//			.id(master.getId())
//			.email(master.getEmail())
//			.jwtToken(jwtToken)
//			.refreshToken(refreshToken)
//			.build();
//	}
//
//}
