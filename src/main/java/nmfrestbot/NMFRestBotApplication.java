package nmfrestbot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "SlotMachine NMF REST Bot Endpoint", version = "1.0", description = "SlotMachine NMF REST Bot Endpoint.")
)
public class NMFRestBotApplication {
    public static void main(String[] args){
        SpringApplication.run(NMFRestBotApplication.class, args);
    }
}
