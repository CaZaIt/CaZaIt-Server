package shop.cazait.domain.master.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_MASTER;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.master.model.dto.request.MasterCreateInDTO;
import shop.cazait.domain.master.dto.request.MasterUpdateInDTO;
import shop.cazait.domain.master.dto.response.MasterCreateOutDTO;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.service.MasterService;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;

@Tag(name = "마스터 API")
@Validated
@RestController
@RequestMapping("/api/masters")
@RequiredArgsConstructor
public class MasterApiController {

	private final MasterService masterService;

	@NoAuth
	@PostMapping("/sign-up")
	@Operation(summary = "마스터 회원가입", description = "마스터 사용자의 정보들을 이용해서 회원가입을 진행한다.")
	public SuccessResponse<MasterCreateOutDTO> registerMaster(@Validated @RequestBody MasterCreateInDTO dto) throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		MasterCreateOutDTO postCreateMasterRes = masterService.registerMaster(dto);
		return new SuccessResponse<>(CREATE_MASTER, postCreateMasterRes);
	}

	@PatchMapping("/{masterId}")
	@Operation(summary = "마스터 정보 수정", description = "특정 ID의 마스터 관련 정보를 수정한다.")
	@Parameter(name = "masterId", description = "response로 발급 받은 계정 마스터 ID 번호")
	public SuccessResponse<String> updateMaster(
		@PathVariable(name = "masterId") UUID masterId,
		@RequestBody @Valid MasterUpdateInDTO masterUpdateInDTO) throws InvalidAlgorithmParameterException, MasterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

		masterService.updateMaster(masterId, masterUpdateInDTO);
		return new SuccessResponse<>(SUCCESS, "마스터 정보 수정 완료");
	}

	@DeleteMapping("/{masterId}")
	@Operation(summary = "마스터 계정 탈퇴", description = "특정 ID의 마스터 계정을 삭제한다.")
	@Parameter(name = "masterId", description = "탈퇴하고자 하는 마스터 ID 번호")
	public SuccessResponse<String> deleteMaster(@Validated @PathVariable UUID masterId) throws MasterException {
		masterService.removeMaster(masterId);
		String response = "회원 탈퇴가 성공하였습니다.";
		return new SuccessResponse<>(SUCCESS, response);
	}


}
