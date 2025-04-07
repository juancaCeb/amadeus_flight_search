import { useContext, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";  
import { FlightResultsContext } from "../main"; 
import { Flight, FlightItinerary, FlightOffer, FlightResponse, FlightDictionaryDTO} from './types';
import data from "../assets/airportData.json";  

const emptyFlightData: Flight = {
  flightOffers: [],
  flightDictionaryDTO: {
    aircraft: {}, 
    carriers: {}   
  }
};


const getAirportCityAndName = (iata: string) => {
  const airport = Object.values(data).find((item) => item.iata && item.iata === iata);
  if (airport) {
    return `${airport.city} (${airport.iata})`;
  }
  return "Unknown Airport"; 
};

export default function ShowFlightResults() {   
  const flightContext = useContext(FlightResultsContext);    
  const flightResponse: FlightResponse = flightContext?.flights ?? emptyFlightData;
  const flights: FlightOffer[] = flightResponse.flightOffers;
  const dictionary: FlightDictionaryDTO = flightResponse.flightDictionaryDTO;

  const navigate = useNavigate();

  const goBack = () => {     
    navigate("/");   
  };

  const goToFlightDetails = (flightOffer: FlightOffer, dictionary: FlightDictionaryDTO) => {
    navigate("/flight-breakdown", { state: { flightOffer, dictionary } });
  };

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

  const airportNames = useMemo(() => {
    const airportNameMap: Record<string, string> = {};
    flights.forEach(flightOffer => {
      flightOffer.flightItineraries.forEach(itinerary => {
        itinerary.flightSegments.forEach(segment => {
          if (segment.departureAirportIATA && !airportNameMap[segment.departureAirportIATA]) {
            airportNameMap[segment.departureAirportIATA] = getAirportCityAndName(segment.departureAirportIATA);
          }
          if (segment.arrivalAirportIATA && !airportNameMap[segment.arrivalAirportIATA]) {
            airportNameMap[segment.arrivalAirportIATA] = getAirportCityAndName(segment.arrivalAirportIATA);
          }
        });
      });
    });
    return airportNameMap;
  }, [flights]); 


  const [sortCriteria, setSortCriteria] = useState<'duration' | 'price'>('duration');


  const sortedFlights = useMemo(() => {
    return flights.sort((a, b) => {
      if (sortCriteria === 'duration') {

        const totalDurationA = a.flightItineraries.reduce((total, itinerary) => {
          return total + (typeof itinerary.totalTime === 'string' ? parseInt(itinerary.totalTime) : itinerary.totalTime);
        }, 0);
  
        const totalDurationB = b.flightItineraries.reduce((total, itinerary) => {
          return total + (typeof itinerary.totalTime === 'string' ? parseInt(itinerary.totalTime) : itinerary.totalTime);
        }, 0);
  
        return totalDurationA - totalDurationB;
      } else if (sortCriteria === 'price') {
        const priceA = parseFloat(a.totalPrice);
        const priceB = parseFloat(b.totalPrice);
        return priceA - priceB;
      }
      return 0;
    });
  }, [flights, sortCriteria]);
  

  return (
    <div className="min-h-screen w-full bg-gray-100">
      <button
        className="mb-6 px-6 py-3 bg-blue-600 text-white rounded-full hover:bg-blue-700 fixed top-6 left-6 z-10"
        onClick={goBack}
      >
        ← Return to Search
      </button>

      <div className="space-y-6 w-full max-h-screen overflow-y-auto pt-20 mx-auto max-w-4xl">
        {/* Sort buttons */}
        <div className="flex justify-center space-x-4 mb-6">
          <button
            onClick={() => setSortCriteria('duration')}
            className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
          >
            Sort by Duration
          </button>
          <button
            onClick={() => setSortCriteria('price')}
            className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
          >
            Sort by Price
          </button>
        </div>

        {sortedFlights.length > 0 ? (
          sortedFlights.map((flightOffer: FlightOffer, index: number) => (
            <div key={index} className="bg-white border shadow-lg rounded-lg p-6 hover:shadow-xl transition-shadow duration-300">
              {/* Flight details here */}
              <div className="flex justify-between items-center">
                <div className="w-2/3">
                  {flightOffer.flightItineraries.map((itinerary, idx) => {
                    const layoverTimes = calculateLayoverTimes(itinerary); 
                    return (
                      <div key={idx} className="space-y-3">
                        <div className="text-xl font-semibold text-black">
                          {formatDate(itinerary.initialDepartureDayTime)} - {formatDate(itinerary.finalDepartureDayTime)}
                        </div>
                        <div className="text-gray-600">
                          
                        </div>
                        <div className="text-gray-800">
                          <p className="font-medium">Total Flight Time: {itinerary.totalTime}</p>
                          <p className="font-medium">Number of Stops: {calculateStops(itinerary)}</p> 
                          {layoverTimes.length > 0 && (
                            <div className="text-gray-600">
                              <p className="font-medium">Layover Times:</p>
                              {layoverTimes.map((layover, idx) => {
                                const layoverAirport = airportNames[layover.waitingAirport] || "Unknown Airport";
                                return (
                                  <p key={idx} className="font-medium">
                                    Stop {idx + 1}: {layover.hours}h {layover.minutes}m at {layoverAirport}
                                  </p>
                                );
                              })}
                            </div>
                          )}
                        </div>

                        {idx < flightOffer.flightItineraries.length - 1 && (
                          <div className="border-t-2 border-gray-300 my-4"></div> 
                        )}
                      </div>
                    );
                  })}
                  <div className="text-xl font-semibold text-blue-600">
                    {flightOffer.flightTravelers && flightOffer.flightTravelers.length > 0 ? (
                      <p className="font-medium">
                        Total Cost Per Traveler: {(parseFloat(flightOffer.totalPrice) / flightOffer.flightTravelers.length).toFixed(2)} {flightOffer.currency}
                      </p>
                    ) : (
                      <p className="font-medium text-gray-500">No travelers available</p>
                    )}

                    <p className="font-medium">Total Cost: {flightOffer.totalPrice} {flightOffer.currency}</p>
                  </div>

                  <div className="mt-4">
                    <button 
                      onClick={() => goToFlightDetails(flightOffer, dictionary)} 
                      className="text-blue-500 hover:text-blue-700 transition-colors"
                    >
                      View Flight Details
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="text-center text-gray-500">Loading Flight Results...</div>
        )}
        <div className="text-center text-gray-500">•••</div>
      </div>
    </div>
  );
}
