package tk.leooresende.redesocial.infra.service.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.dto.v1.MensagemDto;
import tk.leooresende.redesocial.infra.dto.v1.MensagemForm;
import tk.leooresende.redesocial.infra.repository.v1.MensagemRepository;
import tk.leooresende.redesocial.infra.util.ChatUtil;
import tk.leooresende.redesocial.infra.util.MensagemUtil;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Chat;
import tk.leooresende.redesocial.model.Mensagem;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class MensagemService {
	@Autowired
	private MensagemRepository mensagemRepo;
	
	@Autowired
	private ChatService chatService;
	
	public List<MensagemDto> buscarMensagensDoChatComOUsuario(String usuarioDestinatario) {
		String usernameUsuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado().getUsername();
		Chat chat = this.chatService.pegarChatDosUsuariosNoDB(usernameUsuarioAutenticado, usuarioDestinatario);
		
		List<Mensagem> mensagensDoChat = chat.getMensagens();
		ChatUtil.ordenarMensagensPorMaisRecentes(mensagensDoChat);
		
		return MensagemDto.pegarListaDeMensagemComoDto(mensagensDoChat);
	}
	
	public MensagemDto salvarMensagemQueOUsuarioEnviou(MensagemForm mensagemForm, String usernameUsuarioDestinatario) {
		String usernameUsuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado().getUsername();
		
		Chat chat = this.chatService.pegarChatDosUsuariosNoDB(usernameUsuarioAutenticado, usernameUsuarioDestinatario);
		Usuario usuarioQueEnviouAMensagem = MensagemUtil.pegarUsuarioDoChat(usernameUsuarioAutenticado, chat);
		Usuario usuarioDestinatario = MensagemUtil.pegarUsuarioDoChat(usernameUsuarioDestinatario, chat);
		
		Mensagem mensagem = mensagemForm.pegarComoMensagem(chat, usuarioQueEnviouAMensagem, usuarioDestinatario);
		chat.getMensagens().add(mensagem);
		
		this.chatService.salvarChatNoDB(chat);
		Mensagem mensagemSalva = this.mensagemRepo.save(mensagem);
		return MensagemDto.pegarMensagemComoDto(mensagemSalva);
	}


}
