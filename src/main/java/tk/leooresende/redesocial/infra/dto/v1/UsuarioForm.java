package tk.leooresende.redesocial.infra.dto.v1;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import tk.leooresende.redesocial.model.Roles;
import tk.leooresende.redesocial.model.Usuario;
import tk.leooresende.redesocial.model.UsuarioRoles;

public class UsuarioForm {
	private String username;
	private String password;
	private String nomeCompleto;
	private MultipartFile file;

	public UsuarioForm(String username, String password, String nomeCompleto, MultipartFile file) {
		this.username = username;
		this.password = password;
		this.nomeCompleto = nomeCompleto;
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

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

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Usuario mapearParaUsuario() {
		UsuarioRoles tipoDeUsuario = new UsuarioRoles(Roles.ROLE_USER);
		ArrayList<UsuarioRoles> roles = new ArrayList<UsuarioRoles>(Arrays.asList(tipoDeUsuario));
		Usuario usuario = new Usuario(this.username, this.password, this.nomeCompleto, roles);
		tipoDeUsuario.setUsuario(usuario);
		return usuario;
	}

}
