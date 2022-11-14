package tk.leooresende.redesocial.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Mensagens")
public class Mensagem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	private Usuario emissor;
	@OneToOne(fetch = FetchType.LAZY)
	private Usuario destinatario;
	@Lob 
	@Column(name="mensagem", length=1000)
	private String mensagem;
	@ManyToOne
	private Chat chatDaMensagem;
	private LocalDateTime dataDeEnvio;

	public Mensagem(Usuario emissor, Usuario destinatario, String mensagem, Chat chatDaMensagem) {
		this.emissor = emissor;
		this.destinatario = destinatario;
		this.mensagem = mensagem;
		this.chatDaMensagem = chatDaMensagem;
		this.dataDeEnvio = LocalDateTime.now();
	}
	
	public Mensagem() {}
	
	public String getMensagem() {
		return mensagem;
	}

	public Chat getChatDaMensagem() {
		return chatDaMensagem;
	}

	public void setChatDaMensagem(Chat chatDaMensagem) {
		this.chatDaMensagem = chatDaMensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getEmissor() {
		return emissor;
	}

	public void setEmissor(Usuario emissor) {
		this.emissor = emissor;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public LocalDateTime getDataDeEnvio() {
		return dataDeEnvio;
	}

	public void setDataDeEnvio(LocalDateTime dataDeEnvio) {
		this.dataDeEnvio = dataDeEnvio;
	}

}
