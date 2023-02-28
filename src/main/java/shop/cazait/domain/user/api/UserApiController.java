package shop.cazait.domain.user.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_USER;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "유저 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;

    @NoAuth
    @Operation(summary = "회원 가입", description = "User 정보를 추가하여 회원가입을 진행")
    @PostMapping("/sign-up")
    public SuccessResponse<PostUserRes> createUser(@RequestBody @Valid PostUserReq postUserReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new SuccessResponse<>(CREATE_USER, postUserRes);
    }

    @GetMapping("/all")
    @Operation(summary = "모든 회원을 조회", description = "회원가입된 모든 회원 정보를 조회")
    public SuccessResponse<List<GetUserRes>> getUsers(){
        List<GetUserRes> allGetUserRes = userService.getAllUsers();
        return new SuccessResponse<>(SUCCESS, allGetUserRes);
    }

    @GetMapping("/{userIdx}")
    @Operation(summary = "특정 회원 정보를 조회", description ="자신의 계정 정보를 조회")
    @Parameter (name="userIdx", description = "사용자 userId")
    public SuccessResponse<GetUserRes> getUser(
             @PathVariable(name = "userIdx") Long userIdx) throws UserException {
        jwtService.isValidAccessTokenId(userIdx);

        GetUserRes userInfoRes = userService.getUserInfo(userIdx);
        return new SuccessResponse<>(SUCCESS, userInfoRes);
    }

    @PatchMapping("/{userIdx}")
    @Operation(summary="특정한 회원 정보를 수정", description = "자신의 계정 정보를 수정")
    @Parameters({
            @Parameter (name="userIdx", description = "사용자 userId"),
            @Parameter (name="refreshToken", description = "리프레시 토큰")}
    )
    public SuccessResponse<PatchUserRes> modifyUser(
            @PathVariable(name = "userIdx") Long userIdx,
            @RequestBody @Valid  PatchUserReq patchUserReq,
            @RequestHeader(value="REFRESH-TOKEN") String refreshToken) throws UserException {
            jwtService.isValidAccessTokenId(userIdx);
            jwtService.isValidRefreshToken(refreshToken);

            PatchUserRes patchUserRes = userService.modifyUser(userIdx, patchUserReq, refreshToken);
            return new SuccessResponse<>(SUCCESS, patchUserRes);

    }

    @DeleteMapping("/{userIdx}")
    @Operation(summary = "특정한 회원 정보를 삭제", description = "자신의 계정 정보를 삭제")
    @Parameter(name = "userIdx", description = "사용자 userId")
    public SuccessResponse<DeleteUserRes> deleteUser(@PathVariable(name = "userIdx") Long userIdx) throws UserException {
        jwtService.isValidAccessTokenId(userIdx);

        DeleteUserRes deleteUserRes = userService.deleteUser(userIdx);
        return new SuccessResponse<>(SUCCESS, deleteUserRes);
    }

    @NoAuth
    @GetMapping("/email")
    @Operation(summary = "이메일 중복확인", description = "회원가입 전 이미 존재하는 이메일인지 중복확인")
    @Parameter(name = "email", description = "사용자 이메일")
    public SuccessResponse<String> checkDuplicateEmail(@RequestParam @Email @NotBlank  String email) throws UserException {
        SuccessResponse<String> emailDuplicateSuccessResponse = userService.checkduplicateEmail(email);
        return emailDuplicateSuccessResponse;
    }

    @NoAuth
    @GetMapping("/nickname")
    @Operation(summary = "닉네임 중복확인", description = "회원가입 전 이미 존재하는 닉네임인지 중복확인")
    @Parameter(name = "nickName", description = "사용자 닉네임")
    public SuccessResponse<String> checkDuplicateNickname(@RequestParam @NotBlank String nickName) throws UserException {
        SuccessResponse<String> nicknameDuplicateSuccessResponse = userService.checkduplicateNickname(nickName.trim());
        return nicknameDuplicateSuccessResponse;
    }
}


