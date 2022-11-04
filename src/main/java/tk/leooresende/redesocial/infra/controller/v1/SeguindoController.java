package tk.leooresende.redesocial.infra.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.PaginacaoPublicacaoDto;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioDto;
import tk.leooresende.redesocial.infra.service.v1.SeguindoService;
import tk.leooresende.redesocial.infra.service.v1.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios/{username}/seguindo")
public class SeguindoController {

	@Autowired
	private SeguindoService seguindoService;
	
	@Autowired
	private UsuarioService userService;
	
	@GetMapping
	public ResponseEntity<CryptoRequestForm> buscarPessoasQueOUsuarioEstaSeguindo(@PathVariable String username, Integer pagina, Integer quantidade) {
		Page<UsuarioDto> pessoasQueOUsuarioSegue = this.seguindoService.buscarUsuariosSeguindoNoDB(username, pagina, quantidade);
		CryptoRequestForm informacoesDosSeguindosCriptografadas = this.userService.pegarInformacoesDosUsuariosCriptografados(pessoasQueOUsuarioSegue);
		return ResponseEntity.ok(informacoesDosSeguindosCriptografadas);
	}

	@PostMapping("/{usuarioSeguido}")
	@Transactional
	public ResponseEntity<CryptoRequestForm> seguirUsuario(@PathVariable String username, @PathVariable String usuarioSeguido) {
		UsuarioDto usuarioQueFoiSeguido = this.seguindoService.seguirUsuario(username, usuarioSeguido);
		CryptoRequestForm informacoesDoUsuarioQueFoiSeguidoCriptografada = 
				this.userService.pegarInformacoesDoUsuarioCriptografado(usuarioQueFoiSeguido);
		return ResponseEntity.ok(informacoesDoUsuarioQueFoiSeguidoCriptografada);
	}
	
	@DeleteMapping("/{usuarioSeguidoUsername}")
	@Transactional
	public ResponseEntity<Void> deixarDeSeguirUmUsuario(@PathVariable String username, @PathVariable String usuarioSeguidoUsername) {
		this.seguindoService.deixarDeSeguir(username, usuarioSeguidoUsername);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{usernameSeguidor}")
	public ResponseEntity<CryptoRequestForm> buscarSeguidorDoUsuarioPeloUsername(@PathVariable String username, @PathVariable String usernameSeguidor) {
		UsuarioDto usuarioDto = this.seguindoService.buscarPessoaQueOUsuarioEstaSeguindoPeloUsername(username, usernameSeguidor);
		CryptoRequestForm informacoesDoSeguindoCriptografada = this.userService.pegarInformacoesDoUsuarioCriptografado(usuarioDto);
		return ResponseEntity.ok(informacoesDoSeguindoCriptografada);
	}
	
	@GetMapping("/publicacoes")
	public ResponseEntity<CryptoRequestForm> buscarPublicacoesDasPessoasQueOUsuarioSegueTeste(@PathVariable String username, Integer pagina, Integer quantidadeDePublicacoes) {
		PaginacaoPublicacaoDto publicacoesPaginadas = this.seguindoService.buscarPublicacoesDasPessoasQueOUsuarioSegue(username, pagina, quantidadeDePublicacoes);
		CryptoRequestForm informacoesDasPublicacoesCriptografadas = publicacoesPaginadas.pegarPublicacoesComInformacoesCriptografadas();
		return ResponseEntity.ok(informacoesDasPublicacoesCriptografadas);
	}
}
