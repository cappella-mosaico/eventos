package eventos.entities;

import lombok.Data;
import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.CascadeType;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import eventos.entities.Dependente;
import eventos.entities.Evento;

@Entity
@Data
@Table(name = "participantes")
public class Participante {

  @Id
  @GeneratedValue
  private UUID id;

  private String nome;
  private String telefone;
  private String email;
  private String cpf;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL)
  private List<Dependente> dependentes = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "evento_id")
  private Evento evento;
  
}
