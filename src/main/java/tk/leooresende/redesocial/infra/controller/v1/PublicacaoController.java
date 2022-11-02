package tk.leooresende.redesocial.infra.controller.v1;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.PublicacaoDto;
import tk.leooresende.redesocial.infra.service.v1.PublicacaoService;

@RestController
@RequestMapping("/api/v1/usuarios/{username}/publicacoes")
public class PublicacaoController {

	@Autowired
	private PublicacaoService service;

	@GetMapping
	public ResponseEntity<CryptoRequestForm> buscarPublicacoesDoUsuario(@PathVariable String username) {
		List<PublicacaoDto> publicacoes = this.service.buscarPublicacoesDoUsuario(username);
		CryptoRequestForm informacoesDasPublicacoesCriptografadas = this.service.criptografarInformacoesDasPublicacoes(publicacoes);
		return ResponseEntity.ok(informacoesDasPublicacoesCriptografadas);
	}

	@PostMapping
	public ResponseEntity<CryptoRequestForm> criarUmaPublicacao(@RequestParam(required = true) MultipartFile imagem,
			@RequestParam(required = true) String legenda, @PathVariable String username) throws IOException {
		PublicacaoDto publicacao = this.service.salvarPublicacao(imagem, username, legenda);
		CryptoRequestForm informacoesDaPublicacaoCriptografada = this.service.criptografarInformacoesDaPublicacao(publicacao);
		return ResponseEntity.status(HttpStatus.CREATED).body(informacoesDaPublicacaoCriptografada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPublicacaoDoUsuarioPeloId(@PathVariable Integer id,
			@PathVariable String username) {
		this.service.deletarPublicacaoPeloId(id, username);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/imagem/{id}")
	public void buscarImagemDoPerfilDoUsuario(@PathVariable Integer id, HttpServletResponse res) throws IOException {
		byte[] imagem = this.service.buscarImagemPublicadaPeloId(id);
		res.getOutputStream().write(imagem);
	}

}
