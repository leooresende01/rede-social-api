package tk.leooresende.redesocial.infra.service.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.dto.v1.ComentarioDto;
import tk.leooresende.redesocial.infra.dto.v1.ComentarioForm;
import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.repository.v1.ComentarioRepository;
import tk.leooresende.redesocial.infra.util.ComentarioUtil;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Comentario;
import tk.leooresende.redesocial.model.Publicacao;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class ComentarioService {
	@Autowired
	private PublicacaoService publicacaoService;
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private ComentarioRepository comentarioRepo;
	
	public List<ComentarioDto> buscarComentariosDaPublicacao(Integer idDaPublicacao) {
		Publicacao publicacao = this.publicacaoService.buscarPublicacaoPeloID(Long.valueOf(idDaPublicacao));
		List<Comentario> comentarios = publicacao.getComentarios();
		return ComentarioDto.mapearComentariosParaDto(comentarios);
	}

	public ComentarioDto registrarComentarioDaPublicacao(Integer idDaPublicacao, ComentarioForm comentarioForm) {
		Publicacao publicacao = this.publicacaoService.buscarPublicacaoPeloID(Long.valueOf(idDaPublicacao));
		String usernameUsuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado().getUsername();
		Usuario usuarioComentador = this.userService.buscarUsuarioNoDBPeloUsername(usernameUsuarioAutenticado);
		
		Comentario comentario = new Comentario(publicacao, usuarioComentador, comentarioForm.getComentario());
		publicacao.getComentarios().add(comentario);
		
		this.publicacaoService.salvarPublicacaoNoDB(publicacao);
		Comentario comentarioSalvo = this.comentarioRepo.save(comentario);
		return new ComentarioDto(comentarioSalvo);
	}

	public void deletarComentarioPeloId(Integer idDoComentario) {
		Comentario comentario = this.comentarioRepo.findById(Long.valueOf(idDoComentario)).get();
		UsuarioUtil.verificaSeOUsuarioAutenticadoTemPermicoes(comentario.getComentador().getUsername());
		this.comentarioRepo.delete(comentario);
	}

	public CryptoRequestForm criptografarComentario(ComentarioDto comentario) {
		String comentarioCriptografado = ComentarioUtil.aplicarCriptografiaNoComentario(comentario);
		return new CryptoRequestForm(comentarioCriptografado);
	}

	public CryptoRequestForm criptografarListaDeComentarios(List<ComentarioDto> comentarios) {
		String comentariosCriptografados = ComentarioUtil.aplicarCriptografiaNosComentarios(comentarios);
		return new CryptoRequestForm(comentariosCriptografados);
	}
}
