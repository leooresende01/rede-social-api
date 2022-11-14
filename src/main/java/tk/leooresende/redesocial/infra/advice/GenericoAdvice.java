package tk.leooresende.redesocial.infra.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tk.leooresende.redesocial.infra.advice.exception.GenericaException;
import tk.leooresende.redesocial.infra.advice.exception.UsuarioNaoExisteException;
import tk.leooresende.redesocial.infra.dto.v1.MensagemDeErroDto;

@RestControllerAdvice
public class GenericoAdvice {
	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Void> tratarExceptionNaoEncontrado(Exception ex) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler({ GenericaException.class })
	public ResponseEntity<MensagemDeErroDto> tratarDataIntegrityViolationException(GenericaException ex) {
		return ResponseEntity.status(ex.getStatusHTTP()).body(new MensagemDeErroDto(ex.getMessage()));
	} 

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<MensagemDeErroDto> tratarDataIntegrityViolationException(Exception ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemDeErroDto(UsuarioNaoExisteException.MENSAGEM));
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<MensagemDeErroDto> tratarHttpMessageNotReadableException(Exception ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemDeErroDto("Os dados são invalidos ou não foram enviados"));
	}
	
}
