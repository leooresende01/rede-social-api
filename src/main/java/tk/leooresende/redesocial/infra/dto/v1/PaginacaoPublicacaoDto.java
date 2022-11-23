package tk.leooresende.redesocial.infra.dto.v1;

import java.util.List;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.util.CryptoUtil;
import tk.leooresende.redesocial.model.Publicacao;

public class PaginacaoPublicacaoDto {
	private List<PublicacaoDto> content;
	private boolean last;

	public List<PublicacaoDto> getPublicacoes() {
		return content;
	}

	public PaginacaoPublicacaoDto(List<Publicacao> publicacoes, boolean last) {
		this.content = PublicacaoDto.mapearListaDePublicacoesParaDto(publicacoes);
		this.last = last;
	}

	public void setPublicacoes(List<PublicacaoDto> publicacoes) {
		this.content = publicacoes;
	}

	public boolean getLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public CryptoRequestForm pegarPublicacoesComInformacoesCriptografadas() {
		Gson gson = new Gson();
		String publicaoesPaginadas = CryptoUtil.criptografar(gson.toJson(this));
		return new CryptoRequestForm(publicaoesPaginadas);
	}

}
