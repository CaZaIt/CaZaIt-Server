package shop.cazait.domain.user.entity;

import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;
import shop.cazait.domain.favorites.entity.Favorites;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.global.common.entity.BaseEntity;

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
    private String accountName;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorites> favorites;

    @Builder
    public User(UUID id, String accountName, String phoneNumber, String password, String nickname, String refreshToken, Long kakaoId) {
        this.id = id;
        this.accountName = accountName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.nickname = nickname;
        this.refreshToken = refreshToken;
        this.kakaoId = kakaoId;
    }

    public User updateUserRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }


    public static User kakaoSignUpUser(Long kakaoId){
        return User.builder()
                .accountName(generateRandomString())
                .password(generateRandomString())
                .phoneNumber(generateRandomString())
                .nickname(generateRandomString())
                .kakaoId(kakaoId)
                .build();
    }

    public User updateUserPassword(String password){
        this.password = password;

        return this;
    }

    public User updateUserNickname(String nickname){
        this.nickname = nickname;

        return this;
    }

    private static String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}

