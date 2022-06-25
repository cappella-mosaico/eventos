package eventos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import eventos.controllers.ParticipanteController;
import eventos.services.ParticipanteService;
import eventos.EventosApplication;

import eventos.entities.Dependente;
import eventos.entities.Evento;
import eventos.entities.Participante;
import eventos.entities.dtos.ParticipanteDTO;
import eventos.entities.dtos.DependenteDTO;

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
  public void testEventoAndParticipanteCanBeCreated() throws Exception {
    Evento evento = generatePersistedEvento();
    Participante participante = generatePersistedParticipante(
      evento.getId(),
      new ArrayList<>());
    
    assertThat(participante).isNotNull();
    assertThat(participante.getId()).isNotNull();
    assertThat(participante.getNome()).contains("ruither");
  }

  @Test
  public void testRecoverDependentesFromParticipante() throws Exception {
    Evento evento = generatePersistedEvento();
    DependenteDTO dependenteDTO = new DependenteDTO();
    dependenteDTO.setNome("Mia");
    Participante participante = generatePersistedParticipante(
      evento.getId(), Collections.singletonList(dependenteDTO));

    List<LinkedHashMap<String, Object>> dependentes = this.restTemplate.getForObject(
      "http://localhost:" + port +
      "/eventos/" + evento.getId() +
      "/" + participante.getId() +
      "/dependentes",
      List.class);

    assertThat(dependentes.size()).isEqualTo(1);
    assertThat(dependentes.get(0).get("nome")).isEqualTo("Mia");
  }

  private Evento generatePersistedEvento() {
    Evento evento = new Evento();
    evento.setTitulo("Meu Evento");
    evento.setDataInicial(LocalDateTime.now());
    evento.setDataFinal(LocalDateTime.now());
    evento.setSobre("Essa eh a descricao do meu evento");
    evento = this.restTemplate.postForEntity("http://localhost:" + port + "/eventos", evento, Evento.class).getBody();
    return evento;
  }

  private Participante generatePersistedParticipante(Integer eventoId, List<DependenteDTO> dependentes) {
    ParticipanteDTO dto = new ParticipanteDTO();
    dto.setEventoId(eventoId);
    dto.setNome("ruither");
    dto.setTelefone("999999999");
    dto.setEmail("ruither@fakemail.com");
    dto.setCpf("543.214.535-93");
    dto.setDependentes(dependentes);

    Participante participante = this.restTemplate.postForEntity("http://localhost:" + port + "/eventos/participante", dto, Participante.class).getBody();
    return participante;
  }

}
