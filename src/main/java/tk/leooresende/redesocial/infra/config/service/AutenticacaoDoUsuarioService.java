package tk.leooresende.redesocial.infra.config.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.advice.exception.UsuarioNaoExisteException;
import tk.leooresende.redesocial.infra.repository.v1.UsuarioRepository;

@Service
public class AutenticacaoDoUsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {			
			return this.userRepo.findByUsername(username)
				.get();
		} catch (NoSuchElementException ex) {
			throw new UsuarioNaoExisteException();
		}
	}

}
