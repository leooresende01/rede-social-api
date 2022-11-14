package tk.leooresende.redesocial.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "Chats")
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	private Usuario usuarioQueIniciou;
	@OneToOne(fetch = FetchType.LAZY)
	private Usuario outroUsuarioDoChat;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "chatDaMensagem")
	private List<Mensagem> mensagens = new ArrayList<>();
	private LocalDateTime dataDeCriacao;

	public Chat(Usuario usuarioQueIniciou, Usuario oOutroUsuarioDoChat) {
		this.usuarioQueIniciou = usuarioQueIniciou;
		this.outroUsuarioDoChat = oOutroUsuarioDoChat;
		this.dataDeCriacao = LocalDateTime.now();
	}
	
	public Chat() {}
	
	public LocalDateTime getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(LocalDateTime dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuarioQueIniciou() {
		return usuarioQueIniciou;
	}

	public void setUsuarioQueIniciou(Usuario usuarioQueIniciou) {
		this.usuarioQueIniciou = usuarioQueIniciou;
	}

	public Usuario getOutroUsuarioDoChat() {
		return outroUsuarioDoChat;
	}

	public void setOutroUsuarioDoChat(Usuario outroUsuarioDoChat) {
		this.outroUsuarioDoChat = outroUsuarioDoChat;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
}
