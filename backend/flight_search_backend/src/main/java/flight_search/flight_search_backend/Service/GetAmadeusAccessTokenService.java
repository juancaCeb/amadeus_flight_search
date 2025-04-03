package flight_search.flight_search_backend.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetAmadeusAccessTokenService {

    @Value("${amadeus.client.id}")
    private String clientId;

    @Value("${amadeus.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    public GetAmadeusAccessTokenService() {
        this.restTemplate = new RestTemplate();
    }

    public String getAmadeusAccessToken() throws JsonProcessingException {

        String accessTokenUrl = "https://test.api.amadeus.com/v1/security/oauth2/token";
        String body = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        try {

            ResponseEntity<String> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                return rootNode.path("access_token").asText();
            } else {

                System.err.println("Error: Failed to retrieve access token. HTTP Status: " + response.getStatusCode());
                return "";
            }

        } catch (Exception e) {

            System.err.println("Error: Exception occurred while fetching access token - " + e.getMessage());
            e.printStackTrace();
            return "";
        }

    }


}
