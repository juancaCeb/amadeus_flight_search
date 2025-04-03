package flight_search.flight_search_backend.DTOS;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FlightDictionaryDTO {

    private Map<String, String> aircraft;
    private Map<String, String> carriers;

    public FlightDictionaryDTO(JsonNode dictionaryNode){

        // Parsing the "aircraft" map
        aircraft = new HashMap<>();
        JsonNode aircraftNode = dictionaryNode.path("aircraft");
        if (aircraftNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = aircraftNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                aircraft.put(field.getKey(), field.getValue().asText());
            }
        }

        // Parsing the "carriers" map
        carriers = new HashMap<>();
        JsonNode carriersNode = dictionaryNode.path("carriers");
        if (carriersNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = carriersNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                carriers.put(field.getKey(), field.getValue().asText());
            }
        }

    }

    // Getters and Setters
    public Map<String, String> getAircraft() {
        return aircraft;
    }

    public void setAircraft(Map<String, String> aircraft) {
        this.aircraft = aircraft;
    }

    public Map<String, String> getCarriers() {
        return carriers;
    }

    public void setCarriers(Map<String, String> carriers) {
        this.carriers = carriers;
    }

}
