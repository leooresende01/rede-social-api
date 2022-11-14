package tk.leooresende.redesocial.infra.repository.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tk.leooresende.redesocial.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

	Optional<Chat> findByUsuarioQueIniciouUsernameAndOutroUsuarioDoChatUsername(String usernameUsuario1, String usernameUsuario2);
	
	List<Chat> findByUsuarioQueIniciouUsernameOrOutroUsuarioDoChatUsername(String usernameUsuario1, String usernameUsuario2);
}
