package shop.cazait.domain.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.service.MasterService;
import shop.cazait.global.common.response.BaseResponse;

@RestController
@RequestMapping("api/Masters")
@RequiredArgsConstructor
public class MasterController {

	@Autowired
	private final MasterService masterService;

	@ApiOperation(value = "마스터 회원가입", notes = "마스터 사용자의 정보들을 이용해서 회원가입을 진행한다.")
	@PostMapping
	public BaseResponse<PostMasterRes> registerMaster(@Validated @RequestBody PostMasterReq dto) throws
		MasterException {
		PostMasterRes postCreatMasterRes = masterService.registerMaster(dto);
		return new BaseResponse<>(postCreatMasterRes);
	}

	@DeleteMapping
	public BaseResponse<String> deleteMaster(@Validated @PathVariable int id) throws MasterException {
		masterService.removeMaster(id);
		String response = "회원 탈퇴가 성공하였습니다.";
		return new BaseResponse<>(response);
	}

}
