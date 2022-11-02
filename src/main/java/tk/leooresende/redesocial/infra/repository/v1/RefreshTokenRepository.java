package tk.leooresende.redesocial.infra.repository.v1;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tk.leooresende.redesocial.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
	
	Optional<RefreshToken> findByToken(String token);
}
