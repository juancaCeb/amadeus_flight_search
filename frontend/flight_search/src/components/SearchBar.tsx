import  { useState } from "react";
import data from "../assets/airportData.json"; 

interface SearchBarProps {
  setAirport: (airport: string) => void; 
}

export default function SearchBar({ setAirport }: SearchBarProps) {
  const [searchInput, setSearchInput] = useState("");
  const [suggestions, setSuggestions] = useState<any[]>([]);
  const [hoveredIndex, setHoveredIndex] = useState<number | null>(null); // State to track hovered index

  const searchValue = (value: string) => {
    setSearchInput(value);

    if (value.trim() === "") {
      setSuggestions([]);
      return;
    }

    const filteredSuggestions = Object.values(data).filter((item) => {
      return (
        (item.iata && item.iata.toLowerCase().startsWith(value.toLowerCase())) ||
        (item.city && item.city.toLowerCase().startsWith(value.toLowerCase()))
      );
    });

    setSuggestions(filteredSuggestions.slice(0, 10));
  };

  const handleSelectSuggestion = (suggestion: any) => {
    setSearchInput(`${suggestion.city} (${suggestion.iata || suggestion.name})`);
    setSuggestions([]);
    setAirport(suggestion.iata);
  };

  const handleHover = (index: number) => {
    setHoveredIndex(index);
  };

  const handleMouseLeave = () => {
    setHoveredIndex(null);
  };

  return (
    <div className="search-bar-container" style={{ position: "relative" }}>
      <input
        type="text"
        value={searchInput}
        onChange={(e) => searchValue(e.target.value)}
        className="w-full p-2 border border-gray-300 rounded-lg text-black focus:ring-2 focus:ring-blue-500 focus:outline-none"
        placeholder="Search for an airport or city..."
      />

      {suggestions.length > 0 && (
        <ul className="suggestions-list">
          {suggestions.map((item, index) => (
            <li
              key={index}
              className={`suggestion-item text-black cursor-pointer ${hoveredIndex === index ? "bg-blue-500 text-white" : ""}`}
              onClick={() => handleSelectSuggestion(item)}
              onMouseEnter={() => handleHover(index)} // Handle hover event
              onMouseLeave={handleMouseLeave} // Handle mouse leave
            >
              {item.city} ({item.iata || item.name})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
