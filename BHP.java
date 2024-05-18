import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;

public class BHP extends JFrame {
    private JTextField ticketNumberField, bagCounterField, bagWeightField;
    private JTextArea additionalInfoArea;
    private Queue<String> bagInfoQueue;
    private static final String FILE_NAME = "bag_info.txt";

    public BHP() {
        super("Bag Handling System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        bagInfoQueue = new LinkedList<>();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 2, 5, 5));

        mainPanel.add(new JLabel("Ticket Number:"));
        ticketNumberField = new JTextField();
        mainPanel.add(ticketNumberField);

        mainPanel.add(new JLabel("Bag Counter:"));
        bagCounterField = new JTextField();
        mainPanel.add(bagCounterField);

        mainPanel.add(new JLabel("Bag Weight (kg):"));
        bagWeightField = new JTextField();
        mainPanel.add(bagWeightField);

        mainPanel.add(new JLabel("Additional Info:"));
        additionalInfoArea = new JTextArea();
        additionalInfoArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(additionalInfoArea);
        mainPanel.add(scrollPane);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());
        mainPanel.add(addButton);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveButtonListener());
        mainPanel.add(removeButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateButtonListener());
        mainPanel.add(updateButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        mainPanel.add(searchButton);

        JButton showAllButton = new JButton("Show All Data");
        showAllButton.addActionListener(new ShowAllButtonListener());
        mainPanel.add(showAllButton);

        add(mainPanel);
        setVisible(true);
    }

    private void saveBagInfoToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String info : bagInfoQueue) {
                writer.write(info);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBagInfoFromFile() {
        bagInfoQueue.clear(); // Clear existing data

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bagInfoQueue.offer(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ticketNumber = ticketNumberField.getText().trim();
            String bagCounter = bagCounterField.getText().trim();
            String bagWeight = bagWeightField.getText().trim();
            String additionalInfo = additionalInfoArea.getText().trim();

            if (ticketNumber.isEmpty() || bagCounter.isEmpty() || bagWeight.isEmpty()) {
                JOptionPane.showMessageDialog(BHP.this, "Ticket Number, Bag Counter, and Bag Weight are required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate numeric fields
            if (!isNumeric(bagCounter) || !isNumeric(bagWeight)) {
                JOptionPane.showMessageDialog(BHP.this, "Bag Counter and Bag Weight must be numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check for duplicate ticket number
            for (String info : bagInfoQueue) {
                if (info.contains("Ticket Number: " + ticketNumber)) {
                    JOptionPane.showMessageDialog(BHP.this, "Ticket Number already exists. Please enter a different one.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String bagInfo = "Ticket Number: " + ticketNumber + ", " +
                    "Bag Counter: " + bagCounter + ", " +
                    "Bag Weight: " + bagWeight + " kg, " +
                    "Additional Info: " + additionalInfo;

            bagInfoQueue.offer(bagInfo);
            saveBagInfoToFile(); // Save bag info to file

            JOptionPane.showMessageDialog(BHP.this, "Bag information added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields after adding
            clearFields();
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // Regular expression to check if string is numeric
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ticketToRemove = ticketNumberField.getText().trim();
            if (ticketToRemove.isEmpty()) {
                JOptionPane.showMessageDialog(BHP.this, "Ticket Number is a required field.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirm removal operation
            int choice = JOptionPane.showConfirmDialog(BHP.this, "Are you sure you want to remove bag information for ticket number " + ticketToRemove + "?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            boolean removed = bagInfoQueue.removeIf(info -> info.contains("Ticket Number: " + ticketToRemove));

            if (removed) {
                saveBagInfoToFile(); // Save bag info to file
                JOptionPane.showMessageDialog(BHP.this, "Bag information removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(BHP.this, "No matching ticket found to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clear input fields after removal attempt
            clearFields();
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ticketToUpdate = ticketNumberField.getText().trim();
            String updatedBagCounter = bagCounterField.getText().trim();
            String updatedBagWeight = bagWeightField.getText().trim();
            String updatedAdditionalInfo = additionalInfoArea.getText().trim();

            if (ticketToUpdate.isEmpty() || updatedBagCounter.isEmpty() || updatedBagWeight.isEmpty()) {
                JOptionPane.showMessageDialog(BHP.this, "Ticket Number, Bag Counter, and Bag Weight are required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate numeric fields
            if (!isNumeric(updatedBagCounter) || !isNumeric(updatedBagWeight)) {
                JOptionPane.showMessageDialog(BHP.this, "Bag Counter and Bag Weight must be numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean updated = false;
            for (int i = 0; i < bagInfoQueue.size(); i++) {
                String info = bagInfoQueue.poll();
                if (info.contains("Ticket Number: " + ticketToUpdate)) {
                    // Update bag information
                    info = "Ticket Number: " + ticketToUpdate + ", " +
                            "Bag Counter: " + updatedBagCounter + ", " +
                            "Bag Weight: " + updatedBagWeight + " kg, " +
                            "Additional Info: " + updatedAdditionalInfo;
                    updated = true;
                }
                bagInfoQueue.offer(info);
            }

            if (updated) {
                saveBagInfoToFile(); // Save bag info to file
                JOptionPane.showMessageDialog(BHP.this, "Bag information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(BHP.this, "No matching ticket found to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clear input fields after update attempt
            clearFields();
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ticketToSearch = ticketNumberField.getText().trim();

            if (ticketToSearch.isEmpty()) {
                JOptionPane.showMessageDialog(BHP.this, "Please enter a ticket number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean found = false;
            StringBuilder searchResult = new StringBuilder("Search Results:\n");

            for (String info : bagInfoQueue) {
                if (info.contains("Ticket Number: " + ticketToSearch)) {
                    searchResult.append(info).append("\n");
                    found = true;
                }
            }

            if (!found) {
                searchResult.append("No matching ticket found.");
            }

            JOptionPane.showMessageDialog(BHP.this, searchResult.toString(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class ShowAllButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder allData = new StringBuilder("All Entered Data:\n");
            for (String info : bagInfoQueue) {
                allData.append(info).append("\n");
            }

            if (bagInfoQueue.isEmpty()) {
                allData.append("No data entered yet.");
            }

            JOptionPane.showMessageDialog(BHP.this, allData.toString(), "All Entered Data", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        ticketNumberField.setText("");
        bagCounterField.setText("");
        bagWeightField.setText("");
        additionalInfoArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BHP bhp = new BHP();
            bhp.loadBagInfoFromFile(); // Load bag info from file
        });
    }
}