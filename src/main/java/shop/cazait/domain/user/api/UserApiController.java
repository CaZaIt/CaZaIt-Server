package shop.cazait.domain.user.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_USER;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;
import static shop.cazait.global.error.status.SuccessStatus.VALID_USER_INFO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import shop.cazait.domain.user.dto.request.UserCreateInDTO;
import shop.cazait.domain.user.dto.request.UserFindAccountNameInDTO;
import shop.cazait.domain.user.dto.request.UserFindExistAccountNameInDTO;
import shop.cazait.domain.user.dto.request.UserFindExistNicknameInDTO;
import shop.cazait.domain.user.dto.request.UserFindExistPhoneNumberInDTO;
import shop.cazait.domain.user.dto.request.UserUpdateNicknameInDTO;
import shop.cazait.domain.user.dto.request.UserUpdatePasswordInDTO;
import shop.cazait.domain.user.dto.request.UserVerifyPasswordInDTO;
import shop.cazait.domain.user.dto.request.UserVerifyUserInfoInResetPasswordInDTO;
import shop.cazait.domain.user.dto.response.UserCreateOutDTO;
import shop.cazait.domain.user.dto.response.UserDeleteOutDTO;
import shop.cazait.domain.user.dto.response.UserFindAccountNameOutDTO;
import shop.cazait.domain.user.dto.response.UserFindOutDTO;
import shop.cazait.domain.user.dto.response.UserUpdateNicknameOutDTO;
import shop.cazait.domain.user.dto.response.UserUpdatePasswordOutDTO;
import shop.cazait.domain.user.dto.response.UserVerifyPasswordOutDTO;
import shop.cazait.domain.user.dto.response.UserVerifyUserInfoInResetPasswordOutDTO;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.service.UserService;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;

