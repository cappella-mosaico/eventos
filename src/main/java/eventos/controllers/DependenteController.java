package eventos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

import eventos.services.DependenteService;
import eventos.entities.dtos.DependenteDTO;

@RequiredArgsConstructor
@RestController
public class DependenteController {

  private final DependenteService dependenteService;

  @GetMapping("/eventos/{eventoId}/{participanteId}/dependentes")
  public List<DependenteDTO> dependentes(
    @PathVariable(value="eventoId") Integer eventoId,
    @PathVariable(value="participanteId") UUID participanteId) {
    return dependenteService.findByParticipanteId(participanteId)
      .stream()
      .map(dependente -> new DependenteDTO(dependente))
      .collect(Collectors.toList());
  }

  @PutMapping("/eventos/{eventoId}/{participanteId}/dependentes")
  public DependenteDTO save(
    @RequestBody DependenteDTO dependenteDTO,
    @PathVariable(value="eventoId") Integer eventoId,
    @PathVariable(value="participanteId") UUID participanteId) {
    if (dependenteDTO.getNome() == null 
        || dependenteDTO.getNome().isEmpty()) {
      return null;
    }
    dependenteDTO.setParticipanteId(participanteId);
    return new DependenteDTO(dependenteService.persist(
      dependenteDTO.toDependente()));
  }
  
}
