package shop.cazait.domain.master.service;

import static shop.cazait.global.error.status.ErrorStatus.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.cazait.domain.master.dto.get.GetMasterRes;
import shop.cazait.domain.master.dto.patch.PatchMasterReq;
import shop.cazait.domain.master.dto.patch.PatchMasterRes;
import shop.cazait.domain.master.dto.post.PostMasterLogInReq;
import shop.cazait.domain.master.dto.post.PostMasterLogInRes;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.global.common.status.BaseStatus;
import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.Secret;

@Service
@RequiredArgsConstructor
@Transactional
public class MasterService {

	private final MasterRepository masterRepository;
	private final JwtService jwtService;
	private final UserRepository userRepository;

	//회원가입
	public PostMasterRes registerMaster(PostMasterReq dto) throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {

		//이메일 확인
		if (!masterRepository.findMasterByEmail(dto.getEmail()).isEmpty()) {
			throw new MasterException(DUPLICATE_USER_LOGIN_EMAIL);
		}

		//패스워드 암호화
		String password;
		password = new AES128(Secret.MASTER_INFO_PASSWORD_KEY).encrypt(dto.getPassword());
		PostMasterReq EncryptPostMasterReq = new PostMasterReq(dto.getEmail(), password, dto.getNickname());
		Master master = EncryptPostMasterReq.toEntity();
		masterRepository.save(master);
		PostMasterRes postMasterRes = PostMasterRes.of(master);
		return postMasterRes;

	}

	//마스터 회원 로그인
	public PostMasterLogInRes LoginMaster(PostMasterLogInReq dto) throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		if (masterRepository.findMasterByEmail(dto.getEmail()).isEmpty()) {
			throw new MasterException(FAILED_TO_LOGIN);
		}
		Master master = dto.toEntity();
		Master findMaster = masterRepository.findMasterByEmail(master.getEmail()).get();

		String password;
		password = new AES128(Secret.MASTER_INFO_PASSWORD_KEY).decrypt(findMaster.getPassword());

		Long masterIdx;
		if (password.equals(master.getPassword())) {
			masterIdx = findMaster.getId();
			String jwt = jwtService.createJwt(masterIdx);
			String refreshToken = jwtService.createRefreshToken();
			Master LoginMaster = Master.builder()
				.email(findMaster.getEmail())
				.password(findMaster.getPassword())
				.nickname(findMaster.getNickname())
				.refreshToken(refreshToken)
				.build();
			masterRepository.save(LoginMaster);
			return PostMasterLogInRes.of(findMaster, jwt, refreshToken);
		}
		throw new MasterException(FAILED_TO_LOGIN);
	}

	//마스터 회원 전체 조회
	@Transactional(readOnly = true)
	public List<GetMasterRes> getMasterByStatus(BaseStatus status) throws MasterException {
		List<Master> masterList = masterRepository.findMasterByStatus(status);
		List<GetMasterRes> masterResList = new ArrayList<>();
		for (Master master : masterList) {
			GetMasterRes masterRes = GetMasterRes.of(master);
			masterResList.add(masterRes);
		}
		if (masterResList.isEmpty()) {
			throw new MasterException(NOT_EXIST_MASTER);
		}
		return masterResList;
	}

	//마스터 회원 정보
	public PatchMasterRes updateMaster(Long id, PatchMasterReq putMasterReq) {
		Master findMaster = masterRepository.findMasterById(id).get();
		if (putMasterReq.getEmail() != null) {
			findMaster.changeMasterEmail(putMasterReq.getEmail());
		}
		if (putMasterReq.getPassword() != null) {
			findMaster.changeMasterPassword(putMasterReq.getPassword());
		}
		if (putMasterReq.getNickname() != null) {
			findMaster.changeMasterNickname(putMasterReq.getNickname());
		}

		Master updateMaster = masterRepository.save(findMaster);
		return PatchMasterRes.builder()
			.id(updateMaster.getId())
			.email(updateMaster.getEmail())
			.password(updateMaster.getPassword())
			.nickname(updateMaster.getNickname())
			.build();

	}

	// 회원 탈퇴하기
	public void removeMaster(int id) throws MasterException {
		Optional<Master> masterEntity = masterRepository.findMasterById(id);

		if (masterRepository.findMasterById(id).isEmpty()) {
			throw new MasterException(NOT_EXIST_MASTER);
		}

		if (masterEntity.get().getStatus().toString().equals("INACTIVE")) {
			throw new MasterException(ALREADY_INACTIVE_MASTER);
		}

		Master master = masterRepository.findMasterById(id)
			.orElseThrow(() -> new MasterException(NOT_EXIST_USER));
		master.changeMasterStatus(BaseStatus.INACTIVE);
		masterRepository.save(master);
	}

}