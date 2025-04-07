# Amadeus Flight Search

This is a flight searching application built with **React** and **TypeScript** for the frontend, running on **Vite**. The backend is built using the **Java Spring Framework**, which interacts with the **Amadeus REST API** for flight data. This guide includes steps to run the entire application using Docker.

## Project Structure

- **Frontend**: Built using React with TypeScript, running on port `5173`.
- **Backend**: Built using Java Spring Framework, running on port `8080`.

## Getting Started

### Prerequisites

- Docker (v19.03 or later)
- Docker Compose (v1.27 or later)
- API key and API secret from **Amadeus REST API** (for backend)

### Frontend Setup

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd <repository-name>
    ```

2. Make sure Docker and Docker Compose are installed on your machine.

3. Locate the folder containing the **docker-compose.yml** file.

4. Build and run the application using Docker Compose:
    ```bash
    docker-compose up --build
    ```

   This will start both the frontend and backend services in containers.

5. The frontend will be available at [http://localhost:5173](http://localhost:5173), and the backend will be available at [http://localhost:8080](http://localhost:8080).

### Backend Setup

1. Navigate to the **backend** project directory (`./backend/flight_search_backend`).

2. Locate the folder named **properties** in the backend project.

3. Inside the **properties** folder, you will find a file where the **API key** and **API secret** are stored. Replace the placeholder values with your own **Amadeus API key** and **API secret**.

    ```properties
    amadeus.api.key=<your-amadeus-api-key>
    amadeus.api.secret=<your-amadeus-api-secret>
    ```

4. Docker Compose will automatically build and start the backend when running `docker-compose up --build`.

### Frontend Setup

1. Navigate to the **frontend** project directory (`./frontend/flight_search`).

2. The Docker Compose file will build the frontend using the provided Dockerfile and start the React development server. No further setup is needed for the frontend.

### Using the Application

Once both the frontend and backend containers are running:

- You can navigate to [http://localhost:5173](http://localhost:5173) to interact with the frontend.
- The frontend will make requests to the backend, which will then interact with the Amadeus API to fetch flight data.

### API Integration

The backend uses the Amadeus REST API to fetch flight data. Make sure to replace the API key and secret in the `properties` folder as described in the setup steps.

