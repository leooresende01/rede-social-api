package tk.leooresende.redesocial.infra.util;

import java.util.List;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.dto.v1.ComentarioDto;

public class ComentarioUtil {

	public static String aplicarCriptografiaNoComentario(ComentarioDto comentario) {
		Gson gson = new Gson();
		String comentarioDtoJson = gson.toJson(comentario);
		return CryptoUtil.criptografar(comentarioDtoJson);
	}

	public static String aplicarCriptografiaNosComentarios(List<ComentarioDto> comentarios) {
		Gson gson = new Gson();
		String comentarioDtoJson = gson.toJson(comentarios);
		return CryptoUtil.criptografar(comentarioDtoJson);
	}

}
