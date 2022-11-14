package tk.leooresende.redesocial.infra.service.v1;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.dto.v1.ChatDto;
import tk.leooresende.redesocial.infra.dto.v1.ChatForm;
import tk.leooresende.redesocial.infra.repository.v1.ChatRepository;
import tk.leooresende.redesocial.infra.util.ChatUtil;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Chat;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class ChatService {
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UsuarioService userService;
	
	public List<ChatDto> buscarChatsDoUsuarioAutenticado() {
		String ussernameUsuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado().getUsername();
		List<Chat> chatsDoUsuario = this.chatRepository
				.findByUsuarioQueIniciouUsernameOrOutroUsuarioDoChatUsername(ussernameUsuarioAutenticado, ussernameUsuarioAutenticado);
		List<ChatDto> chatsDto = ChatDto.mapearListaDeChatsParaDto(chatsDoUsuario);
		ChatUtil.ordenarChatsPorMensagemMaisRecente(chatsDto);
		return chatsDto;
	}

	public ChatDto criarChat(ChatForm chatForm) {
		String usernameUsuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado().getUsername();
		Usuario usuarioQueIniciouOChat = this.userService.buscarUsuarioNoDBPeloUsername(usernameUsuarioAutenticado);
		Usuario outroUsuarioDoChat = this.userService.buscarUsuarioNoDBPeloUsername(chatForm.getoOutroUsuarioDoChat());
		
		Chat chat = new Chat(usuarioQueIniciouOChat, outroUsuarioDoChat);
		Chat chatSalvo = this.chatRepository.save(chat);
		return new ChatDto(chatSalvo);
	}

	
	public Chat pegarChatDosUsuariosNoDB(String usernameUsuario1, String usernameUsuario2) {
		try {
			return this.chatRepository
					.findByUsuarioQueIniciouUsernameAndOutroUsuarioDoChatUsername(usernameUsuario1, usernameUsuario2).get();
		} catch (NoSuchElementException ex) {
			return this.chatRepository
					.findByUsuarioQueIniciouUsernameAndOutroUsuarioDoChatUsername(usernameUsuario2, usernameUsuario1).get();
		}
	}

	public Chat salvarChatNoDB(Chat chat) {
		return this.chatRepository.save(chat);
	}

	public ChatDto buscarChatDoUsuarioPeloId(Integer id) {
		List<ChatDto> chatsDoUsuarioAutenticado = this.buscarChatsDoUsuarioAutenticado();
		return ChatUtil.pegarChatPeloId(chatsDoUsuarioAutenticado, id);
	}
}
