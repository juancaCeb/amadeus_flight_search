package flight_search.flight_search_backend.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flight_search.flight_search_backend.DTOS.AircraftDTO;
import flight_search.flight_search_backend.DTOS.FlightDictionaryDTO;
import flight_search.flight_search_backend.DTOS.FlightOfferDTO;
import flight_search.flight_search_backend.DTOS.FlightResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertResponseToDTO {

    private List<FlightOfferDTO> flightOffers;

    private FlightResponseDTO flightResponse;

    public FlightResponseDTO buildFlightOffer(JsonNode rootNode){

        flightOffers = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode flightOffersNode = rootNode.path("data");

        for (JsonNode offerNode : flightOffersNode) {
            FlightOfferDTO flightOffer = new FlightOfferDTO(offerNode);
            flightOffers.add(flightOffer);

        }

        //get dictionary

        JsonNode flightDictionary = rootNode.path("dictionaries");
        FlightDictionaryDTO dictionary = new FlightDictionaryDTO(flightDictionary);

        flightResponse = new FlightResponseDTO(flightOffers, dictionary);

        return flightResponse;

    }


}
