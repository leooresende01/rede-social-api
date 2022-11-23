package tk.leooresende.redesocial.util.factory;

import tk.leooresende.redesocial.infra.dto.v1.UsuarioForm;

public class UsuarioFactory {

	public static UsuarioForm getUsuarioVazio() {
		return new UsuarioForm("", "", "", null);
	}

	public static UsuarioForm getUsuarioComUsernameInvalido() {
		return new UsuarioForm("3kwfe", "12345678", "Leonard Sousa Resende", null);
	}

	public static UsuarioForm getUsuarioComSenhaInvalida() {
		return new UsuarioForm("leoo.resende", "123", "Leonard Sousa Resende", null);
	}

}
