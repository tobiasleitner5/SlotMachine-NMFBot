package connectivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testdatagenerator.TestDataConfigDTO;
import java.io.File;
import java.io.IOException;

public class JsonInput {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonInput.class);

    private JsonInput(){
        super();
    }

    public static TestDataConfigDTO readConfig(String fileName) {
        LOGGER.info(String.format("Reading JSON file from '%s'.", fileName));
        ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

        try {
            TestDataConfigDTO testDataConfigDTO  = mapper.readValue(new File(fileName), TestDataConfigDTO.class);
            LOGGER.info("Testdata Config successfully created from JSON file.");
            return testDataConfigDTO;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
