package tk.leooresende.redesocial.infra.util;

import java.util.List;

import tk.leooresende.redesocial.infra.dto.v1.ChatDto;
import tk.leooresende.redesocial.infra.dto.v1.MensagemDto;
import tk.leooresende.redesocial.model.Chat;
import tk.leooresende.redesocial.model.Mensagem;

public class ChatUtil {

	public static MensagemDto pegarUltimaMensagem(Chat chat) {
		List<Mensagem> mensagens = chat.getMensagens();
		try {
			mensagens.sort((o1, o2) -> o2.getDataDeEnvio().compareTo(o1.getDataDeEnvio()));
			return new MensagemDto(mensagens.get(0));
		} catch (Exception ex) {
			return null;
		}
	}

	public static void ordenarMensagensPorMaisRecentes(List<Mensagem> mensagensDoChat) {
		mensagensDoChat.sort((o1, o2) -> o1.getDataDeEnvio().compareTo(o2.getDataDeEnvio()));
	}

	public static ChatDto pegarChatPeloId(List<ChatDto> chatsDoUsuarioAutenticado, Integer id) {
		return chatsDoUsuarioAutenticado.stream()
			.filter(chat -> chat.getId() == Long.valueOf(id))
			.findFirst()
			.get();
	}

	public static void ordenarChatsPorMensagemMaisRecente(List<ChatDto> chatsDoUsuario) {
		try {
			chatsDoUsuario.sort((chat1, chat2) -> chat2.getUltimaMensagem()
					.getDataSemFormatar()
					.compareTo(chat1.getUltimaMensagem()
							.getDataSemFormatar()));			
		} catch (NullPointerException ex) {
			
		}
	}

}
