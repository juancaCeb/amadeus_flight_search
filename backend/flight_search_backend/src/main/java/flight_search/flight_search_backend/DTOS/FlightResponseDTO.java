package flight_search.flight_search_backend.DTOS;

import java.util.List;

public class FlightResponseDTO {

    private List<FlightOfferDTO> flightOffers;
    private FlightDictionaryDTO dictionary;

    // Constructor
    public FlightResponseDTO(List<FlightOfferDTO> flightOffers, FlightDictionaryDTO dictionary){
        this.flightOffers = flightOffers;
        this.dictionary = dictionary;
    }

    // Getters and Setters
    public List<FlightOfferDTO> getFlightOffers() {
        return flightOffers;
    }

    public void setFlightOffers(List<FlightOfferDTO> flightOffers) {
        this.flightOffers = flightOffers;
    }

    public FlightDictionaryDTO getFlightDictionaryDTO ()  {
        return dictionary;
    }

    public void setFlightDictionaryDTO(FlightDictionaryDTO dictionary) {
        this.dictionary = dictionary;
    }

}
