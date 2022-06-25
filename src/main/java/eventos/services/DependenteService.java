package eventos.services;

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;

import eventos.repositories.DependenteRepository;
import eventos.entities.Dependente;
import eventos.entities.Participante;
import eventos.entities.dtos.DependenteDTO;

@Service
public class DependenteService {

  private final DependenteRepository dependenteRepository;

  public DependenteService(DependenteRepository dependenteRepository) {
    this.dependenteRepository = dependenteRepository;
  }

  public List<Dependente> findByParticipanteId(UUID participanteId) {
    return dependenteRepository.findByParticipanteId(participanteId);
  }

  public void persistDependentes(List<DependenteDTO> dependentes,
				 Participante participante) {
    dependentes
      .stream()
      .forEach(dto -> {
	  dependenteRepository.saveAndFlush(
            new Dependente(dto, participante));
      });
  }

}
