package eventos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import java.lang.Void;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

import eventos.controllers.ParticipanteController;
import eventos.services.ParticipanteService;
import eventos.EventosApplication;

import eventos.entities.Dependente;
import eventos.entities.Evento;
import eventos.entities.Participante;
import eventos.entities.dtos.ParticipanteDTO;
import eventos.entities.dtos.DependenteDTO;
import eventos.entities.dtos.EventoDTO;

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
  public void eventoAndParticipanteCanBeCreated() throws Exception {
    Evento evento = generatePersistedEvento();
    Participante participante = generatePersistedParticipante(
      evento.getId(),
      new ArrayList<>());
    
    assertThat(participante).isNotNull();
    assertThat(participante.getId()).isNotNull();
    assertThat(participante.getNome()).contains("ruither");
  }

  @Test
  public void eventoCanBeQueried() throws Exception {
    Evento evento = generatePersistedEvento();
    DependenteDTO maria = new DependenteDTO("Maria", 10);
    DependenteDTO joao = new DependenteDTO("Joao", 13);
    Participante participante = generatePersistedParticipante(
      evento.getId(),
      Arrays.asList(maria, joao)
    );

    EventoDTO dto = this.restTemplate.getForObject(
      "http://localhost:" + port + "/eventos/" + evento.getId(),
      EventoDTO.class);

    assertThat(dto).isNotNull();
    assertThat(dto.getQuantidadePessoas()).isEqualTo(3);
  }

  @Test
  public void participantesCanBeQueried() throws Exception {
    Evento evento = generatePersistedEvento();
    Participante participante = generatePersistedParticipante(
      evento.getId(), Collections.emptyList());

    List<LinkedHashMap<String, Object>> participantes = this.restTemplate.getForObject(
      "http://localhost:" + port +
      "/eventos/" + evento.getId() +
      "/participantes", List.class);

    assertThat(participantes).hasSize(1);
    assertThat(participantes.get(0).get("id")).isEqualTo(participante.getId().toString());
  }

  @Test
  public void recoverDependentesFromParticipante() throws Exception {
    Evento evento = generatePersistedEvento();
    DependenteDTO dependenteDTO = new DependenteDTO("Mia", 10);
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
  public void preventDependenteWithoutNome() throws Exception {
    Evento evento = generatePersistedEvento();
    DependenteDTO dependenteDTO = new DependenteDTO(" ", 10);
    Participante participante = generatePersistedParticipante(
      evento.getId(), Collections.singletonList(dependenteDTO));

    List<LinkedHashMap<String, Object>> dependentes = this.restTemplate.getForObject(
      "http://localhost:" + port +
      "/eventos/" + evento.getId() +
      "/" + participante.getId() +
      "/dependentes",
      List.class);

    assertThat(dependentes).hasSize(0);
  }

  @Test
  public void preventDuplicatedDependenteFromBeingAddedAfterASecondSubmissionOfThoseSameDependentes() {
    Evento evento = generatePersistedEvento();
    DependenteDTO maria = new DependenteDTO("Maria", 10);
    DependenteDTO joao = new DependenteDTO("Joao", 10);
    generatePersistedParticipante(
      evento.getId(), Arrays.asList(joao, maria));
    DependenteDTO eduardo = new DependenteDTO("Eduardo", 10);

    Participante participante = generatePersistedParticipante(
      evento.getId(), Arrays.asList(maria, joao, eduardo));

    List<LinkedHashMap<String, Object>> dependentes = this.restTemplate.getForObject(
      "http://localhost:" + port +
      "/eventos/" + evento.getId() +
      "/" + participante.getId() +
      "/dependentes",
      List.class);

    assertThat(dependentes.stream().map(d -> d.get("nome")).collect(Collectors.toList()))
      .hasSize(3)
      .contains("Maria", "Joao", "Eduardo");
  }

  @Test
  public void preventNewParticipanteWithTheSameCPF() throws Exception {
    Evento evento = generatePersistedEvento();
    Participante participante = generatePersistedParticipante(
      evento.getId(), Collections.emptyList());
    ParticipanteDTO duplicatedParticipanteDTO = new ParticipanteDTO();
    duplicatedParticipanteDTO.setEventoId(evento.getId());
    duplicatedParticipanteDTO.setNome("Joyce");
    duplicatedParticipanteDTO.setTelefone("888888888");
    duplicatedParticipanteDTO.setEmail("joyce@fakemail.com");
    duplicatedParticipanteDTO.setCpf("543.214.535-93");
    duplicatedParticipanteDTO.setValorPago(15.5);
    duplicatedParticipanteDTO.setIdade(42);

    Participante duplicatedParticipante = this.restTemplate.postForEntity(
        "http://localhost:" + port + "/eventos/participante",
        duplicatedParticipanteDTO,
        Participante.class)
      .getBody();
    assertThat(duplicatedParticipante.getId()).isEqualTo(participante.getId());
  }

  @Test
  public void assertThatADependenteCanBeIsento() {
    Evento evento = generatePersistedEvento();
    DependenteDTO mia = new DependenteDTO("Mia", 10);
    Participante participante = generatePersistedParticipante(
      evento.getId(),
      Collections.singletonList(mia)
    );

    mia.setIsento(true);
    this.restTemplate.exchange(
      "http://localhost:" + port + "/eventos/" 
        + evento.getId() 
        + "/" 
        + participante.getId() 
        + "/dependentes",
      HttpMethod.PUT,
      new HttpEntity(mia),
      Void.class);

    List<LinkedHashMap<String, Object>> dependentes = this.restTemplate
      .getForObject("http://localhost:" + port +
      "/eventos/" + evento.getId() +
      "/" + participante.getId() +
      "/dependentes",
      List.class);

    assertThat(dependentes.get(0).get("isento")).asString().contains("true");
  }

  @Test
  public void aParticipanteCanBeIsento() {
    Evento evento = generatePersistedEvento();

    ParticipanteDTO dto = new ParticipanteDTO();
    dto.setEventoId(evento.getId());
    dto.setNome("Maria");
    dto.setTelefone("999999999");
    dto.setEmail("maria@fakemail.com");
    dto.setCpf("553.766.071-78");
    dto.setIsento(false);
    dto.setValorPago(15.0);
    dto.setIdade(15);
    dto.setDependentes(Collections.emptyList());

    Participante participante = this.restTemplate.postForEntity(
        "http://localhost:" + port + "/eventos/participante",
        dto,
        Participante.class)
      .getBody();

    List<LinkedHashMap<String, Object>> participantes = this.restTemplate.getForObject(
      "http://localhost:" + port +
      "/eventos/" + evento.getId() +
      "/participantes", List.class);

    assertThat(participantes.get(0).get("isento")).isEqualTo(false);

    dto.setIsento(true);

    Participante participanteIsento = this.restTemplate.postForEntity(
        "http://localhost:" + port + "/eventos/participante",
        dto,
        Participante.class)
      .getBody();

    assertThat(participanteIsento.isIsento()).isEqualTo(true);
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
    dto.setValorPago(15.4);
    dto.setIdade(12);
    dto.setDependentes(dependentes);

    Participante participante = this.restTemplate.postForEntity(
        "http://localhost:" + port + "/eventos/participante",
        dto,
        Participante.class)
      .getBody();
    return participante;
  }

}
