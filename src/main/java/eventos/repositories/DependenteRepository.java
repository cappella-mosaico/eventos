package eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import eventos.entities.Dependente;


public interface DependenteRepository extends JpaRepository<Dependente, UUID> {

}
