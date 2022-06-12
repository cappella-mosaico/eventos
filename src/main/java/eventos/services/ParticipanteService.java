package eventos.services;

import org.springframework.stereotype.Service;
import java.lang.RuntimeException;

import eventos.repositories.ParticipanteRepository;
import eventos.repositories.EventoRepository;
import eventos.repositories.DependenteRepository;

import eventos.entities.Participante;
import eventos.entities.Evento;
import eventos.entities.Dependente;
import eventos.entities.dtos.ParticipanteDTO;


@Service
public class ParticipanteService {

  private ParticipanteRepository participanteRepository;
  private EventoRepository eventoRepository;
  private DependenteRepository dependenteRepository;

  public ParticipanteService(
     ParticipanteRepository participanteRepository,
     EventoRepository eventoRepository,
     DependenteRepository dependenteRepository) {
    this.participanteRepository = participanteRepository;
    this.eventoRepository = eventoRepository;
    this.dependenteRepository = dependenteRepository;
  }

  public Participante persist(ParticipanteDTO dto) {
    Evento evento = eventoRepository.findById(dto.getEventoId()).orElse(null);
    if (evento == null) {
      throw new RuntimeException("Evento (" + dto.getEventoId() + ") nao encontrado.");
    }
    
    Participante participante = participanteRepository.saveAndFlush(new Participante(dto, evento));
    dto.getDependentes()
      .stream()
      .forEach(dependenteDTO -> {
	  Dependente dependente = dependenteRepository
	    .saveAndFlush(new Dependente(dependenteDTO, participante));
	  participante.getDependentes().add(dependente);
      });

    return participante;
  }

}
