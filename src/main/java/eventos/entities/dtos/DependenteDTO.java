package eventos.entities.dtos;

import lombok.Data;
import java.util.UUID;

@Data
public class DependenteDTO {

  private String nome;
  private UUID participanteId;

}
