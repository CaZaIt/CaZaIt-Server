//package shop.cazait.domain.auth.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import shop.cazait.domain.auth.Role;
//import shop.cazait.domain.auth.dto.PostLoginReq;
//import shop.cazait.domain.auth.dto.PostLoginRes;
//import shop.cazait.domain.master.repository.MasterRepository;
//import shop.cazait.domain.user.dto.PostUserLoginReq;
//import shop.cazait.domain.user.dto.PostUserLoginRes;
//import shop.cazait.domain.user.entity.User;
//import shop.cazait.domain.user.exception.UserException;
//import shop.cazait.domain.user.repository.UserRepository;
//import shop.cazait.global.config.encrypt.AES128;
//import shop.cazait.global.config.encrypt.JwtService;
//import shop.cazait.global.config.encrypt.Secret;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//import static shop.cazait.global.error.status.ErrorStatus.FAILED_TO_LOGIN;
//
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class AuthService {
//    private final JwtService jwtService;
//
//    private final UserRepository userRepository;
//
//    private final MasterRepository masterRepository;
//    public PostLoginRes logIn(PostLoginReq postLoginReq)
//            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
//
//        Role role = postLoginReq.getRole();
//
//        if(role==Role.USER){
//            if(userRepository.findByEmail(postLoginReq.getEmail()).isEmpty()){
//                throw new UserException(FAILED_TO_LOGIN);
//            }
//        }
//        else if(role == Role.MASTER){
//            if(masterRepository.findMasterByEmail(postLoginReq.getEmail()).isEmpty()){
//                throw new UserException(FAILED_TO_LOGIN);
//            }
//        }
//
//
//
//        User user = postUserLoginReq.toEntity();
//        User findUser = userRepository.findByEmail(user.getEmail()).get();
//        String password;
//        password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(findUser.getPassword());
//        Long userIdx;
//        if (password.equals(user.getPassword())) {
//            userIdx = findUser.getId();
//            String jwt = jwtService.createJwt(userIdx);
//            String refreshToken = jwtService.createRefreshToken();
//            User loginUser = User.builder()
//                    .id(findUser.getId())
//                    .email(findUser.getEmail())
//                    .password(findUser.getPassword())
//                    .nickname(findUser.getNickname())
//                    .refreshToken(refreshToken)
//                    .build();
//            userRepository.save(loginUser);
//            return PostUserLoginRes.of(findUser, jwt, refreshToken);
//        }
//        throw new UserException(FAILED_TO_LOGIN);
//    }
//}
