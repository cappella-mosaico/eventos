package eventos.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.lang.RuntimeException;

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
    
    Participante participante = participanteRepository.saveAndFlush(
      new Participante(dto, evento));
    
    dependenteService.persistDependentes(dto.getDependentes(), participante);
    
    return participante;
  }

}
