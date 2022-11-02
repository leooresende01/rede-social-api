package tk.leooresende.redesocial.infra.dto.v1;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class LoginForm {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Authentication pegarAuthentication() {
		return new UsernamePasswordAuthenticationToken(this.getUsername(), this.getPassword());
	}
}
