package eventos.entities.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import eventos.entities.dtos.DependenteDTO;

@Data
public class ParticipanteDTO {

  private UUID id;

  private String nome;
  private String telefone;
  private String email;
  private String cpf;
  private boolean isento;
  private Double valorPago;
  private Integer idade;

  private Integer eventoId;

  private LocalDateTime createdAt;

  private List<DependenteDTO> dependentes = new ArrayList<>();

  public String toString() {
    return nome + " " + telefone + " " + email + " " + cpf + " " + isento + " " + eventoId + " " + valorPago + " " + idade;
  }

}
