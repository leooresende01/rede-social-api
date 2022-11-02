package tk.leooresende.redesocial.infra.advice.exception;

import org.springframework.http.HttpStatus;

public class SemPermicaoParaManipular extends GenericaException {
	private static final String MENSAGEM = "Você não pode manipular ou acessar esse recurso";
	private static final long serialVersionUID = 1L;
	private static final HttpStatus STATUS_HTTP = HttpStatus.FORBIDDEN;

	public SemPermicaoParaManipular() {
		super(SemPermicaoParaManipular.MENSAGEM, SemPermicaoParaManipular.STATUS_HTTP);
	}
}
