package eventos.services;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

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
    return dependenteRepository.findByParticipanteIdOrderByIdDesc(participanteId);
  }

  public Dependente persist(Dependente dependente) {
    Dependente persisted = dependenteRepository.findByParticipanteIdAndNome(
      dependente.getParticipante().getId(),
      dependente.getNome());

    if (persisted == null) {
      persisted = dependente;
    } else {
      persisted.applyFromOther(dependente);
    }

    return dependenteRepository.saveAndFlush(persisted);
  }

  public void persistDependentes(List<DependenteDTO> dependentes,
				 Participante participante) {
    List<String> nomes = findByParticipanteId(participante.getId())
      .stream()
      .map(d -> d.getNome())
      .collect(Collectors.toList());
    

    dependentes
      .stream()
      // we should only add new dependentes if they do not yet exist
      .filter(dto -> nomes.indexOf(dto.getNome()) < 0)
      .forEach(dto -> {
	  dependenteRepository.saveAndFlush(
            new Dependente(dto, participante));
      });
  }

  public Integer countByEvento(Integer eventoId) {
    return dependenteRepository.countByEventoId(eventoId);
  }

}
