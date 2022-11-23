package tk.leooresende.redesocial.infra.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.dto.v1.RefreshForm;
import tk.leooresende.redesocial.infra.util.CryptoUtil;

@SuppressWarnings("deprecation")
public class AtualizarTokenInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String corpoDaRequisicaoDescriptografado = CryptoUtil.pegarEDescriptografarRequisicao(request);
		Gson gson = new Gson();
		RefreshForm refreshForm = gson.fromJson(corpoDaRequisicaoDescriptografado, RefreshForm.class);
		request.setAttribute("refreshForm", refreshForm);
		return super.preHandle(request, response, handler);
	}
}
