package shop.cazait.domain.master.service;

import static shop.cazait.domain.master.error.MasterErrorStatus.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.cazait.domain.master.dto.get.GetMasterRes;
import shop.cazait.domain.master.dto.patch.PutMasterRes;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.global.config.encrypt.SHA256;

@Service
@RequiredArgsConstructor
public class MasterService {

	private final MasterRepository masterRepository;

	//회원가입
	@Transactional(readOnly = false)
	public PostMasterRes registerMaster(PostMasterReq dto) throws MasterException {

		//이메일 확인
		if (!masterRepository.findMasterByEmail(dto.getEmail()).isEmpty()) {
			throw new MasterException(DUPLICATE_USER_LOGIN_EMAIL);
		}

		//패스워드 암호화
		dto.setPassword(SHA256.encrypt(dto.getPassword()));

		Master master = dto.toEntity();
		masterRepository.save(master);

		PostMasterRes postMasterRes = PostMasterRes.ofDto(master);
		return postMasterRes;
	}

	//마스터 회원 전체 조회
	public List<GetMasterRes> getMasters(Long id) {
		List<Master> findMaster = masterRepository.findAllMasterById(id);
		List<GetMasterRes> masterRes = findMaster.stream()
			.map(master -> {
				return GetMasterRes.builder()
					.id(master.getId())
					.email(master.getEmail())
					.nickname(master.getNickname())
					.build();
			}).collect(Collectors.toList());
		return masterRes;
	}

	//마스터 회원 정보 수정
	public PutMasterRes updateMaster(Long id, PutMasterRes putMasterRes) {
		Master findMaster = masterRepository.findMasterById(id).get();
		if (putMasterRes.getEmail() != null) {
			findMaster.changeMasterEmail(putMasterRes.getEmail());
		}
		if (putMasterRes.getPassword() != null) {
			findMaster.changeMasterPassword(putMasterRes.getPassword());
		}
		if (putMasterRes.getNickname() != null) {
			findMaster.changeMasterNickname(putMasterRes.getNickname());
		}

		Master updateMaster = masterRepository.save(findMaster);
		return PutMasterRes.builder()
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
			throw new MasterException(NOT_EXISTS_MASTER);
		}

		if (masterEntity.get().getStatus().toString().equals("INACTIVE")) {
			throw new MasterException(ALREADY_INACTIVE_MASTER);
		}

		Master master = masterRepository.findMasterById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id=" + id));

		masterRepository.delete(master);
	}

}
