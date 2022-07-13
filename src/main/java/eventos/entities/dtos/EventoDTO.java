package eventos.entities.dtos;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

import eventos.entities.Evento;

@Data
@NoArgsConstructor
public class EventoDTO {

  private Integer id;
  
  private String titulo;
  private LocalDateTime dataInicial;
  private LocalDateTime dataFinal;
  private String imagem;
  private String sobre;
  private String valor;
  private String local;
  private String endereco;

  private Integer quantidadePessoas;

  public EventoDTO(Evento evento) {
    this.id = evento.getId();
    this.titulo = evento.getTitulo();
    this.dataInicial = evento.getDataInicial();
    this.dataFinal = evento.getDataFinal();
    this.imagem = evento.getImagem();
    this.sobre = evento.getSobre();
    this.valor = evento.getValor();
    this.local = evento.getLocal();
    this.endereco = evento.getEndereco();

    this.quantidadePessoas = 0;
  }

}
