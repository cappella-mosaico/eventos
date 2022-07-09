package eventos.entities.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class DependenteDTO {

  private String nome;
  private UUID participanteId;

  public DependenteDTO(String nome) {
    this.nome = nome;
  }

}
