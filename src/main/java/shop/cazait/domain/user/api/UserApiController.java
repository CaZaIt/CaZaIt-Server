package shop.cazait.domain.user.api;

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
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.service.UserService;

import java.util.List;
import shop.cazait.global.common.response.SuccessResponse;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.exception.BaseException;

@Api
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;
    private final JwtService jwtService;
    @NoAuth
    @ApiOperation(value = "회원 가입", notes = "User 정보를 추가하여 회원가입을 진행")
    @PostMapping("/sign-up")
    public SuccessResponse<PostUserRes> createUser (@Valid @RequestBody PostUserReq postUserReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new SuccessResponse<>(postUserRes);
    }

    @NoAuth
    @PostMapping("/log-in")
    @ApiOperation(value = "회원 로그인", notes="이메일과 로그인을 통해 로그인을 진행")
    public SuccessResponse<PostLoginRes> logIn (@Valid @RequestBody PostLoginReq postLoginReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PostLoginRes postLoginRes = userService.logIn(postLoginReq);
        return new SuccessResponse<>(postLoginRes);
    }

    @NoAuth
    @GetMapping("/all")
    @ApiOperation(value = "모든 회원을 조회",notes = "회원가입된 모든 회원 정보를 조회")
    public SuccessResponse<List<GetUserRes>> getUsers(){
        List<GetUserRes> allGetUserRes = userService.getAllUsers();
        return new SuccessResponse<>(allGetUserRes);
    }

    @NoAuth
    @GetMapping("/{email}")
    @ApiOperation(value = "특정 회원 정보를 조회", notes ="자신의 계정 정보를 조회")
    @ApiImplicitParam(name="email", value = "회원의 email")
    public SuccessResponse<GetUserRes> getUser(@PathVariable("email") @Email String email) throws UserException {
        GetUserRes emailGetUserRes = userService.getUserByEmail(email);
        return new SuccessResponse<>(emailGetUserRes);
    }

    @PatchMapping("/{userIdx}")
    @ApiOperation(value="특정한 회원 정보를 수정", notes = "자신의 계정 정보를 수정")
    @ApiImplicitParams({@ApiImplicitParam (name="userIdx",value = "사용자 userId"),
                        @ApiImplicitParam (name="refreshToken",value = "리프레시 토큰")})
    public SuccessResponse<PatchUserRes> modifyUser(
            @NotNull @PathVariable("userIdx") Long userIdx,
            @Valid @RequestBody PatchUserReq patchUserReq,
            @NotNull @RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        PatchUserRes patchUserRes = userService.modifyUser(userIdx, patchUserReq, refreshToken);
        return new SuccessResponse<>(patchUserRes);
    }

    @DeleteMapping("/{userIdx}")
    @ApiOperation(value="특정한 회원 정보를 삭제", notes = "자신의 계정 정보를 삭제")
    @ApiImplicitParams({@ApiImplicitParam (name="userIdx",value = "사용자 userId"),
            @ApiImplicitParam (name="refreshToken",value = "리프레시 토큰")})
    public SuccessResponse<DeleteUserRes> deleteUser(
            @NotNull @PathVariable("userIdx") Long userIdx) {
        DeleteUserRes deleteUserRes = userService.deleteUser(userIdx);
        return new SuccessResponse<>(deleteUserRes);
    }

    @NoAuth
    @PostMapping(value = "/refresh")
    @ApiOperation(value="토큰 재발급", notes = "인터셉터에서 accesstoken이 만료되고 난 후 클라이언트에서 해당 api로 토큰 재발급 요청 필요")
    @ApiImplicitParams({@ApiImplicitParam(name="accessToken", value = "액세스 토큰"),
                        @ApiImplicitParam(name="refreshToken", value = "리프레시 토큰")})
    public SuccessResponse<PostLoginRes>refreshToken(
            @NotNull @RequestHeader(value="X-ACCESS-TOKEN") String accessToken,
            @NotNull@RequestHeader(value="REFRESH-TOKEN") String refreshToken ) throws UserException, BaseException {
        PostLoginRes postLoginRes = userService.issueAccessToken(accessToken, refreshToken);
        return new SuccessResponse<>(postLoginRes);
    }
}
