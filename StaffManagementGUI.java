import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StaffManagementGUI extends JFrame {
    private StaffManagement staffManagement;
    private JTextArea displayArea = new JTextArea(10, 20);
    private JTextField idInput = new JTextField(20);
    private JTextField nameInput = new JTextField(20);
    private JTextField roleInput = new JTextField(20);
    private JTextField searchInput = new JTextField(20);

    public StaffManagementGUI() {
        // Assume "staffDetails.txt" is in the current working directory
        staffManagement = new StaffManagement("staffDetails.txt");

        setLayout(new BorderLayout());
        setTitle("Staff Management (File Based)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        setupDisplayArea();
        setupInputPanel();
        setupButtonPanel();

        pack();
        setVisible(true);
    }

    private void setupDisplayArea() {
        JScrollPane scrollPane = new JScrollPane(displayArea);
        displayArea.setEditable(false);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Staff ID:"));
        inputPanel.add(idInput);

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameInput);

        inputPanel.add(new JLabel("Role:"));
        inputPanel.add(roleInput);

        inputPanel.add(new JLabel("Search Name:"));
        inputPanel.add(searchInput);

        add(inputPanel, BorderLayout.NORTH);
    }

    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton addStaffButton = new JButton("Add Staff");
        addStaffButton.addActionListener(this::addStaff);

        JButton removeStaffButton = new JButton("Remove Staff");
        removeStaffButton.addActionListener(this::removeStaff);

        JButton displayAllStaffButton = new JButton("Display All Staff");
        displayAllStaffButton.addActionListener(this::displayAllStaff);

        JButton searchStaffButton = new JButton("Search Staff by Name");
        searchStaffButton.addActionListener(this::searchStaffByName);

        JButton clearAllStaffButton = new JButton("Clear All Staff");
        clearAllStaffButton.addActionListener(this::clearAllStaff);

        buttonPanel.add(addStaffButton);
        buttonPanel.add(removeStaffButton);
        buttonPanel.add(displayAllStaffButton);
        buttonPanel.add(searchStaffButton);
        buttonPanel.add(clearAllStaffButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addStaff(ActionEvent e) {
        try {
            int id = Integer.parseInt(idInput.getText().trim());
            String name = nameInput.getText().trim();
            String role = roleInput.getText().trim();

            if (name.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Role cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            staffManagement.addStaff(id, name, role);
            displayArea.append("Added: " + id + ", " + name + ", " + role + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for Staff ID", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding staff: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeStaff(ActionEvent e) {
        try {
            int id = Integer.parseInt(idInput.getText().trim());
            staffManagement.removeStaff(id);
            displayArea.append("Removed Staff ID: " + id + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for Staff ID", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error removing staff: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAllStaff(ActionEvent e) {
        String allStaff = staffManagement.getAllStaffDetailsAsString();
        displayArea.setText(allStaff);
    }

    private void searchStaffByName(ActionEvent e) {
        String name = searchInput.getText().trim();
        String result = staffManagement.searchStaffByName(name);
        if (result.equals("Staff not found.")) {
            JOptionPane.showMessageDialog(this, "Staff not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            displayArea.setText(result);
        }
    }

    private void clearAllStaff(ActionEvent e) {
        staffManagement.clearAllStaff();
        displayArea.setText("All staff cleared.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StaffManagementGUI::new);
    }
}
