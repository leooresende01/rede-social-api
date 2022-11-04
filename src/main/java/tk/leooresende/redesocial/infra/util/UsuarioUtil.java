package tk.leooresende.redesocial.infra.util;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.advice.exception.GenericaException;
import tk.leooresende.redesocial.infra.advice.exception.ImagemNaoEncontrada;
import tk.leooresende.redesocial.infra.advice.exception.SemPermicaoParaManipular;
import tk.leooresende.redesocial.infra.dto.v1.PaginacaoUsuarioDto;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioDto;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioForm;
import tk.leooresende.redesocial.model.Usuario;

public class UsuarioUtil {

	public static Usuario pegarUsuarioAutenticado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static void verificaSeOUsuarioAutenticadoTemPermicoes(String username) {
		Usuario usuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado();
		if (!usuarioAutenticado.getUsername().equals(username)) {
			throw new SemPermicaoParaManipular();
		}
	}
	
	public static void verificaSeOUsuarioAutenticadoTemPermicoes(Usuario usuarioAutenticado, String username) {
		if (!usuarioAutenticado.getUsername().equals(username)) {
			throw new SemPermicaoParaManipular();
		}
	}

	public static void salvarImagemDoUsuario(Usuario usuario, MultipartFile file) {
		try {
			byte[] byteObjects = new byte[file.getBytes().length];
			int i = 0;
			for (byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			usuario.setImagemDoPerfil(byteObjects);
		} catch (Exception ex) {
		}
	}

	public static void verificaSeAImagemExiste(byte[] imagemDoPerfilEmBytes) {
		if (imagemDoPerfilEmBytes == null) {
			throw new ImagemNaoEncontrada();
		}
	}

	public static void validarInformacoesDoUsuario(UsuarioForm userForm) {
		UsuarioUtil.aplicarValidacoes(userForm.getUsername(), 8, 20);
		UsuarioUtil.aplicarValidacoes(userForm.getPassword(), 8, 20);
	}
	
	private static void aplicarValidacoes(String alvoValidado, Integer caracteresMinimasAceitas, Integer caracteresMaximasAceitas) {
		UsuarioUtil.validarELancarExcessao(alvoValidado.length() < caracteresMinimasAceitas);
		UsuarioUtil.validarELancarExcessao(alvoValidado.length() > caracteresMaximasAceitas);
	}
	
	private static void validarELancarExcessao(boolean check) {
		if (check) {
			throw new GenericaException("Os dados desse usuario não foram aceitos", HttpStatus.BAD_REQUEST);
		}
	}

	public static String criptografarInformacoesDoUsuario(UsuarioDto usuarioAtualizado) {
		Gson gson = new Gson();
		String usuarioDtoJson = gson.toJson(usuarioAtualizado);
		return SecurityUtil.criptografar(usuarioDtoJson);
	}
	
	public static String criptografarInformacoesDosUsuarios(PaginacaoUsuarioDto usuarioAtualizado) {
		Gson gson = new Gson();
		String usuariosDtoPaginadosEmJson = gson.toJson(usuarioAtualizado);
		System.out.println(usuariosDtoPaginadosEmJson);
		return SecurityUtil.criptografar(usuariosDtoPaginadosEmJson);
	}
	
	public static String criptografarInformacoesDosUsuarios(List<UsuarioDto> usuarioAtualizado) {
		Gson gson = new Gson();
		String usuariosDtoJson = gson.toJson(usuarioAtualizado);
		return SecurityUtil.criptografar(usuariosDtoJson);
	}

	public static List<UsuarioDto> pegarUsuariosComoDtoEEmbaralharLista(List<Usuario> usuarios) {
		List<UsuarioDto> usuariosDto = UsuarioDto.mapearUsuariosParaDTO(usuarios);
		Collections.shuffle(usuariosDto);
		return usuariosDto;
	}
}
