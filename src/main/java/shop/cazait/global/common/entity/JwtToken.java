package shop.cazait.global.common.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtToken {

	private String grantType;
	private String accessToken;
	private String refreshToken;
	private String key;

	@Builder
	public JwtToken(String grantType, String accessToken, String refreshToken, String key) {
		this.grantType = grantType;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.key = key;
	}

}
