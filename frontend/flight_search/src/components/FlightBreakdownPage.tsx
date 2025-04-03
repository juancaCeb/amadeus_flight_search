import { useNavigate } from "react-router-dom";
import FlightBreakdown from "./FlightBreakdown";



export default function FlightBreakDownPage() {

  const navigate = useNavigate(); 

  const goBack = () => {     
    navigate("/flight-results");   
  };
    
    return (

      <div className="w-full bg-gray-50">
        <button
        className="mb-4 px-4 py-2 bg-gray-300 rounded hover:bg-gray-400 fixed top-4 left-4 z-10"
        onClick={goBack}
      >
        ← Return To Results
      </button>
        <div className="flex flex-col w-full">
          <h1 className="text-4xl font-bold text-gray-800 mb-8 pt-16 text-center">
            Flight BreakDown
          </h1>
          <div className="flex-grow w-full"> 
              <FlightBreakdown/>
          </div>
        </div>
      </div>
    );
  }
  