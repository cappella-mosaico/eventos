package eventos.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
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
  public Participante add(@RequestBody ParticipanteDTO participante) {
    return participanteService.persist(participante);
  }

}
