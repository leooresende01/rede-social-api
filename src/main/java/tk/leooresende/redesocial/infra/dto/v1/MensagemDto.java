package tk.leooresende.redesocial.infra.dto.v1;

import java.time.LocalDateTime;
import java.util.List;

import tk.leooresende.redesocial.infra.util.PublicacaoUtil;
import tk.leooresende.redesocial.model.Mensagem;

public class MensagemDto {
	private Long id;
	private String mensagem;
	private String emissor;
	private Long chatId;
	private String destinatario;
	private String dataDeEnvio;
	private LocalDateTime dataSemFormatar;

	public MensagemDto(Mensagem mensagem) {
		this.id = mensagem.getId();
		this.mensagem = mensagem.getMensagem();
		this.emissor = mensagem.getEmissor().getUsername();
		this.destinatario = mensagem.getDestinatario().getUsername();
		this.dataDeEnvio = PublicacaoUtil.pegarDataDePublicacaoFormatada(mensagem.getDataDeEnvio());
		this.chatId = mensagem.getChatDaMensagem().getId();
		this.dataSemFormatar = mensagem.getDataDeEnvio();
	}

	public LocalDateTime getDataSemFormatar() {
		return dataSemFormatar;
	}

	public void setDataSemFormatar(LocalDateTime dataSemFormatar) {
		this.dataSemFormatar = dataSemFormatar;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public String getDataDeEnvio() {
		return dataDeEnvio;
	}

	public void setDataDeEnvio(String dataDeEnvio) {
		this.dataDeEnvio = dataDeEnvio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public static MensagemDto pegarMensagemComoDto(Mensagem mensagemSalva) {
		return new MensagemDto(mensagemSalva);
	}

	public static List<MensagemDto> pegarListaDeMensagemComoDto(List<Mensagem> mensagensDoChat) {
		return mensagensDoChat.stream().map(MensagemDto::new).toList();
	}
}
