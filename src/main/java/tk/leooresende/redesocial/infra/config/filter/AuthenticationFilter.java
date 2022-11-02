package tk.leooresende.redesocial.infra.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import tk.leooresende.redesocial.infra.repository.v1.UsuarioRepository;
import tk.leooresende.redesocial.infra.util.TokenUtil;
import tk.leooresende.redesocial.model.Usuario;

public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private final UsuarioRepository userRepo;

	public AuthenticationFilter(UsuarioRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenJWT = TokenUtil.validaEPegaOToken(request);
		if (tokenJWT != null) {
			String username = TokenUtil.verificaSeOUsuarioExisteEPegaEle(tokenJWT);
			if (username != null) {
				this.buscarUsuarioNoDBEAuthenticar(username);
			}
		}
		filterChain.doFilter(request, response);
	}

	private void buscarUsuarioNoDBEAuthenticar(String username) {
		try {					
			Usuario usuario = this.userRepo.findByUsername(username).get();
			this.autenticarUsuario(usuario);
		} catch (Exception ex) {}
	}

	private void autenticarUsuario(Usuario usuario) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(),
				usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
