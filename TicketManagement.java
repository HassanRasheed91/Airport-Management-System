import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack; 
import java.io.*;

public class TicketManagement {
    private int ticketNumber;
    private String passengerName;
    private String seatNumber;
    private String flightNumber;
    static Map<Integer, TicketManagement> tickets = new HashMap<>();
    private static Stack<Integer> undoStack = new Stack<>();
    public static void cancelTicket(int ticketNumber) {
    }

    public static List<TicketManagement> getTicketsByFlightNumber(String flightNumber) {
        return null;
    }


    // Constructor for ticket management object
    public TicketManagement(int ticketNumber, String passengerName, String seatNumber, String flightNumber) {
        this.ticketNumber = ticketNumber;
        this.passengerName = passengerName;
        this.seatNumber = seatNumber;
        this.flightNumber = flightNumber;
        tickets.put(ticketNumber, this);
        undoStack.push(ticketNumber);
    }

    public TicketManagement() {}


    // Returns a list of all tickets stored in memory
    public static List<TicketManagement> getTicketsFromMemory() {
        return new ArrayList<>(tickets.values());
    }



    // Getters and setters
    public int getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(int ticketNumber) { this.ticketNumber = ticketNumber; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    // Booking a ticket

    public static TicketManagement bookTicket(int ticketNumber, String passengerName, String seatNumber, String flightNumber) {
        if (tickets.containsKey(ticketNumber)) {
            System.out.println("Ticket number " + ticketNumber + " is already in use.");
            return null;
        }
        return new TicketManagement(ticketNumber, passengerName, seatNumber, flightNumber);
    }

    public static TicketManagement findTicket(int ticketNumber) {
        return tickets.get(ticketNumber);
    }

    public static String displayTicketDetails(int ticketNumber) {
        TicketManagement ticket = findTicket(ticketNumber);
        if (ticket != null) {
            return ticket.toString();
        } else {
            return "Ticket with ticket number " + ticketNumber + " not found.";
        }
    }

    public static String displayAllTickets() {
        StringBuilder result = new StringBuilder("All Tickets:\n");
        if (tickets.isEmpty()) {
            result.append("No tickets found.");
        } else {
            for (int ticketNumber : tickets.keySet()) {
                result.append(tickets.get(ticketNumber)).append("\n");
            }
        }
        return result.toString();
    }

    public static List<TicketManagement> getTicketsForFlight(String flightNumber) {
        List<TicketManagement> result = new ArrayList<>();
        for (int ticketNumber : tickets.keySet()) {
            if (tickets.get(ticketNumber).getFlightNumber().equalsIgnoreCase(flightNumber)) {
                result.add(tickets.get(ticketNumber));
            }
        }
        return result;
    }

    public static List<TicketManagement> searchByFlightNumber(String flightNumber) {
        List<TicketManagement> result = new ArrayList<>();
        for (int ticketNumber : tickets.keySet()) {
            if (tickets.get(ticketNumber).getFlightNumber().equalsIgnoreCase(flightNumber)) {
                result.add(tickets.get(ticketNumber));
            }
        }
        return result;
    }

    public static int getNumberOfTickets() {
        return tickets.size();
    }

    public static boolean undoLastBooking() {
        if (!undoStack.isEmpty()) {
            Integer lastTicketNumber = undoStack.pop();
            tickets.remove(lastTicketNumber);
        }
        return false;
    }

    public static void saveTicketsToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (TicketManagement ticket : tickets.values()) {
                writer.write(ticket.getTicketNumber() + "," + ticket.getPassengerName() + "," + ticket.getSeatNumber() + "," + ticket.getFlightNumber() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loading tickets from file
    public static void loadTicketsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int ticketNumber = Integer.parseInt(parts[0]);
                    String passengerName = parts[1];
                    String seatNumber = parts[2];
                    String flightNumber = parts[3];
                    bookTicket(ticketNumber, passengerName, seatNumber, flightNumber);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Clearing all tickets in memory
    public static void clearAllTicketsInMemory() {
        tickets.clear();
        undoStack.clear();
        System.out.println("All in-memory ticket data has been cleared.");
    }

    // Clearing persisted ticket data
    public static void clearPersistedTicketData(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            writer.write("");
            System.out.println("Persisted ticket data has been cleared.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "TicketManagement{" +
                "ticketNumber=" + ticketNumber +
                ", passengerName='" + passengerName + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                '}';
    }

    // The main method to demonstrate functionality
    public static void main(String[] args) {
        // Load tickets, demonstrate functionalities, and then clear data
        loadTicketsFromFile("tickets.txt");

        TicketManagement ticket1 = bookTicket(34, "Hassan", "20", "2");
        TicketManagement ticket2 = bookTicket(65, "Ijaz", "21", "2");
        TicketManagement ticket3 = bookTicket(78, "Mubeen", "22", "3");
        TicketManagement ticket4 = bookTicket(43, "Rizwan", "23", "3");

        System.out.println(displayAllTickets());

          findTicket(65); // This will return null if ticket #3 doesn't exist
        if (ticket3 != null) {
            ticket3.setPassengerName("Suifan");
            ticket3.setSeatNumber("67");
            ticket3.setFlightNumber("FL100");
        } else {
            System.out.println("Ticket 3 not found. Cannot modify.");
        }

        System.out.println(displayAllTickets());

        tickets.remove(ticket4);

        System.out.println(displayAllTickets());

        System.out.println("Show the Details of Ticket ");
        System.out.println(displayTicketDetails(8));



        System.out.println("Searching for Tickets by Flight Number:");
        System.out.println(searchByFlightNumber("3"));

        System.out.println("Number of Tickets: " + getNumberOfTickets());

        System.out.println("Tickets for Flight 2:");
        System.out.println(getTicketsForFlight("2"));

        TicketManagement ticket5 = bookTicket(5, "Ali Raza", "45", "2");

        System.out.println(displayAllTickets());

        System.out.println("Undoing Last Booking");
        undoLastBooking();
        System.out.println(displayAllTickets());


        saveTicketsToFile("tickets.txt"); // Save tickets at the end


////        // Clearing all tickets data at the end
//        clearAllTicketsInMemory();
//        clearPersistedTicketData("tickets.txt");
    }
}
