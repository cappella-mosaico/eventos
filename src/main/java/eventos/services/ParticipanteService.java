package eventos.services;

import eventos.entities.Evento;
import eventos.entities.Participante;
import eventos.entities.dtos.ParticipanteDTO;
import eventos.repositories.EventoRepository;
import eventos.repositories.ParticipanteRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

  private final ParticipanteRepository participanteRepository;
  private final EventoRepository eventoRepository;
  private final DependenteService dependenteService;

  public Participante persist(ParticipanteDTO dto) {
    Evento evento = eventoRepository.findById(dto.getEventoId()).orElse(null);
    if (evento == null) {
      throw new RuntimeException("Evento (" + dto.getEventoId() + ") nao encontrado.");
    }

    Participante existingParticipante =
        participanteRepository.findByCpfAndEventoId(dto.getCpf(), dto.getEventoId());
    Participante participante = new Participante(dto, evento);
    if (existingParticipante == null) {
      participante = participanteRepository.saveAndFlush(participante);
    } else {
      existingParticipante.setUpdatedAt(LocalDateTime.now());
      existingParticipante.applyFromOther(participante);
      participante = participanteRepository.saveAndFlush(existingParticipante);
    }

    dependenteService.persistDependentes(dto.getDependentes(), participante);
    return participante;
  }

  public List<Participante> findByEventoId(Integer eventoId) {
    return participanteRepository.findByEventoIdOrderByIdDesc(eventoId);
  }

  public Integer countByEvento(Integer eventoId) {
    return participanteRepository.countByEventoId(eventoId);
  }
}
