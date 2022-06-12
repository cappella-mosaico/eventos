package eventos.entities.dtos;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

import eventos.entities.dtos.DependenteDTO;

@Data
public class ParticipanteDTO {

  private String nome;
  private String telefone;
  private String email;
  private String cpf;

  private Integer eventoId;

  private List<DependenteDTO> dependentes = new ArrayList<>();

}
