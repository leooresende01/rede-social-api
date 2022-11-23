package tk.leooresende.redesocial.infra.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.dto.v1.LoginForm;
import tk.leooresende.redesocial.infra.util.CryptoUtil;

@SuppressWarnings("deprecation")
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String corpoDescriptografadoEmJSON = CryptoUtil.pegarEDescriptografarRequisicao(request);
		Gson gson = new Gson();
		LoginForm loginForm = gson.fromJson(corpoDescriptografadoEmJSON, LoginForm.class);
		request.setAttribute("loginForm", loginForm);
		return super.preHandle(request, response, handler);
	}
}
