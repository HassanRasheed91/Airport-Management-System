import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AircraftManagement {

    private String registrationNumber;
    private String aircraftType;
    private int seatingCapacity;
    private String CompanyName;

    static List<AircraftManagement> aircrafts = new ArrayList<>();
    private static AircraftManagement highestCapacityAircraft = null;

    public AircraftManagement(String registrationNumber, String aircraftType, int seatingCapacity, String CompanyName) {
        this.registrationNumber = registrationNumber;
        this.aircraftType = aircraftType;
        this.seatingCapacity = seatingCapacity;
        this.CompanyName = CompanyName;
    }

    public AircraftManagement() {

    }

    public static void loadAircraftsFromFile() {
        try {
            File file = new File("aircraft.txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 4) {
                        String registrationNumber = data[0];
                        if (isRegistrationNumberUnique(registrationNumber)) {
                            String aircraftType = data[1];
                            int seatingCapacity = Integer.parseInt(data[2]);
                            String CompanyName = data[3];
                            AircraftManagement aircraft = new AircraftManagement(registrationNumber, aircraftType, seatingCapacity, CompanyName);
                            aircrafts.add(aircraft);
                            getAircraftWithHighestCapacity(aircraft);
                        } else {
                            System.out.println("Duplicate registration number found in file: " + registrationNumber);
                        }
                    } else {
                        System.out.println("Invalid data format in file: " + line);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isRegistrationNumberUnique(String registrationNumber) {
        for (AircraftManagement aircraft : aircrafts) {
            if (aircraft.getRegistrationNumber().equals(registrationNumber)) {
                return false;
            }
        }
        return true;
    }

    public static void saveAircraftsToFile() {
        try {
            File file = new File("aircraft.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (AircraftManagement aircraft : aircrafts) {
                String line = aircraft.getRegistrationNumber() + "," + aircraft.getAircraftType() + "," + aircraft.getSeatingCapacity() + "," + aircraft.getCompanyName();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        if (isRegistrationNumberUnique(registrationNumber)) {
            this.registrationNumber = registrationNumber;
        } else {
            System.out.println("Registration number " + registrationNumber + " already exists. Cannot update.");
        }
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        if (seatingCapacity > 0) {
            this.seatingCapacity = seatingCapacity;
            getAircraftWithHighestCapacity(this);
        } else {
            System.out.println("Seating capacity must be a positive value.");
        }
    }

    private static void getAircraftWithHighestCapacity(AircraftManagement aircraftManagement) {
        if (highestCapacityAircraft == null || aircraftManagement.getSeatingCapacity() > highestCapacityAircraft.getSeatingCapacity()) {
            highestCapacityAircraft = aircraftManagement;
        }
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    @Override
    public String toString() {
        return "AircraftManagement{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", aircraftType='" + aircraftType + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                ", CompanyName='" + CompanyName + '\'' +
                '}';
    }

    public static boolean addAircraft(String registrationNumber, String aircraftType, int seatingCapacity, String assignedCrewMember) {
        if (seatingCapacity > 0 && !registrationNumber.isEmpty() && !aircraftType.isEmpty() && !assignedCrewMember.isEmpty()) {
            if (isRegistrationNumberUnique(registrationNumber)) {
                AircraftManagement newAircraft = new AircraftManagement(registrationNumber, aircraftType, seatingCapacity, assignedCrewMember);
                aircrafts.add(newAircraft);
                getAircraftWithHighestCapacity(newAircraft);
                saveAircraftsToFile();
                return true;
            } else {
                System.out.println("Registration number " + registrationNumber + " already exists. Cannot add aircraft.");
                return false;
            }
        } else {
            System.out.println("Invalid input data. Please check the values provided.");
            return false;
        }
    }

    public static boolean updateAircraft(String registrationNumber, String aircraftType, int seatingCapacity,String CompanyName) {
        if (seatingCapacity > 0 && !registrationNumber.isEmpty() && !aircraftType.isEmpty()) {
            for (int i = 0; i < aircrafts.size(); i++) {
                AircraftManagement aircraft = aircrafts.get(i);
                if (aircraft.getRegistrationNumber().equals(registrationNumber)) {
                    aircraft.setAircraftType(aircraftType);
                    aircraft.setSeatingCapacity(seatingCapacity);
                    aircraft.setCompanyName(CompanyName);
                    getAircraftWithHighestCapacity(aircraft);
                    saveAircraftsToFile();
                    return true;
                }
            }
            System.out.println("Aircraft with registration number " + registrationNumber + " not found.");
            return false;
        } else {
            System.out.println("Invalid input data. Please check the values provided.");
            return false;
        }
    }

    public static boolean deleteAircraft(String registrationNumber) {
        if (!registrationNumber.isEmpty()) {
            for (int i = 0; i < aircrafts.size(); i++) {
                if (aircrafts.get(i).getRegistrationNumber().equals(registrationNumber)) {
                    aircrafts.remove(i);
                    saveAircraftsToFile();
                    return true;
                }
            }
            System.out.println("Aircraft with registration number " + registrationNumber + " not found.");
            return false;
        } else {
            System.out.println("Invalid registration number provided.");
            return false;
        }
    }

    public static String displayAircraftDetails(String registrationNumber) {
        if (!registrationNumber.isEmpty()) {
            for (AircraftManagement aircraft : aircrafts) {
                if (aircraft.getRegistrationNumber().equals(registrationNumber)) {
                    System.out.println(aircraft);
                    return registrationNumber;
                }
            }
            System.out.println("Aircraft with registration number " + registrationNumber + " not found.");
            return registrationNumber;
        } else {
            System.out.println("Invalid registration number provided.");
            return null;
        }
    }

    public static void displayAllAircrafts() {
        if (aircrafts.isEmpty()) {
            System.out.println("No aircraft data available.");
        } else {
            System.out.println("All Aircrafts:");
            for (AircraftManagement aircraft : aircrafts) {
                System.out.println(aircraft);
            }
        }
    }

    public static String searchByRegistrationNumber(String registrationNumber) {
        if (!registrationNumber.isEmpty()) {
            for (AircraftManagement aircraft : aircrafts) {
                if (aircraft.getRegistrationNumber().equals(registrationNumber)) {
                    return aircraft.toString();
                }
            }
            return "Aircraft with registration number " + registrationNumber + " not found.";
        } else {
            System.out.println("Invalid registration number provided.");
            return null;
        }
    }

    public static String getAllAircrafts() {
        StringBuilder sb = new StringBuilder();
        for (AircraftManagement aircraft : aircrafts) {
            sb.append(aircraft.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void clearAircraftsFromFile() {
        try {
            File file = new File("aircraft.txt");
            if (file.exists()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("");
                writer.close();
                aircrafts.clear();
                highestCapacityAircraft = null;
                System.out.println("All aircraft data has been cleared from the file.");
            } else {
                System.out.println("Aircraft file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Load aircrafts from file
        loadAircraftsFromFile();

        // Adding Aircraft
        AircraftManagement.addAircraft("10", "Boeing 868", 75, "PIA");
        AircraftManagement.addAircraft("20", "Airbus 222", 150, "Air Blue");
        AircraftManagement.addAircraft("30", "Air craft 444", 25, "Aimrates AirLine");
        AircraftManagement.addAircraft("40", "Boeing 868", 75, "Qatar AirLine");

        // Display all Aircraft after Adding
        AircraftManagement.displayAllAircrafts();

        // Editing Aircraft
        AircraftManagement.updateAircraft("10", "Boeing 777", 80,"PIA");
        // Display Editing its Details
        AircraftManagement.displayAllAircrafts();

        // Deleting Aircraft
        AircraftManagement.deleteAircraft("40");
        // Display after Removing
        AircraftManagement.displayAllAircrafts();

        // Displaying Aircraft Details
        System.out.println("Show the Details of Aircraft ");
        AircraftManagement.displayAircraftDetails("20");

        // Displaying All Aircraft
        System.out.println("Displaying all Aircrafts ");
        AircraftManagement.displayAllAircrafts();

        // Search by registration num
        System.out.println("Searching for Aircraft by Registration Number:");
        System.out.println(AircraftManagement.searchByRegistrationNumber("30"));

        // Clear all aircraft data from file
//        AircraftManagement.clearAircraftsFromFile();
    }
}