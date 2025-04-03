import FlightSearchPage from "./components/FlightSearchPage";

function App() {
  return (
    <div className="h-screen w-screen w-full flex items-center justify-center bg-gray-100">
      <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-lg">
        <FlightSearchPage />
      </div>
    </div>
  );
}

export default App;

