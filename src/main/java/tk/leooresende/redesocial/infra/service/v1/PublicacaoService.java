package tk.leooresende.redesocial.infra.service.v1;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.PublicacaoDto;
import tk.leooresende.redesocial.infra.repository.v1.PublicacaoRepository;
import tk.leooresende.redesocial.infra.util.PublicacaoUtil;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Publicacao;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class PublicacaoService {

	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private PublicacaoRepository publiRepo;
	
	public List<PublicacaoDto> buscarPublicacoesDoUsuario(String username) {
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		List<Publicacao> publicacoes = usuario.getPublicacoes();
		List<PublicacaoDto> publicacoesOrdenadasPorMaisRecentesEmDto = PublicacaoUtil
				.pegarPublicacoesOrdenadasPorMaisRecentesEmDto(publicacoes);
		return publicacoesOrdenadasPorMaisRecentesEmDto;
	}

	public PublicacaoDto salvarPublicacao(MultipartFile imagem, String username, String legenda) throws IOException {
		UsuarioUtil.verificaSeOUsuarioAutenticadoTemPermicoes(username);
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(username);
		
		Publicacao publicacao = new Publicacao(imagem.getBytes(), usuario, legenda);
		
		usuario.getPublicacoes().add(publicacao);
		
		Publicacao publicacaoSalva = salvarPublicacaoNoDB(publicacao);
		this.userService.salvarTodosOsUsuarioNoDB(usuario);
		return new PublicacaoDto(publicacaoSalva);
	}
	
	public void deletarPublicacaoPeloId(Integer id, String username) {
		Publicacao publicacao = this.buscarPublicacaoPeloID(Long.valueOf(id));
		PublicacaoUtil.verificaSeOUsuarioAutenticadoEhODonoDoPublicacao(publicacao);
		UsuarioUtil.verificaSeOUsuarioAutenticadoTemPermicoes(username);
		this.publiRepo.deleteById(Long.valueOf(id));
	}

	public Publicacao salvarPublicacaoNoDB(Publicacao publicacao) {
		return this.publiRepo.save(publicacao);
	}

	public byte[] buscarImagemPublicadaPeloId(Integer id) {
		Publicacao publicacao = this.buscarPublicacaoPeloID(Long.valueOf(id));
		return publicacao.getFoto();
	}
	
	public Publicacao buscarPublicacaoPeloID(Long id) {
		return this.publiRepo.findById(id).get();
	}
	
	public CryptoRequestForm criptografarInformacoesDasPublicacoes(List<PublicacaoDto> publicacao) {
		String infoPublicacoesCriptografadas = PublicacaoUtil.aplicarCriptografia(publicacao);
		return new CryptoRequestForm(infoPublicacoesCriptografadas);
	}

	public CryptoRequestForm criptografarInformacoesDaPublicacao(PublicacaoDto publicacao) {
		String infoPublicacaoCriptografada = PublicacaoUtil.aplicarCriptografia(publicacao);
		return new CryptoRequestForm(infoPublicacaoCriptografada);
	}
}
