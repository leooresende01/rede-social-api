package tk.leooresende.redesocial.infra.dto.v1;

import tk.leooresende.redesocial.infra.util.TokenUtil;

public class LoginDto {
	private String token;
	private final String authType = "Bearer";
	private final String refreshToken;
	private final Long expirationTime = TokenUtil.EXP_TOKEN_TIME;

	public LoginDto(String token, String refreshToken) {
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAuthType() {
		return authType;
	}

	public Long getExpirationTime() {
		return expirationTime;
	}
}
