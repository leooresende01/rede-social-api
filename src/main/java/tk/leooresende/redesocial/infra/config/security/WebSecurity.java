package tk.leooresende.redesocial.infra.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import tk.leooresende.redesocial.infra.config.filter.AuthenticationFilter;
import tk.leooresende.redesocial.infra.repository.v1.UsuarioRepository;

@Configuration
public class WebSecurity {
	@Autowired
	private UsuarioRepository userRepo;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/login").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/login/atualizarToken").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/usuarios/{\\.\\*}/imagemDoPerfil").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/usuarios/{\\.\\*}/publicacoes/imagem/**").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling()
	    	.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(new AuthenticationFilter(userRepo), UsernamePasswordAuthenticationFilter.class);
		http.cors();
		http.csrf().disable();
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
		corsConfiguration.addAllowedMethod(HttpMethod.PUT);
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
