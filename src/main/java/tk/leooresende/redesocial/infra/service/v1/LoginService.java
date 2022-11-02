package tk.leooresende.redesocial.infra.service.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.LoginDto;
import tk.leooresende.redesocial.infra.dto.v1.LoginForm;
import tk.leooresende.redesocial.infra.dto.v1.RefreshForm;
import tk.leooresende.redesocial.infra.repository.v1.RefreshTokenRepository;
import tk.leooresende.redesocial.infra.util.LoginUtil;
import tk.leooresende.redesocial.infra.util.TokenUtil;
import tk.leooresende.redesocial.model.RefreshToken;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class LoginService {
	@Autowired
	private RefreshTokenRepository refreshTokenRepo;
	
	public Authentication verificarFormularioEPegarAuthentication(LoginForm loginForm, AuthenticationManager authManager) {
		Authentication autentication = loginForm.pegarAuthentication();
		return authManager.authenticate(autentication);
	}

	public LoginDto gerarTokenJWTERefreshToken(Authentication usuarioAutenticado, String usernameUsuario) {
		String token = TokenUtil.gerarTokenJWT(usuarioAutenticado);
		Usuario usuario = TokenUtil.pegarUsuarioApartirDeUmaAutenticacao(usuarioAutenticado);
		return gerarESalvarRefreshTokenEPegarTokenDto(usuario, token);
	}

	public LoginDto atualizarTokenJWTApartirDoRefreshToken(RefreshForm refreshForm) {
		String refreshToken = refreshForm.getRefreshToken();
		Usuario usuario = this.buscarUsuarioDonoDoRefreshToken(refreshToken);
		String tokenJWT = TokenUtil.gerarTokenJWT(usuario);
		return this.gerarESalvarRefreshTokenEPegarTokenDto(usuario, tokenJWT);
	}
	
	private LoginDto gerarESalvarRefreshTokenEPegarTokenDto(Usuario usuario, String token) {
		String refreshToken = TokenUtil.gerarRefreshToken();
		RefreshToken refreshTokenObj = new RefreshToken(refreshToken, usuario);
		this.refreshTokenRepo.save(refreshTokenObj);
		return new LoginDto(token, refreshToken);
	}

	private Usuario buscarUsuarioDonoDoRefreshToken(String refreshToken) {
		RefreshToken refreshTokenDB = this.refreshTokenRepo.findByToken(refreshToken).get();
		return refreshTokenDB.getDono();
	}

	public CryptoRequestForm criptografarDadosDaRequisicao(LoginDto loginDto) {
		String loginDtoCriptografado = LoginUtil.criptografarLoginDto(loginDto);
		return new CryptoRequestForm(loginDtoCriptografado);
	}

}
