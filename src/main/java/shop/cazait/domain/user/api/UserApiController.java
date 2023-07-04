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
    public SuccessResponse<UserCreateOutDTO> createUser(@RequestBody @Valid UserCreateInDTO userCreateInDTO)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserCreateOutDTO userCreateOutDTO = userService.createUser(userCreateInDTO);
        return new SuccessResponse<>(CREATE_USER, userCreateOutDTO);
    }

    @GetMapping("/all")
    @Operation(summary = "모든 회원을 조회", description = "회원가입된 모든 회원 정보를 조회")
    public SuccessResponse<List<UserFindOutDTO>> getUsers(){
        List<UserFindOutDTO> allUserRes = userService.getAllUsers();
        return new SuccessResponse<>(SUCCESS, allUserRes);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "특정 회원 정보를 조회", description ="자신의 계정 정보를 조회")
    @Parameter(name = "userIdx", description = "response로 발급 받은 계정 ID번호",example="1")
    public SuccessResponse<UserFindOutDTO> getUser(
             @PathVariable(name = "userId") Long userIdx) throws UserException {
        UserFindOutDTO userInfoRes = userService.getUserInfo(userIdx);
        return new SuccessResponse<>(SUCCESS, userInfoRes);
    }

    @PatchMapping("/{userId}")
    @Operation(summary="특정한 회원 정보를 수정", description = "자신의 계정 정보를 수정")
    @Parameters({
            @Parameter(name = "userId", description = "response로 발급 받은 계정 ID번호"),
            @Parameter(name = "Refresh-Token", description = "발급 받은 refreshtoken")}
    )
    public SuccessResponse<UserUpdateOutDTO> modifyUser(
            @PathVariable(name = "userId") UUID userIdx,
            @RequestBody @Valid UserUpdateInDTO userUpdateInDTO,
            @RequestHeader(value="Refresh-Token") String refreshToken) throws UserException {

            UserUpdateOutDTO userUpdateOutDTO = userService.modifyUser(userIdx, userUpdateInDTO, refreshToken);
            return new SuccessResponse<>(SUCCESS, userUpdateOutDTO);

    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "특정한 회원 정보를 삭제", description = "자신의 계정 정보를 삭제")
    @Parameter(name = "userId", description = "response로 발급 받은 계정 ID번호",example="1")
    public SuccessResponse<UserDeleteOutDTO> deleteUser(@PathVariable(name = "userId") Long userIdx) throws UserException {
        UserDeleteOutDTO userDeleteOutDTO = userService.deleteUser(userIdx);
        return new SuccessResponse<>(SUCCESS, userDeleteOutDTO);
    }

    @NoAuth
    @PostMapping ("/email")
    @Operation(summary = "이메일 중복확인", description = "회원가입 전 이미 존재하는 이메일인지 중복확인")
    public SuccessResponse<String> checkDuplicateEmail(@RequestBody @Valid UserFindDuplicateEmaliInDTO userFindDuplicateEmailInDTO) throws UserException {
        SuccessResponse<String> emailDuplicateSuccessResponse = userService.checkduplicateEmail(userFindDuplicateEmailInDTO);
        return emailDuplicateSuccessResponse;
    }

    @NoAuth
    @PostMapping ("/nickname")
    @Operation(summary = "닉네임 중복확인", description = "회원가입 전 이미 존재하는 닉네임인지 중복확인")
    public SuccessResponse<String> checkDuplicateNickname(@RequestBody @Valid UserFindDuplicateNicknameInDTO userFindDuplicateNicknameInDTO) throws UserException {
        SuccessResponse<String> nicknameDuplicateSuccessResponse = userService.checkduplicateNickname(userFindDuplicateNicknameInDTO);
        return nicknameDuplicateSuccessResponse;
    }
}


