package tk.leooresende.redesocial.infra.advice.exception;

import org.springframework.http.HttpStatus;

public class JaEstaSeguindoException extends GenericaException {
	
	private static final String MENSAGEM = "Você já está seguindo esse usuario";
	private static final long serialVersionUID = 1L;
	private static final HttpStatus STATUS_HTTP = HttpStatus.BAD_REQUEST;
	
	public JaEstaSeguindoException() {
		super(JaEstaSeguindoException.MENSAGEM, JaEstaSeguindoException.STATUS_HTTP);
	}
}
