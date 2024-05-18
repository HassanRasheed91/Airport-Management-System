import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PassengerManagementGUI extends JFrame implements ActionListener {
    private static final String DATA_FILE = "passengers.txt";
    private static final Map<Integer, PassengerManagement> passengerMap = new LinkedHashMap<>();
    private static final Deque<PassengerManagement> undoStack = new ArrayDeque<>();
    private static final Queue<PassengerManagement> processingQueue = new LinkedList<>();

    private JTextField passengerIDField, passengerNameField, ageField, addressField;
    private JComboBox<String> genderComboBox;
    private JTable passengerTable;
    private DefaultTableModel tableModel;
    public PassengerManagementGUI() {
        setTitle("Passenger Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        JPanel buttonsPanel = createButtonsPanel();
        JPanel tablePanel = createTablePanel();

        add(inputPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);

        loadData();
        updateTable();
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Passenger ID:"));
        passengerIDField = new JTextField();
        panel.add(passengerIDField);

        panel.add(new JLabel("Passenger Name:"));
        passengerNameField = new JTextField();
        panel.add(passengerNameField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Gender:"));
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        panel.add(genderComboBox);

        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add Passenger");
        addButton.addActionListener(this);
        panel.add(addButton);

        JButton updateButton = new JButton("Update Passenger");
        updateButton.addActionListener(this);
        panel.add(updateButton);

        JButton deleteButton = new JButton("Delete Passenger");
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        JButton undoButton = new JButton("Undo Delete");
        undoButton.addActionListener(this);
        panel.add(undoButton);

        JButton displayAllButton = new JButton("Display All Passengers");
        displayAllButton.addActionListener(this);
        panel.add(displayAllButton);

        JButton displayDetailsButton = new JButton("Display Passenger Details");
        displayDetailsButton.addActionListener(this);
        panel.add(displayDetailsButton);

        JButton clearButton = new JButton("Clear All Data");
        clearButton.addActionListener(this);
        panel.add(clearButton);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Passenger ID", "Passenger Name", "Age", "Address", "Gender"};
        tableModel = new DefaultTableModel(columnNames, 0);
        passengerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(passengerTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    private void addPassenger() {
        int passengerID = Integer.parseInt(passengerIDField.getText());
        String passengerName = passengerNameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String address = addressField.getText();
        String gender = (String) genderComboBox.getSelectedItem();

        if (passengerMap.containsKey(passengerID)) {
            JOptionPane.showMessageDialog(this, "Passenger with ID " + passengerID + " already exists.");
            return;
        }

        PassengerManagement passenger = new PassengerManagement(passengerID, passengerName, age, address, gender);
        passengerMap.put(passengerID, passenger);
        processingQueue.offer(passenger);
        updateTable();
        saveData(); // Save data to file
        JOptionPane.showMessageDialog(this, "Passenger " + passengerName + " added successfully.");
    }

    private void updatePassenger() {
        int passengerID = Integer.parseInt(passengerIDField.getText());

        if (!passengerMap.containsKey(passengerID)) {
            JOptionPane.showMessageDialog(this, "Passenger with ID " + passengerID + " not found.");
            return;
        }

        PassengerManagement passenger = passengerMap.get(passengerID);
        PassengerManagement updatedPassenger = new PassengerManagement(
                passenger.getPassengerID(),
                passengerNameField.getText().isEmpty() ? passenger.getPassengerName() : passengerNameField.getText(),
                ageField.getText().isEmpty() ? passenger.getAge() : Integer.parseInt(ageField.getText()),
                addressField.getText().isEmpty() ? passenger.getAddress() : addressField.getText(),
                genderComboBox.getSelectedItem().toString().isEmpty() ? passenger.getGender() : genderComboBox.getSelectedItem().toString()
        );

        passengerMap.put(passengerID, updatedPassenger);
        undoStack.offerLast(passenger);
        updateTable();
        saveData(); // Save data to file
        JOptionPane.showMessageDialog(this, "Passenger " + updatedPassenger.getPassengerName() + "'s information updated.");
    }

    private void deletePassenger() {
        int passengerID = Integer.parseInt(passengerIDField.getText());

        if (!passengerMap.containsKey(passengerID)) {
            JOptionPane.showMessageDialog(this, "Passenger with ID " + passengerID + " not found.");
            return;
        }

        PassengerManagement passenger = passengerMap.remove(passengerID);
        undoStack.offerLast(passenger);
        updateTable();
        saveData(); // Save data to file
        JOptionPane.showMessageDialog(this, "Passenger " + passenger.getPassengerName() + " deleted successfully.");
    }

    private void undoLastDelete() {
        if (undoStack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No delete operation to undo.");
            return;
        }

        PassengerManagement passenger = undoStack.pop();
        passengerMap.put(passenger.getPassengerID(), passenger);
        updateTable();
        saveData(); // Save data to file
        JOptionPane.showMessageDialog(this, "Undo delete operation for passenger: " + passenger.getPassengerName());
    }

    private void displayAllPassengers() {
        updateTable();
    }

    private void displayPassengerDetails() {
        int passengerID = Integer.parseInt(passengerIDField.getText());

        if (!passengerMap.containsKey(passengerID)) {
            JOptionPane.showMessageDialog(this, "Passenger with ID " + passengerID + " not found.");
            return;
        }

        PassengerManagement passenger = passengerMap.get(passengerID);
        JOptionPane.showMessageDialog(this, "Passenger Details:\n" + passenger);
    }

    private void clearAllData() {
        passengerMap.clear();
        undoStack.clear();
        processingQueue.clear();
        updateTable();
        saveData(); // Save empty data to file
        JOptionPane.showMessageDialog(this, "All passenger data has been cleared.");
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Clear existing data

        List<PassengerManagement> passengers = new ArrayList<>(passengerMap.values());
        passengers.forEach(passenger -> {
            Object[] rowData = {
                    passenger.getPassengerID(),
                    passenger.getPassengerName(),
                    passenger.getAge(),
                    passenger.getAddress(),
                    passenger.getGender()
            };
            tableModel.addRow(rowData);
        });
    }



    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int passengerID = Integer.parseInt(parts[0]);
                    String passengerName = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String address = parts[3];
                    String gender = parts[4];
                    PassengerManagement passenger = new PassengerManagement(passengerID, passengerName, age, address, gender);
                    passengerMap.put(passengerID, passenger);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while loading passenger data: " + e.getMessage());
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (PassengerManagement passenger : passengerMap.values()) {
                String line = passenger.getPassengerID() + "," + passenger.getPassengerName() + "," + passenger.getAge() + "," + passenger.getAddress() + "," + passenger.getGender();
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving passenger data: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Add Passenger":
                addPassenger();
                break;
            case "Update Passenger":
                updatePassenger();
                break;
            case "Delete Passenger":
                deletePassenger();
                break;
            case "Undo Delete":
                undoLastDelete();
                break;
            case "Display All Passengers":
                displayAllPassengers();
                break;
            case "Display Passenger Details":
                displayPassengerDetails();
                break;
            case "Clear All Data":
                clearAllData();
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PassengerManagementGUI gui = new PassengerManagementGUI();
                gui.setVisible(true);
            }
        });
    }
}

