package shop.cazait.domain.user.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.Secret;
import shop.cazait.global.error.status.ErrorStatus;

import static shop.cazait.global.error.status.ErrorStatus.*;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public PostUserRes createUser(PostUserReq postUserReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        if(postUserReq.getEmail().isEmpty()){
            throw new UserException(EMPTY_EMAIL);
        }
        if(postUserReq.getPassword().isEmpty()){
            throw new UserException(EMPTY_PASSWORD);
        }
        if(postUserReq.getNickname().isEmpty()){
            throw new UserException(EMPTY_NICKNAME);
        }

        if(!userRepository.findByEmail(postUserReq.getEmail()).isEmpty()){
            throw new UserException(EXIST_EMAIL);
        }

        if(!userRepository.findByNickname(postUserReq.getNickname()).isEmpty()){
            throw new UserException(EXIST_NICKNAME);
        }
        
        String pwd;
        pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
        PostUserReq EncryptPostUserReq = new PostUserReq(postUserReq.getEmail(), pwd, postUserReq.getNickname());
        User user = EncryptPostUserReq.toEntity();
        Long userIdx = user.getId();
        String jwt = jwtService.createJwt(userIdx);
        userRepository.save(user);
        return PostUserRes.of(user,jwt);
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User user = postLoginReq.toEntity();
        User findUser = userRepository.findByEmail(user.getEmail()).get();
        String password;
        password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(findUser.getPassword());
        Long userIdx;
        if (password.equals(user.getPassword())) {
            userIdx = findUser.getId();
            String jwt = jwtService.createJwt(userIdx);
            return PostLoginRes.of(findUser, jwt);
        }
        throw new UserException(ErrorStatus.FAILED_TO_LOGIN);
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
