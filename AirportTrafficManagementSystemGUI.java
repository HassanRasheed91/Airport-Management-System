import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Airplane implements Serializable {
    private static final long serialVersionUID = 1L; // Required for serialization
    private String registration;
    private String runwayUsed;
    private String actualTimeOfLanding;
    private String actualTimeOfDeparture;
    private int numberOfCircuits;

    public Airplane(String registration, String runwayUsed, String actualTimeOfLanding, String actualTimeOfDeparture, int numberOfCircuits) {
        this.registration = registration;
        this.runwayUsed = runwayUsed;
        this.actualTimeOfLanding = actualTimeOfLanding;
        this.actualTimeOfDeparture = actualTimeOfDeparture;
        this.numberOfCircuits = numberOfCircuits;
    }

    public String getRegistration() {
        return registration;
    }

    public String getRunwayUsed() {
        return runwayUsed;
    }

    public String getActualTimeOfLanding() {
        return actualTimeOfLanding;
    }

    public String getActualTimeOfDeparture() {
        return actualTimeOfDeparture;
    }

    public int getNumberOfCircuits() {
        return numberOfCircuits;
    }

    public void setRunwayUsed(String runwayUsed) {
        this.runwayUsed = runwayUsed;
    }

    public void setActualTimeOfLanding(String actualTimeOfLanding) {
        this.actualTimeOfLanding = actualTimeOfLanding;
    }

    public void setActualTimeOfDeparture(String actualTimeOfDeparture) {
        this.actualTimeOfDeparture = actualTimeOfDeparture;
    }

    public void setNumberOfCircuits(int numberOfCircuits) {
        this.numberOfCircuits = numberOfCircuits;
    }
    @Override
    public String toString() {
        return  "Registration Number "+ registration + ", " + "runwayUsed " + runwayUsed + ", " +  " actualTimeOfLanding "+ actualTimeOfLanding + ", " + "actualTimeOfDeparture "+actualTimeOfDeparture + ", " + "numberOfCircuits "+ numberOfCircuits;
    }
    
}

public class AirportTrafficManagementSystemGUI extends JFrame {
    private JTextField registrationField;
    private JTextField runwayField;
    private JTextField landingField;
    private JTextField departureField;
    private JTextField circuitsField;
    private final List<Airplane> flights;
    private final String fileName = "flights.txt";