@Tag(name = "유저 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

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
        List<UserFindOutDTO> allUserRes = userService.getUsers();
        return new SuccessResponse<>(SUCCESS, allUserRes);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "특정 회원 정보를 조회", description ="자신의 계정 정보를 조회")
    @Parameter(name = "userId", description = "response로 발급 받은 계정 ID번호")
    public SuccessResponse<UserFindOutDTO> getUser(@PathVariable(name = "userId") UUID userIdx) throws UserException {
        UserFindOutDTO userInfoRes = userService.getUser(userIdx);
        return new SuccessResponse<>(SUCCESS, userInfoRes);
    }


    @DeleteMapping("/{userId}")
    @Operation(summary = "특정한 회원 정보를 삭제", description = "자신의 계정 정보를 삭제")
    @Parameter(name = "userId", description = "response로 발급 받은 계정 ID번호")
    public SuccessResponse<UserDeleteOutDTO> deleteUserById(@PathVariable(name = "userId") UUID userIdx) throws UserException {
        UserDeleteOutDTO userDeleteOutDTO = userService.deleteUserById(userIdx);
        return new SuccessResponse<>(SUCCESS, userDeleteOutDTO);
    }

    @NoAuth
    @PostMapping ("/exist/accountname")
    @Operation(summary = "아이디 DB 조회", description = "입력한 아이디를 통해 회원 DB를 통해 존재/존재하지 않음 여부를 조회")
    public SuccessResponse<UUID> findUserExistAccountName(@RequestBody @Valid UserFindExistAccountNameInDTO userFindExistAccountNameInDTO) throws UserException {
        SuccessResponse<UUID> userFindExistAccountNameSuccessResponse = userService.findUserExistAccountName(userFindExistAccountNameInDTO);
        return userFindExistAccountNameSuccessResponse;
    }

    @NoAuth
    @PostMapping ("/exist/nickname")
    @Operation(summary = "닉네임 DB 조회", description = "입력한 비밀번호를 통해 회원 DB를 통해 존재/존재하지 않음 여부를 조회")
    public SuccessResponse<UUID> findUserExistNickname(@RequestBody @Valid UserFindExistNicknameInDTO userFindExistNicknameInDTO) throws UserException {
        SuccessResponse<UUID> userFindExistNicknameSuccessResponse = userService.findUserExistNickname(userFindExistNicknameInDTO);
        return userFindExistNicknameSuccessResponse;
    }

    @NoAuth
    @PostMapping ("/exist/phonenumber")
    @Operation(summary = "전화번호 DB 조회", description = "입력한 전화번호를 통해 회원 DB를 통해 존재/존재하지 않음 여부를 조회")
    public SuccessResponse<UUID> findUserExistPhoneNumber(@RequestBody @Valid UserFindExistPhoneNumberInDTO userFindExistPhoneNumberInDTO) throws UserException {
        SuccessResponse<UUID> userFindExistPhonenumber = userService.findUserExistPhoneNumber(userFindExistPhoneNumberInDTO);
        return userFindExistPhonenumber;
    }

    @NoAuth
    @PostMapping ("/find-accountname")
    @Operation(summary = "아이디 찾기", description = "문자 인증 완료 후 아이디 반환")
    public SuccessResponse<UserFindAccountNameOutDTO> findUserAccountName(@RequestBody @Valid UserFindAccountNameInDTO userFindAccountNameInDTO) throws UserException {
        UserFindAccountNameOutDTO userAccountName = userService.findUserAccountName(userFindAccountNameInDTO.getUserPhoneNumber());
        return new SuccessResponse<>(SUCCESS,userAccountName);
    }

    @NoAuth
    @PatchMapping("/reset-password/password/{userId}")
    @Operation(summary = "비밀번호 찾기(초기화) 페이지 새 비밀번호 입력", description = "변경하려는 새로운 비밀번호를 입력")
    public SuccessResponse<UserUpdatePasswordOutDTO> updateUserPasswordInResetPassword(
            @RequestBody @Valid UserUpdatePasswordInDTO userUpdatePasswordInDTO,
            @PathVariable(name = "userId") UUID userId) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserUpdatePasswordOutDTO  userUpdatePasswordOutDTO  = userService.updateUserPassword(userId, userUpdatePasswordInDTO.getPassword());
        return new SuccessResponse<>(SUCCESS, userUpdatePasswordOutDTO );
    }

    @NoAuth
    @PostMapping("/reset-password/checkuserinfo/{userId}")
    @Operation(summary = "비밀번호 찾기(초기화) 페이지 유저정보 검증", description = "아이디와 전화번호가 일치하는지")
    public SuccessResponse<UserVerifyUserInfoInResetPasswordOutDTO> verifyUserInfoInResetPassword(
            @RequestBody @Valid UserVerifyUserInfoInResetPasswordInDTO userVerifyUserInfoInResetPasswordInDTO,
            @PathVariable(name = "userId") UUID userId) throws UserException {
        UserVerifyUserInfoInResetPasswordOutDTO userVerifyUserInfoInResetPasswordOutDTO = userService.verifyUserInfoInResetPassword(userId, userVerifyUserInfoInResetPasswordInDTO.getPhoneNumber());
        return new SuccessResponse<>(VALID_USER_INFO,userVerifyUserInfoInResetPasswordOutDTO);
    }

    @PostMapping("/verify-password/{userId}")
    @Operation(summary = "계정정보 관리 페이지 비밀번호 검증", description = "로그인한 회원의 비밀번호가 유효한지")
    public SuccessResponse<UserVerifyPasswordOutDTO> verifyUserPassword(
            @RequestBody @Valid UserVerifyPasswordInDTO userVerifyPasswordInDTO,
            @PathVariable(name = "userId") UUID userId)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserVerifyPasswordOutDTO userVerifyPasswordOutDTO = userService.verifyUserPassword(userId,userVerifyPasswordInDTO.getPassword());
        return new SuccessResponse<>(SUCCESS,userVerifyPasswordOutDTO);
    }

    @PatchMapping("/userinfo/password/{userId}")
    @Operation(summary = "계정정보 관리 페이지 비밀번호 변경")
    public SuccessResponse<UserUpdatePasswordOutDTO> updateUserPasswordInUserInfo(
            @RequestBody @Valid UserUpdatePasswordInDTO userUpdatePasswordInDTO,
            @PathVariable(name = "userId") UUID userId)
            throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserUpdatePasswordOutDTO userUpdatePasswordOutDTO = userService.updateUserPassword(userId, userUpdatePasswordInDTO.getPassword());
        return new SuccessResponse<>(SUCCESS, userUpdatePasswordOutDTO);
    }

    @PatchMapping("/userinfo/nickname/{userId}")
    @Operation(summary = "계정정보 관리 페이지 닉네임 변경")
    public SuccessResponse<UserUpdateNicknameOutDTO> updateUserNickname(
            @RequestBody @Valid UserUpdateNicknameInDTO userUpdateNicknameInDTO,
            @PathVariable(name = "userId") UUID userId) throws UserException {
        UserUpdateNicknameOutDTO userUpdateNicknameOutDTO = userService.updateUserNickname(
                userId, userUpdateNicknameInDTO.getNickname());
        return new SuccessResponse<>(SUCCESS,userUpdateNicknameOutDTO);
    }

}


