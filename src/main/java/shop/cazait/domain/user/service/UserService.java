package shop.cazait.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.global.common.response.BaseResponse;
import shop.cazait.global.common.status.BaseErrorStatus;
import shop.cazait.global.error.BaseException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;


    public PostUserRes createUser(PostUserReq postUserReq){
        User user = postUserReq.toEntity();
        userRepository.save(user);
        return PostUserRes.of(user);
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) {
        User user = postLoginReq.toEntity();
        User findUser = userRepository.findByEmail(user.getEmail());
        if (findUser.getPassword().equals(user.getPassword())) {
            return PostLoginRes.of(findUser);
        }
        return new PostLoginRes(404L, "fail");
    }

    public List<GetUserRes> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<GetUserRes> userResList = new ArrayList<>();
        for (User user : allUsers) {
            GetUserRes of = GetUserRes.of(user);
            userResList.add(of);
        }
        return userResList;
    }

    public GetUserRes getUserByEmail(String email){
        User findUser = userRepository.findByEmail(email);
        return GetUserRes.of(findUser);
    }

    public PatchUserRes modifyUser(String email, PatchUserReq patchUserReq){
        User modifyUser = patchUserReq.toEntity();
        User existUser = userRepository.findByEmail(email);

        existUser = User.builder()
                .id(existUser.getId())
                .email(modifyUser.getEmail())
                .password(modifyUser.getPassword())
                .nickname(modifyUser.getNickname())
                .build();
        userRepository.save(existUser);
        return PatchUserRes.of(existUser);
    }

    public DeleteUserRes deleteUser(String email){
        User deleteUser = userRepository.findByEmail(email);
        userRepository.delete(deleteUser);
        return DeleteUserRes.of(deleteUser);
    }
}
