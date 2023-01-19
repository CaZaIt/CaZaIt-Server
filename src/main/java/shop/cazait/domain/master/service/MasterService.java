package shop.cazait.domain.master.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafefavorites.dto.GetCafeFavoritesRes;
import shop.cazait.domain.cafefavorites.entity.CafeFavorites;
import shop.cazait.domain.master.dto.get.GetMasterRes;
import shop.cazait.domain.master.dto.post.PostMasterReq;
import shop.cazait.domain.master.dto.post.PostMasterRes;
import shop.cazait.global.config.encrypt.SHA256;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.repository.MasterRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //마스터 회원 전체 조회
    public List<GetMasterRes> getMasters(Long id){
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


    // 회원 탈퇴하기
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
