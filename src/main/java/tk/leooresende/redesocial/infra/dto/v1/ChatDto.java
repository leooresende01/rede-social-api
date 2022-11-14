package tk.leooresende.redesocial.infra.dto.v1;

import java.util.ArrayList;
import java.util.List;

import tk.leooresende.redesocial.infra.util.ChatUtil;
import tk.leooresende.redesocial.infra.util.PublicacaoUtil;
import tk.leooresende.redesocial.model.Chat;

public class ChatDto {
	private Long id;
	private String usuarioQueIniciou;
	private String oOutroUsuarioDoChat;
	private String dataDeCriacao;
	private MensagemDto ultimaMensagem;

	public ChatDto(Chat chat) {
		this.id = chat.getId();
		this.usuarioQueIniciou = chat.getUsuarioQueIniciou().getUsername();
		this.oOutroUsuarioDoChat = chat.getOutroUsuarioDoChat().getUsername();
		this.dataDeCriacao = PublicacaoUtil.pegarDataDePublicacaoFormatada(chat.getDataDeCriacao());
		this.ultimaMensagem = ChatUtil.pegarUltimaMensagem(chat);
	}

	public ChatDto(Long id, String usuarioQueIniciou, String oOutroUsuarioDoChat, String dataDeCriacao) {
		this.id = id;
		this.usuarioQueIniciou = usuarioQueIniciou;
		this.oOutroUsuarioDoChat = oOutroUsuarioDoChat;
		this.dataDeCriacao = dataDeCriacao;
	}

	public MensagemDto getUltimaMensagem() {
		return ultimaMensagem;
	}

	public void setUltimaMensagem(MensagemDto ultimaMensagem) {
		this.ultimaMensagem = ultimaMensagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuarioQueIniciou() {
		return usuarioQueIniciou;
	}

	public void setUsuarioQueIniciou(String usuarioQueIniciou) {
		this.usuarioQueIniciou = usuarioQueIniciou;
	}

	public String getoOutroUsuarioDoChat() {
		return oOutroUsuarioDoChat;
	}

	public void setoOutroUsuarioDoChat(String oOutroUsuarioDoChat) {
		this.oOutroUsuarioDoChat = oOutroUsuarioDoChat;
	}

	public String getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(String dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	public static List<ChatDto> mapearListaDeChatsParaDto(List<Chat> chatsDoUsuario) {
		return new ArrayList<>(chatsDoUsuario.stream().map(ChatDto::new).toList());
	}
}
