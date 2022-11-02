package tk.leooresende.redesocial.infra.dto.v1;

import java.util.ArrayList;
import java.util.List;

import tk.leooresende.redesocial.infra.util.CurtidaUtil;
import tk.leooresende.redesocial.infra.util.PublicacaoUtil;
import tk.leooresende.redesocial.model.Publicacao;

public class PublicacaoDto {
	private Long id;
	private Integer comentarios;
	private Integer curtidas;
	private String dono;
	private Boolean usuarioAutenticadoCurtiu;
	private String dataDePublicacao;
	private String legenda;
	private CurtidaDto primeiraCurtida;

	public PublicacaoDto(Publicacao publicacao) {
		this.id = publicacao.getId();
		this.curtidas = publicacao.getCurtidas().size();
		this.comentarios = publicacao.getComentarios().size();
		this.dono = publicacao.getDono().getUsername();
		this.dataDePublicacao = PublicacaoUtil.pegarDataDePublicacaoFormatada(publicacao.getDataDePublicacao());
		this.usuarioAutenticadoCurtiu = CurtidaUtil.verificaSeOUsuarioAutenticadoCurtiuAPublicao(publicacao);
		this.legenda = publicacao.getLegenda();
		this.primeiraCurtida = CurtidaUtil.pegarUmaPessoaAleatoriaQueCurtiu(publicacao.getCurtidas());
	}

	public PublicacaoDto() {
	}

	public Integer getComentarios() {
		return comentarios;
	}

	public void setComentarios(Integer comentarios) {
		this.comentarios = comentarios;
	}

	public String getLegenda() {
		return legenda;
	}

	public void setLegenda(String legenda) {
		this.legenda = legenda;
	}

	public Long getId() {
		return id;
	}

	public CurtidaDto getPrimeiraCurtida() {
		return primeiraCurtida;
	}

	public void setPrimeiraCurtida(CurtidaDto primeiraCurtida) {
		this.primeiraCurtida = primeiraCurtida;
	}

	public String getDataDePublicacao() {
		return dataDePublicacao;
	}

	public void setDataDePublicacao(String dataDePublicacao) {
		this.dataDePublicacao = dataDePublicacao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(Integer curtidas) {
		this.curtidas = curtidas;
	}

	public String getDono() {
		return dono;
	}

	public void setDono(String dono) {
		this.dono = dono;
	}

	public Boolean getUsuarioAutenticadoCurtiu() {
		return usuarioAutenticadoCurtiu;
	}

	public void setUsuarioAutenticadoCurtiu(Boolean usuarioAutenticadoCurtiu) {
		this.usuarioAutenticadoCurtiu = usuarioAutenticadoCurtiu;
	}

	public static List<PublicacaoDto> mapearListaDePublicacoesParaDto(List<Publicacao> publicacoes) {
		return new ArrayList<PublicacaoDto>(publicacoes.stream().map(PublicacaoDto::new).toList());
	}
}
