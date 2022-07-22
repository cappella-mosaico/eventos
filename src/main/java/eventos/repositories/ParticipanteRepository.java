package eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

import eventos.entities.Participante;


public interface ParticipanteRepository extends JpaRepository<Participante, UUID> {

  Participante findByCpf(String cpf);
  List<Participante> findByEventoIdOrderByIdDesc(Integer eventoId);
  Integer countByEventoId(Integer eventoId);

}
