package shop.cazait.domain.master.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_MASTER;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.master.dto.patch.PatchMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.service.MasterService;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;

@Tag(name = "마스터 API")
@RestController
@RequestMapping("/api/masters")
@RequiredArgsConstructor
public class MasterController {

	private final MasterService masterService;

	@NoAuth
	@PostMapping("/sign-up")
	@Operation(summary = "마스터 회원가입", description = "마스터 사용자의 정보들을 이용해서 회원가입을 진행한다.")
	public SuccessResponse<PostMasterRes> registerMaster(@Validated @RequestBody PostMasterReq dto) throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		PostMasterRes postCreateMasterRes = masterService.registerMaster(dto);
		return new SuccessResponse<>(CREATE_MASTER, postCreateMasterRes);
	}

//	@NoAuth
//	@PostMapping("/log-in")
//	@Operation(value = "회원 로그인", notes = "이메일과 패스워드를 통해 로그인을 진행")
//	public SuccessResponse<PostMasterLogInRes> logIn(@RequestBody PostMasterLogInReq postMasterLogInReq)
//		throws
//		MasterException,
//		InvalidAlgorithmParameterException,
//		NoSuchPaddingException,
//		IllegalBlockSizeException,
//		NoSuchAlgorithmException,
//		BadPaddingException,
//		InvalidKeyException {
//		PostMasterLogInRes postMasterLogInRes = masterService.LoginMaster(postMasterLogInReq);
//		return new SuccessResponse<>(postMasterLogInRes);
//	}

//	@GetMapping("/all")
//	@Operation(value = "마스터 계정 전체 조회", notes = "ACTIVE한 마스터 계정을 조회한다.")
//	public SuccessResponse<List<GetMasterRes>> getMasterByStatus() throws MasterException {
//		List<GetMasterRes> masterResList = masterService.getMasterByStatus(BaseStatus.ACTIVE);
//		return new SuccessResponse<>(masterResList);
//	}

	@PatchMapping("/update/{cafeId}")
	@Operation(summary = "마스터 정보 수정", description = "특정 ID의 마스터 관련 정보를 수정한다.")
	@Parameters({
		@Parameter(name = "masterId", description = "마스터 ID"),
	})
	public SuccessResponse<String> updateMaster(@PathVariable Long masterId, @RequestBody PatchMasterReq masterReq) {
		masterService.updateMaster(masterId, masterReq);
		return new SuccessResponse<>(SUCCESS,"카페 수정 완료");
	}

	@DeleteMapping
	@Operation(summary = "마스터 계정 탈퇴(상태  변경)", description = "특정 ID의 마스터 상태를 INACTIVE로 변경한다.")
	@Parameters({
		@Parameter(name = "Id", description = "탈퇴하고자 하는 마스터 ID"),
	})
	public SuccessResponse<String> deleteMaster(@Validated @PathVariable Long id) throws MasterException {
		masterService.removeMaster(id);
		String response = "회원 탈퇴가 성공하였습니다.";
		return new SuccessResponse<>(SUCCESS, response);
	}

//	@PostMapping(value = "/refresh")
//	@Operation(value = "토큰 재발급", notes = "인터셉터에서 accesstoken이 만료되고 난 후 클라이언트에서 해당 api로 토큰 재발급 요청 필요")
//	public SuccessResponse<PostMasterLogInRes> refreshToken(
//		@RequestHeader(value = "X-ACCESS-TOKEN") @NotBlank String accessToken,
//		@RequestHeader(value = "REFRESH-TOKEN") @NotBlank String refreshToken) throws
//		MasterException,
//		BaseException, UserException {
//		PostMasterLogInRes postMasterLoginRes = masterService.issueAccessToken(accessToken, refreshToken);
//		return new SuccessResponse<>(postMasterLoginRes);
//	}

}
