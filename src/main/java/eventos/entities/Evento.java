package eventos.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Evento {

  private String titulo;
  private LocalDateTime dataInicial;
  private LocalDateTime dataFim;
  private String imagemUrl;
  private String sobre;
  private String valor;
  private String local;
  private String endereco;
    
}
