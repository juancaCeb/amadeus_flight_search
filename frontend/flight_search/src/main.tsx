import { createContext, StrictMode, useState } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import FlightResultsPage from './components/FlightResults.tsx'
import { FlightContextType, FlightResponse } from './components/types';  
import FlightBreakDownPage from './components/FlightBreakdownPage.tsx'

export const FlightResultsContext = createContext<FlightContextType | undefined>(undefined);

const AppWrapper = () => {

  const [flights, setFlights] = useState<FlightResponse | null>(null);

  const router = createBrowserRouter([
    { path: "/", element: <App /> },
  
    { path: "/flight-results", element: 
      <div className="h-screen w-screen w-full flex items-center justify-center bg-gray-100">
        <FlightResultsPage />
      </div>
    },
  
    // Update this route to accept flightOfferId as a parameter
    { path: "/flight-breakdown", element: 
      <div className="h-screen w-screen w-full flex items-center justify-center bg-gray-100">
        <FlightBreakDownPage />
      </div>
    },
  ]);
  

  return (
    <FlightResultsContext.Provider value={{ flights, setFlights }}>
      <RouterProvider router={router}/>
    </FlightResultsContext.Provider>
  );
};

createRoot(document.getElementById('root')!).render(

  <StrictMode>
    <AppWrapper />
  </StrictMode>,
  
);
