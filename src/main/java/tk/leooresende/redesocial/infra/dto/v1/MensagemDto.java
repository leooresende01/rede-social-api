package tk.leooresende.redesocial.infra.dto.v1;

public class MensagemDto {
	private String mensagem;

	public MensagemDto(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
