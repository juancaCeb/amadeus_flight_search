package flight_search.flight_search_backend.MockAnswers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;


public class AmadeusMockResponse {

    private final String amadeusMockJsonResponseFile = "flight_offers.json";

    public Map<String, Object> returnMockResponse() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File(Objects.requireNonNull(AmadeusMockResponse.class.getClassLoader().getResource(amadeusMockJsonResponseFile)).getFile());

        return objectMapper.readValue(jsonFile, Map.class);
    }



}
