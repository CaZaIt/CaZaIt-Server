package shop.cazait.domain.user.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.service.UserService;
import shop.cazait.global.common.response.BaseResponse;

import java.util.List;
@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;
    @ApiOperation(value = "회원 가입", notes = "User 정보를 추가하여 회원가입을 진행")
    @ResponseBody
    @PostMapping("/sign-up")
    public BaseResponse<PostUserRes> createUser (@RequestBody PostUserReq postUserReq){
        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new BaseResponse<>(postUserRes);
    }

    @ResponseBody
    @PostMapping("/log-in")
    @ApiOperation(value = "회원 로그인", notes="이메일과 로그인을 통해 로그인을 진행")
    public BaseResponse<PostLoginRes> logIn (@RequestBody PostLoginReq postLoginReq){
        PostLoginRes postLoginRes = userService.logIn(postLoginReq);
        return new BaseResponse<>(postLoginRes);
    }

    @ResponseBody
    @GetMapping("/all")
    @ApiOperation(value = "모든 회원을 조회",notes = "회원가입된 모든 회원 정보를 조회")
    public BaseResponse<List<GetUserRes>> getUsers(){
        List<GetUserRes> allGetUserRes = userService.getAllUsers();
        return new BaseResponse<>(allGetUserRes);
    }

    @ResponseBody
    @GetMapping("/{email}")
    @ApiOperation(value = "특정 회원 정보를 조회", notes ="자신의 계정 정보를 조회")
    @ApiImplicitParam(name="email", value = "회원의 email")
    public BaseResponse<GetUserRes> getUser(@PathVariable("email") String email){
        GetUserRes emailGetUserRes = userService.getUserByEmail(email);
        return new BaseResponse<>(emailGetUserRes);
    }

    @ResponseBody
    @PatchMapping("/{email}")
    @ApiOperation(value="특정한 회원 정보를 수정", notes = "자신의 계정 정보를 수정")
    @ApiImplicitParam(name="email", value = "회원의 email")
    public BaseResponse<PatchUserRes> modifyUser(@PathVariable("email") String email,@RequestBody PatchUserReq patchUserReq){
        PatchUserRes patchUserRes = userService.modifyUser(email, patchUserReq);
        return new BaseResponse<>(patchUserRes);
    }

    @ResponseBody
    @DeleteMapping("/{email}")
    @ApiOperation(value="특정한 회원 정보를 삭제", notes = "자신의 계정 정보를 삭제")
    @ApiImplicitParam(name="email", value = "회원의 email")
    public BaseResponse<DeleteUserRes> deleteUser(@PathVariable("email") String email){
        DeleteUserRes deleteUserRes = userService.deleteUser(email);
        return new BaseResponse<>(deleteUserRes);
    }

}
