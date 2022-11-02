package tk.leooresende.redesocial.infra.util;

import java.util.List;

import tk.leooresende.redesocial.infra.advice.exception.JaEstaSeguindoException;
import tk.leooresende.redesocial.model.Usuario;

public class SeguidorESeguindoUTIL {

	public static void removerSeguidorDaLista(Usuario usuario, String usernameSeguidor) {
		List<Usuario> seguidores = usuario.getSeguidores();
		Usuario usuarioSeguindor = SeguidorESeguindoUTIL.pegarUsuarioNaListaDeSeguidoresOuSeguindo(usernameSeguidor,
				seguidores);
		SeguidorESeguindoUTIL.removeSeguidorOuSeguindo(seguidores, usuarioSeguindor);
	}

	public static void removerDaListaDeSeguindo(Usuario usuarioSeguidor, String username) {
		List<Usuario> listaDeSeguindo = usuarioSeguidor.getSeguindo();
		Usuario usuarioQueEstaSendoSeguido = SeguidorESeguindoUTIL.pegarUsuarioNaListaDeSeguidoresOuSeguindo(username,
				listaDeSeguindo);
		SeguidorESeguindoUTIL.removeSeguidorOuSeguindo(listaDeSeguindo, usuarioQueEstaSendoSeguido);
	}

	public static Usuario pegarUsuarioNaListaDeSeguidoresOuSeguindo(String usernameSeguidor,
			List<Usuario> listaDeSeguidoresOuSeguindo) {
		return listaDeSeguidoresOuSeguindo.stream()
				.filter(seguidorFiltro -> seguidorFiltro.getUsername().equals(usernameSeguidor)).findFirst().get();
	}

	private static void removeSeguidorOuSeguindo(List<Usuario> seguidores, Usuario seguidorOuSeguindo) {
		if (seguidores.remove(seguidorOuSeguindo))
			return;
		throw new RuntimeException("Seguidor não encontrado");
	}

	public static void verificaSeJaEstaSeguindo(Usuario usuario, Usuario usuarioSeguido) {
		usuarioSeguido.getSeguidores().stream()
				.filter(usuarioFiltro -> usuarioFiltro.getUsername().equals(usuario.getUsername())).findFirst()
				.ifPresent(Usuario -> {
					throw new JaEstaSeguindoException();
				});
	}

	public static Boolean verificaSeOUsuarioAutenticadoSegueEle(Usuario usuario) {
		List<Usuario> seguidores = usuario.getSeguidores();
		return SeguidorESeguindoUTIL.verificarSeOUsuarioAutenticadoEstaNaLista(seguidores);
	}

	public static Boolean verificaSeEleSegueOUsuarioAutenticado(Usuario usuario) {
		List<Usuario> seguindo = usuario.getSeguindo();
		return SeguidorESeguindoUTIL.verificarSeOUsuarioAutenticadoEstaNaLista(seguindo);
	}
	
	private static Boolean verificarSeOUsuarioAutenticadoEstaNaLista(List<Usuario> seguidoresOuSeguindo) {
		try {
			String usernameUsuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado().getUsername();
			return seguidoresOuSeguindo.stream()
				.filter(usuarioFiltro -> usuarioFiltro.getUsername().equals(usernameUsuarioAutenticado))
				.findFirst()
				.isPresent();
		} catch (Exception ex) {
			return false;
		}
	}
}
