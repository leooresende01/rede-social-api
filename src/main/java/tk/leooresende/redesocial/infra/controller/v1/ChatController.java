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

import tk.leooresende.redesocial.infra.dto.v1.ChatDto;
import tk.leooresende.redesocial.infra.dto.v1.ChatForm;
import tk.leooresende.redesocial.infra.service.v1.ChatService;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

	@Autowired
	private ChatService service;
	
	@GetMapping
	public ResponseEntity<List<ChatDto>> buscarChatsDoUsuario() {
		List<ChatDto> chatsDoUsuario = this.service.buscarChatsDoUsuarioAutenticado();
		return ResponseEntity.ok(chatsDoUsuario);
	}
	
	@PostMapping
	public ResponseEntity<ChatDto> criarChatComUmUsuario(@RequestBody ChatForm chatForm) {
		ChatDto chat = this.service.criarChat(chatForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(chat);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ChatDto> buscarChatDoUsuarioPeloId(@PathVariable Integer id) {
		ChatDto chat = this.service.buscarChatDoUsuarioPeloId(id);
		return ResponseEntity.ok(chat);
	}
}
