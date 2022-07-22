package eventos.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.lang.RuntimeException;
import java.util.List;

import eventos.repositories.ParticipanteRepository;
import eventos.repositories.EventoRepository;
import eventos.services.DependenteService;

import eventos.entities.Participante;
import eventos.entities.Evento;
import eventos.entities.Dependente;
import eventos.entities.dtos.ParticipanteDTO;


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

    // TODO fix the bug where the same participante can be in multiple events
    Participante existingParticipante = participanteRepository
      .findByCpf(dto.getCpf());
    Participante participante = new Participante(dto, evento);
    if (existingParticipante == null) {
      participante = participanteRepository.saveAndFlush(participante);
    } else {
      existingParticipante.applyFromOther(participante);
      participante = participanteRepository.saveAndFlush(existingParticipante);
    }
    
    dependenteService.persistDependentes(dto.getDependentes(), participante);
    return participante;
  }

  public List<Participante> findByEventoId(Integer eventoId) {
    return participanteRepository.findByEventoId(eventoId);
  }

  public Integer countByEvento(Integer eventoId) {
    return participanteRepository.countByEventoId(eventoId);
  }

}
