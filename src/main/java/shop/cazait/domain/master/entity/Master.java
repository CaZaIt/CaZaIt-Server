package shop.cazait.domain.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.global.common.entity.BaseEntity;
import shop.cazait.global.common.status.BaseStatus;

;import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Master extends BaseEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(nullable = false)
	private String idNumber;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false, unique = true)
	private String nickname;

	@Column(nullable = true)
	private String refreshToken;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_id")
	private Cafe cafe;

	@Builder
	public Master(UUID id, String idNumber, String password, String phoneNumber, String nickname, String refreshToken, Cafe cafe) {
		this.id =id;
		this.idNumber = idNumber;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.refreshToken = refreshToken;
		this.cafe = cafe;
	}

	public void changeMasterEmail(String email) {
		this.idNumber = idNumber;
	}

	public void changeMasterPassword(String password) {
		this.password = password;
	}

	public void changeMasterNickname(String nickname) {
		this.nickname = nickname;
	}

	public void changeMasterStatus(BaseStatus status) {
		super.setStatus(status);
	}

	public void setCafe(Cafe cafe) {
		this.cafe = cafe;
	}
}
