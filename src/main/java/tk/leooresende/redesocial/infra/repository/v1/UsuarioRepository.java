package tk.leooresende.redesocial.infra.repository.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tk.leooresende.redesocial.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Optional<Usuario> findByUsername(String username);
	
	@Query(nativeQuery=true, value="SELECT * FROM usuarios WHERE username regexp ?1")
	List<Usuario> findAllByUsernameWhitRegex(String regex);
}
