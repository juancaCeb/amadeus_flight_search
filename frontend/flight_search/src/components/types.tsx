// types.ts

export interface Amenity {
    description: string;
    isChargeable: string; 
  }
  
  export interface FareDetail {
    segmentId: string;
    cabin: string;
    fareClass: string;
    amenities: Amenity[];
  }
  
  export interface FlightSegment {
    departureDayTime: string;
    arrivalDayTime: string;
    departureAirportIATA: string;
    arrivalAirportIATA: string;
    aircraftCode: string;
    carriers:string;

  }
  
  export interface FlightItinerary {
    totalTime: string;
    initialDepartureDayTime: string;
    flightSegments: FlightSegment[];
    finalDepartureDayTime: string;
  }
  
  export interface FlightTraveler {
    travelerId: string; 
    fareOption: string;
    travelerPriceTotal: string;
    travelerPriceBase: string;
    fareDetails: FareDetail[];
  }

  export interface FlightOffer {

    flightItineraries: FlightItinerary[];
    totalPrice: string;
    currency: string;
    flightTravelers: FlightTraveler[];
    fees : number;
    basePrice : number;

  }
  
  export interface Flight {

    flightOffers: FlightOffer[]
    flightDictionaryDTO: FlightDictionaryDTO

  }

  export interface FlightDictionaryDTO {
    aircraft: Record<string, string>;
    carriers: Record<string, string>;
  }
  

  export interface FlightContextType {
    flights: FlightResponse | null;
    setFlights: (flightOffers: FlightResponse) => void;
  }

  export interface FlightResponse {

    flightOffers: FlightOffer[];
    flightDictionaryDTO: FlightDictionaryDTO;

  }

  



  