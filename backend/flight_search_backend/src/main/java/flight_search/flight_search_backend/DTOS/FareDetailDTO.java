package flight_search.flight_search_backend.DTOS;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class FareDetailDTO {

    private String segmentId;
    private String cabin;
    private String fareClass;
    private List<AmenitiesDTO> amenities;

    public FareDetailDTO(JsonNode fareDetailNode){
        segmentId = fareDetailNode.path("segmentId").asText();
        cabin = fareDetailNode.path("cabin").asText();
        fareClass = fareDetailNode.path("class").asText();

        amenities = new ArrayList<>();

        // Generate amenities
        JsonNode amenitiesNode = fareDetailNode.path("amenities");
        for(JsonNode node : amenitiesNode){
            AmenitiesDTO amenity = new AmenitiesDTO(node);
            amenities.add(amenity);
        }
    }

    // Getters and Setters

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getFareClass() {
        return fareClass;
    }

    public void setFareClass(String fareClass) {
        this.fareClass = fareClass;
    }

    public List<AmenitiesDTO> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<AmenitiesDTO> amenities) {
        this.amenities = amenities;
    }
}
