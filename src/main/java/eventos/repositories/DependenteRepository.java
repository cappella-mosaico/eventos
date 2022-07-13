package eventos.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eventos.entities.Dependente;


public interface DependenteRepository extends JpaRepository<Dependente, UUID> {

  List<Dependente> findByParticipanteId(UUID participanteId);

  @Query(value = "SELECT COUNT(d) FROM Dependente d JOIN d.participante p WHERE p.evento.id = ?1")
  Integer countByEventoId(Integer eventoId);

}
