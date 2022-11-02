package tk.leooresende.redesocial.infra.dto.v1;

import org.springframework.web.multipart.MultipartFile;

import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Usuario;

public class UsuarioAtualizadoForm {
	private String username;
	private String nomeCompleto;
	private MultipartFile file;

	public UsuarioAtualizadoForm() {
	}

	public UsuarioAtualizadoForm(String username, String nomeCompleto, MultipartFile file) {
		this.username = username;
		this.nomeCompleto = nomeCompleto;
		this.file = file;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public void atualizarInformacoesDoUsuario(Usuario usuario) {
		usuario.setUsername(this.username);
		usuario.setNomeCompleto(this.nomeCompleto);
		UsuarioUtil.salvarImagemDoUsuario(usuario, file);
	}
}
