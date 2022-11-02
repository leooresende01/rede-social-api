package tk.leooresende.redesocial.infra.dto.v1;

import java.util.List;

import tk.leooresende.redesocial.infra.util.PublicacaoUtil;
import tk.leooresende.redesocial.model.Comentario;

public class ComentarioDto {
	private Long id;
	private String comentador;
	private Long publicacao;
	private String comentario;
	private String dataDoComentario;

	public ComentarioDto() {
	}

	public ComentarioDto(Comentario comentario) {
		this.id = comentario.getId();
		this.comentador = comentario.getComentador().getUsername();
		this.publicacao = comentario.getPublicacao().getId();
		this.comentario = comentario.getComentario();
		this.dataDoComentario = PublicacaoUtil.pegarDataDePublicacaoFormatada(comentario.getDataDoComentario());
	}

	public String getDataDoComentario() {
		return dataDoComentario;
	}

	public void setDataDoComentario(String dataDoComentario) {
		this.dataDoComentario = dataDoComentario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentador() {
		return comentador;
	}

	public void setComentador(String comentador) {
		this.comentador = comentador;
	}

	public Long getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Long publicacao) {
		this.publicacao = publicacao;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public static List<ComentarioDto> mapearComentariosParaDto(List<Comentario> comentarios) {
		return comentarios.stream().map(ComentarioDto::new).toList();
	}

}
