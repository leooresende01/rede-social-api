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
import tk.leooresende.redesocial.infra.dto.v1.MensagemDto;

@RestControllerAdvice
public class GenericoAdvice {
	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Void> tratarExceptionNaoEncontrado(Exception ex) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler({ GenericaException.class })
	public ResponseEntity<MensagemDto> tratarDataIntegrityViolationException(GenericaException ex) {
		return ResponseEntity.status(ex.getStatusHTTP()).body(new MensagemDto(ex.getMessage()));
	} 

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<MensagemDto> tratarDataIntegrityViolationException(Exception ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemDto(UsuarioNaoExisteException.MENSAGEM));
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<MensagemDto> tratarHttpMessageNotReadableException(Exception ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemDto("Os dados são invalidos ou não foram enviados"));
	}
	
}
