package tk.leooresende.redesocial.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "curtidas")
public class Curtida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Usuario curtidor;
	@ManyToOne
	private Publicacao publicacao;

	public Curtida() {
	}

	public Curtida(Usuario curtidor, Publicacao publicacao) {
		this.curtidor = curtidor;
		this.publicacao = publicacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getCurtidor() {
		return curtidor;
	}

	public void setCurtidor(Usuario curtidor) {
		this.curtidor = curtidor;
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}
}
