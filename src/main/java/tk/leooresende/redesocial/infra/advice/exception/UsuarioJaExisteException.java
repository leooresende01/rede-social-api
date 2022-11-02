package tk.leooresende.redesocial.infra.advice.exception;

import org.springframework.http.HttpStatus;

public class UsuarioJaExisteException extends GenericaException {
	private static final long serialVersionUID = 1L;
	private static final String MENSAGEM = "Esse usuario já está sendo usado";
	private static final HttpStatus STATUS_HTTP = HttpStatus.BAD_REQUEST;
	
	public UsuarioJaExisteException() {
		super(UsuarioJaExisteException.MENSAGEM, UsuarioJaExisteException.STATUS_HTTP);
	}
}
