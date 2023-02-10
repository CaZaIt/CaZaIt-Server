package shop.cazait.domain.user.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_USER;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.user.dto.DeleteUserRes;
import shop.cazait.domain.user.dto.PatchUserReq;
import shop.cazait.domain.user.dto.PatchUserRes;
import shop.cazait.domain.user.dto.PostUserReq;
import shop.cazait.domain.user.dto.PostUserRes;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.service.UserService;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.NoAuth;

@Api(tags = "유저 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;

    @NoAuth
    @ApiOperation(value = "회원 가입", notes = "User 정보를 추가하여 회원가입을 진행")
    @PostMapping("/sign-up")
    public SuccessResponse<PostUserRes> createUser (@RequestBody @Valid PostUserReq postUserReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new SuccessResponse<>(CREATE_USER, postUserRes);
    }

//    @GetMapping("/all")
//    @ApiOperation(value = "모든 회원을 조회",notes = "회원가입된 모든 회원 정보를 조회")
//    public SuccessResponse<List<GetUserRes>> getUsers(){
//        List<GetUserRes> allGetUserRes = userService.getAllUsers();
//        return new SuccessResponse<>(allGetUserRes);
//    }

//    @GetMapping("/{userIdx}")
//    @ApiOperation(value = "특정 회원 정보를 조회", notes ="자신의 계정 정보를 조회")
//    @ApiImplicitParam (name="userIdx",value = "사용자 userId")
//    public SuccessResponse<GetUserRes> getUser(
//             @PathVariable(name = "userIdx") Long userIdx) throws UserException {
//        GetUserRes userInfoRes = userService.getUserInfo(userIdx);
//        return new SuccessResponse<>(userInfoRes);
//    }

    @PatchMapping("/{userIdx}")
    @ApiOperation(value="특정한 회원 정보를 수정", notes = "자신의 계정 정보를 수정")
    @ApiImplicitParams({
            @ApiImplicitParam (name="userIdx",value = "사용자 userId"),
            @ApiImplicitParam (name="refreshToken",value = "리프레시 토큰")}
    )
    public SuccessResponse<PatchUserRes> modifyUser(
            @PathVariable(name = "userIdx") Long userIdx,
            @RequestBody @Valid  PatchUserReq patchUserReq,
            @RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        PatchUserRes patchUserRes = userService.modifyUser(userIdx, patchUserReq, refreshToken);
        return new SuccessResponse<>(SUCCESS, patchUserRes);
    }

    @DeleteMapping("/{userIdx}")
    @ApiOperation(value="특정한 회원 정보를 삭제", notes = "자신의 계정 정보를 삭제")
    @ApiImplicitParam (name="userIdx",value = "사용자 userId")
    public SuccessResponse<DeleteUserRes> deleteUser(@PathVariable(name = "userIdx") Long userIdx) {

        DeleteUserRes deleteUserRes = userService.deleteUser(userIdx);
        return new SuccessResponse<>(SUCCESS, deleteUserRes);
    }

    @NoAuth
    @PostMapping("/email")
    @ApiOperation(value="이메일 중복확인", notes = "회원가입 전 이미 존재하는 이메일인지 중복확인")
    @ApiImplicitParam (name="email",value = "사용자 이메일")
    public SuccessResponse<String> checkDuplicateEmail(@RequestParam @Email String email) throws UserException {
        SuccessResponse<String> emailDuplicateSuccessResponse = userService.checkduplicateEmail(email);
        return emailDuplicateSuccessResponse;
    }

    @NoAuth
    @PostMapping("/nickname")
    @ApiOperation(value="닉네임 중복확인", notes = "회원가입 전 이미 존재하는 닉네임인지 중복확인")
    @ApiImplicitParam (name="nickName",value = "사용자 닉네임")
    public SuccessResponse<String> checkDuplicateNickname(@RequestParam @NotBlank String nickName) throws UserException {
        SuccessResponse<String> nicknameDuplicateSuccessResponse = userService.checkduplicateNickname(nickName);
        return nicknameDuplicateSuccessResponse;
    }

//    @NoAuth
//    @PostMapping(value = "/refresh")
//    @ApiOperation(value="토큰 재발급", notes = "인터셉터에서 accesstoken이 만료되고 난 후 클라이언트에서 해당 api로 토큰 재발급 요청 필요")
//    public SuccessResponse<PostUserLoginRes>refreshToken(
//            @RequestHeader (value="X-ACCESS-TOKEN") String accessToken,
//            @RequestHeader(value="REFRESH-TOKEN") String refreshToken) throws UserException, BaseException {
//        PostUserLoginRes postUserLoginRes = userService.issueAccessToken(accessToken, refreshToken);
//        return new SuccessResponse<>(postUserLoginRes);
//    }
}
