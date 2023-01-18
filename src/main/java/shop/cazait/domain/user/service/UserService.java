package shop.cazait.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.cazait.domain.user.dto.PostLoginReq;
import shop.cazait.domain.user.dto.PostLoginRes;
import shop.cazait.domain.user.dto.PostUserReq;
import shop.cazait.domain.user.dto.PostUserRes;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.global.common.response.BaseResponse;
import shop.cazait.global.common.status.BaseErrorStatus;
import shop.cazait.global.error.BaseException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;


    public BaseResponse<PostUserRes> createUser(PostUserReq postUserReq){
        User user = postUserReq.toEntity();
        userRepository.save(user);
        return new BaseResponse<>(PostUserRes.of(user));
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) {
        User user = postLoginReq.toEntity();
        User findUser = userRepository.findByEmail(user.getEmail());
        if (findUser.getPassword().equals(user.getPassword())) {
            return PostLoginRes.of(findUser);
        }
        return new PostLoginRes(404L, "fail");
    }
}
