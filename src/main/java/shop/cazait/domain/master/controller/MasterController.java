package shop.cazait.domain.master.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.master.dto.post.PostRegisterReq;
import shop.cazait.domain.master.dto.post.PostRegisterRes;
import shop.cazait.domain.master.service.MasterService;
import shop.cazait.global.common.response.BaseResponse;

@RestController
@RequestMapping("/Master")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @PostMapping
    public BaseResponse<PostRegisterRes> registerMaster(@Validated @RequestBody PostRegisterReq dto){
        PostRegisterRes postCreatMasterRes = masterService.registerMaster(dto);
        return new BaseResponse<>(postCreatMasterRes);
    }

}
