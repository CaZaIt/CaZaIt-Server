package shop.cazait.domain.master.entity;

import javax.persistence.*;;
import lombok.*;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.global.common.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Master extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Builder
    public Master(String email, String password, String nickname, Cafe cafe){
        this.email =email;
        this.password=password;
        this.nickname=nickname;
        this.cafe=cafe;
    }
}
