package connectivity;

import at.jku.dke.slotmachine.nmf.service.dto.EnvelopeDTO;
import testdatagenerator.TestDataGenerator;

import java.io.IOException;

public class TestDataGeneratorConnector {
    private TestDataGeneratorConnector(){super();}

    public static EnvelopeDTO generateEnvelopeDTO(){
        try {
            return TestDataGenerator.generateEnvelopeDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
