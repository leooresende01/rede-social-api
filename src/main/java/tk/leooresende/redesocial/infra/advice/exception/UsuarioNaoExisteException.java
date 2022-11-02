package tk.leooresende.redesocial.infra.advice.exception;

import org.springframework.http.HttpStatus;

public class UsuarioNaoExisteException extends GenericaException {
	private static final long serialVersionUID = 1L;
	public static final String MENSAGEM = "Usuário inexistente ou senha inválida";
	private static final HttpStatus STATUS_HTTP = HttpStatus.UNAUTHORIZED;

	public UsuarioNaoExisteException() {
		super(UsuarioNaoExisteException.MENSAGEM, UsuarioNaoExisteException.STATUS_HTTP);
	}
}
