import java.io.*;
import java.util.*;

public class FlightManagement {
    private static final String FILE_NAME = "flights.txt";
    private static Set<Flight> flights = new LinkedHashSet<>();

    public static Iterable<? extends Flight> getFlights() {
        return flights != null ? flights : Collections.emptyList();
    }

    public static class Flight {
        private String flightNumber;
        private String source;
        private String destination;
        private String departureTime;
        private FlightStatus status;
        private int delayInMinutes;

        public Flight(String flightNumber, String source, String destination, String departureTime,
                      FlightStatus status, int delayInMinutes) {
            this.flightNumber = flightNumber;
            this.source = source;
            this.destination = destination;
            this.departureTime = departureTime;
            this.status = status;
            this.delayInMinutes = delayInMinutes;
        }

        // Getters and setter


        public String getFlightNumber() {
            return flightNumber;
        }

        public void setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public FlightStatus getStatus() {
            return status;
        }

        public void setStatus(FlightStatus status) {
            this.status = status;
        }

        public int getDelayInMinutes() {
            return delayInMinutes;
        }

        public void setDelayInMinutes(int delayInMinutes) {
            this.delayInMinutes = delayInMinutes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Flight flight = (Flight) o;
            return Objects.equals(flightNumber, flight.flightNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(flightNumber);
        }

        public static int getNumberOfFlights() {
            return flights.size();
        }

        @Override
        public String toString() {
            return "Flight{" +
                    "flightNumber='" + flightNumber + '\'' +
                    ", source='" + source + '\'' +
                    ", destination='" + destination + '\'' +
                    ", departureTime='" + departureTime + '\'' +
                    ", status=" + status +
                    ", delayInMinutes=" + delayInMinutes +
                    '}';
        }
    }

    enum FlightStatus {
        ON_TIME, DELAYED, CANCELLED
    }

    public static void addFlight(String flightNumber, String source, String destination, String departureTime,
                                 FlightStatus status) {
        int delayInMinutes = 0;
        Flight newFlight = new Flight(flightNumber, source, destination, departureTime, status, delayInMinutes);
        if (flights.add(newFlight)) {
            System.out.println("Flight " + flightNumber + " added successfully.");
            saveFlightsToFile();
        } else {
            System.out.println("Flight " + flightNumber + " already exists.");
        }
    }

    public static void updateFlight(String flightNumber, String source, String destination, String departureTime,
                                    FlightStatus status) {
        boolean found = false;
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                flight.setSource(source);
                flight.setDestination(destination);
                flight.setDepartureTime(departureTime);
                flight.setStatus(status);
                found = true;
                System.out.println("Flight " + flightNumber + " updated successfully.");
                saveFlightsToFile();
                break;
            }
        }
        if (!found) {
            System.out.println("Flight with flight number " + flightNumber + " not found.");
        }
    }

    public static void deleteFlight(String flightNumber) {
        boolean found = flights.removeIf(flight -> flight.getFlightNumber().equals(flightNumber));
        if (found) {
            System.out.println("Flight " + flightNumber + " deleted successfully.");
            saveFlightsToFile();
        } else {
            System.out.println("Flight with flight number " + flightNumber + " not found.");
        }
    }

    public static void displayFlightDetails(String flightNumber) {
        boolean found = false;
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                System.out.println(flight);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Flight with flight number " + flightNumber + " not found.");
        }
    }

    public static void displayAllFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            System.out.println("All Flights:");
            flights.forEach(System.out::println);
        }
    }

    public static void delayFlight(String flightNumber, int delayInMinutes) {
        boolean found = false;
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                flight.setDelayInMinutes(flight.getDelayInMinutes() + delayInMinutes);
                flight.setStatus(FlightStatus.DELAYED);
                found = true;
                System.out.println("Flight " + flightNumber + " delayed by " + delayInMinutes + " minutes.");
                saveFlightsToFile();
                break;
            }
        }
        if (!found) {
            System.out.println("Flight with flight number " + flightNumber + " not found.");
        }
    }

    private static void saveFlightsToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Flight flight : flights) {
                writer.write(flight.getFlightNumber() + "," +
                        flight.getSource() + "," +
                        flight.getDestination() + "," +
                        flight.getDepartureTime() + "," +
                        flight.getStatus() + "," +
                        flight.getDelayInMinutes() + "\n");
            }
            System.out.println("Flight data saved to file: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void loadFlightsFromFile() {
        flights.clear(); // Clear the existing flights
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String flightNumber = parts[0];
                    String source = parts[1];
                    String destination = parts[2];
                    String departureTime = parts[3];
                    FlightStatus status = FlightStatus.valueOf(parts[4]);
                    int delayInMinutes = Integer.parseInt(parts[5]);
                    Flight flight = new Flight(flightNumber, source, destination, departureTime, status, delayInMinutes);
                    flights.add(flight);
                } else {
                    System.out.println("Invalid line at line " + lineNumber + " in file: " + line);
                }
                lineNumber++;
            }
            System.out.println("Flight data loaded from file: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        loadFlightsFromFile(); // Load flights from file

        // Add flights

        addFlight("1", "New York", "Dubai", "09:00 am", FlightStatus.ON_TIME);
        addFlight("2", "Lahore", "Saudia Arabia", "10:00 pm", FlightStatus.DELAYED);
        addFlight("3", "Karachi", "Multan", "11:00 pm", FlightStatus.CANCELLED);
        addFlight("4", "Islamabad", "Peshawar", "11:00 pm", FlightStatus.ON_TIME);

        // Display all flights after adding
        displayAllFlights();

        // Update a flight
        updateFlight("1", "New York", "London", "09:30 am", FlightStatus.ON_TIME);

        // Display all flights after update
        System.out.println("\nAfter updating flight 1:");
        displayAllFlights();

        // Delete a flight
        deleteFlight("4");

        // Display all flights after deletion
        System.out.println("\nAfter deleting flight 4:");
        displayAllFlights();

        // Display specific flight details
        System.out.println("\nDisplaying details of flight 2:");
        displayFlightDetails("2");

        // Delay a flight
        delayFlight("2", 60);
        System.out.println("\nAfter delaying flight 2:");
        displayFlightDetails("2");

        // Display the number of flights
        System.out.println("\nTotal number of flights: " + Flight.getNumberOfFlights());
    }
}
