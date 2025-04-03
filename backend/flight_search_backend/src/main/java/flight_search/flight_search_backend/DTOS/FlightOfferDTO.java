package flight_search.flight_search_backend.DTOS;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class FlightOfferDTO {

    private List<FlightItineraryDTO> flightItineraries = new ArrayList<>();
    private String totalPrice;
    private List<TravelerPricingDTO> travelerPricing;
    private String currency;
    private String basePrice;
    private String fees;

    // Constructor
    public FlightOfferDTO(JsonNode flightOffer){

        flightItineraries = new ArrayList<>();
        travelerPricing= new ArrayList<>();

        // Gets the total price
        JsonNode itineraries = flightOffer.path("itineraries");
        JsonNode priceNode = flightOffer.path("price");

        totalPrice = priceNode.path("total").asText();
        basePrice = priceNode.path("base").asText();
        generateFees();
        currency = priceNode.path("currency").asText();

        // Gets the travelerPricing
        JsonNode travelersNode = flightOffer.path("travelerPricings");
        for(JsonNode traveler : travelersNode){
            TravelerPricingDTO flightTraveler = new TravelerPricingDTO(traveler);
            travelerPricing.add(flightTraveler);
        }

        // Gets the itineraries
        for (JsonNode itineraryNode : itineraries) {
            FlightItineraryDTO flightItinerary = new FlightItineraryDTO(itineraryNode);
            flightItineraries.add(flightItinerary);
        }

    }

    private void generateFees() {
        double totalPriceDouble = Double.parseDouble(totalPrice);
        double basePriceDouble = Double.parseDouble(basePrice);

        double doubleFees = totalPriceDouble - basePriceDouble;

        // Use String.format to round the value to 2 decimal places
        this.fees = String.format("%.2f", doubleFees);
    }


    // Getters and Setters
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<FlightItineraryDTO> getFlightItineraries() {
        return flightItineraries;
    }

    public void setFlightItineraries(List<FlightItineraryDTO> flightItineraries) {
        this.flightItineraries = flightItineraries;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<TravelerPricingDTO> getFlightTravelers() {
        return travelerPricing;
    }

    public void setFlightTravelers(List<TravelerPricingDTO> flightTravelers) {
        this.travelerPricing = flightTravelers;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }
}
