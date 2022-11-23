package tk.leooresende.redesocial.infra.util;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.dto.v1.LoginDto;

public class LoginUtil {

	public static String criptografarLoginDto(LoginDto loginDto) {
		Gson gson = new Gson();
		String loginFormJson = gson.toJson(loginDto, LoginDto.class);
		return CryptoUtil.criptografar(loginFormJson);
	}

}
