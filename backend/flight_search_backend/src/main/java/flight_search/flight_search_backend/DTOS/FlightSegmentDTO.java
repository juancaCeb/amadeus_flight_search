package flight_search.flight_search_backend.DTOS;

import com.fasterxml.jackson.databind.JsonNode;

public class FlightSegmentDTO {

    private String departureDayTime;
    private String arrivalDayTime;
    private String departureAirportIATA;
    private String arrivalAirportIATA;
    private String aircraftCode;
    private String carriers;

    // Constructor
    public FlightSegmentDTO(JsonNode segment) {
        // Parsing the departure information
        JsonNode departureNode = segment.path("departure");
        departureAirportIATA = departureNode.path("iataCode").asText();
        departureDayTime = departureNode.path("at").asText();

        // Parsing the arrival information
        JsonNode arrivalNode = segment.path("arrival");
        arrivalAirportIATA = arrivalNode.path("iataCode").asText();
        arrivalDayTime = arrivalNode.path("at").asText();

        // Getting the aircraft info
        JsonNode aircraftNode = segment.path("aircraft");
        aircraftCode = aircraftNode.path("code").asText();

        // Getting the airline info
        carriers = segment.path("carrierCode").asText();
    }

    // Getters and Setters
    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public String getDepartureDayTime() {
        return departureDayTime;
    }

    public void setDepartureDayTime(String departureDayTime) {
        this.departureDayTime = departureDayTime;
    }

    public String getArrivalDayTime() {
        return arrivalDayTime;
    }

    public void setArrivalDayTime(String arrivalDayTime) {
        this.arrivalDayTime = arrivalDayTime;
    }

    public String getDepartureAirportIATA() {
        return departureAirportIATA;
    }

    public void setDepartureAirportIATA(String departureAirportIATA) {
        this.departureAirportIATA = departureAirportIATA;
    }

    public String getArrivalAirportIATA() {
        return arrivalAirportIATA;
    }

    public void setArrivalAirportIATA(String arrivalAirportIATA) {
        this.arrivalAirportIATA = arrivalAirportIATA;
    }

    public String getCarriers() {
        return carriers;
    }

    public void setCarriers(String carriers) {
        this.carriers = carriers;
    }
}
