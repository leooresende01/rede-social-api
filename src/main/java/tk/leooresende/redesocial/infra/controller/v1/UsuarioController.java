package tk.leooresende.redesocial.infra.controller.v1;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioAtualizadoForm;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioDto;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioForm;
import tk.leooresende.redesocial.infra.service.v1.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService userService;

	private final String PATH_API = "/api/v1/usuarios";

	@GetMapping
	public ResponseEntity<CryptoRequestForm> buscarUsuarios(Integer pagina, Integer quantidade) {
		Page<UsuarioDto> usuarios = this.userService.buscarUsuarioNoDB(pagina, quantidade);
		CryptoRequestForm usuariosCriptografados = this.userService.pegarInformacoesDosUsuariosCriptografados(usuarios);
		return ResponseEntity.ok(usuariosCriptografados);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<CryptoRequestForm> registrarUsuario(@RequestAttribute UsuarioForm usuarioForm,
			UriComponentsBuilder urlBuilder) throws Exception {
		UsuarioDto usuario = this.userService.validarERegistrarUsuario(usuarioForm);
		URI uriLocationUsuario = urlBuilder.path(PATH_API + "/" + usuario.getId()).build().toUri();
		CryptoRequestForm usuarioCriptografado = this.userService.pegarInformacoesDoUsuarioCriptografado(usuario);
		return ResponseEntity.created(uriLocationUsuario).body(usuarioCriptografado);
	}

	@PutMapping("/{usernameUsuarioAtualizado}")
	@Transactional
	public ResponseEntity<CryptoRequestForm> atualizarInformacoesDoUsuario(@PathVariable String usernameUsuarioAtualizado,
			@RequestAttribute UsuarioAtualizadoForm usuarioAtualizadoForm) throws Exception {
		UsuarioDto usuarioAtualizado = this.userService.validarEAtualizarUsuario(usuarioAtualizadoForm, usernameUsuarioAtualizado);
		CryptoRequestForm usuarioCriptografado = this.userService.pegarInformacoesDoUsuarioCriptografado(usuarioAtualizado);
		return ResponseEntity.ok(usuarioCriptografado);
	}

	@GetMapping("/{username}")
	public ResponseEntity<CryptoRequestForm> buscarUsuarioPeloUsername(@PathVariable String username) {
		UsuarioDto usuario = this.userService.buscarUsuarioPeloUsername(username);
		CryptoRequestForm usuarioCriptografado = this.userService.pegarInformacoesDoUsuarioCriptografado(usuario);
		return ResponseEntity.ok(usuarioCriptografado);
	}

	@GetMapping("/regex/{username}")
	public ResponseEntity<CryptoRequestForm> buscarUsuariosPeloUsernameComRegex(@PathVariable String username, Integer quantidade) {
		List<UsuarioDto> usuarios = this.userService.buscarUsuariosPeloUsernameComRegex(username, quantidade);
		CryptoRequestForm usuariosCriptografados = this.userService.pegarInformacoesDosUsuariosCriptografados(usuarios);
		return ResponseEntity.ok(usuariosCriptografados);
	}

	@GetMapping("/{username}/imagemDoPerfil")
	public void buscarImagemDoPerfilDoUsuario(@PathVariable String username, HttpServletResponse res)
			throws IOException {
		byte[] imagemDoUsuario = this.userService.buscarImagemDoUsuario(username);
		res.getOutputStream().write(imagemDoUsuario);
	}
}
