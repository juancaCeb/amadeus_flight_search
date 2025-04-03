package flight_search.flight_search_backend.Entities;

import java.util.Date;

public class FlightRequestDetails {

    private String departureAirportCode;
    private String arrivalAirportCode;
    private String departureDate;
    private String arrivalDate;
    private Integer numberOfAdults;
    private String currency;
    private Boolean hasStops;

    public FlightRequestDetails(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate,
                                Integer numberOfAdults, String currency, Boolean hasStops) {
        this.departureAirportCode = departureAirportCode;
        this.arrivalAirportCode = arrivalAirportCode;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.numberOfAdults = numberOfAdults;
        this.currency = currency;
        this.hasStops = hasStops;
    }

    // Getter and Setter for departureAirportCode
    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public String getArrivalDate(){
        return this.arrivalDate;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    // Getter and Setter for arrivalAirportCode
    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    // Getter and Setter for departureDate
    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    // Getter and Setter for numberOfAdults
    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    // Getter and Setter for currency
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Getter and Setter for hasStops
    public Boolean getHasStops() {
        return hasStops;
    }

    public void setHasStops(Boolean hasStops) {
        this.hasStops = hasStops;
    }
}
