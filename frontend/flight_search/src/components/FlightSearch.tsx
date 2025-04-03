import { useState, useContext } from "react";
import { FlightResultsContext } from "../main"; // Adjust path if necessary
import SearchBar from "./SearchBar";
import { useNavigate } from "react-router-dom"; 

import { FlightResponse } from './types';  

export default function FlightSearch() {
  const flightContext = useContext(FlightResultsContext);
  const setFlights = flightContext?.setFlights; 
  
  const [departureAirport, setDepartureAirport] = useState("");
  const [arrivalAirport, setArrivalAirport] = useState("");
  const [departureDate, setDepartureDate] = useState("");
  const [returnDate, setReturnDate] = useState("");
  const [numberOfAdults, setNumberOfAdults] = useState("1");
  const [currency, setCurrency] = useState("USD");
  const [nonstop, setNonStop] = useState(false); 

  const BASE_URL = "http://localhost:8080/flights";
  const navigate = useNavigate(); 

  // Helper function to check if all fields are filled
  const isFormValid = () => {
    return (
      departureAirport !== "" &&
      arrivalAirport !== "" &&
      departureDate !== "" &&
      returnDate !== "" &&
      numberOfAdults !== "" &&
      currency !== "" 
    );
  };

  // Function to check if the dates are valid
  const areDatesValid = () => {
    const today = new Date().toISOString().split("T")[0]; // Get current date in YYYY-MM-DD format
    if (departureDate < today) {
      alert("Departure date cannot be in the past.");
      return false;
    }
    if (returnDate < departureDate) {
      alert("Return date cannot be earlier than departure date.");
      return false;
    }
    return true;
  };

  const performFetch = (e: React.FormEvent) => {
    e.preventDefault(); 

    if (!isFormValid()) {
      alert("Please fill in all required fields before searching.");
      return; 
    }

    if (!areDatesValid()) {
      return; // Stop further processing if dates are invalid
    }

    const url = `${BASE_URL}/${departureAirport}/${arrivalAirport}/${departureDate}/${returnDate}/${numberOfAdults}/${currency}/${nonstop}`;
    console.log(url);

    fetch(url)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch flights");
        }
        return response.json();
      })
      .then((data: FlightResponse) => {
        console.log(data);
        setFlights?.(data); 
      })
      .catch((error) => {
        console.error("Error fetching flights:", error);
      });

    navigate("flight-results"); 
  };

  return (
    <div className="flex justify-center w-full">
      <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-lg mx-auto">
        <form className="space-y-4" onSubmit={performFetch}>

          <div>
            <label className="block text-gray-700">Departure Airport</label>
            <SearchBar setAirport={setDepartureAirport} />
          </div>

          <div>
            <label className="block text-gray-700">Arrival Airport</label>
            <SearchBar setAirport={setArrivalAirport} />
          </div>

          <div>
            <label className="block text-gray-700">Departure Date</label>
            <input
              type="date"
              name="departureDate"
              onChange={(e) => setDepartureDate(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded-lg text-black focus:ring-2 focus:ring-blue-500 focus:outline-none"
            />
          </div>

          <div>
            <label className="block text-gray-700">Return Date</label>
            <input
              type="date"
              name="returnDate"
              onChange={(e) => setReturnDate(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded-lg text-black focus:ring-2 focus:ring-blue-500 focus:outline-none"
            />
          </div>

          <div>
            <label className="block text-gray-700">Number Of Adults</label>
            <select
              name="numberOfAdults"
              onChange={(e) => setNumberOfAdults(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded-lg text-black focus:ring-2 focus:ring-blue-500 focus:outline-none"
            >
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
            </select>
          </div>

          <div>
            <label className="block text-gray-700">Currency</label>
            <select
              name="currency"
              onChange={(e) => setCurrency(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded-lg text-black focus:ring-2 focus:ring-blue-500 focus:outline-none"
            >
              <option value="USD">USD</option>
              <option value="MXN">MXN</option>
              <option value="EUR">EUR</option>
            </select>
          </div>

          <div className="flex items-center">
            <input
              type="checkbox"
              name="nonstop"
              checked={nonstop} 
              onChange={(e) => setNonStop(e.target.checked)} 
              className="mr-2"
            />
            <label className="text-gray-700">Non-stop</label>
          </div>

          <button
            type="submit"
            className="w-full bg-blue-600 text-white p-2 rounded-lg hover:bg-blue-700 focus:ring-2 focus:ring-blue-500 focus:outline-none"
            disabled={!isFormValid()}  
          >
            Search
          </button>
        </form>
      </div>
    </div>
  );
}
