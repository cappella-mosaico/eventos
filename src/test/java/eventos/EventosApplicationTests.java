package eventos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

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
@Sql("/db/clean-test-database.sql")
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
    DependenteDTO dependenteDTO = new DependenteDTO("Mia");
    Participante participante = generatePersistedParticipante(
      evento.getId(), Collections.singletonList(dependenteDTO));

    List<LinkedHashMap<String, Object>> dependentes = this.restTemplate.getForObject(
      "http://localhost:" + port +
      "/eventos/" + evento.getId() +
      "/" + participante.getId() +
      "/dependentes",
      List.class);

    assertThat(dependentes).hasSize(1);
    assertThat(dependentes.get(0).get("nome")).isEqualTo("Mia");
  }

  @Test
  public void testPreventDuplicatedDependenteFromBeingAddedAfterASecondSubmissionOfThoseSameDependentes() {
    // given
    Evento evento = generatePersistedEvento();
    DependenteDTO maria = new DependenteDTO("Maria");
    DependenteDTO joao = new DependenteDTO("Joao");
    generatePersistedParticipante(
      evento.getId(), Arrays.asList(joao, maria));
    DependenteDTO eduardo = new DependenteDTO("Eduardo");

    Participante participante = generatePersistedParticipante(
      evento.getId(), Arrays.asList(maria, joao, eduardo));

    // when
    List<LinkedHashMap<String, Object>> dependentes = this.restTemplate.getForObject(
      "http://localhost:" + port +
      "/eventos/" + evento.getId() +
      "/" + participante.getId() +
      "/dependentes",
      List.class);
    
    // then
    assertThat(dependentes.stream().map(d -> d.get("nome")).collect(Collectors.toList()))
      .hasSize(3)
      .contains("Maria", "Joao", "Eduardo");
  }

  @Test
  public void testPreventNewParticipanteWithTheSameCPF() throws Exception {
    Evento evento = generatePersistedEvento();
    Participante participante = generatePersistedParticipante(
      evento.getId(), Collections.emptyList());
    ParticipanteDTO duplicatedParticipanteDTO = new ParticipanteDTO();
    duplicatedParticipanteDTO.setEventoId(evento.getId());
    duplicatedParticipanteDTO.setNome("Joyce");
    duplicatedParticipanteDTO.setTelefone("888888888");
    duplicatedParticipanteDTO.setEmail("joyce@fakemail.com");
    duplicatedParticipanteDTO.setCpf("543.214.535-93");

    Participante duplicatedParticipante = this.restTemplate.postForEntity(
        "http://localhost:" + port + "/eventos/participante",
        duplicatedParticipanteDTO,
        Participante.class)
      .getBody();
    assertThat(duplicatedParticipante.getId()).isEqualTo(participante.getId());
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

    Participante participante = this.restTemplate.postForEntity(
        "http://localhost:" + port + "/eventos/participante",
        dto,
        Participante.class)
      .getBody();
    return participante;
  }

}
