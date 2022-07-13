package eventos.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import eventos.services.ParticipanteService;
import eventos.services.DependenteService;
import eventos.repositories.EventoRepository;

import eventos.entities.Evento;
import eventos.entities.dtos.EventoDTO;


@Service
public class EventoService {

  private EventoRepository eventoRepository;
  private ParticipanteService participanteService;
  private DependenteService dependenteService;

  public EventoService(
    EventoRepository eventoRepository,
    ParticipanteService participanteService,
    DependenteService dependenteService) {
    
    this.eventoRepository = eventoRepository;
    this.participanteService = participanteService;
    this.dependenteService = dependenteService;
  }

  public EventoDTO fillQuantity(Evento evento) {
    EventoDTO dto = new EventoDTO(evento);
    Integer countParticipantes = participanteService.countByEvento(evento.getId());
    Integer countDependentes = dependenteService.countByEvento(evento.getId());
    dto.setQuantidadePessoas(countParticipantes + countDependentes);
    return dto;
  }

  public List<EventoDTO> findEventosWithQuantity() {
    List<Evento> eventos = eventoRepository.findTop10ByOrderByIdDesc();
    return eventos.stream()
      .map(e -> fillQuantity(e))
      .collect(Collectors.toList());
  }

}
