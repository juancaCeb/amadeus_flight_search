import { useLocation } from "react-router-dom";
import { FlightItinerary, FlightTraveler } from "./types";
import data from "../assets/airportData.json";  


const formatDate = (dateStr: string) => {
  const date = new Date(dateStr);
  return date.toLocaleString("en-US", {
    year: "numeric", 
    month: "short",
    day: "numeric", 
    hour: "numeric", 
    minute: "2-digit", 
    hour12: true, 
  });
};

const calculateStops = (flightItinerary: FlightItinerary) => {
  const numberOfSegments = flightItinerary.flightSegments.length;
  return numberOfSegments > 1 ? numberOfSegments - 1 : 0; 
};

const calculateLayoverTimes = (flightItinerary: FlightItinerary) => {
  const layoverTimes = [];
  for (let i = 0; i < flightItinerary.flightSegments.length - 1; i++) {
    const segment = flightItinerary.flightSegments[i];
    const nextSegment = flightItinerary.flightSegments[i + 1];

    const waitingAirport = segment.arrivalAirportIATA;

    const arrivalTime = new Date(segment.arrivalDayTime);
    const nextDepartureTime = new Date(nextSegment.departureDayTime);

    const layoverTime = nextDepartureTime.getTime() - arrivalTime.getTime(); 

    const layoverInMinutes = Math.floor(layoverTime / (1000 * 60));
    const hours = Math.floor(layoverInMinutes / 60);
    const minutes = layoverInMinutes % 60;

    layoverTimes.push({ hours, minutes, waitingAirport });
  }
  return layoverTimes;
};

const getAirportCityAndName = (iata: string) => {
  const airport = Object.values(data).find((item) => item.iata && item.iata === iata);
  if (airport) {
    return `${airport.city} (${airport.iata})`;
  }
  return "Unknown Airport"; 
};

