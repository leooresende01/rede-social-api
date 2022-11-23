package tk.leooresende.redesocial.infra.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.dto.v1.ComentarioForm;
import tk.leooresende.redesocial.infra.util.CryptoUtil;

@SuppressWarnings("deprecation")
public class CriarComentarioInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!request.getMethod().equals(HttpMethod.POST.toString())) {
			return super.preHandle(request, response, handler);			
		}
		String corpoDescriptografadoEmJSON = CryptoUtil.pegarEDescriptografarRequisicao(request);
		Gson gson = new Gson();
		ComentarioForm loginForm = gson.fromJson(corpoDescriptografadoEmJSON, ComentarioForm.class);
		request.setAttribute("comentarioForm", loginForm);
		return super.preHandle(request, response, handler);
	}
}
