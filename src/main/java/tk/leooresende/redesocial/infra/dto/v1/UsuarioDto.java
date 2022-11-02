package tk.leooresende.redesocial.infra.dto.v1;

import java.util.ArrayList;
import java.util.List;

import tk.leooresende.redesocial.infra.util.SeguidorESeguindoUTIL;
import tk.leooresende.redesocial.model.Usuario;

public class UsuarioDto {
	private Integer id;
	private String username;
	private String nomeCompleto;
	private Integer publicacoes;
	private Integer seguidores;
	private Integer seguindo;
	private List<String> tipoDeUsuario;
	private Boolean ehSeguidoPeloUsuarioAutenticado;
	private Boolean segueOUsuarioAutenticado;
	private String dataDeRegistro;

	public UsuarioDto() {
	}

	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.username = usuario.getUsername();
		this.nomeCompleto = usuario.getNomeCompleto();
		this.tipoDeUsuario = usuario.getRoles().stream().map(tipo -> tipo.getAuthority()).toList();
		this.seguidores = usuario.getSeguidores().size();
		this.seguindo = usuario.getSeguindo().size();
		this.publicacoes = usuario.getPublicacoes().size();
		this.ehSeguidoPeloUsuarioAutenticado = SeguidorESeguindoUTIL.verificaSeOUsuarioAutenticadoSegueEle(usuario);
		this.segueOUsuarioAutenticado = SeguidorESeguindoUTIL.verificaSeEleSegueOUsuarioAutenticado(usuario);
		this.dataDeRegistro =usuario.getDataDeRegistro().toString();
	}

	public String getDataDeRegistro() {
		return dataDeRegistro;
	}

	public void setDataDeRegistro(String dataDeRegistro) {
		this.dataDeRegistro = dataDeRegistro;
	}

	public Integer getPublicacoes() {
		return publicacoes;
	}

	public void setPublicacoes(Integer publicacoes) {
		this.publicacoes = publicacoes;
	}

	public Integer getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(Integer seguidores) {
		this.seguidores = seguidores;
	}

	public Integer getSeguindo() {
		return seguindo;
	}

	public void setSeguindo(Integer seguindo) {
		this.seguindo = seguindo;
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

	public List<String> getTipoDeUsuario() {
		return tipoDeUsuario;
	}

	public void setTipoDeUsuario(List<String> tipoDeUsuario) {
		this.tipoDeUsuario = tipoDeUsuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEhSeguidoPeloUsuarioAutenticado() {
		return ehSeguidoPeloUsuarioAutenticado;
	}

	public Boolean getEhSeguidoPeloUsuarioAutenticado() {
		return ehSeguidoPeloUsuarioAutenticado;
	}

	public void setEhSeguidoPeloUsuarioAutenticado(Boolean ehSeguidoPeloUsuarioAutenticado) {
		this.ehSeguidoPeloUsuarioAutenticado = ehSeguidoPeloUsuarioAutenticado;
	}

	public Boolean getSegueOUsuarioAutenticado() {
		return segueOUsuarioAutenticado;
	}

	public void setSegueOUsuarioAutenticado(Boolean segueOUsuarioAutenticado) {
		this.segueOUsuarioAutenticado = segueOUsuarioAutenticado;
	}

	public void setEhSeguidoPeloUsuarioAutenticado(boolean ehSeguidoPeloUsuarioAutenticado) {
		this.ehSeguidoPeloUsuarioAutenticado = ehSeguidoPeloUsuarioAutenticado;
	}

	public static List<UsuarioDto> mapearUsuariosParaDTO(List<Usuario> usuarios) {
		return new ArrayList<>(usuarios.stream().map(UsuarioDto::new).toList());
	}

}
