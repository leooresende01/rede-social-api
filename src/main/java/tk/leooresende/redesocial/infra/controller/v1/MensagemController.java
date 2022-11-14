package tk.leooresende.redesocial.infra.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.leooresende.redesocial.infra.dto.v1.MensagemDto;
import tk.leooresende.redesocial.infra.dto.v1.MensagemForm;
import tk.leooresende.redesocial.infra.service.v1.MensagemService;

@RestController
@RequestMapping("/api/v1/chats/{usuarioDestinatario}/mensagens")
public class MensagemController {
	
	@Autowired
	private MensagemService service;
	
	@GetMapping
	public ResponseEntity<List<MensagemDto>> buscarMensagensDoChat(@PathVariable String usuarioDestinatario) {
		List<MensagemDto> mensagensDoChat = this.service.buscarMensagensDoChatComOUsuario(usuarioDestinatario);
		return ResponseEntity.ok(mensagensDoChat);
	}
	
	@PostMapping
	public ResponseEntity<MensagemDto> enviarMensagemParaOUsuario(@RequestBody MensagemForm mensagemForm, @PathVariable String usuarioDestinatario) {
		MensagemDto mensagem = this.service.salvarMensagemQueOUsuarioEnviou(mensagemForm, usuarioDestinatario);
		return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
	}

}
