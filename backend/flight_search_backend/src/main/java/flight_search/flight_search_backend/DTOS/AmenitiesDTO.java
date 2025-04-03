package flight_search.flight_search_backend.DTOS;

import com.fasterxml.jackson.databind.JsonNode;

public class AmenitiesDTO {

    private String description;
    private String isChargeable;

    public AmenitiesDTO(JsonNode amenityNode){
        description = amenityNode.path("description").asText();
        isChargeable = amenityNode.path("isChargeable").asText();
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for isChargeable
    public String getIsChargeable() {
        return isChargeable;
    }

    // Setter for isChargeable
    public void setIsChargeable(String isChargeable) {
        this.isChargeable = isChargeable;
    }
}
