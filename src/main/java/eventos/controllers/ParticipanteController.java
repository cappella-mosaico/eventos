package eventos.controllers;

import java.util.List;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import eventos.entities.dtos.ParticipanteDTO;
import eventos.services.ParticipanteService;
import eventos.entities.Participante;


@RestController
public class ParticipanteController {

  private ParticipanteService participanteService;

  public ParticipanteController(ParticipanteService participanteService) {
    this.participanteService = participanteService;
  }
  
  @PostMapping("/eventos/participante")
  public Participante save(@RequestBody ParticipanteDTO participante) {
    if (participante.getId() == null) {
      participante.setCreatedAt(LocalDateTime.now());
    }
    return participanteService.persist(participante);
  }

  @GetMapping("/eventos/{eventoId}/participantes")
  public List<Participante> list(
    @PathVariable(value="eventoId") Integer eventoId) {
    return participanteService.findByEventoId(eventoId);
  }

}
