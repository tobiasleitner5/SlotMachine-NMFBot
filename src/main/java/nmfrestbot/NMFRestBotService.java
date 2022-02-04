package nmfrestbot;

import at.jku.dke.slotmachine.controller.service.dto.SolutionListDTO;
import at.jku.dke.slotmachine.nmf.service.dto.AcceptedFlightListDTO;
import at.jku.dke.slotmachine.nmf.service.dto.EnvelopeDTO;
import connectivity.TestDataGeneratorConnector;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class NMFRestBotService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NMFRestBotService.class);
    private static final ControllerClient controllerClient = new ControllerClient();
    private static EnvelopeDTO flightList = TestDataGeneratorConnector.generateEnvelopeDTO();

    @PostMapping(value = "api/flight_proposals", consumes = "application/json") //SolutionListDTO
    @Operation(
            description = "Returns a solution."
    )
    public ResponseEntity<String> getSolutionList(@RequestBody SolutionListDTO solutionListDTO){
        LOGGER.info(String.format("Received SolutionNotification: %s.", solutionListDTO));
        String optId = solutionListDTO.getSolutions().get(0).getOptimizationId();
        String solutionId = solutionListDTO.getSolutions().get(0).getSolutionId();
        if(solutionId == null || optId == null){
            return new ResponseEntity<String>("Problem with SolutionList input.", HttpStatus.BAD_REQUEST);
        }
        AcceptedFlightListDTO acceptedFlightListDTO = new AcceptedFlightListDTO();
        acceptedFlightListDTO.setSolutionId(solutionId);
        acceptedFlightListDTO.setOptimizationId(optId);
        flightList = TestDataGeneratorConnector.generateEnvelopeDTO();
        if(flightList == null){
            return new ResponseEntity<String>("Problem with FlightListGenerator.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            controllerClient.acceptSolution(acceptedFlightListDTO, optId);
            LOGGER.info("Solution accepted.");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Problem with solution acceptance.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Problem with solution acceptance.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @GetMapping(value = "/api/FlightListByAerodrome", produces = "application/xml")
    @Operation(
            description = "Returns a solution."
    )
    public EnvelopeDTO getFlightList(){
        return flightList;
    }
}
