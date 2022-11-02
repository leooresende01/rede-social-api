package tk.leooresende.redesocial.infra.config.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tk.leooresende.redesocial.infra.dto.v1.UsuarioForm;
import tk.leooresende.redesocial.infra.util.SecurityUtil;

@SuppressWarnings("deprecation")
public class RegistrarUsuarioInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!request.getMethod().equals(HttpMethod.POST.toString())) {
			return super.preHandle(request, response, handler);			
		}
		UsuarioForm usuarioForm = this.pegarCorpoDaRequisicaoComoUsuarioForm(request);
		request.setAttribute("usuarioForm", usuarioForm);
		return super.preHandle(request, response, handler);
	}

	private UsuarioForm pegarCorpoDaRequisicaoComoUsuarioForm(HttpServletRequest request) throws IOException, ServletException {
		String username = SecurityUtil.pegarParametroDescriptografado("username", request);
		String nomeCompleto = SecurityUtil.pegarParametroDescriptografado("nomeCompleto", request);
		String password = SecurityUtil.pegarParametroDescriptografado("password", request);
		MultipartFile imagem = SecurityUtil.pegarImagemDaRequisicao(request);
		return new UsuarioForm(username, password, nomeCompleto, imagem);
	}
}
