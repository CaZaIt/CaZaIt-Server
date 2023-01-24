package shop.cazait.domain.user.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.service.UserService;

import java.util.List;
import shop.cazait.global.common.response.SuccessResponse;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;
    @ApiOperation(value = "회원 가입", notes = "User 정보를 추가하여 회원가입을 진행")
    @PostMapping("/sign-up")
    public SuccessResponse<PostUserRes> createUser (@RequestBody PostUserReq postUserReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new SuccessResponse<>(postUserRes);
    }


    @PostMapping("/log-in")
    @ApiOperation(value = "회원 로그인", notes="이메일과 로그인을 통해 로그인을 진행")
    public SuccessResponse<PostLoginRes> logIn (@RequestBody PostLoginReq postLoginReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PostLoginRes postLoginRes = userService.logIn(postLoginReq);
        return new SuccessResponse<>(postLoginRes);
    }


    @GetMapping("/all")
    @ApiOperation(value = "모든 회원을 조회",notes = "회원가입된 모든 회원 정보를 조회")
    public SuccessResponse<List<GetUserRes>> getUsers(){
        List<GetUserRes> allGetUserRes = userService.getAllUsers();
        return new SuccessResponse<>(allGetUserRes);
    }


    @GetMapping("/{email}")
    @ApiOperation(value = "특정 회원 정보를 조회", notes ="자신의 계정 정보를 조회")
    @ApiImplicitParam(name="email", value = "회원의 email")
    public SuccessResponse<GetUserRes> getUser(@PathVariable("email") String email){
        GetUserRes emailGetUserRes = userService.getUserByEmail(email);
        return new SuccessResponse<>(emailGetUserRes);
    }


    @PatchMapping("/{email}")
    @ApiOperation(value="특정한 회원 정보를 수정", notes = "자신의 계정 정보를 수정")
    @ApiImplicitParam(name="email", value = "회원의 email")
    public SuccessResponse<PatchUserRes> modifyUser(@PathVariable("email") String email,@RequestBody PatchUserReq patchUserReq){
        PatchUserRes patchUserRes = userService.modifyUser(email, patchUserReq);
        return new SuccessResponse<>(patchUserRes);
    }

    
    @DeleteMapping("/{email}")
    @ApiOperation(value="특정한 회원 정보를 삭제", notes = "자신의 계정 정보를 삭제")
    @ApiImplicitParam(name="email", value = "회원의 email")
    public SuccessResponse<DeleteUserRes> deleteUser(@PathVariable("email") String email){
        DeleteUserRes deleteUserRes = userService.deleteUser(email);
        return new SuccessResponse<>(deleteUserRes);
    }

}
