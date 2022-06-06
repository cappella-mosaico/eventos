package eventos.entities;

import lombok.Data;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import eventos.entities.Participante;

@Entity
@Data
@Table(name = "dependentes")
public class Dependente {

  @Id
  @GeneratedValue
  private UUID id;

  private String nome;
  
  @ManyToOne
  @JoinColumn(name = "participante_id")
  private Participante participante;
  
}
