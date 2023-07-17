package shop.cazait.domain.master.service;

import static shop.cazait.global.error.status.ErrorStatus.EXIST_ACCOUNTNUMBER;
import static shop.cazait.global.error.status.ErrorStatus.EXIST_NICKNAME;
import static shop.cazait.global.error.status.ErrorStatus.EXIST_PHONENUMBER;
import static shop.cazait.global.error.status.ErrorStatus.FAILED_TO_LOGIN;
import static shop.cazait.global.error.status.ErrorStatus.INVALID_JWT;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_MASTER;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXPIRED_TOKEN;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.auth.dto.UserAuthenticateInDTO;
import shop.cazait.domain.auth.dto.UserAuthenticateOutDTO;
import shop.cazait.domain.master.dto.request.MasterUpdateInDTO;
import shop.cazait.domain.master.dto.response.MasterCreateOutDTO;
import shop.cazait.domain.master.dto.response.MasterUptateOutDTO;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.model.dto.request.MasterCreateInDTO;
import shop.cazait.domain.master.model.entity.Master;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.JwtService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MasterService {

	private final MasterRepository masterRepository;
	private final JwtService jwtService;

	@Value("${password-secret-key}")
	private String PASSWORD_SECRET_KEY;

	/**
	 * 마스터 회원 가입
	 */
	public MasterCreateOutDTO registerMaster(MasterCreateInDTO dto) throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {

		if (masterRepository.findMasterByAccountNumber(dto.getAccountNumber()).isPresent()) {
			throw new MasterException(EXIST_ACCOUNTNUMBER);
		}

		if (masterRepository.findByPhoneNumber(dto.getPhoneNumber()).isPresent()) {
			throw new MasterException(EXIST_PHONENUMBER);
		}

		if (masterRepository.findMasterByNickname(dto.getNickname()).isPresent()) {
			throw new MasterException(EXIST_NICKNAME);
		}

		String encryptedMasterPassword = encryptPassword(dto.getPassword());
		MasterCreateInDTO masterCreateInDTO = dto.encryptMasterCreateDTO(encryptedMasterPassword);

		Master master = MasterCreateInDTO.toEntity(masterCreateInDTO);
		masterRepository.save(master);

		return MasterCreateOutDTO.of(master);

	}

	/**
	 * 마스터 로그인
	 */
	public UserAuthenticateOutDTO LoginMaster(UserAuthenticateInDTO userAuthenticateInDTO) throws
		MasterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {

		Master findMaster = masterRepository.findMasterByAccountNumber(userAuthenticateInDTO.getAccountNumber())
				.orElseThrow(() -> new MasterException(FAILED_TO_LOGIN));

		String findMasterPassword = findMaster.getPassword();
		String loginPassword = encryptPassword(userAuthenticateInDTO.getPassword());

		if (!findMasterPassword.equals(loginPassword)){
			throw new MasterException(FAILED_TO_LOGIN);
		}

		UUID masterIdx = findMaster.getId();
		String accessToken = jwtService.createJwt(masterIdx);
		String refreshToken = jwtService.createRefreshToken();

		findMaster.loginMaster(refreshToken);
		masterRepository.save(findMaster);

		return UserAuthenticateOutDTO.of(findMaster, accessToken);

	}

	/**
	 * 수정
	 */
	public MasterUptateOutDTO updateMaster(UUID id, MasterUpdateInDTO masterUpdateInDTO) throws MasterException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
		Master master = masterRepository.findMasterById(id)
				.orElseThrow(()->new MasterException(NOT_EXIST_MASTER));

		String encryptPassword = encryptPassword(masterUpdateInDTO.getPassword());
		MasterUpdateInDTO encryptedUserUpdateDTO = masterUpdateInDTO.encryptMasterUpdateDTO(encryptPassword);

		Master updatedMaster = master.updateMasterProfile(encryptedUserUpdateDTO);
		return MasterUptateOutDTO.of(updatedMaster);
	}

	/**
	 * 삭제
	*/
	public void removeMaster(UUID id) throws MasterException {
		Master master = masterRepository.findMasterById(id).get();

		if (masterRepository.findMasterById(id).isEmpty()) {
			throw new MasterException(NOT_EXIST_MASTER);
		}

		masterRepository.delete(master);
	}

	public String encryptPassword(String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
		return new AES128(PASSWORD_SECRET_KEY).encrypt(password);
	}

	/**
	 *  토큰 재발급
	 */
	public UserAuthenticateOutDTO issueAccessToken(String accessToken, String refreshToken) throws
		MasterException,
		UserException {

		Master master = null;
		UUID masterIdx = null;

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
		return UserAuthenticateOutDTO.of(master, accessToken);
	}

}