import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageCargoGUI extends JFrame {

    private ManageCargo cargoManager;
    private JTextArea cargoTextArea;
    private JTextField nameField;
    private JTextField weightField;

    public ManageCargoGUI() {
        cargoManager = new ManageCargo();

        setTitle("Cargo Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        cargoTextArea = new JTextArea(10, 30);
        cargoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(cargoTextArea);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Weight:"));
        weightField = new JTextField();
        inputPanel.add(weightField);

        JButton addButton = new JButton("Add Cargo");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCargo();
            }
        });
        inputPanel.add(addButton);

        JButton displayButton = new JButton("Display Cargo");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCargo();
            }
        });
        inputPanel.add(displayButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // private void addCargo() {
    //     String name = nameField.getText();
    //     double weight = Double.parseDouble(weightField.getText());
    //     Cargo cargo = new Cargo(name, weight);
    //     cargoManager.addCargo(cargo);
    //     nameField.setText("");
    //     weightField.setText("");
    //     JOptionPane.showMessageDialog(this, "Cargo added successfully.");
    // }
    private void addCargo() {
    String name = nameField.getText();
    String weightText = weightField.getText();
    if (weightText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the weight.");
        return;
    }
    try {
        double weight = Double.parseDouble(weightText);
        Cargo cargo = new Cargo(name, weight);
        cargoManager.addCargo(cargo);
        nameField.setText("");
        weightField.setText("");
        JOptionPane.showMessageDialog(this, "Cargo added successfully.");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid weight format. Please enter a valid number.");
    }
}


    private void displayCargo() {
        cargoTextArea.setText("");
        for (Cargo cargo : cargoManager.getCargoList()) {
            cargoTextArea.append(cargo.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ManageCargoGUI cargoGUI = new ManageCargoGUI();
                cargoGUI.setVisible(true);
            }
        });
    }
}
