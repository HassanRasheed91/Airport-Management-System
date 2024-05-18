import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FlightManagementGUI extends JFrame {
    private static final String FILE_NAME = "flights.txt";
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private JTextField flightNumberField, sourceField, destinationField, departureTimeField, delayField;
    private JComboBox<FlightManagement.FlightStatus> statusComboBox;

    public FlightManagementGUI() {
        setTitle("Flight Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create components
        flightTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Flight Number", "Source", "Destination", "Departure Time", "Status", "Delay (mins)"}, 0);
        flightTable.setModel(tableModel);

        flightNumberField = new JTextField(10);
        sourceField = new JTextField(10);
        destinationField = new JTextField(10);
        departureTimeField = new JTextField(10);
        delayField = new JTextField(5);
        statusComboBox = new JComboBox<>(FlightManagement.FlightStatus.values());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Flight Number:"));
        inputPanel.add(flightNumberField);
        inputPanel.add(new JLabel("Source:"));
        inputPanel.add(sourceField);
        inputPanel.add(new JLabel("Destination:"));
        inputPanel.add(destinationField);
        inputPanel.add(new JLabel("Departure Time:"));
        inputPanel.add(departureTimeField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusComboBox);
        inputPanel.add(new JLabel("Delay (mins):"));
        inputPanel.add(delayField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton delayButton = new JButton("Delay");
        JButton displayAllButton = new JButton("Display All");
        JButton displayFlightButton = new JButton("Display Flight");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(delayButton);
        buttonPanel.add(displayAllButton);
        buttonPanel.add(displayFlightButton);

//        Container contentPane = getContentPane();
//        contentPane.setLayout(new BorderLayout());
//        contentPane.add(new JScrollPane(flightTable), BorderLayout.CENTER);
//        contentPane.add(inputPanel, BorderLayout.NORTH);
//        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(flightTable), BorderLayout.CENTER);
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(buttonPanel, BorderLayout.EAST);
        // Add action listeners
        addButton.addActionListener(new AddFlightListener());
        updateButton.addActionListener(new UpdateFlightListener());
        deleteButton.addActionListener(new DeleteFlightListener());
        delayButton.addActionListener(new DelayFlightListener());
        displayAllButton.addActionListener(new DisplayAllFlightsListener());
        displayFlightButton.addActionListener(new DisplayFlightListener());

        // Load flights from file
        loadFlights();
    }

    private void loadFlights() {
        tableModel.setRowCount(0);
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
                    FlightManagement.FlightStatus status = FlightManagement.FlightStatus.valueOf(parts[4]);
                    int delayInMinutes = Integer.parseInt(parts[5]);
                    Object[] row = {flightNumber, source, destination, departureTime, status, delayInMinutes};
                    tableModel.addRow(row);
                } else {
                    System.out.println("Invalid line at line " + lineNumber + " in file: " + line);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    private void saveFlights() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    sb.append(tableModel.getValueAt(i, j)).append(",");
                }
                sb.deleteCharAt(sb.length() - 1); // Remove the last comma
                writer.write(sb.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private class AddFlightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String flightNumber = flightNumberField.getText();
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String departureTime = departureTimeField.getText();
            FlightManagement.FlightStatus status = (FlightManagement.FlightStatus) statusComboBox.getSelectedItem();

            if (flightNumber.isEmpty() || source.isEmpty() || destination.isEmpty() || departureTime.isEmpty()) {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Please fill in all fields.");
                return;
            }

            // Check if the flight number already exists
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(flightNumber)) {
                    JOptionPane.showMessageDialog(FlightManagementGUI.this, "Flight number already exists.");
                    return;
                }
            }

            Object[] row = {flightNumber, source, destination, departureTime, status, 0};
            tableModel.addRow(row);
            saveFlights();
            clearFields();
        }
    }

    private class UpdateFlightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String flightNumber = flightNumberField.getText();
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String departureTime = departureTimeField.getText();
            FlightManagement.FlightStatus status = (FlightManagement.FlightStatus) statusComboBox.getSelectedItem();

            if (flightNumber.isEmpty() || source.isEmpty() || destination.isEmpty() || departureTime.isEmpty()) {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Please fill in all fields.");
                return;
            }

            boolean found = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(flightNumber)) {
                    tableModel.setValueAt(source, i, 1);
                    tableModel.setValueAt(destination, i, 2);
                    tableModel.setValueAt(departureTime, i, 3);
                    tableModel.setValueAt(status, i, 4);
                    found = true;
                    break;
                }
            }

            if (found) {
                saveFlights();
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Flight updated successfully.");
            } else {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Flight not found.");
            }

            clearFields();
        }
    }

    private class DeleteFlightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String flightNumber = flightNumberField.getText();

            if (flightNumber.isEmpty()) {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Please enter a flight number.");
                return;
            }

            boolean found = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(flightNumber)) {
                    tableModel.removeRow(i);
                    found = true;
                    break;
                }
            }

            if (found) {
                saveFlights();
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Flight deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Flight not found.");
            }

            clearFields();
        }
    }

    private class DelayFlightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String flightNumber = flightNumberField.getText();
            String delayString = delayField.getText();

            if (flightNumber.isEmpty() || delayString.isEmpty()) {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Please enter a flight number and delay.");
                return;
            }

            int delayInMinutes;
            try {
                delayInMinutes = Integer.parseInt(delayString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Invalid delay value.");
                return;
            }

            boolean found = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(flightNumber)) {
                    int currentDelay = (int) tableModel.getValueAt(i, 5);
                    tableModel.setValueAt(currentDelay + delayInMinutes, i, 5);
                    tableModel.setValueAt(FlightManagement.FlightStatus.DELAYED, i, 4);
                    found = true;
                    break;
                }
            }

            if (found) {
                saveFlights();
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Flight delayed successfully.");
            } else {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Flight not found.");
            }

            clearFields();
        }
    }
    private class DisplayAllFlightsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "No flights available.");
            }
        }
    }

    private class DisplayFlightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String flightNumber = flightNumberField.getText();

            if (flightNumber.isEmpty()) {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Please enter a flight number.");
                return;
            }

            boolean found = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(flightNumber)) {
                    StringBuilder sb = new StringBuilder("Flight Details:\n");
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        sb.append(tableModel.getColumnName(j)).append(": ").append(tableModel.getValueAt(i, j)).append("\n");
                    }
                    JOptionPane.showMessageDialog(FlightManagementGUI.this, sb.toString());
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(FlightManagementGUI.this, "Flight not found.");
            }
        }
    }

    private void clearFields() {
        flightNumberField.setText("");
        sourceField.setText("");
        destinationField.setText("");
        departureTimeField.setText("");
        statusComboBox.setSelectedIndex(0);
        delayField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlightManagementGUI gui = new FlightManagementGUI();
            gui.setVisible(true);
        });
    }
}