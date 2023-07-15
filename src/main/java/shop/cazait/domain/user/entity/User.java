package shop.cazait.domain.user.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import shop.cazait.domain.user.dto.UserUpdateInDTO;
import shop.cazait.global.common.entity.BaseEntity;
import shop.cazait.global.common.status.BaseStatus;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String refreshToken;

    @Column(nullable = true)
    private Long kakaoId;

    @Builder
    public User(UUID id, String accountNumber, String phoneNumber, String password, String nickname, String refreshToken, Long kakaoId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.nickname = nickname;
        this.refreshToken = refreshToken;
        this.kakaoId = kakaoId;
    }

    public User loginUser(String refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }

    public static User updateUserProfile(UUID userIdx, String refreshToken, UserUpdateInDTO userUpdateInDTO){
        return User.builder()
                .id(userIdx)
                .accountNumber(userUpdateInDTO.getAccountNumber())
                .password(userUpdateInDTO.getPassword())
                .phoneNumber(userUpdateInDTO.getPhoneNumber())
                .nickname(userUpdateInDTO.getNickname())
                .refreshToken(refreshToken)
                .build();
    }

    public static User kakaoSignUpUser(Long kakaoId){
        return User.builder()
                .accountNumber(generateRandomString())
                .password(generateRandomString())
                .phoneNumber(generateRandomString())
                .nickname(generateRandomString())
                .kakaoId(kakaoId)
                .build();
    }

    public static User kakaoLoginUser(User user, String refreshToken) {
        return User.builder()
                .id(user.getId())
                .accountNumber(user.getAccountNumber())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .kakaoId(user.getKakaoId())
                .refreshToken(refreshToken)
                .build();
    }

    private static String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}

