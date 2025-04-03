package flight_search.flight_search_backend.DTOS;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.ArrayList;

public class TravelerPricingDTO {

    private String travelerId;
    private String fareOption;
    private String travelerPriceTotal;
    private String travelerPriceBase;
    private List<FareDetailDTO> fareDetails;

    public TravelerPricingDTO(JsonNode travelerPricingsNode) {
        // Initialize the fareDetails list
        fareDetails = new ArrayList<>();

        // Get traveler ID and fare option
        travelerId = travelerPricingsNode.path("travelerId").asText();
        fareOption = travelerPricingsNode.path("fareOption").asText();

        // Get price total and base
        JsonNode priceNode = travelerPricingsNode.path("price");
        travelerPriceTotal = priceNode.path("total").asText();
        travelerPriceBase = priceNode.path("base").asText();

        // Generate FareDetail list
        JsonNode fareDetailBySegmentNode = travelerPricingsNode.path("fareDetailsBySegment");
        for (JsonNode node : fareDetailBySegmentNode) {
            FareDetailDTO fareDetail = new FareDetailDTO(node);
            fareDetails.add(fareDetail);
        }
    }

    // Getter for travelerId
    public String getTravelerId() {
        return travelerId;
    }

    // Setter for travelerId
    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }

    // Getter for fareOption
    public String getFareOption() {
        return fareOption;
    }

    // Setter for fareOption
    public void setFareOption(String fareOption) {
        this.fareOption = fareOption;
    }

    // Getter for travelerPriceTotal
    public String getTravelerPriceTotal() {
        return travelerPriceTotal;
    }

    // Setter for travelerPriceTotal
    public void setTravelerPriceTotal(String travelerPriceTotal) {
        this.travelerPriceTotal = travelerPriceTotal;
    }

    // Getter for travelerPriceBase
    public String getTravelerPriceBase() {
        return travelerPriceBase;
    }

    // Setter for travelerPriceBase
    public void setTravelerPriceBase(String travelerPriceBase) {
        this.travelerPriceBase = travelerPriceBase;
    }

    // Getter for fareDetails
    public List<FareDetailDTO> getFareDetails() {
        return fareDetails;
    }

    // Setter for fareDetails
    public void setFareDetails(List<FareDetailDTO> fareDetails) {
        this.fareDetails = fareDetails;
    }
}
