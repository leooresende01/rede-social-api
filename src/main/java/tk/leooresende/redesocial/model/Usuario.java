package tk.leooresende.redesocial.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tk.leooresende.redesocial.infra.util.PublicacaoUtil;

@Entity
@Table(name = "Usuarios")
public class Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String nomeCompleto;
	private LocalDateTime dataDeRegistro = PublicacaoUtil.pegarDataDeAgora();
	@Lob
	private byte[] imagemDoPerfil;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
	private List<UsuarioRoles> roles;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dono")
	private List<Publicacao> publicacoes = new ArrayList<>();
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Usuario> seguidores = new ArrayList<>();
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Usuario> seguindo = new ArrayList<>();

	public Usuario() {
	}

	public Usuario(String username, String password, String nomeCompleto, List<UsuarioRoles> roles) {
		this.username = username;
		this.password = password;
		this.nomeCompleto = nomeCompleto;
		this.roles = roles;
	}

	public LocalDateTime getDataDeRegistro() {
		return dataDeRegistro;
	}

	public void setDataDeRegistro(LocalDateTime dataDeRegistro) {
		this.dataDeRegistro = dataDeRegistro;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	public byte[] getImagemDoPerfil() {
		return imagemDoPerfil;
	}

	public void setImagemDoPerfil(byte[] imagemDoPerfil) {
		this.imagemDoPerfil = imagemDoPerfil;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public List<UsuarioRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<UsuarioRoles> roles) {
		this.roles = roles;
	}

	public List<Publicacao> getPublicacoes() {
		return publicacoes;
	}

	public void setPublicacoes(List<Publicacao> publicacoes) {
		this.publicacoes = publicacoes;
	}

	public List<Usuario> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<Usuario> seguidores) {
		this.seguidores = seguidores;
	}

	public List<Usuario> getSeguindo() {
		return seguindo;
	}

	public void setSeguindo(List<Usuario> seguindo) {
		this.seguindo = seguindo;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
