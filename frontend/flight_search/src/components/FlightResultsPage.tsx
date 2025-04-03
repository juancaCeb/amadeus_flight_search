import FlightResults from "./FlightResults";


export default function FlightResultsPage() {
    return (
      <div className="w-full bg-gray-50">
        <div className="flex flex-col w-full">
          <h1 className="text-4xl font-bold text-gray-800 mb-8 pt-16 text-center">
            Search Results
          </h1>
          
          <div className="flex-grow w-full"> 
              <FlightResults />
          </div>

        </div>
      </div>
    );
  }
  