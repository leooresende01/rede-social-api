package tk.leooresende.redesocial;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tk.leooresende.redesocial.infra.util.CryptoUtil;
import tk.leooresende.redesocial.util.CryptoClientUtil;;

@SpringBootTest
@RunWith(SpringRunner.class)
class CryptografiaTeste {

	private static final String TARGET = "Valor que será criptografado";

	@Test
	void deveriaCriptografarEDescriptografarAStringClienteParaServidor() {
		String targetCrypto = CryptoClientUtil.criptografar(CryptografiaTeste.TARGET);
		String targetDescrypt = CryptoUtil.descriptografarAESComAKey(targetCrypto);
		assertEquals(CryptografiaTeste.TARGET, targetDescrypt);
	}
	
	@Test
	void deveriaCriptografarEDescriptografarAStringServidorParaCliente() {
		String targetCrypto = CryptoUtil.criptografar(CryptografiaTeste.TARGET);
		String targetDescrypt = CryptoClientUtil.descriptografarAESComAKey(targetCrypto);
		assertEquals(CryptografiaTeste.TARGET, targetDescrypt);
	}
}