function FlightBreakdown() {
  const location = useLocation();
  const { flightOffer, dictionary } = location.state || {};

  if (!flightOffer) return <div>No flight details found</div>;

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white rounded-lg shadow-md relative">
      <div className="mb-6">
        <h2 className="text-xl font-semibold text-black">Flight Details</h2>
        <p className="text-gray-600">
          Total Price: {flightOffer.totalPrice} {flightOffer.currency}
        </p>
      </div>

      {/* Main itinerary container */}
      <div className="space-y-4 max-h-screen overflow-y-auto">
        {flightOffer.flightItineraries.map((itinerary: FlightItinerary, index: number) => {
          const layoverTimes = calculateLayoverTimes(itinerary);

          return (
            <div key={index} className="bg-gray-100 p-6 rounded-lg shadow-md border border-gray-300">
              <h3 className="text-xl font-semibold text-black mb-2">Itinerary {index + 1}</h3>
              <div className="text-gray-700">

                <p className="mb-2"><strong>Departure:</strong> {formatDate(itinerary.initialDepartureDayTime)}</p>
                <p className="mb-2"><strong>Arrival:</strong> {formatDate(itinerary.finalDepartureDayTime)}</p>
                <p className="font-medium">Total Flight Time: {itinerary.totalTime}</p>
                <p className="font-medium">Number of Stops: {calculateStops(itinerary)}</p> 

                {layoverTimes.length > 0 && (
                  <div className="text-gray-600">
                    <p className="font-medium">Layover Times:</p>
                    {layoverTimes.map((layover, idx) => {
                      const layoverAirport = getAirportCityAndName(layover.waitingAirport) || "Unknown Airport";
                      return (
                        <p key={idx} className="font-medium">
                          Stop {idx + 1}: {layover.hours}h {layover.minutes}m at {layoverAirport}
                        </p>
                      );
                    })}
                  </div>
                )}
                <div className="text-xl font-semibold text-blue-600">
  {flightOffer.flightTravelers && flightOffer.flightTravelers.length > 0 ? (
    <>
      <p className="font-medium">
        Total Cost Per Traveler:{" "}
        {(
          (parseFloat(flightOffer.totalPrice) /
          flightOffer.flightTravelers.length)/2
        ).toFixed(2)}{" "} 
        {flightOffer.currency}
      </p>
      <p className="font-medium">
        Total Cost: {flightOffer.totalPrice/2} {flightOffer.currency}
      </p>
    </>
  ) : (
    <p className="font-medium text-gray-500">No travelers available</p>
  )}
</div>


                <div className="mt-4">
                  <h4 className="font-medium text-black">Flight Segments:</h4>
                  <div>
                  
                    {itinerary.flightSegments.map((segment, idx) => (
                      <div key={idx} className="p-4 mb-2 bg-white border border-gray-200 rounded-md shadow-sm">
                        <p><strong>Departure Airport:</strong> {getAirportCityAndName(segment.departureAirportIATA)}</p>
                        <p><strong>Arrival Airport:</strong> {getAirportCityAndName(segment.arrivalAirportIATA)}</p>
                        <p><strong>Departure Time:</strong> {formatDate(segment.departureDayTime)}</p>
                        <p><strong>Arrival Time:</strong> {formatDate(segment.arrivalDayTime)}</p>
                        <p><strong>Airline:</strong> {dictionary.carriers[segment.carriers] || "Unknown"} ({segment.carriers})</p>

                        {flightOffer.flightTravelers && flightOffer.flightTravelers.length > 0 && (
                          <div className="mt-4">

                            {flightOffer.flightTravelers && flightOffer.flightTravelers.length > 0 && (
                              <div className="mt-4">
                                {flightOffer.flightTravelers.slice(0, 1).map((traveler: FlightTraveler, travelerIndex: number) => (
                                  <div key={travelerIndex} className="p-4 mb-2 bg-white border border-gray-200 rounded-md shadow-sm">
                                    {/* Displaying Amenities only for the first traveler */}
                                    {traveler.fareDetails.length > 0 && (
                                      <div className="mt-2">
                                        <p className="font-semibold text-gray-700">Amenities for this Fare Detail:</p>
                                        {traveler.fareDetails[0].amenities.map((amenity, amenityIdx) => (
                                          <div key={amenityIdx} className="text-sm text-gray-500">
                                            <strong>{amenity.description}</strong> -{" "}
                                            {amenity.isChargeable === "true" ? "Chargeable" : "Free"}
                                          </div>
                                        ))}
                                      </div>
                                    )}  
                                      
                                      
                                      <div className="mt-2">
                                        <p className="font-semibold text-gray-700">Class:</p>
                                        {traveler.fareDetails[0].fareClass}
                                      </div>
                                      <div className="mt-2">
                                        <p className="font-semibold text-gray-700">Cabin:</p>
                                        {traveler.fareDetails[0].cabin}
                                      </div>
                                    
                                  </div>
                                  
                                ))}
                              </div>
                            )}

                          </div>
                        )}                        
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </div>

      {/* Optional: Price breakdown, you can adjust positioning as needed */}
      <div className="fixed bottom-4 right-4 bg-white shadow-lg border border-gray-300 rounded-lg p-6 w-64">
        <h3 className="text-xl font-semibold text-black">Price Breakdown</h3>
        <div className="mt-4">
          <p className="text-gray-700">
            <strong>Base Price:</strong> {flightOffer.basePrice} {flightOffer.currency}
          </p>
          <p className="text-gray-700">
            <strong>Fees:</strong> {flightOffer.fees} {flightOffer.currency}
          </p>
          <p className="text-gray-700">
            <strong>Total Price:</strong> {flightOffer.totalPrice} {flightOffer.currency}
          </p>
          {flightOffer.flightTravelers && flightOffer.flightTravelers.length > 0 && (
                          <div className="mt-4">
                            <h5 className="font-medium text-black">Travelers Price Breakdown:</h5>
                            {flightOffer.flightTravelers.map((traveler: FlightTraveler, travelerIndex: number) => (
                              <div key={travelerIndex} className="p-4 mb-2 bg-white border border-gray-200 rounded-md shadow-sm">
                                <p className="text-black" >Traveler: {travelerIndex + 1}</p>
                                <p className="text-black"><strong>Base Price:</strong> {traveler.travelerPriceBase || "Unknown"}</p>
                                <p className="text-black"><strong>Total Price:</strong> {traveler.travelerPriceTotal || "Unknown"}</p>
                              </div>
                            ))}
                          </div>
                        )}
        </div>
      </div>
    </div>
  );
}

export default FlightBreakdown;
