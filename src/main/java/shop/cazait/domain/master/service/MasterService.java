package shop.cazait.domain.master.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.global.config.encrypt.SHA256;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.repository.MasterRepository;

import java.util.Optional;

import static shop.cazait.domain.master.error.MasterResStatus.*;

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

        PostMasterRes postMasterRes = PostMasterRes.toDto(master);
        return postMasterRes;
    }


    // 회원 탈퇴하기
    @Transactional(readOnly = false)
    public void removeMaster(int id) throws MasterException{
        Optional<Master> masterEntity = masterRepository.findMasterById(id);

        if(masterRepository.findMasterById(id).isEmpty()){
            throw new MasterException(NOT_EXISTS_MASTER);
        }

        if(masterEntity.get().getStatus().toString().equals("INACTIVE")){
            throw new MasterException(ALREADY_INACTIVE_MASTER);
        }

        Master master = masterRepository.findMasterById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id=" + id));

        masterRepository.delete(master);
    }

}
