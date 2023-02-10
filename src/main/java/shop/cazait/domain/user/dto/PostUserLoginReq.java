//package shop.cazait.domain.user.dto;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import shop.cazait.domain.user.entity.User;
//
//import javax.validation.constraints.NotBlank;
//
//@Schema(description = "유저 로그인 Request : 로그인시 필요한 유저 정보")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class PostUserLoginReq {
//
//    @Schema(description = "이메일", example = "12345@gmail.com")
//    @NotBlank
//    private String email;
//    @Schema(description = "비밀번호", example = "abc12345#!")
//    @NotBlank
//    private String password;
//
//    @Builder
//    public PostUserLoginReq(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }
//
//    public User toEntity(){
//        return User.builder()
//                .email(email)
//                .password(password)
//                .build();
//    }
//}
