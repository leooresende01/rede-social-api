package tk.leooresende.redesocial.infra.dto.v1;

import java.util.List;

import tk.leooresende.redesocial.model.Curtida;

public class CurtidaDto {
	private Long id;
	private String curtidor;
	private Long publicacaoId;
	
	
	public CurtidaDto(Curtida curtida) {
		this.id = curtida.getId();
		this.curtidor = curtida.getCurtidor().getUsername();
		this.publicacaoId = curtida.getPublicacao().getId();
	}

	public CurtidaDto(Long id, String curtidor, Long publicacaoId) {
		this.id = id;
		this.curtidor = curtidor;
		this.publicacaoId = publicacaoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurtidor() {
		return curtidor;
	}

	public void setCurtidor(String curtidor) {
		this.curtidor = curtidor;
	}

	public Long getPublicacaoId() {
		return publicacaoId;
	}

	public void setPublicacaoId(Long publicacaoId) {
		this.publicacaoId = publicacaoId;
	}

	public static List<CurtidaDto> mapearCurtidasParaDto(List<Curtida> curtidas) {
		return curtidas.stream()
				.map(CurtidaDto::new)
				.toList();
	}
}
