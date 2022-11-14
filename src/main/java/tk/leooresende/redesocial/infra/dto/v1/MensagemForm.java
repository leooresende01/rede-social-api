package tk.leooresende.redesocial.infra.dto.v1;

import tk.leooresende.redesocial.model.Chat;
import tk.leooresende.redesocial.model.Mensagem;
import tk.leooresende.redesocial.model.Usuario;

public class MensagemForm {
	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Mensagem pegarComoMensagem(Chat chat, Usuario usuarioQueEnviouAMensagem, Usuario usuarioDestinatario) {
		return new Mensagem(usuarioQueEnviouAMensagem, usuarioDestinatario, this.mensagem, chat);
	}
}
