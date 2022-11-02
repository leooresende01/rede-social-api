package tk.leooresende.redesocial.infra.service.v1;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tk.leooresende.redesocial.infra.advice.exception.UsuarioJaExisteException;
import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioAtualizadoForm;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioDto;
import tk.leooresende.redesocial.infra.dto.v1.UsuarioForm;
import tk.leooresende.redesocial.infra.repository.v1.UsuarioRepository;
import tk.leooresende.redesocial.infra.util.UsuarioUtil;
import tk.leooresende.redesocial.model.Usuario;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository userRepo;

	@Autowired
	private PasswordEncoder passEncoder;

	public List<UsuarioDto> buscarUsuarioNoDB() {
		List<Usuario> usuarios = this.userRepo.findAll();
		return UsuarioDto.mapearUsuariosParaDTO(usuarios);
	}

	public UsuarioDto validarERegistrarUsuario(UsuarioForm userForm) throws IOException {
		UsuarioUtil.validarInformacoesDoUsuario(userForm);
		this.verificaSeOUsernameJaEstaSendoUsado(userForm.getUsername());
		Usuario usuario = userForm.mapearParaUsuario();
		UsuarioUtil.salvarImagemDoUsuario(usuario, userForm.getFile());
		return this.salvarUsuarioRegistradoEPegarDTO(usuario);
	}
	
	public UsuarioDto validarEAtualizarUsuario(UsuarioAtualizadoForm userForm, String usernameUsuarioAtualizado) {
		UsuarioUtil.verificaSeOUsuarioAutenticadoTemPermicoes(usernameUsuarioAtualizado);
		Usuario usuario = this.buscarUsuarioNoDBPeloUsername(usernameUsuarioAtualizado);
		userForm.atualizarInformacoesDoUsuario(usuario);
		Usuario usuarioSalvo = this.salvarUsuarioNoDB(usuario);
		return new UsuarioDto(usuarioSalvo);
	}

	public UsuarioDto buscarUsuarioPeloUsername(String username) {
		Usuario usuario = this.buscarUsuarioNoDBPeloUsername(username);
		return new UsuarioDto(usuario);
	}

	private UsuarioDto salvarUsuarioRegistradoEPegarDTO(Usuario usuario) {
		this.criptografarSenha(usuario);
		Usuario usuarioSalvo = this.salvarUsuarioNoDB(usuario);
		return new UsuarioDto(usuarioSalvo);
	}

	public Usuario salvarUsuarioNoDB(Usuario usuario) {
		return this.userRepo.save(usuario);
	}

	public Usuario buscarUsuarioNoDBPeloUsername(String username) {
		return this.userRepo.findByUsername(username).get();
	}

	public void salvarTodosOsUsuarioNoDB(Usuario... usuario) {
		List<Usuario> usuarios = Stream.of(usuario).toList();
		this.userRepo.saveAll(usuarios);
	}

	private void verificaSeOUsernameJaEstaSendoUsado(String username) {
		this.userRepo.findByUsername(username).ifPresent(Usuario -> {
			throw new UsuarioJaExisteException();
		});
	}

	private void criptografarSenha(Usuario usuario) {
		usuario.setPassword(this.passEncoder.encode(usuario.getPassword()));
	}

	public List<UsuarioDto> buscarUsuariosPeloUsernameComRegex(String username) {
		List<Usuario> usuarios = this.userRepo.findAllByUsernameWhitRegex(username);
		return UsuarioUtil.pegarUsuariosComoDtoEEmbaralharLista(usuarios);
	}

	public byte[] buscarImagemDoUsuario(String username) throws IOException {
		Usuario usuario = this.buscarUsuarioNoDBPeloUsername(username);
		byte[] imagemDoPerfilEmBytes = usuario.getImagemDoPerfil();
		UsuarioUtil.verificaSeAImagemExiste(imagemDoPerfilEmBytes);
		return imagemDoPerfilEmBytes;
	}

	public CryptoRequestForm pegarInformacoesDoUsuarioCriptografado(UsuarioDto usuarioAtualizado) {
		String usuarioCriptografado = UsuarioUtil.criptografarInformacoesDoUsuario(usuarioAtualizado);
		return new CryptoRequestForm(usuarioCriptografado);
	}
	
	public CryptoRequestForm pegarInformacoesDosUsuariosCriptografados(List<UsuarioDto> usuarios) {
		String usuariosCriptografados = UsuarioUtil.criptografarInformacoesDosUsuarios(usuarios);
		return new CryptoRequestForm(usuariosCriptografados);
	}


}
