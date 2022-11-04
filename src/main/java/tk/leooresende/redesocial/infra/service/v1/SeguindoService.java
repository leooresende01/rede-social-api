package tk.leooresende.redesocial.infra.service.v1;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.dto.v1.PaginacaoPublicacaoDto;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioDto;
import tk.leooresende.redesocial.infra.repository.v1.PublicacaoRepository;
import tk.leooresende.redesocial.infra.repository.v1.UsuarioRepository;
import tk.leooresende.redesocial.infra.util.SeguidorESeguindoUTIL;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Publicacao;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class SeguindoService {
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private PublicacaoRepository publicacaoRepo;
	
	public List<UsuarioDto> buscarUsuariosSeguindoNoDB(String username) {
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		List<Usuario> seguindo = usuario.getSeguindo();
		return UsuarioDto.mapearUsuariosParaDTO(seguindo);
	}
	
	public Page<UsuarioDto> buscarUsuariosSeguindoNoDB(String username, Integer pagina, Integer quantidade) {
		PageRequest paginacaoConfig = PageRequest.of(pagina, quantidade);
		Page<Usuario> pessoasQueOUsuarioSegue = this.userRepo.findBySeguidoresUsername(username, paginacaoConfig);
		return pessoasQueOUsuarioSegue.map(UsuarioDto::new);
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
	
	public PaginacaoPublicacaoDto buscarPublicacoesDasPessoasQueOUsuarioSegue(String usernameUsuario, Integer pagina, Integer quantidadeDePublicacoes) {
		Pageable criarPaginacao = PageRequest.of(pagina, quantidadeDePublicacoes);
		Page<Publicacao> listaDePublicacoesPaginada = this.publicacaoRepo.findByDonoSeguidoresUsername(usernameUsuario, criarPaginacao);
		PaginacaoPublicacaoDto paginacaoPublicacaoDto = new PaginacaoPublicacaoDto(listaDePublicacoesPaginada.getContent(), listaDePublicacoesPaginada.isLast());
		Collections.shuffle(paginacaoPublicacaoDto.getPublicacoes());
		return paginacaoPublicacaoDto;
	}
}
