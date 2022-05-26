package eventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

import eventos.entities.Evento;


@SpringBootApplication
@RestController
public class EventosApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventosApplication.class, args);
  }

  @GetMapping("/eventos")
  public List<Evento> eventos(@RequestParam(value = "amount") Integer amount) {
    return Collections.singletonList(new Evento(
		      "acampamento",
		      LocalDateTime.now(),
		      LocalDateTime.now().plusDays(7),
		      "https://reactjs.org/logo-og.png",
		      "A Igreja Presbiteriana Mosaico existe para acolher pessoas e formar discípulos de Cristo através de relacionamentos saudáveis e uma pregação bíblica contemporânea no bairro Setor Bueno, na cidade de Goiânia e no mundo",
		      "R$ 15,00 adulto R$ 10,00 até 12 anos",
		      "Na Igreja Presbiteriana Mosaico",
		      "Rua T-53, 480, Setor Bueno Goiânia/Go - Cep 74810-210"));
  }

}
