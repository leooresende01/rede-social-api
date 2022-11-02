package tk.leooresende.redesocial.infra.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import tk.leooresende.redesocial.infra.config.interceptor.AtualizarTokenInterceptor;
import tk.leooresende.redesocial.infra.config.interceptor.AtualizarUsuarioInterceptor;
import tk.leooresende.redesocial.infra.config.interceptor.CriarComentarioInterceptor;
import tk.leooresende.redesocial.infra.config.interceptor.LoginInterceptor;
import tk.leooresende.redesocial.infra.config.interceptor.RegistrarUsuarioInterceptor;

@SuppressWarnings("deprecation")
@Configuration
public class InterceptorSecurity extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/v1/login");
		registry.addInterceptor(new AtualizarTokenInterceptor()).addPathPatterns("/api/v1/login/atualizarToken");
		registry.addInterceptor(new RegistrarUsuarioInterceptor()).addPathPatterns("/api/v1/usuarios");
		registry.addInterceptor(new AtualizarUsuarioInterceptor()).addPathPatterns("/api/v1/usuarios/*");
		registry.addInterceptor(new CriarComentarioInterceptor()).addPathPatterns("/api/v1/usuarios/{\\.\\*}/publicacoes/{\\.\\*}/comentarios");
	}			
}
