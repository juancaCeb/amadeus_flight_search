package flight_search.flight_search_backend.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flight_search.flight_search_backend.DTOS.FlightResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class GetMockResponseService {

    @Autowired
    private ConvertResponseToDTO convertResponseToDTO;
    private final String amadeusMockJsonResponseFile = "mock_flight_response.json";

    public FlightResponseDTO returnMockResponse() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File(Objects.requireNonNull(GetMockResponseService.class.getClassLoader().getResource(amadeusMockJsonResponseFile)).getFile());

        // Read the JSON file into a JsonNode
        JsonNode jsonNode = objectMapper.readTree(jsonFile);

        FlightResponseDTO response = convertResponseToDTO.buildFlightOffer(jsonNode);

        return response;

    }


}
