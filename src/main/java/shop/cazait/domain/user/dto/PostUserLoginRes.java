//package shop.cazait.domain.user.dto;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import shop.cazait.domain.user.entity.User;
//
//@Schema(description = "유저 로그인 Response : 로그인 완료된 유저 정보")
//@Getter
//@Builder(access = AccessLevel.PRIVATE)
//public class PostUserLoginRes {
//
//    @Schema(description = "회원 id", example = "1")
//    private Long id;
//
//    @Schema(description = "이메일", example = "12345@gmail.com")
//    private String email;
//
//    @Schema(description = "jwt token")
//    private String jwtToken;
//
//    @Schema(description = "refresh token")
//    private String refreshToken;
//
//    public static PostUserLoginRes of(User user, String jwtToken, String refreshToken){
//        return PostUserLoginRes.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .jwtToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
//}
