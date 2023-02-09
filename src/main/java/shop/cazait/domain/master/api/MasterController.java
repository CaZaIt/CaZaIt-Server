package shop.cazait.domain.master.api;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import shop.cazait.domain.master.dto.get.GetMasterRes;
import shop.cazait.domain.master.dto.patch.PatchMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterLogInReq;
import shop.cazait.domain.master.dto.post.PostMasterLogInRes;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.service.MasterService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.common.status.BaseStatus;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.exception.BaseException;

@Api(tags = "마스터 API")
@RestController
@RequestMapping("/api/masters")
@RequiredArgsConstructor
public class MasterController {

	private final MasterService masterService;

	@NoAuth
	@PostMapping("/sign-up")
	@ApiOperation(value = "마스터 회원가입", notes = "마스터 사용자의 정보들을 이용해서 회원가입을 진행한다.")
	public SuccessResponse<PostMasterRes> registerMaster(@Validated @RequestBody PostMasterReq dto) throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		PostMasterRes postCreateMasterRes = masterService.registerMaster(dto);
		return new SuccessResponse<>(postCreateMasterRes);
	}

	@NoAuth
	@PostMapping("/log-in")
	@ApiOperation(value = "회원 로그인", notes = "이메일과 패스워드를 통해 로그인을 진행")
	public SuccessResponse<PostMasterLogInRes> logIn(@RequestBody PostMasterLogInReq postMasterLogInReq)
		throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		PostMasterLogInRes postMasterLogInRes = masterService.LoginMaster(postMasterLogInReq);
		return new SuccessResponse<>(postMasterLogInRes);
	}

	@GetMapping("/all")
	@ApiOperation(value = "마스터 계정 전체 조회", notes = "ACTIVE한 마스터 계정을 조회한다.")
	public SuccessResponse<List<GetMasterRes>> getMasterByStatus() throws MasterException {
		List<GetMasterRes> masterResList = masterService.getMasterByStatus(BaseStatus.ACTIVE);
		return new SuccessResponse<>(masterResList);
	}

	@PatchMapping("/update/{cafeId}")
	@ApiOperation(value = "마스터 정보 수정", notes = "특정 ID의 마스터 관련 정보를 수정한다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "masterId", value = "마스터 ID"),
	})
	public SuccessResponse<String> updateMaster(@PathVariable Long masterId, @RequestBody PatchMasterReq masterReq) {
		masterService.updateMaster(masterId, masterReq);
		return new SuccessResponse<>("카페 수정 완료");
	}

	@DeleteMapping
	@ApiOperation(value = "마스터 계정 탈퇴(상태  변경)", notes = "특정 ID의 마스터 상태를 INACTIVE로 변경한다.")
	public SuccessResponse<String> deleteMaster(@Validated @PathVariable int id) throws MasterException {
		masterService.removeMaster(id);
		String response = "회원 탈퇴가 성공하였습니다.";
		return new SuccessResponse<>(response);
	}

	@PostMapping(value = "/refresh")
	@ApiOperation(value = "토큰 재발급", notes = "인터셉터에서 accesstoken이 만료되고 난 후 클라이언트에서 해당 api로 토큰 재발급 요청 필요")
	public SuccessResponse<PostMasterLogInRes> refreshToken(
		@RequestHeader(value = "X-ACCESS-TOKEN") @NotBlank String accessToken,
		@RequestHeader(value = "REFRESH-TOKEN") @NotBlank String refreshToken) throws
		MasterException,
		BaseException, UserException {
		PostMasterLogInRes postMasterLoginRes = masterService.issueAccessToken(accessToken, refreshToken);
		return new SuccessResponse<>(postMasterLoginRes);
	}

}
