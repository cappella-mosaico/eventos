package eventos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

import eventos.services.DependenteService;
import eventos.entities.Dependente;

@RequiredArgsConstructor
@RestController
public class DependenteController {

  private final DependenteService dependenteService;

  @GetMapping("/eventos/{eventoId}/{participanteId}/dependentes")
  public List<Dependente> dependentes(
    @PathVariable(value="eventoId") Integer eventoId,
    @PathVariable(value="participanteId") UUID participanteId) {
    return dependenteService.findByParticipanteId(participanteId);
  }
  
}
