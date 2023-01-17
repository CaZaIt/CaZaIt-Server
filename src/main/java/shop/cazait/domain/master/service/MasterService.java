package shop.cazait.domain.master.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.master.dto.post.PostRegisterReq;
import shop.cazait.domain.master.dto.post.PostRegisterRes;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.global.error.BaseException;

import static shop.cazait.domain.master.error.MasterResStatus.DUPLICATE_USER_LOGIN_EMAIL;

@Service
@RequiredArgsConstructor
public class MasterService {

    private final MasterRepository masterRepository;

    @Transactional(readOnly = false)
    public PostRegisterRes registerMaster(PostRegisterReq dto) throws MasterException {

        //이메일 확인
        if (!masterRepository.findMasterByEmail(dto.getEmail()).isEmpty()) {
            throw new MasterException(DUPLICATE_USER_LOGIN_EMAIL);
        }

        //패스워드 암호화
        dto.setPassword(SHA256.encrypt(dto.getPassword()));

        Master master = dto.toEntity();
        masterRepository.save(master);

        PostRegisterRes postRegisterRes = PostRegisterRes.toDto(master);
        return postRegisterRes;
    }

}
