package tk.leooresende.redesocial.infra.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.CurtidaDto;
import tk.leooresende.redesocial.infra.dto.v1.PublicacaoDto;
import tk.leooresende.redesocial.infra.service.v1.CurtidaService;
import tk.leooresende.redesocial.infra.service.v1.PublicacaoService;

@RestController
@RequestMapping("/api/v1/usuarios/{username}/publicacoes/{idDaPublicacao}/curtidas")
public class CurtidasController {
	
	@Autowired
	private PublicacaoService publicacaoService;
	
	@Autowired
	private CurtidaService service;

	@GetMapping
	public ResponseEntity<CryptoRequestForm> buscarCurtidasDaPublicacao(@PathVariable String username,
			@PathVariable Integer idDaPublicacao) {
		List<CurtidaDto> curtidas = this.service.buscarCurtidasDeUmaPublicacao(idDaPublicacao);
		CryptoRequestForm informacoesDasCurtidasCriptografadas = this.service.criptografarInformacoesDasCurtidas(curtidas);
		return ResponseEntity.ok(informacoesDasCurtidasCriptografadas);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<CryptoRequestForm> curtirUmaPublicacao(@PathVariable Integer idDaPublicacao) {
		PublicacaoDto publicacao = this.service.curtirAPublicacaoPeloId(idDaPublicacao);
		CryptoRequestForm informacoesDaPublicacaoCriptografadas = this.publicacaoService.criptografarInformacoesDaPublicacao(publicacao);
		return ResponseEntity.ok(informacoesDaPublicacaoCriptografadas);
	}

	@DeleteMapping
	@Transactional
	public ResponseEntity<Void> deletarCurtidaDoUsuarioAutenticadoAPublicacao(@PathVariable String username,
			@PathVariable Integer idDaPublicacao) {
		this.service.descurtirUmaPublicacao(username, idDaPublicacao);
		return ResponseEntity.noContent().build();
	}
}
