package shop.cazait.domain.master.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.service.MasterService;
import shop.cazait.global.common.response.BaseResponse;

@RestController
@RequestMapping("api/Masters")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @ApiOperation(value = "마스터 회원가입", notes = "마스터 사용자의 정보들을 이용해서 회원가입을 진행한다.")
    @PostMapping
    public BaseResponse<PostMasterRes> registerMaster(@Validated @RequestBody PostMasterReq dto) throws MasterException {
        PostMasterRes postCreatMasterRes = masterService.registerMaster(dto);
        return new BaseResponse<>(postCreatMasterRes);
    }

}
