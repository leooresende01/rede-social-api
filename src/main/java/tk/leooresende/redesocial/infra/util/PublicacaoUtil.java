package tk.leooresende.redesocial.infra.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.advice.exception.GenericaException;
import tk.leooresende.redesocial.infra.dto.v1.PublicacaoDto;
import tk.leooresende.redesocial.model.Curtida;
import tk.leooresende.redesocial.model.Publicacao;
import tk.leooresende.redesocial.model.Usuario;

public class PublicacaoUtil {
	private static final Integer MAXIMO_DE_DIAS = -2;

	public static void verificaSeOUsuarioAutenticadoEhODonoDoPublicacao(Publicacao publicacao) {
		Usuario usuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado();
		Usuario donoDaPublicacao = publicacao.getDono();
		if (!usuarioAutenticado.getUsername().equals(donoDaPublicacao.getUsername())) {
			throw new GenericaException("Você não é o dono dessa publicacao", HttpStatus.FORBIDDEN);
		}
	}

	public static Long removeCurtidaDoUsuarioAutenticadoDaPublicacao(Publicacao publicacao) {
		Usuario usuarioAutenticado = UsuarioUtil.pegarUsuarioAutenticado();
		List<Curtida> curtidas = publicacao.getCurtidas();
		try {
			Curtida curtida = curtidas.stream().filter(
					curtidaFilter -> curtidaFilter.getCurtidor().getUsername().equals(usuarioAutenticado.getUsername()))
					.findFirst().get();
			return curtida.getId();
		} catch (Exception ex) {
		}
		throw new GenericaException("Você não curte essa publicacao", HttpStatus.BAD_REQUEST);
	}

	public static String pegarDataDePublicacaoFormatada(LocalDateTime offsetDateTime) {
		String dataFormatada = DateTimeFormatter
					.ofPattern("dd MMM yyyy HH:mm", Locale.forLanguageTag("pt-br"))
					.format(offsetDateTime);
		return dataFormatada.replaceAll("(\\d{2}) (.*) (\\d{4}) (\\d{2}:\\d{2})", "$1 de $2 de $3 as $4");
	}

	public static List<Publicacao> pegarPublicacoesMaisRecentesDasPessoasQueOUsuarioSegue(Usuario usuario) {
		List<Usuario> seguidosPeloUsuario = usuario.getSeguindo();
		List<List<Publicacao>> publicacoesDentroDaDataMaximaPermitida = PublicacaoUtil
				.pegarPublicacoesDentroDaDataMaximaDePublicacaoPermitida(seguidosPeloUsuario);
		return PublicacaoUtil
				.colocarTodasAsPublicacoesEmApenasUmaLista(publicacoesDentroDaDataMaximaPermitida);
	}
	
	public static LocalDateTime pegarDataDeAgora() {
		return LocalDateTime.now().plusHours(-3l);
	}
	
	private static List<List<Publicacao>> pegarPublicacoesDentroDaDataMaximaDePublicacaoPermitida(
			List<Usuario> seguidosPeloUsuario) {
		LocalDateTime dataMaximaDePublicacaoAceita = PublicacaoUtil.pegarDataMaximaDePublicacaoAceita();
		System.out.println(dataMaximaDePublicacaoAceita);
		return seguidosPeloUsuario.stream().map(usuarioSeguido -> usuarioSeguido.getPublicacoes().stream().filter(
				publicacao -> publicacao.getDataDePublicacao().isAfter(dataMaximaDePublicacaoAceita))
				.toList()).toList();
	}

	private static LocalDateTime pegarDataMaximaDePublicacaoAceita() {
		ZonedDateTime dataAtual = ZonedDateTime.now();
		ZonedDateTime dataMaximoDaPublicacao = dataAtual.plusDays(PublicacaoUtil.MAXIMO_DE_DIAS);
		return LocalDateTime.from(dataMaximoDaPublicacao);
	}

	private static List<Publicacao> colocarTodasAsPublicacoesEmApenasUmaLista(
			List<List<Publicacao>> publicacoesDentroDaDataMaximaPermitida) {
		List<Publicacao> publicacoes = new ArrayList<Publicacao>();
		publicacoesDentroDaDataMaximaPermitida.forEach(listaDePublicacaoForEach -> listaDePublicacaoForEach
				.forEach(publicacaoForEach -> publicacoes.add(publicacaoForEach)));
		return publicacoes;
	}

	public static String aplicarCriptografia(PublicacaoDto comentario) {
		Gson gson = new Gson();
		String comentarioDtoJson = gson.toJson(comentario);
		return SecurityUtil.criptografar(comentarioDtoJson);
	}

	public static String aplicarCriptografia(List<PublicacaoDto> comentarios) {
		Gson gson = new Gson();
		String comentarioDtoJson = gson.toJson(comentarios);
		return SecurityUtil.criptografar(comentarioDtoJson);
	}

	public static List<PublicacaoDto> pegarPublicacoesOrdenadasPorMaisRecentesEmDto(List<Publicacao> publicacoes) {
		List<PublicacaoDto> publicacoesDto = PublicacaoDto.mapearListaDePublicacoesParaDto(publicacoes);
		publicacoesDto.sort((publicacao1, publicacao2) -> publicacao2.getDataDePublicacao()
				.compareTo(publicacao1.getDataDePublicacao()));
		return publicacoesDto;
	}
}
