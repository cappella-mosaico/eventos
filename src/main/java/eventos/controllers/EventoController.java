package eventos.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import eventos.services.EventoService;
import eventos.repositories.EventoRepository;

import eventos.entities.dtos.EventoDTO;
import eventos.entities.Evento;

@RestController
public class EventoController {

  private EventoService service;
  private EventoRepository repository;

  public EventoController(EventoService eventoService,
			  EventoRepository eventoRepository) {
    this.service = eventoService;
    this.repository = eventoRepository;
  }

  @GetMapping("/eventos")
  public List<EventoDTO> eventos() {
    return service.findEventosWithQuantity();
  }

  @GetMapping("/eventos/{eventoId}")
  public EventoDTO evento(
    @PathVariable(value="eventoId") Integer eventoId) {
    Evento evento = repository.findById(eventoId).orElse(new Evento());
    return service.fillQuantity(evento);
  }

  @PostMapping("/eventos")
  public EventoDTO persist(@RequestBody Evento evento) {
    evento.setCreatedAt(LocalDateTime.now());
    return service.fillQuantity(repository.saveAndFlush(evento));
  }

  @DeleteMapping("/eventos/{eventoId}")
  public Integer delete(
    @PathVariable(value="eventoId") Integer eventoId) {
    repository.deleteById(eventoId);
    return eventoId;
    }
  


}
