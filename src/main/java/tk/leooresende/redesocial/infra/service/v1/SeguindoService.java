package tk.leooresende.redesocial.infra.service.v1;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.dto.v1.PublicacaoDto;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioDto;
import tk.leooresende.redesocial.infra.util.PublicacaoUtil;
import tk.leooresende.redesocial.infra.util.SeguidorESeguindoUTIL;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Publicacao;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class SeguindoService {

	@Autowired
	private UsuarioService userService;
	
	public List<UsuarioDto> buscarUsuariosSeguindoNoDB(String username) {
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		List<Usuario> seguindo = usuario.getSeguindo();
		return UsuarioDto.mapearUsuariosParaDTO(seguindo);
	}

	public UsuarioDto seguirUsuario(String username, String usuarioSeguidoUsername) {
		Usuario usuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado();
		UsuarioUtil.verificaSeOUsuarioAutenticadoTemPermicoes(usuarioAutenticado, username);
		
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		Usuario usuarioSeguido = this.userService.buscarUsuarioNoDBPeloUsername(usuarioSeguidoUsername);
		
		SeguidorESeguindoUTIL.verificaSeJaEstaSeguindo(usuario, usuarioSeguido);
		this.adicionarSeguidorESeguindoNaListas(usuario, usuarioSeguido);
		
		this.userService.salvarTodosOsUsuarioNoDB(usuario, usuarioSeguido);
		return new UsuarioDto(usuarioSeguido);
	}


	public void deixarDeSeguir(String username, String usuarioSeguidoUsername) {
		Usuario usuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado();
		UsuarioUtil.verificaSeOUsuarioAutenticadoTemPermicoes(usuarioAutenticado, username);
		
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		Usuario usuarioSeguido = this.userService.buscarUsuarioNoDBPeloUsername(usuarioSeguidoUsername);
		
		SeguidorESeguindoUTIL.removerDaListaDeSeguindo(usuario, usuarioSeguidoUsername);
		SeguidorESeguindoUTIL.removerSeguidorDaLista(usuarioSeguido, username);
		
		this.userService.salvarTodosOsUsuarioNoDB(usuario, usuarioSeguido);
	}
	
	public UsuarioDto buscarPessoaQueOUsuarioEstaSeguindoPeloUsername(String username, String usernameSeguidor) {
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		List<Usuario> seguidores = usuario.getSeguindo();
		
		Usuario usuarioSeguidor = SeguidorESeguindoUTIL.pegarUsuarioNaListaDeSeguidoresOuSeguindo(usernameSeguidor, seguidores);
		return new UsuarioDto(usuarioSeguidor);
	}
	
	private void adicionarSeguidorESeguindoNaListas(Usuario usuario, Usuario usuarioSeguido) {
		usuarioSeguido.getSeguidores().add(usuario);
		usuario.getSeguindo().add(usuarioSeguido);
	}

	public List<PublicacaoDto> buscarPublicacoesDasPessoasQueOUsuarioSegue(String usernameUsuario) {
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(usernameUsuario);
		List<Publicacao> publicacoesMaisRecentesDasPessoasQueOUsuarioSegue = PublicacaoUtil.pegarPublicacoesMaisRecentesDasPessoasQueOUsuarioSegue(usuario);
		List<PublicacaoDto> publicacoesDto = PublicacaoDto
				.mapearListaDePublicacoesParaDto(publicacoesMaisRecentesDasPessoasQueOUsuarioSegue);
		Collections.shuffle(publicacoesDto);
		return publicacoesDto;
	}
}
