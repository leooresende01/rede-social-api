package tk.leooresende.redesocial.infra.util;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tk.leooresende.redesocial.model.Usuario;

public class TokenUtil {

	private static final String TOKEN_KEY = "I#OHHUEUEFO**E*(W(*(YE(#&RR##RR#UIR#R#Y##&#&#R&R#GI#RG&RIGR#GR#&#RQKNKQDDBD";
	public static final long EXP_TOKEN_TIME = 900000l;
	private static final String REDE_SOCIAL = "rede-social";
	private static final String AUTHORIZATION = "Authorization";

	public static String gerarTokenJWT(Authentication autenticado) {
		Usuario principal = pegarUsuarioApartirDeUmaAutenticacao(autenticado);
		return TokenUtil.gerarTokenJWT(principal);

	}

	public static Usuario pegarUsuarioApartirDeUmaAutenticacao(Authentication autenticado) {
		return (Usuario) autenticado.getPrincipal();
	}

	public static String gerarTokenJWT(Usuario usuario) {
		Date data = new Date();
		return Jwts.builder().setIssuer(TokenUtil.REDE_SOCIAL).setSubject(usuario.getUsername())
				.setIssuedAt(new Date(data.getTime()))
				.setExpiration(new Date(data.getTime() + TokenUtil.EXP_TOKEN_TIME))
				.signWith(SignatureAlgorithm.HS256, TokenUtil.TOKEN_KEY).compact();
	}

	public static String validaEPegaOToken(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		return TokenUtil.verificaSeOTokenEhValidoEPegaEle(token);
	}

	private static String verificaSeOTokenEhValidoEPegaEle(String token) {
		if (token != null && token.matches("\\w+\\s{1}.+")) {
			return token.split(" ")[1];
		}
		return null;
	}

	public static String verificaSeOUsuarioExisteEPegaEle(String tokenJWT) {
		try {
			return (String) Jwts.parser().setSigningKey(TokenUtil.TOKEN_KEY).parseClaimsJws(tokenJWT).getBody()
					.getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	public static String gerarRefreshToken() {
		return UUID.randomUUID().toString();
	}
}
