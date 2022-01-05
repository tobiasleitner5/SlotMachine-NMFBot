package connectivity;

import at.jku.dke.slotmachine.nmf.service.dto.EnvelopeDTO;
import testdatagenerator.TestDataGenerator;

public class TestDataGeneratorConnector {
    private TestDataGeneratorConnector(){super();}

    public static EnvelopeDTO generateEnvelopeDTO(){
        return TestDataGenerator.generateEnvelopeDTO();
    }
}
