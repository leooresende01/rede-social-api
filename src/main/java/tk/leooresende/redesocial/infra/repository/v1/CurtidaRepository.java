package tk.leooresende.redesocial.infra.repository.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tk.leooresende.redesocial.model.Curtida;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, Long>{

}
