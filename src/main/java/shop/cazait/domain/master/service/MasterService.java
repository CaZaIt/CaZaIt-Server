package shop.cazait.domain.master.service;

import static shop.cazait.domain.auth.Role.*;
import static shop.cazait.global.error.status.ErrorStatus.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.cazait.domain.auth.dto.PostLoginReq;
import shop.cazait.domain.auth.dto.PostLoginRes;
import shop.cazait.domain.master.dto.get.GetMasterRes;
import shop.cazait.domain.master.dto.patch.PatchMasterReq;
import shop.cazait.domain.master.dto.patch.PatchMasterRes;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.status.BaseStatus;
import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.Secret;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MasterService {

	private final MasterRepository masterRepository;
	private final JwtService jwtService;

	/**
	 * 마스터 회원 가입
	 */
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
			throw new MasterException(EXIST_EMAIL);
		}
		//닉네임 중복확인
		if (!masterRepository.findMasterByNickname(dto.getNickname()).isEmpty()) {
			throw new MasterException(EXIST_NICKNAME);
		}

		// 마스터 엔티티 생성
		Master master = dto.toEntity();
		masterRepository.save(master);

		return PostMasterRes.of(master);

	}

	/**
	 * 마스터 로그인
	 */
	public PostLoginRes LoginMaster(PostLoginReq dto) throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {

		if (masterRepository.findMasterByEmail(dto.getEmail()).isEmpty()) {
			throw new MasterException(NOT_EXIST_MASTER);
		}

		Master findMaster = masterRepository.findMasterByEmail(dto.getEmail()).get();

		String password = new AES128(Secret.MASTER_INFO_PASSWORD_KEY).decrypt(findMaster.getPassword());

		Long masterIdx;
		if (password.equals(dto.getPassword())) {
			masterIdx = findMaster.getId();
			String jwt = jwtService.createJwt(masterIdx);
			String refreshToken = jwtService.createRefreshToken();

			findMaster = Master.builder()
				.id(masterIdx)
				.email(findMaster.getEmail())
				.password(findMaster.getPassword())
				.nickname(findMaster.getNickname())
				.refreshToken(refreshToken)
				.build();
			//			findMaster.builder()
			//							.refreshToken(refreshToken)
			//					        .build();
			masterRepository.save(findMaster);
			return PostLoginRes.of(findMaster, jwt, refreshToken, MASTER);
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

	//마스터 회원 정보 업데이트
	public PatchMasterRes updateMaster(Long id, PatchMasterReq patchMasterReq) {
		Master findMaster = masterRepository.findMasterById(id).get();
		Master updateMaster = masterRepository.save(findMaster);
		return PatchMasterRes.builder()
			.id(updateMaster.getId())
			.email(updateMaster.getEmail())
			.password(updateMaster.getPassword())
			.nickname(updateMaster.getNickname())
			.build();

	}

	// 회원 탈퇴하기
	public void removeMaster(Long id) throws MasterException {
		Master master = masterRepository.findMasterById(id).get();

		if (masterRepository.findMasterById(id).isEmpty()) {
			throw new MasterException(NOT_EXIST_MASTER);
		}

		masterRepository.delete(master);
	}

	// 토큰 재발급
	public PostLoginRes issueAccessToken(String accessToken, String refreshToken) throws
		MasterException,
		UserException {

		Master master = null;
		Long masterIdx = null;

		log.info("accessToken = " + accessToken);
		log.info("refreshToken = " + refreshToken);

		if (jwtService.isValidAccessTokenInRefresh(accessToken)) {
			log.info("아직 accesstoken 유효");
			throw new MasterException(NOT_EXPIRED_TOKEN);
		} else {
			log.info("Access 토큰 만료됨");
			if (jwtService.isValidRefreshTokenInRefresh(refreshToken)) {     //들어온 Refresh 토큰이 유효한지
				log.info("아직 refreshtoken 유효함");
				masterIdx = jwtService.getUserIdx(accessToken);
				master = masterRepository.findById(masterIdx).get();
				String tokenFromDB = master.getRefreshToken();
				log.info("userIdx from accessToken: " + masterIdx);
				log.info("refreshToken found by accessToken(userIdx): " + tokenFromDB);

				if (refreshToken.equals(tokenFromDB)) {
					log.info("Access token 재발급");
					accessToken = jwtService.createJwt(masterIdx);
				} else {
					log.error("Refresh Token Tampered, not equal from db refreshtoken");
					throw new MasterException(INVALID_JWT);
				}
			} else {
				log.info("refresh token 재발급");
				masterIdx = jwtService.getUserIdx(accessToken);
				master = masterRepository.findById(masterIdx).get();
				accessToken = jwtService.createJwt(masterIdx);
				refreshToken = jwtService.createRefreshToken();
			}
		}
		return PostLoginRes.of(master, accessToken, refreshToken, MASTER);
	}

}