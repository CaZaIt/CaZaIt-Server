package shop.cazait.domain.master.model.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.master.dto.request.MasterUpdateInDTO;
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
	private String accountName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false, unique = true)
	private String nickname;

	@Column(nullable = true)
	private String refreshToken;

	@OneToMany(mappedBy = "master", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Cafe> cafes;

	@Builder
	public Master(UUID id, String accountName, String password, String phoneNumber, String nickname, String refreshToken, List<Cafe> cafes) {
		this.id =id;
		this.accountName = accountName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.refreshToken = refreshToken;
		this.cafes = cafes;
	}

	public void changeMasterAccountName(String accountName) {
		this.accountName = accountName;
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

	public Master updateMasterProfile(MasterUpdateInDTO masterUpdateInDTO){
		this.accountName = masterUpdateInDTO.getAccountName();
		this.password = masterUpdateInDTO.getPassword();
		this.phoneNumber = masterUpdateInDTO.getPhoneNumber();
		this.nickname = masterUpdateInDTO.getNickname();

		return this;
	}

	public Master loginMaster(String refreshToken){
		this.refreshToken = refreshToken;

		return this;
	}
}
