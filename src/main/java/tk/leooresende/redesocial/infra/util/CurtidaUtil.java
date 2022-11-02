package tk.leooresende.redesocial.infra.util;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.advice.exception.GenericaException;
import tk.leooresende.redesocial.infra.dto.v1.CurtidaDto;
import tk.leooresende.redesocial.model.Curtida;
import tk.leooresende.redesocial.model.Publicacao;
import tk.leooresende.redesocial.model.Usuario;

public class CurtidaUtil {

	public static void verificaSeOUsuarioJaCurteAPublicacao(Publicacao publicacao, String usernameUsuarioAutenticado) {
		List<Curtida> curtidas = publicacao.getCurtidas();
		curtidas.stream().filter(curtida -> curtida.getCurtidor().getUsername().equals(usernameUsuarioAutenticado))
				.findFirst().ifPresent(t -> {
					throw new GenericaException("Você já curtiu essa publicação", HttpStatus.BAD_REQUEST);
				});
	}

	public static Boolean verificaSeOUsuarioAutenticadoCurtiuAPublicao(Publicacao publicacao) {
		try {
			Usuario usuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado();
			publicacao.getCurtidas().stream()
					.filter(curtida -> curtida.getCurtidor().getUsername().equals(usuarioAutenticado.getUsername()))
					.findFirst().get();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static CurtidaDto pegarUmaPessoaAleatoriaQueCurtiu(List<Curtida> curtidas) {
		List<CurtidaDto> curtidasDto = CurtidaDto.mapearCurtidasParaDto(curtidas);
		try {
			return curtidasDto.get(0);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static String aplicarCriptografia(CurtidaDto curtidas) {
		Gson gson = new Gson();
		String comentarioDtoJson = gson.toJson(curtidas);
		return SecurityUtil.criptografar(comentarioDtoJson);
	}
	
	public static String aplicarCriptografia(List<CurtidaDto> curtidas) {
		Gson gson = new Gson();
		String comentarioDtoJson = gson.toJson(curtidas);
		return SecurityUtil.criptografar(comentarioDtoJson);
	}

}
