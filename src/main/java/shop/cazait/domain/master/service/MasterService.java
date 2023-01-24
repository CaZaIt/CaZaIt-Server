package shop.cazait.domain.master.service;

import static shop.cazait.global.error.status.ErrorStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.cazait.domain.master.dto.get.GetMasterRes;
import shop.cazait.domain.master.dto.patch.PutMasterReq;
import shop.cazait.domain.master.dto.patch.PutMasterRes;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.global.common.status.BaseStatus;
import shop.cazait.global.config.encrypt.SHA256;

@Service
@RequiredArgsConstructor
@Transactional
public class MasterService {

	private final MasterRepository masterRepository;

	//회원가입
	public PostMasterRes registerMaster(PostMasterReq dto) throws MasterException {

		//이메일 확인
		if (!masterRepository.findMasterByEmail(dto.getEmail()).isEmpty()) {
			throw new MasterException(DUPLICATE_USER_LOGIN_EMAIL);
		}

		//패스워드 암호화
		dto.setPassword(SHA256.encrypt(dto.getPassword()));

		Master master = dto.toEntity();
		masterRepository.save(master);

		PostMasterRes postMasterRes = PostMasterRes.of(master);
		return postMasterRes;
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
	public PutMasterRes updateMaster(Long id, PutMasterReq putMasterReq) {
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