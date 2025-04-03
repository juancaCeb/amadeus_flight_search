package flight_search.flight_search_backend.Controller;

import flight_search.flight_search_backend.DTOS.FlightOfferDTO;
import flight_search.flight_search_backend.DTOS.FlightResponseDTO;
import flight_search.flight_search_backend.Entities.FlightRequestDetails;
import flight_search.flight_search_backend.Service.AmadeusHeldCheck;
import flight_search.flight_search_backend.Service.FlightSearchService;
import flight_search.flight_search_backend.Service.GetMockResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
public class FlightSearchController {

    @Autowired
    private FlightSearchService flightSearchService;

    @Autowired
    private AmadeusHeldCheck amadeusHeldCheck;

    @Autowired
    private GetMockResponseService mockResponseService;

    @CrossOrigin(origins = "http://localhost:5173") // Allow CORS for this specific endpoint
    @GetMapping("{departureAirportCode}/{arrivalAirportCode}/{departureDate}/{arrivalDate}/{numberOfAdults}/{currency}/{hasStops}")
    public @ResponseBody FlightResponseDTO searchFlights(
            @PathVariable("departureAirportCode") String departureAirportCode,
            @PathVariable("arrivalAirportCode") String arrivalAirportCode,
            @PathVariable("departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String departureDate,
            @PathVariable("departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String arrivalDate,
            @PathVariable("numberOfAdults") Integer numberOfAdults,
            @PathVariable("currency") String currency,
            @PathVariable("hasStops") Boolean hasStops
    ) throws IOException {

        FlightRequestDetails flightRequestDetails = new FlightRequestDetails(
                departureAirportCode,
                arrivalAirportCode,
                departureDate,
                arrivalDate,
                numberOfAdults,
                currency,
                hasStops
        );

        if (amadeusHeldCheck.checkIfAmadeusIsUp()){

            return flightSearchService.searchFlights(flightRequestDetails);

        } else{

            return mockResponseService.returnMockResponse();

        }

    }

}
