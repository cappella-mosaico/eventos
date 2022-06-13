package eventos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import eventos.controllers.ParticipanteController;
import eventos.services.ParticipanteService;
import eventos.EventosApplication;

import eventos.entities.Evento;
import eventos.entities.Participante;
import eventos.entities.dtos.ParticipanteDTO;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EventosApplicationTests {

  @LocalServerPort
  private int port;
  
  @Autowired
  private ParticipanteController participanteController;
  @Autowired
  private ParticipanteService participanteService;
  @Autowired
  private EventosApplication eventoController;

  @Autowired
  private TestRestTemplate restTemplate;
  
  @Test
  public void contextLoads() throws Exception {
    Evento evento = new Evento();
    evento.setTitulo("Meu Evento");
    evento.setDataInicial(LocalDateTime.now());
    evento.setDataFinal(LocalDateTime.now());
    evento.setSobre("Essa eh a descricao do meu evento");
    evento = this.restTemplate.postForEntity("http://localhost:" + port + "/eventos", evento, Evento.class).getBody();
    
    ParticipanteDTO dto = new ParticipanteDTO();
    dto.setEventoId(evento.getId());
    dto.setNome("ruither");
    dto.setTelefone("999999999");
    dto.setEmail("ruither@fakemail.com");
    dto.setCpf("543.214.535-93");

    Participante participante = this.restTemplate.postForEntity("http://localhost:" + port + "/eventos/participante", dto, Participante.class).getBody();

    assertThat(participante != null);
    assertThat(participante.getId() != null);
    System.out.println(participante);
    assertThat(participante.getNome().contains("ruither"));
  }

}
