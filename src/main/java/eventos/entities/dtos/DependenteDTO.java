package eventos.entities.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

import eventos.entities.Participante;
import eventos.entities.Dependente;

@Data
@NoArgsConstructor
public class DependenteDTO {

  private UUID id;
  private String nome;
  private Integer idade;
  private UUID participanteId;
  private boolean isento;

  public DependenteDTO(String nome, int idade) {
    this.nome = nome;
    this.idade = idade;
  }

  public DependenteDTO(Dependente dependente) {
    this.id = dependente.getId();
    this.nome = dependente.getNome();
    this.participanteId = dependente.getParticipante().getId();
    this.isento = dependente.isIsento();
    this.idade = dependente.getIdade();
  }

  public Dependente toDependente() {
    Participante participante = new Participante();
    participante.setId(this.getParticipanteId());

    Dependente dependente = new Dependente();
    dependente.setId(this.getId());
    dependente.setNome(this.getNome());
    dependente.setIsento(this.isIsento());
    dependente.setParticipante(participante);
    dependente.setIdade(this.getIdade());

    return dependente;
  }

  public String toString() {
    return this.id + " " + this.nome + " " + this.isento + " " + this.participanteId + " " + this.idade;
  }

}
