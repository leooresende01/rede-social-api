package tk.leooresende.redesocial.infra.util;

import org.springframework.http.HttpStatus;

import tk.leooresende.redesocial.infra.advice.exception.GenericaException;
import tk.leooresende.redesocial.model.Chat;
import tk.leooresende.redesocial.model.Usuario;

public class MensagemUtil {

	public static Usuario pegarUsuarioDoChat(String usernameUsuario, Chat chat) {
		Usuario usuarioQueIniciou = chat.getUsuarioQueIniciou();
		if (usuarioQueIniciou.getUsername().equals(usernameUsuario)) return usuarioQueIniciou;
		
		Usuario outroUsuarioDoChat = chat.getOutroUsuarioDoChat();
		if (outroUsuarioDoChat.getUsername().equals(usernameUsuario)) return outroUsuarioDoChat;
		
		throw new GenericaException("Ocorreu um erro", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
