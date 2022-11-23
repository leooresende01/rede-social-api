package tk.leooresende.redesocial.infra.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tk.leooresende.redesocial.infra.dto.v1.UsuarioAtualizadoForm;
import tk.leooresende.redesocial.infra.util.CryptoUtil;

@SuppressWarnings("deprecation")
public class AtualizarUsuarioInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!request.getMethod().equals(HttpMethod.PUT.toString())) {
			return super.preHandle(request, response, handler);			
		}
		UsuarioAtualizadoForm usuarioAtualizadoForm = this.pegarRequisicaoComoUsuarioAtualizadoForm(request);
		request.setAttribute("usuarioAtualizadoForm", usuarioAtualizadoForm);
		return super.preHandle(request, response, handler);		
	}

	private UsuarioAtualizadoForm pegarRequisicaoComoUsuarioAtualizadoForm(HttpServletRequest request) {
		String username = CryptoUtil.pegarParametroDescriptografado("username", request);
		String nomeCompleto = CryptoUtil.pegarParametroDescriptografado("nomeCompleto", request);
		MultipartFile imagem = CryptoUtil.pegarImagemDaRequisicao(request);
		return new UsuarioAtualizadoForm(username, nomeCompleto, imagem);
	}
}
