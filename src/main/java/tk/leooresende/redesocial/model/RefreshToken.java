package tk.leooresende.redesocial.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class RefreshToken {
	@Id
	private String usernameDono;
	@OneToOne(fetch = FetchType.EAGER)
	private Usuario dono;
	private String token;

	public RefreshToken() {
	}

	public RefreshToken(String token, Usuario dono) {
		this.token = token;
		this.dono = dono;
		this.usernameDono = dono.getUsername();
	}

	public String getUsernameDono() {
		return usernameDono;
	}

	public void setUsernameDono(String usernameDono) {
		this.usernameDono = usernameDono;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getDono() {
		return dono;
	}

	public void setDono(Usuario dono) {
		this.dono = dono;
	}

}
