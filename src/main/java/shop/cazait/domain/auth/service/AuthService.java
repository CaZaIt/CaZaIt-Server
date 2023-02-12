package shop.cazait.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.auth.Role;
import shop.cazait.domain.auth.dto.PostLoginReq;
import shop.cazait.domain.auth.dto.PostLoginRes;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.service.MasterService;

import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.service.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static shop.cazait.domain.auth.Role.USER;



@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserService userService;

    private final MasterService masterService;

    public PostLoginRes reIssueTokensByRole(Role exactRole, String accessToken, String refreshToken, Long userIdx) throws MasterException, UserException {
        if (exactRole.equals(USER)) {
            return userService.reIssueTokens(accessToken, refreshToken, userIdx);
        }
        else{
            return masterService.issueAccessToken(accessToken, refreshToken);
        }
    }

    public PostLoginRes logInByRole(Role exactRole, PostLoginReq postLoginReq) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MasterException {
        if (exactRole.equals(USER)) {
            return userService.logIn(postLoginReq);
        }
        else{
            return masterService.LoginMaster(postLoginReq);
        }
    }
}
