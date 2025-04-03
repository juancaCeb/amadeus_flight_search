import FlightSearch from "./FlightSearch";


export default function FlightSearchPage() {

  return (
    <div className="flex items-center justify-center">
      <div className="flex flex-col w-full max-w-lg px-4 mx-auto">
        <h1 className="text-4xl font-bold text-gray-800 mb-8 pt-16 text-center">Search Your Flight</h1>
       
          <FlightSearch />
      </div>
    </div>
  );
}
