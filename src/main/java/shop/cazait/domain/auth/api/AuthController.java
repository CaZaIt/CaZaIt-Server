package shop.cazait.domain.auth.api;

import static shop.cazait.domain.auth.Role.MASTER;
import static shop.cazait.domain.auth.Role.USER;
import static shop.cazait.global.error.status.ErrorStatus.INVALID_REQUEST;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.auth.Role;
import shop.cazait.domain.auth.dto.PostLoginReq;
import shop.cazait.domain.auth.dto.PostLoginRes;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.service.MasterService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.service.UserService;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.exception.BaseException;


@Api(tags = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auths")
public class AuthController {

    private final UserService userService;

    private final MasterService masterService;

    private final JwtService jwtService;

    @NoAuth
    @PostMapping("/log-in")
    @ApiOperation(value = "회원 로그인", notes="이메일과 로그인을 통해 로그인을 진행")
    @ApiImplicitParam(name="role",value = "유저인지 마스터인지(user/master)")
    public SuccessResponse<PostLoginRes> logIn (
            @RequestParam @NotBlank String role,
            @RequestBody @Valid PostLoginReq postLoginReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MasterException {
        PostLoginRes postLoginRes=null;
        Role exactRole = Role.of(role);

        if(exactRole.equals(USER)){
            postLoginRes = userService.logIn(postLoginReq);
        }
        else if(exactRole.equals(MASTER)){
            postLoginRes = masterService.LoginMaster(postLoginReq);
        }
        return new SuccessResponse<>(SUCCESS, postLoginRes);
    }


    @NoAuth
    @PostMapping(value = "/refresh/{userIdx}")
    @ApiOperation(value="토큰 재발급", notes = "인터셉터에서 accesstoken이 만료되고 난 후 클라이언트에서 해당 api로 토큰 재발급 요청 필요")
    @ApiImplicitParam(name="role",value = "유저인지 마스터인지(user/master)")
    public SuccessResponse<PostLoginRes>refreshToken(
            @PathVariable(name = "userIdx") Long userIdx,
            @RequestParam @NotBlank String role,
            @RequestHeader(value="X-ACCESS-TOKEN") String accessToken,
            @RequestHeader(value="REFRESH-TOKEN") String refreshToken) throws UserException, BaseException, MasterException {
        Long userIdxFromJwt = jwtService.getUserIdx(accessToken);
        if (!userIdx.equals(userIdxFromJwt)) {
            throw new UserException(INVALID_REQUEST);
        }

        PostLoginRes postLoginRes = null;
        Role exactRole = Role.of(role);

        if(exactRole.equals(USER)){
            postLoginRes = userService.reIssueTokens(accessToken, refreshToken, userIdx);
        } else if (exactRole.equals(MASTER)) {
            postLoginRes = masterService.issueAccessToken(accessToken, refreshToken);
        }

        return new SuccessResponse<>(SUCCESS, postLoginRes);
    }
}
