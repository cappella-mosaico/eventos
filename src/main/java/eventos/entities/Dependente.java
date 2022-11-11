package eventos.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import eventos.entities.Participante;
import eventos.entities.dtos.DependenteDTO;

@Entity
@Data
@Table(name = "dependentes")
@NoArgsConstructor
public class Dependente {

  @Id
  @GeneratedValue
  private UUID id;

  private String nome;
  private Integer idade;

  private boolean isento;
  
  @ManyToOne
  @JoinColumn(name = "participante_id")
  private Participante participante;

  public Dependente(DependenteDTO dto, Participante participante) {
    this.nome = dto.getNome();
    this.isento = dto.isIsento();
    this.idade = dto.getIdade();
    this.participante = participante;
  }

  public void applyFromOther(Dependente dependente) {
    this.nome = dependente.getNome();
    this.isento = dependente.isIsento();
    this.idade = dependente.getIdade();
  }
  
}
