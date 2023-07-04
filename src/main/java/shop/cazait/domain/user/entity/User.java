package shop.cazait.domain.user.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;
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
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String refreshToken;

//    @Enumerated(EnumType.STRING)
//    private KakaoIntegrated isKakao;

    @Builder
    public User(UUID id, String email, String password, String nickname, String refreshToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.refreshToken = refreshToken;
//        this.isKakao = KakaoIntegrated.NORMAL;
    }
}

