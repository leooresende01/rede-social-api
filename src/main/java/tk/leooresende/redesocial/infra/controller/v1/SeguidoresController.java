package tk.leooresende.redesocial.infra.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioDto;
import tk.leooresende.redesocial.infra.service.v1.SeguidoresService;
import tk.leooresende.redesocial.infra.service.v1.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios/{username}/seguidores")
public class SeguidoresController {
	@Autowired
	private SeguidoresService seguidoresService;
	
	@Autowired
	private UsuarioService userService;
	
	@GetMapping
	public ResponseEntity<CryptoRequestForm> buscarSeguidoresDoUsuario(@PathVariable String username, Integer pagina, Integer quantidade) {
		Page<UsuarioDto> pessoasQueSeguemOUsuario = this.seguidoresService.buscarSeguidoresDoUsuario(username, pagina, quantidade);
		CryptoRequestForm informacoesSeguidoresCriptografada = this.userService.pegarInformacoesDosUsuariosCriptografados(pessoasQueSeguemOUsuario);
		return ResponseEntity.ok(informacoesSeguidoresCriptografada);
	}
	
	@DeleteMapping("/{usernameSeguidor}")
	@Transactional
	public ResponseEntity<Void> removerSeguidor(@PathVariable String username, @PathVariable String usernameSeguidor) {
		this.seguidoresService.removerSeguidorDaLista(username, usernameSeguidor);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{usernameSeguidor}")
	public ResponseEntity<CryptoRequestForm> buscarSeguidorDoUsuarioPeloUsername(@PathVariable String username, @PathVariable String usernameSeguidor) {
		UsuarioDto usuarioDto = this.seguidoresService.buscarSeguidorPeloId(username, usernameSeguidor);
		CryptoRequestForm informacoesUsuarioCriptografado = this.userService.pegarInformacoesDoUsuarioCriptografado(usuarioDto);
		return ResponseEntity.ok(informacoesUsuarioCriptografado);
	}
}
