package eventos.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import eventos.entities.Participante;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventos")
public class Evento {

  @Id
  @GeneratedValue
  private Integer id;
  
  private String titulo;
  private LocalDateTime dataInicial;
  private LocalDateTime dataFinal;
  private String imagem;
  private String sobre;
  private String valor;
  private String local;
  private String endereco;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  /*
  @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
  private List<Participante> participantes = new ArrayList<>();
  */
}
