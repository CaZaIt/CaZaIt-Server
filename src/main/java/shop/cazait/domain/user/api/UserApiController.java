package shop.cazait.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.user.dto.PostLoginReq;
import shop.cazait.domain.user.dto.PostLoginRes;
import shop.cazait.domain.user.dto.PostUserReq;
import shop.cazait.domain.user.dto.PostUserRes;
import shop.cazait.domain.user.service.UserService;
import shop.cazait.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;
    @ResponseBody
    @PostMapping("/sign-up")
    public BaseResponse<PostUserRes> createUser (@RequestBody PostUserReq postUserReq){
        return userService.createUser(postUserReq);
    }

    @ResponseBody
    @PostMapping("/log-in")
    public BaseResponse<PostLoginRes> logIn (@RequestBody PostLoginReq postLoginReq){
        PostLoginRes postLoginRes = userService.logIn(postLoginReq);
        return new BaseResponse<>(postLoginRes);
    }
}
