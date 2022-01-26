package nmfrestbot;

import at.jku.dke.slotmachine.nmf.service.dto.AcceptedFlightListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public class ControllerClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();
    private final String controller = System.getProperty("controller");

    public String acceptSolution(AcceptedFlightListDTO acceptedFlightListDTO, String optId) throws IOException, InterruptedException {
        objectMapper.findAndRegisterModules();
        final String body = objectMapper.writeValueAsString(acceptedFlightListDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(controller + "/optimizations/"+optId+"/solutions/accept"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return (response.body());
    }

    public boolean isActive(){
        try {
            String urlString = controller + "/controller/active";
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return text.equals("Controller is active.");
        } catch (Exception e) {
            return false;
        }
    }

}
