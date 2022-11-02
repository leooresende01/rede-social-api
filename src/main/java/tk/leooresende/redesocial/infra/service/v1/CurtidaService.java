package tk.leooresende.redesocial.infra.service.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.CurtidaDto;
import tk.leooresende.redesocial.infra.dto.v1.PublicacaoDto;
import tk.leooresende.redesocial.infra.repository.v1.CurtidaRepository;
import tk.leooresende.redesocial.infra.util.CurtidaUtil;
import tk.leooresende.redesocial.infra.util.PublicacaoUtil;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Curtida;
import tk.leooresende.redesocial.model.Publicacao;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class CurtidaService {
	
	@Autowired
	private PublicacaoService publicacaoService;
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private CurtidaRepository curtidaRepo;
	
	public List<CurtidaDto> buscarCurtidasDeUmaPublicacao(Integer idDaPublicacao) {
		Publicacao publicacao = this.publicacaoService.buscarPublicacaoPeloID(Long.valueOf(idDaPublicacao));
		List<Curtida> curtidas = publicacao.getCurtidas();
		return CurtidaDto.mapearCurtidasParaDto(curtidas);
	}

	public PublicacaoDto curtirAPublicacaoPeloId(Integer idDaPublicacao) {
		Publicacao publicacao = this.publicacaoService.buscarPublicacaoPeloID(Long.valueOf(idDaPublicacao));
		String usernameUsuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado().getUsername();
		
		CurtidaUtil.verificaSeOUsuarioJaCurteAPublicacao(publicacao, usernameUsuarioAutenticado);
		
		Usuario usuario = this.userService.buscarUsuarioNoDBPeloUsername(usernameUsuarioAutenticado);
		Curtida curtida = new Curtida(usuario, publicacao);
		Curtida curtidaSalva = this.curtidaRepo.save(curtida);
		
		publicacao.getCurtidas().add(curtidaSalva);
		Publicacao publicacaoSalva = this.publicacaoService.salvarPublicacaoNoDB(publicacao);
		return new PublicacaoDto(publicacaoSalva);
	}

	public void descurtirUmaPublicacao(String username, Integer idDaPublicacao) {
		Publicacao publicacao = this.publicacaoService.buscarPublicacaoPeloID(Long.valueOf(idDaPublicacao));
		Long idCurtida = PublicacaoUtil.removeCurtidaDoUsuarioAutenticadoDaPublicacao(publicacao);
		
		this.curtidaRepo.deleteById(idCurtida);
	}

	public CryptoRequestForm criptografarInformacoesDasCurtidas(CurtidaDto curtidas) {
		String infoCurtidasCriptografadas = CurtidaUtil.aplicarCriptografia(curtidas);
		return new CryptoRequestForm(infoCurtidasCriptografadas);
	}
	
	public CryptoRequestForm criptografarInformacoesDasCurtidas(List<CurtidaDto> curtidas) {
		String infoCurtidasCriptografadas = CurtidaUtil.aplicarCriptografia(curtidas);
		return new CryptoRequestForm(infoCurtidasCriptografadas);
	}
}