    public AirportTrafficManagementSystemGUI() {
        flights = new ArrayList<>();
        loadFlightsFromFile();

        setTitle("Airport Traffic Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel registrationLabel = new JLabel("Airplane Registration:");
        registrationField = new JTextField(15);
        JLabel runwayLabel = new JLabel("Runway:");
        runwayField = new JTextField(15);
        JLabel landingLabel = new JLabel("Actual Time of Landing:");
        landingField = new JTextField(15);
        JLabel departureLabel = new JLabel("Actual Time of Departure:");
        departureField = new JTextField(15);
        JLabel circuitsLabel = new JLabel("Number of Circuits:");
        circuitsField = new JTextField(15);

        JButton registerButton = new JButton("Register Flight");
        JButton updateButton = new JButton("Update Flight");
        JButton deleteButton = new JButton("Delete Flight");
        JButton outputButton = new JButton("Show Flight Schedule");

        JTextArea outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String registration = registrationField.getText();
                String runway = runwayField.getText();
                String landingTime = landingField.getText();
                String departureTime = departureField.getText();
                int circuits = Integer.parseInt(circuitsField.getText());

                // Check if registration number already exists
                for (Airplane flight : flights) {
                    if (flight.getRegistration().equals(registration)) {
                        JOptionPane.showMessageDialog(null, "Airplane with registration " + registration + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Check if the runway is already occupied at the given time
                for (Airplane flight : flights) {
                    if ((landingTime.equals(flight.getActualTimeOfLanding()) || landingTime.equals(flight.getActualTimeOfDeparture()))
                            && flight.getRunwayUsed().equals(runway)) {
                        JOptionPane.showMessageDialog(null, "Runway " + runway + " is already occupied at the given time.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if ((departureTime.equals(flight.getActualTimeOfLanding()) || departureTime.equals(flight.getActualTimeOfDeparture()))
                            && flight.getRunwayUsed().equals(runway)) {
                        JOptionPane.showMessageDialog(null, "Runway " + runway + " is already occupied at the given time.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Check if landing and departure times are the same
                if (landingTime.equals(departureTime)) {
                    JOptionPane.showMessageDialog(null, "Landing and departure times cannot be the same.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Register the flight if all conditions are met
                Airplane flight = new Airplane(registration, runway, landingTime, departureTime, circuits);
                flights.add(flight);
                outputArea.append("Flight Registered: " + registration + "\n");
                clearFields();
                saveFlightsToFile();
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flights.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No flights registered yet.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String registration = JOptionPane.showInputDialog(null, "Enter Airplane registration:", "Update Flight", JOptionPane.PLAIN_MESSAGE);
                if (registration == null) {
                    return; // User canceled
                }

                Airplane flightToUpdate = null;
                for (Airplane flight : flights) {
                    if (flight.getRegistration().equals(registration)) {
                        flightToUpdate = flight;
                        break;
                    }
                }

                if (flightToUpdate == null) {
                    JOptionPane.showMessageDialog(null, "Flight with registration '" + registration + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Display dialog to update flight information
                JPanel panel = new JPanel(new GridLayout(5, 2));
                panel.add(new JLabel("Runway:"));
                JTextField runwayField = new JTextField(flightToUpdate.getRunwayUsed());
                panel.add(runwayField);
                panel.add(new JLabel("Actual Time of Landing:"));
                JTextField landingField = new JTextField(flightToUpdate.getActualTimeOfLanding());
                panel.add(landingField);
                panel.add(new JLabel("Actual Time of Departure:"));
                JTextField departureField = new JTextField(flightToUpdate.getActualTimeOfDeparture());
                panel.add(departureField);
                panel.add(new JLabel("Number of Circuits:"));
                JTextField circuitsField = new JTextField(String.valueOf(flightToUpdate.getNumberOfCircuits()));
                panel.add(circuitsField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Update Flight Information",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    flightToUpdate.setRunwayUsed(runwayField.getText());
                    flightToUpdate.setActualTimeOfLanding(landingField.getText());
                    flightToUpdate.setActualTimeOfDeparture(departureField.getText());
                    flightToUpdate.setNumberOfCircuits(Integer.parseInt(circuitsField.getText()));
                    JOptionPane.showMessageDialog(null, "Flight information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flights.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No flights registered yet.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String registration = JOptionPane.showInputDialog(null, "Enter Airplane registration:", "Delete Flight", JOptionPane.PLAIN_MESSAGE);
                if (registration == null) {
                    return; // User canceled
                }

                boolean found = false;
                for (int i = 0; i < flights.size(); i++) {
                    if (flights.get(i).getRegistration().equals(registration)) {
                        flights.remove(i);
                        outputArea.append("Flight Deleted: " + registration + "\n");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(null, "Flight with registration '" + registration + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFlightSchedule();
            }
        });

        add(registrationLabel);
        add(registrationField);
        add(runwayLabel);
        add(runwayField);
        add(landingLabel);
        add(landingField);
        add(departureLabel);
        add(departureField);
        add(circuitsLabel);
        add(circuitsField);
        add(registerButton);
        add(updateButton);
        add(deleteButton);
        add(outputButton);
        add(new JScrollPane(outputArea));
    }

    private void loadFlightsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Airplane flight = new Airplane(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
                    flights.add(flight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFlightsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write header line with field names
            // writer.write("registration,runwayUsed,actualTimeOfLanding,actualTimeOfDeparture,numberOfCircuits");
            writer.newLine();
            for (Airplane flight : flights) {
                writer.write(flight.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        registrationField.setText("");
        runwayField.setText("");
        landingField.setText("");
        departureField.setText("");
        circuitsField.setText("");
    }

    private void showFlightSchedule() {
        StringBuilder schedule = new StringBuilder();
        for (Airplane flight : flights) {
            schedule.append("Flight Registration: ").append(flight.getRegistration()).append("\n");
            schedule.append("Runway Used: ").append(flight.getRunwayUsed()).append("\n");
            schedule.append("Actual Time of Landing: ").append(flight.getActualTimeOfLanding()).append("\n");
            schedule.append("Actual Time of Departure: ").append(flight.getActualTimeOfDeparture()).append("\n");
            schedule.append("Number of Circuits: ").append(flight.getNumberOfCircuits()).append("\n\n");
        }
        JTextArea outputArea = new JTextArea(schedule.toString(), 10, 30);
        outputArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(outputArea), "Flight Schedule", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AirportTrafficManagementSystemGUI gui = new AirportTrafficManagementSystemGUI();
            gui.setVisible(true);
        });
    }
}
