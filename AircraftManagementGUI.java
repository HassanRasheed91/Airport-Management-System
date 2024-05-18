import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AircraftManagementGUI extends JFrame implements ActionListener {
    private JTextField registrationNumberField, aircraftTypeField, companyNameField;
    private JTextField seatingCapacityField;
    private JButton addButton, updateButton, deleteButton, displayButton, displayAllButton, clearButton;
    private JTable aircraftTable;
    private DefaultTableModel tableModel;
    private JTextArea outputArea;

    private static final Pattern REGISTRATION_NUMBER_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");
//    private static final Pattern AIRCRAFT_TYPE_PATTERN = Pattern.compile("^[A-Za-z ]+$");
    private static final Pattern COMPANY_NAME_PATTERN = Pattern.compile("^[A-Za-z ]+$");

    private static final ArrayList<AircraftManagement> aircraftList = new ArrayList<>();
    private static final String FILE_NAME = "aircraft.txt";

    public AircraftManagementGUI() {
        super("Aircraft Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create input fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Aircraft Details"));
        inputPanel.add(new JLabel("Registration Number:"));
        registrationNumberField = new JTextField();
        inputPanel.add(registrationNumberField);
        inputPanel.add(new JLabel("Aircraft Type:"));
        aircraftTypeField = new JTextField();
        inputPanel.add(aircraftTypeField);
        inputPanel.add(new JLabel("Seating Capacity:"));
        seatingCapacityField = new JTextField();
        seatingCapacityField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
        inputPanel.add(seatingCapacityField);
        inputPanel.add(new JLabel("Company Name:"));
        companyNameField = new JTextField();
        inputPanel.add(companyNameField);

        // Create buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        buttonPanel.add(updateButton);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);
        displayButton = new JButton("Display");
        displayButton.addActionListener(this);
        buttonPanel.add(displayButton);
        displayAllButton = new JButton("Display All");
        displayAllButton.addActionListener(this);
        buttonPanel.add(displayAllButton);
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        // Create table
        String[] columnNames = {"Registration Number", "Aircraft Type", "Seating Capacity", "Company Name"};
        tableModel = new DefaultTableModel(columnNames, 0);
        aircraftTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(aircraftTable);

        // Create output area
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        add(outputScrollPane, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Load aircraft data from file
        loadAircraftsFromFile();
        updateTableData();
    }

    private void updateTableData() {
        tableModel.setRowCount(0);
        for (AircraftManagement aircraft : aircraftList) {
            tableModel.addRow(new Object[]{aircraft.getRegistrationNumber(), aircraft.getAircraftType(), aircraft.getSeatingCapacity(), aircraft.getCompanyName()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String registrationNumber = registrationNumberField.getText().trim();
            String aircraftType = aircraftTypeField.getText().trim();
            String seatingCapacityStr = seatingCapacityField.getText().trim();
            String companyName = companyNameField.getText().trim();

            if (registrationNumber.isEmpty() || aircraftType.isEmpty() || seatingCapacityStr.isEmpty() || companyName.isEmpty()) {
                outputArea.setText("Please fill in all fields.");
            } else if (!isRegistrationNumberValid(registrationNumber)) {
                outputArea.setText("Invalid registration number format.");
//            } else if (!isAircraftTypeValid(aircraftType)) {
//                outputArea.setText("Invalid aircraft type format.");
//            } else if (!isCompanyNameValid(companyName)) {
                outputArea.setText("Invalid company name format.");
            } else {
                int seatingCapacity;
                try {
                    seatingCapacity = Integer.parseInt(seatingCapacityStr);
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid seating capacity format.");
                    return;
                }

                if (aircraftExists(registrationNumber)) {
                    outputArea.setText("Aircraft with the same registration number already exists.");
                } else {
                    AircraftManagement aircraft = new AircraftManagement(registrationNumber, aircraftType, seatingCapacity, companyName);
                    if (addAircraft(aircraft)) {
                        outputArea.setText("Aircraft added successfully.");
                        clearFields();
                        updateTableData();
                        saveAircraftsToFile();
                    } else {
                        outputArea.setText("Failed to add aircraft.");
                    }
                }
            }
        } else if (e.getSource() == updateButton) {
            String registrationNumber = registrationNumberField.getText().trim();
            String aircraftType = aircraftTypeField.getText().trim();
            String seatingCapacityStr = seatingCapacityField.getText().trim();
            String companyName = companyNameField.getText().trim();

            if (registrationNumber.isEmpty() || aircraftType.isEmpty() || seatingCapacityStr.isEmpty() || companyName.isEmpty()) {
                outputArea.setText("Please fill in all fields.");
            } else if (!isRegistrationNumberValid(registrationNumber)) {
                outputArea.setText("Invalid registration number format.");
//            } else if (!isAircraftTypeValid(aircraftType)) {
//                outputArea.setText("Invalid aircraft type format.");
//            } else if (!isCompanyNameValid(companyName)) {
                outputArea.setText("Invalid company name format.");
            } else {
                int seatingCapacity;
                try {
                    seatingCapacity = Integer.parseInt(seatingCapacityStr);
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid seating capacity format.");
                    return;
                }

                if (!aircraftExists(registrationNumber)) {
                    outputArea.setText("Aircraft with the given registration number does not exist.");
                } else {
                    AircraftManagement aircraft = new AircraftManagement(registrationNumber, aircraftType, seatingCapacity, companyName);
                    if (updateAircraft(aircraft)) {
                        outputArea.setText("Aircraft updated successfully.");
                        clearFields();
                        updateTableData();
                        saveAircraftsToFile();
                    } else {
                        outputArea.setText("Failed to update aircraft.");
                    }
                }
            }
        } else if (e.getSource() == deleteButton) {
            String registrationNumber = registrationNumberField.getText().trim();

            if (registrationNumber.isEmpty()) {
                outputArea.setText("Please enter the registration number.");
            } else if (!isRegistrationNumberValid(registrationNumber)) {
                outputArea.setText("Invalid registration number format.");
            } else {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this aircraft?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (deleteAircraft(registrationNumber)) {
                        outputArea.setText("Aircraft deleted successfully.");
                        clearFields();
                        updateTableData();
                        saveAircraftsToFile();
                    } else {
                        outputArea.setText("Failed to delete aircraft.");
                    }
                }
            }
        } else if (e.getSource() == displayButton) {
            String registrationNumber = registrationNumberField.getText().trim();

            if (registrationNumber.isEmpty()) {
                outputArea.setText("Please enter the registration number.");
            } else if (!isRegistrationNumberValid(registrationNumber)) {
                outputArea.setText("Invalid registration number format.");
            } else {
                String aircraftDetails = searchByRegistrationNumber(registrationNumber);
                if (aircraftDetails != null) {
                    outputArea.setText(aircraftDetails);
                } else {
                    outputArea.setText("Aircraft not found.");
                }
            }
        } else if (e.getSource() == displayAllButton) {
            updateTableData();
            outputArea.setText("All aircraft data displayed in the table.");
        } else if (e.getSource() == clearButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all aircraft data?", "Confirm Clear", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                clearAircraftsFromFile();
                clearFields();
                updateTableData();
                outputArea.setText("All aircraft data has been cleared.");
            }
        }
    }

    private boolean isRegistrationNumberValid(String registrationNumber) {
        return REGISTRATION_NUMBER_PATTERN.matcher(registrationNumber).matches();
    }

//    private boolean isAircraftTypeValid(String aircraftType) {
//        return AIRCRAFT_TYPE_PATTERN.matcher(aircraftType).matches();
//    }

    private boolean isCompanyNameValid(String companyName) {
        return COMPANY_NAME_PATTERN.matcher(companyName).matches();
    }

    private void clearFields() {
        registrationNumberField.setText("");
        aircraftTypeField.setText("");
        seatingCapacityField.setText("");
        companyNameField.setText("");
    }

    private static boolean aircraftExists(String registrationNumber) {
        for (AircraftManagement aircraft : aircraftList) {
            if (aircraft.getRegistrationNumber().equals(registrationNumber)) {
                return true;
            }
        }
        return false;
    }

    private static boolean addAircraft(AircraftManagement aircraft) {
        return aircraftList.add(aircraft);
    }

    private static boolean updateAircraft(AircraftManagement newAircraft) {
        for (int i = 0; i < aircraftList.size(); i++) {
            AircraftManagement aircraft = aircraftList.get(i);
            if (aircraft.getRegistrationNumber().equals(newAircraft.getRegistrationNumber())) {
                aircraftList.set(i, newAircraft);
                return true;
            }
        }
        return false;
    }

    private static boolean deleteAircraft(String registrationNumber) {
        for (AircraftManagement aircraft : aircraftList) {
            if (aircraft.getRegistrationNumber().equals(registrationNumber)) {
                return aircraftList.remove(aircraft);
            }
        }
        return false;
    }

    private static String searchByRegistrationNumber(String registrationNumber) {
        for (AircraftManagement aircraft : aircraftList) {
            if (aircraft.getRegistrationNumber().equals(registrationNumber)) {
                return aircraft.toString();
            }
        }
        return null;
    }

    private static void loadAircraftsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 4) {
                    String registrationNumber = details[0].trim();
                    String aircraftType = details[1].trim();
                    int seatingCapacity = Integer.parseInt(details[2].trim());
                    String companyName = details[3].trim();
                    AircraftManagement aircraft = new AircraftManagement(registrationNumber, aircraftType, seatingCapacity, companyName);
                    aircraftList.add(aircraft);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveAircraftsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (AircraftManagement aircraft : aircraftList) {
                writer.write(aircraft.getRegistrationNumber() + "," + aircraft.getAircraftType() + "," + aircraft.getSeatingCapacity() + "," + aircraft.getCompanyName() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearAircraftsFromFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        aircraftList.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AircraftManagementGUI();
            }
        });
    }
}


