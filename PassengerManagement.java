import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PassengerManagement {

    private int passengerID;
    private String passengerName;
    private int age;
    private String address;
    private String gender;

    // LinkedList to store passenger details
    static LinkedList<PassengerManagement> passengers = new LinkedList<>();
    // Stack for undo operations
    private static Stack<PassengerManagement> undoStack = new Stack<>();
    // Queue for processing passengers
    private static Queue<PassengerManagement> processingQueue = new LinkedList<>();

    public PassengerManagement(int passengerID, String passengerName, int age, String address, String gender) {
        this.passengerID = passengerID;
        this.passengerName = passengerName;
        this.age = age;
        this.address = address;
        this.gender = gender;
    }

    public PassengerManagement() {
        // Default constructor
    }

    // Getters and Setters
    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "PassengerManagement{" +
                "passengerID=" + passengerID +
                ", passengerName='" + passengerName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public static void loadData() {
        File file = new File("passengers.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // Check for correct number of fields
                    int passengerID = Integer.parseInt(parts[0]);
                    String passengerName = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String address = parts[3];
                    String gender = parts[4];
                    PassengerManagement passenger = new PassengerManagement(passengerID, passengerName, age, address, gender);
                    if (!isPassengerIDExists(passengerID)) { // Check for duplicate passenger ID
                        passengers.addLast(passenger);
                    } else {
                        System.out.println("Duplicate passenger ID found: " + passengerID + ". Skipping this entry.");
                    }
                } else {
                    System.out.println("Invalid data format in file: " + line + ". Skipping this entry.");
                }
            }
            System.out.println("Passenger data loaded from file.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading passenger data: " + e.getMessage());
        }
    }

    public static void saveData() {
        File file = new File("passengers.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (PassengerManagement passenger : passengers) {
                if (!isPassengerIDExists(passenger.getPassengerID())) { // Check for duplicate passenger ID
                    String line = passenger.getPassengerID() + "," + passenger.getPassengerName() + "," + passenger.getAge() + "," + passenger.getAddress() + "," + passenger.getGender();
                    writer.write(line + "\n");
                } else {
                    System.out.println("Duplicate passenger ID found: " + passenger.getPassengerID() + " - Skipping save for this entry.");
                }
            }
            System.out.println("Passenger data saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving passenger data: " + e.getMessage());
        }
    }

    private static boolean isPassengerIDExists(int passengerID) {
        File file = new File("passengers.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    if (id == passengerID) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while checking for duplicate passenger ID: " + e.getMessage());
        }
        return false;
    }

    public static void updatePassenger(int passengerID, String passengerName, int age, String address, String gender) {
        boolean found = false;
        for (PassengerManagement passenger : passengers) {
            if (passenger.getPassengerID() == passengerID) {
                passenger.setPassengerName(passengerName);
                passenger.setAge(age);
                passenger.setAddress(address);
                passenger.setGender(gender);
                found = true;
                System.out.println("Passenger " + passengerName + "'s information updated.");
                break;
            }
        }

        if (!found) {
            System.out.println("Passenger with ID " + passengerID + " not found. Unable to update.");
        }
    }

    public static void addPassenger(int passengerID, String passengerName, int age, String address, String gender) {
        // Perform input validation
        if (passengerID <= 0 || passengerName == null || passengerName.isEmpty() || age <= 0 || address == null || address.isEmpty() || gender == null || gender.isEmpty()) {
            System.out.println("Invalid input data. Please provide valid information.");
            return;
        }

        PassengerManagement newPassenger = new PassengerManagement(passengerID, passengerName, age, address, gender);
        if (!passengers.contains(newPassenger)) { // Check for duplicates
            passengers.addLast(newPassenger);
            processingQueue.offer(newPassenger); // Add to processing queue
            System.out.println("Passenger " + passengerName + " added successfully.");
        } else {
            System.out.println("Passenger " + passengerName + " already exists.");
        }
    }

    public static void deletePassenger(int passengerID) {
        boolean found = false;
        for (PassengerManagement passenger : passengers) {
            if (passenger.getPassengerID() == passengerID) {
                passengers.remove(passenger);
                undoStack.push(passenger); // Push deleted passenger for undo operation
                found = true;
                System.out.println("Passenger with ID " + passengerID + " deleted successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Passenger with ID " + passengerID + " not found. Unable to delete.");
        }
    }

    public static void undoLastDelete() {
        if (!undoStack.isEmpty()) {
            PassengerManagement passenger = undoStack.pop();
            addPassenger(passenger.getPassengerID(), passenger.getPassengerName(), passenger.getAge(), passenger.getAddress(), passenger.getGender());
            System.out.println("Undo delete operation for passenger: " + passenger.getPassengerName());
        } else {
            System.out.println("No delete operation to undo.");
        }
    }

    public static void displayAllPassengers() {
        if (passengers.isEmpty()) {
            System.out.println("No passengers found.");
        } else {
            System.out.println("All Passengers:");
            for (PassengerManagement passenger : passengers) {
                System.out.println(passenger);
            }
        }
    }

    public static void displayPassengerDetails(int passengerID) {
        boolean found = false;
        for (PassengerManagement passenger : passengers) {
            if (passenger.getPassengerID() == passengerID) {
                System.out.println("Passenger Details:");
                System.out.println(passenger);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Passenger with ID " + passengerID + " not found.");
        }
    }

    public static void clearAllData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("passengers.txt"))) {
            writer.write(""); // Clear the contents of the file
            passengers.clear();
            undoStack.clear();
            processingQueue.clear();
            System.out.println("All passenger data has been cleared.");
        } catch (IOException e) {
            System.out.println("An error occurred while clearing passenger data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        loadData();

        // Adding Passengers
        addPassenger(1, "Sufian", 23, "Lahore", "Male");
        addPassenger(2, "Ayesha", 38, "Multan", "Female");
        addPassenger(3, "Amna", 47, "Karachi", "Female");
        addPassenger(4, "Saad", 61, "Peshawar", "Male");
        // Display after addition
        displayAllPassengers();

        // Deleting Passenger
        deletePassenger(3);
        // Display after Removing
        displayAllPassengers();

        // Undo last delete operation
        undoLastDelete();
        // Display after Undo Operation
        displayAllPassengers();

        // Editing Passenger
        updatePassenger(3, "Amna", 45, "Karachi", "Female");
        // Display after Editing its Details
        displayAllPassengers();

        // Displaying Passenger Details
        System.out.println("Show the Details of Passenger ");
        displayPassengerDetails(4);

        saveData();

        // Clear all passenger data
//        clearAllData();
    }
}