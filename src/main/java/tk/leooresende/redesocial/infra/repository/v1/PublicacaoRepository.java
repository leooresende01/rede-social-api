package tk.leooresende.redesocial.infra.repository.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tk.leooresende.redesocial.model.Publicacao;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {
	
	Page<Publicacao> findByDonoSeguidoresUsername(String username, Pageable paginacao);
}
