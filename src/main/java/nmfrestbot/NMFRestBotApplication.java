package nmfrestbot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "SlotMachine NMF REST Bot Endpoint", version = "1.0", description = "SlotMachine NMF REST Bot Endpoint.")
)
public class NMFRestBotApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(NMFRestBotService.class);
    private static final String controller = System.getProperty("controller");
    private static final ControllerClient controllerClient= new ControllerClient();

    public static void main(String[] args){
        if(!controllerClient.isActive()){
            throw new RuntimeException("Controller is not active.");
        } else {
            SpringApplication.run(NMFRestBotApplication.class, args);
            LOGGER.info("Controller host: " + controller);
        }
    }
}
