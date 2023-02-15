package shop.cazait.domain.user.api;

import static shop.cazait.global.error.status.ErrorStatus.INVALID_REQUEST;
import static shop.cazait.global.error.status.SuccessStatus.CREATE_USER;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.service.UserService;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.status.ErrorStatus;

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
    public SuccessResponse<PostUserRes> createUser(@RequestBody @Valid PostUserReq postUserReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new SuccessResponse<>(CREATE_USER, postUserRes);
    }

    @GetMapping("/all")
    @ApiOperation(value = "모든 회원을 조회",notes = "회원가입된 모든 회원 정보를 조회")
    public SuccessResponse<List<GetUserRes>> getUsers(){
        List<GetUserRes> allGetUserRes = userService.getAllUsers();
        return new SuccessResponse<>(SUCCESS, allGetUserRes);
    }

    @GetMapping("/{userIdx}")
    @ApiOperation(value = "특정 회원 정보를 조회", notes ="자신의 계정 정보를 조회")
    @ApiImplicitParam (name="userIdx",value = "사용자 userId")
    public SuccessResponse<GetUserRes> getUser(
             @PathVariable(name = "userIdx") Long userIdx) throws UserException {
        jwtService.isValidAccessTokenId(userIdx);

        GetUserRes userInfoRes = userService.getUserInfo(userIdx);
        return new SuccessResponse<>(SUCCESS, userInfoRes);
    }

    @PatchMapping("/{userIdx}")
    @ApiOperation(value="특정한 회원 정보를 수정", notes = "자신의 계정 정보를 수정")
    @ApiImplicitParams({
            @ApiImplicitParam (name="userIdx",value = "사용자 userId"),
            @ApiImplicitParam (name="refreshToken",value = "리프레시 토큰")}
    )
    public SuccessResponse<PatchUserRes> modifyUser(
            @PathVariable(name = "userIdx") Long userIdx,
            @RequestBody @Valid  PatchUserReq patchUserReq,
            @RequestHeader(value="REFRESH-TOKEN") String refreshToken) throws UserException {
            jwtService.isValidAccessTokenId(userIdx);
            jwtService.isValidToken(refreshToken);

            PatchUserRes patchUserRes = userService.modifyUser(userIdx, patchUserReq, refreshToken);
            return new SuccessResponse<>(SUCCESS, patchUserRes);

    }

    @DeleteMapping("/{userIdx}")
    @ApiOperation(value = "특정한 회원 정보를 삭제", notes = "자신의 계정 정보를 삭제")
    @ApiImplicitParam(name = "userIdx", value = "사용자 userId")
    public SuccessResponse<DeleteUserRes> deleteUser(@PathVariable(name = "userIdx") Long userIdx) throws UserException {
        jwtService.isValidAccessTokenId(userIdx);

        DeleteUserRes deleteUserRes = userService.deleteUser(userIdx);
        return new SuccessResponse<>(SUCCESS, deleteUserRes);
    }

    @NoAuth
    @GetMapping("/email")
    @ApiOperation(value = "이메일 중복확인", notes = "회원가입 전 이미 존재하는 이메일인지 중복확인")
    @ApiImplicitParam(name = "email", value = "사용자 이메일")
    public SuccessResponse<String> checkDuplicateEmail(@RequestParam @Email @NotBlank String email) throws UserException {
        SuccessResponse<String> emailDuplicateSuccessResponse = userService.checkduplicateEmail(email.trim());
        return emailDuplicateSuccessResponse;
    }

    @NoAuth
    @GetMapping("/nickname")
    @ApiOperation(value = "닉네임 중복확인", notes = "회원가입 전 이미 존재하는 닉네임인지 중복확인")
    @ApiImplicitParam(name = "nickName", value = "사용자 닉네임")
    public SuccessResponse<String> checkDuplicateNickname(@RequestParam @NotBlank String nickName) throws UserException {
        SuccessResponse<String> nicknameDuplicateSuccessResponse = userService.checkduplicateNickname(nickName.trim());
        return nicknameDuplicateSuccessResponse;
    }
}


