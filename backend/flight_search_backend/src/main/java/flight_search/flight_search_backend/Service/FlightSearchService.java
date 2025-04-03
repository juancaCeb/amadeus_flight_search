package flight_search.flight_search_backend.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flight_search.flight_search_backend.DTOS.FlightOfferDTO;
import flight_search.flight_search_backend.DTOS.FlightResponseDTO;
import flight_search.flight_search_backend.Entities.FlightRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class FlightSearchService {

    @Autowired
    private GetMockResponseService mockResponseService;

    @Autowired
    private GetAmadeusAccessTokenService amadeusAccessTokenService;

    @Autowired
    private ConvertResponseToDTO convertResponseToDTO;

    private final RestTemplate restTemplate;

    public FlightSearchService(){
        this.restTemplate = new RestTemplate();
    }


    public FlightResponseDTO searchFlights(FlightRequestDetails flightRequestDetails) throws IOException {

        String accessToken = amadeusAccessTokenService.getAmadeusAccessToken();

        if (Objects.equals(accessToken, "")) {
            mockResponseService.returnMockResponse();
        }

        ResponseEntity<String> responseFromAmadeus = performFlightSearchPetitionToAmadeus(flightRequestDetails, accessToken);
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(responseFromAmadeus.getBody());

        FlightResponseDTO response = convertResponseToDTO.buildFlightOffer(rootNode);

        return response;

    }

    private ResponseEntity<String> performFlightSearchPetitionToAmadeus(FlightRequestDetails flightRequestDetails, String accessToken){

        String apiUrl = "https://test.api.amadeus.com/v2/shopping/flight-offers";
        String apiUrlWithParams;

        if(Objects.equals(flightRequestDetails.getArrivalDate(), " ")){
            apiUrlWithParams = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("originLocationCode", flightRequestDetails.getDepartureAirportCode())
                    .queryParam("destinationLocationCode", flightRequestDetails.getArrivalAirportCode())
                    .queryParam("departureDate", flightRequestDetails.getDepartureDate())
                    .queryParam("returnDate", flightRequestDetails.getArrivalDate())
                    .queryParam("adults", flightRequestDetails.getNumberOfAdults())
                    .queryParam("currencyCode", flightRequestDetails.getCurrency())
                    .queryParam("nonStop", flightRequestDetails.getHasStops())
                    .toUriString();

        }else{
            apiUrlWithParams = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("originLocationCode", flightRequestDetails.getDepartureAirportCode())
                    .queryParam("destinationLocationCode", flightRequestDetails.getArrivalAirportCode())
                    .queryParam("departureDate", flightRequestDetails.getDepartureDate())
                    .queryParam("returnDate", flightRequestDetails.getArrivalDate())
                    .queryParam("adults", flightRequestDetails.getNumberOfAdults())
                    .queryParam("currencyCode", flightRequestDetails.getCurrency())
                    .queryParam("nonStop", flightRequestDetails.getHasStops())
                    .toUriString();

        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> responseFromAmadeus = restTemplate.exchange(apiUrlWithParams, HttpMethod.GET, entity, String.class);

            if (responseFromAmadeus.getStatusCode() == HttpStatus.OK) {
                return responseFromAmadeus;
            } else {

                return handleErrorResponse(responseFromAmadeus);
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return handleHttpException(e);
        } catch (Exception e) {

            return handleException(e);
        }

    }

    private ResponseEntity<String> handleErrorResponse(ResponseEntity<String> response) {

        return new ResponseEntity<>("Error occurred: " + response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> handleHttpException(Exception e) {

        return new ResponseEntity<>("HTTP Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> handleException(Exception e) {

        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> convertResponseEntityToMap(ResponseEntity<String> responseFromAmadeus) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> convertedResponse = objectMapper.readValue(responseFromAmadeus.getBody(), Map.class);

        return convertedResponse;
    }

}
