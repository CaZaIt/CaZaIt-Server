package shop.cazait.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.repository.UserRepository;



import java.util.ArrayList;
import java.util.List;


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
    @Transactional(readOnly=true)
    public List<GetUserRes> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<GetUserRes> userResList = new ArrayList<>();
        for (User user : allUsers) {
            GetUserRes of = GetUserRes.of(user);
            userResList.add(of);
        }
        return userResList;
    }
    @Transactional(readOnly = true)
    public GetUserRes getUserByEmail(String email){
        User findUser = userRepository.findByEmail(email).get();
        return GetUserRes.of(findUser);
    }

    public PatchUserRes modifyUser(String email, PatchUserReq patchUserReq){
        User modifyUser = patchUserReq.toEntity();
        User existUser = userRepository.findByEmail(email).get();

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
        User deleteUser = userRepository.findByEmail(email).get();
        userRepository.delete(deleteUser);
        return DeleteUserRes.of(deleteUser);
    }
}
