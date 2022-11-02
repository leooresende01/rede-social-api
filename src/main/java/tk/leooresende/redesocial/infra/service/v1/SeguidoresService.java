package tk.leooresende.redesocial.infra.service.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.dto.v1.UsuarioDto;
import tk.leooresende.redesocial.infra.util.SeguidorESeguindoUTIL;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class SeguidoresService {
	@Autowired
	private UsuarioService userService;
	
	public List<UsuarioDto> buscarSeguidoresDoUsuario(String username) {
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		List<Usuario> seguidores = usuario.getSeguidores();
		return UsuarioDto.mapearUsuariosParaDTO(seguidores);
	}

	public void removerSeguidorDaLista(String username, String usernameSeguidor) {
		Usuario usuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado();
		UsuarioUtil.verificaSeOUsuarioAutenticadoTemPermicoes(usuarioAutenticado, username);
		
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		Usuario usuarioSeguidor = this.userService.buscarUsuarioNoDBPeloUsername(usernameSeguidor);
		
		SeguidorESeguindoUTIL.removerSeguidorDaLista(usuario, usernameSeguidor);
		SeguidorESeguindoUTIL.removerDaListaDeSeguindo(usuarioSeguidor, username);
		
		this.userService.salvarTodosOsUsuarioNoDB(usuario, usuarioSeguidor);
	}

	public UsuarioDto buscarSeguidorPeloId(String username, String usernameSeguidor) {
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		List<Usuario> seguidores = usuario.getSeguidores();
		
		Usuario usuarioSeguidor = SeguidorESeguindoUTIL.pegarUsuarioNaListaDeSeguidoresOuSeguindo(usernameSeguidor, seguidores);
		return new UsuarioDto(usuarioSeguidor);
	}
}
