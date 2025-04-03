package flight_search.flight_search_backend.DTOS;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightItineraryDTO {

    private String totalTime;
    private String initialDepartureDayTime;
    private String finalArrivalDayTime;
    private List<FlightSegmentDTO> flightSegments;

    // Constructor
    public FlightItineraryDTO(JsonNode itinerary) {
        flightSegments = new ArrayList<>();

        JsonNode flightSegmentsNode = itinerary.path("segments");

        for (JsonNode itineraryNode : flightSegmentsNode) {
            FlightSegmentDTO flightSegment = new FlightSegmentDTO(itineraryNode);
            flightSegments.add(flightSegment);
        }
        generateInitialDepartureDateTime();
        generateTotalTime();
    }

    private void generateInitialDepartureDateTime(){

        initialDepartureDayTime = flightSegments.getFirst().getDepartureDayTime();
        finalArrivalDayTime = flightSegments.getLast().getArrivalDayTime();

    }

    private void generateTotalTime(){

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime initialDateTime = LocalDateTime.parse(initialDepartureDayTime, formatter);
        LocalDateTime finalDateTime = LocalDateTime.parse(finalArrivalDayTime, formatter);

        Duration duration = Duration.between(initialDateTime, finalDateTime);

        long hours = duration.toHours();  // Get the total hours
        long minutes = duration.toMinutes() % 60;  // Get the remaining minutes after hours are calculated

        String timeDifference = hours + " hours and " + minutes + " minutes";

        this.totalTime = timeDifference;

    }

    // Getters and Setters
    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }


    public String getInitialDepartureDayTime() {
        return initialDepartureDayTime;
    }

    public void setInitialDepartureDayTime(String initialDepartureDayTime) {
        this.initialDepartureDayTime = initialDepartureDayTime;
    }

    public String getFinalDepartureDayTime() {
        return finalArrivalDayTime;
    }

    public void setFinalDepartureDayTime(String finalDepartureDayTime) {
        this.finalArrivalDayTime = finalDepartureDayTime;
    }

    public List<FlightSegmentDTO> getFlightSegments() {
        return flightSegments;
    }

    public void setFlightSegments(List<FlightSegmentDTO> flightSegments) {
        this.flightSegments = flightSegments;
    }
}
