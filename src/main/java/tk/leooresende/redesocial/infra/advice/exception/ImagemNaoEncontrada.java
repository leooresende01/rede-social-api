package tk.leooresende.redesocial.infra.advice.exception;

import org.springframework.http.HttpStatus;

public class ImagemNaoEncontrada extends GenericaException {

	private static final String MENSAGEM = "Imagem não encontrada";
	private static final long serialVersionUID = 1L;
	private static final HttpStatus STATUS_HTTP = HttpStatus.NOT_FOUND;
	
	
	public ImagemNaoEncontrada() {
		super(ImagemNaoEncontrada.MENSAGEM, ImagemNaoEncontrada.STATUS_HTTP);
	}

}
