package tk.leooresende.redesocial.infra.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.leooresende.redesocial.infra.dto.v1.ComentarioDto;
import tk.leooresende.redesocial.infra.dto.v1.ComentarioForm;
import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.service.v1.ComentarioService;

@RestController
@RequestMapping("/api/v1/usuarios/{username}/publicacoes/{idDaPublicacao}/comentarios")
public class ComentarioController {

	@Autowired
	private ComentarioService service;

	@GetMapping
	public ResponseEntity<CryptoRequestForm> buscarComentariosDeUmaPublicacao(@PathVariable String username,
			@PathVariable Integer idDaPublicacao) {
		List<ComentarioDto> comentarios = this.service.buscarComentariosDaPublicacao(idDaPublicacao);
		CryptoRequestForm comentariosCriptografados = this.service.criptografarListaDeComentarios(comentarios);
		return ResponseEntity.ok(comentariosCriptografados);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CryptoRequestForm> registrarComentario(@PathVariable String username, 
			@PathVariable Integer idDaPublicacao, @RequestAttribute ComentarioForm comentarioForm) {
		ComentarioDto comentario = this.service.registrarComentarioDaPublicacao(idDaPublicacao, comentarioForm);
		CryptoRequestForm comentarioCriptografado = this.service.criptografarComentario(comentario);
		return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriptografado);
	}
	
	@DeleteMapping("/{idDoComentario}")
	@Transactional
	public ResponseEntity<Void> deletarComentario(@PathVariable Integer idDoComentario) {
		this.service.deletarComentarioPeloId(idDoComentario);
		return ResponseEntity.noContent().build();
	}
}
