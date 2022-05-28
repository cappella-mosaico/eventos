package eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import eventos.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

  List<Evento> findTop10ByOrderByIdDesc();

}
