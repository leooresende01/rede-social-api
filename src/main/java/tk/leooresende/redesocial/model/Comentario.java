package tk.leooresende.redesocial.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tk.leooresende.redesocial.infra.util.PublicacaoUtil;

@Entity
@Table(name = "Comentarios")
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Publicacao publicacao;
	@ManyToOne
	private Usuario comentador;
	private String comentario;
	private LocalDateTime dataDoComentario = PublicacaoUtil.pegarDataDeAgora();

	public Comentario() {
	}

	public Comentario(Publicacao publicacao, Usuario comentador, String comentario) {
		this.publicacao = publicacao;
		this.comentador = comentador;
		this.comentario = comentario;
	}

	public LocalDateTime getDataDoComentario() {
		return dataDoComentario;
	}

	public void setDataDoComentario(LocalDateTime dataDoComentario) {
		this.dataDoComentario = dataDoComentario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}

	public Usuario getComentador() {
		return comentador;
	}

	public void setComentador(Usuario comentador) {
		this.comentador = comentador;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
