package flight_search.flight_search_backend.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flight_search.flight_search_backend.Entities.FlightRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class AmadeusHeldCheck {

    @Autowired
    GetAmadeusAccessTokenService amadeusAccessTokenService;

    @Autowired
    FlightSearchService flightSearchService;

    public boolean checkIfAmadeusIsUp() throws IOException {
        String accessToken = amadeusAccessTokenService.getAmadeusAccessToken();

        if (Objects.equals(accessToken, "")) {
            return false;
        }

        return true;


    }


}
