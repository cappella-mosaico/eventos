package eventos.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entities.Dependente;


public interface DependenteRepository extends JpaRepository<Dependente, UUID> {

  List<Dependente> findByParticipanteId(UUID participanteId);

}
