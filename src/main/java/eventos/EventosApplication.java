package eventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;

import eventos.repositories.EventoRepository;
import eventos.entities.Evento;


@SpringBootApplication
@RestController
public class EventosApplication {

  @Autowired
  private EventoRepository repository;
  

  public static void main(String[] args) {
    SpringApplication.run(EventosApplication.class, args);
  }

  @GetMapping("/eventos")
  public List<Evento> eventos() {
    return repository.findTop10ByOrderByIdDesc();
  }

  @PostMapping("/eventos")
  public Evento persist(@RequestBody Evento evento) {
    evento.setCreatedAt(LocalDateTime.now());
    return repository.saveAndFlush(evento);
  }

}
