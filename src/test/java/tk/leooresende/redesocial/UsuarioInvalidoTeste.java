package tk.leooresende.redesocial;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tk.leooresende.redesocial.infra.advice.exception.GenericaException;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioForm;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.util.factory.UsuarioFactory;

@SpringBootTest
@RunWith(SpringRunner.class)
class UsuarioInvalidoTeste {
	private String mensagemThrows = "Os dados desse usuario não foram aceitos";
	@Test
	void deveriaLancarAExcessaoCasoSejaPassadoUmUsuarioVazio() {
		UsuarioForm usuarioVazio = UsuarioFactory.getUsuarioVazio();
		assertThrows(GenericaException.class, (() -> UsuarioUtil.validarInformacoesDoUsuario(usuarioVazio)));
		assertThrows(this.mensagemThrows, GenericaException.class, (() -> UsuarioUtil.validarInformacoesDoUsuario(usuarioVazio)));
	}
	
	@Test
	void deveriaLancarAExcessaoCasoSejaPassadoUmUsuarioComUsernameInvalido() {
		UsuarioForm usuarioComUsernameErrado = UsuarioFactory.getUsuarioComUsernameInvalido();
		assertThrows(GenericaException.class, (() -> UsuarioUtil.validarInformacoesDoUsuario(usuarioComUsernameErrado)));
		assertThrows(this.mensagemThrows, GenericaException.class, (() -> UsuarioUtil.validarInformacoesDoUsuario(usuarioComUsernameErrado)));
	}
	
	@Test
	void deveriaLancarAExcessaoCasoSejaPassadoUmUsuarioComSenhaInvalida() {
		UsuarioForm usuarioComSnhaInvalida = UsuarioFactory.getUsuarioComSenhaInvalida();
		assertThrows(GenericaException.class, (() -> UsuarioUtil.validarInformacoesDoUsuario(usuarioComSnhaInvalida)));
		assertThrows(this.mensagemThrows, GenericaException.class, (() -> UsuarioUtil.validarInformacoesDoUsuario(usuarioComSnhaInvalida)));
	}
}
