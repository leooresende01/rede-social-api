package tk.leooresende.redesocial.infra.controller.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.LoginDto;
import tk.leooresende.redesocial.infra.dto.v1.LoginForm;
import tk.leooresende.redesocial.infra.dto.v1.RefreshForm;
import tk.leooresende.redesocial.infra.service.v1.LoginService;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping
	@Transactional
	public ResponseEntity<CryptoRequestForm> gerarTokenDeAutenticacao(@RequestAttribute LoginForm loginForm) {
		System.out.println(loginForm.getUsername());
		Authentication usuarioAutenticado = this.loginService.verificarFormularioEPegarAuthentication(loginForm, this.authManager);
		LoginDto loginDto = this.loginService.gerarTokenJWTERefreshToken(usuarioAutenticado, loginForm.getUsername());
		CryptoRequestForm loginDtoCrypto = this.loginService.criptografarDadosDaRequisicao(loginDto);
		return ResponseEntity.ok(loginDtoCrypto);
	}
	
	@PostMapping("/atualizarToken")
	public ResponseEntity<CryptoRequestForm> atualizarTokenJWTComRefreshToken(@RequestAttribute RefreshForm refreshForm) {
		LoginDto loginDto = this.loginService.atualizarTokenJWTApartirDoRefreshToken(refreshForm);
		CryptoRequestForm loginDtoCrypto = this.loginService.criptografarDadosDaRequisicao(loginDto);
		return ResponseEntity.ok(loginDtoCrypto);
	}
}
