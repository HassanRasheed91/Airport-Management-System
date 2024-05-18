import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirportManagementSystem extends JFrame {
    private static final String ADMIN_USERNAME = "ijaz";
    private static final String ADMIN_PASSWORD = "ijaz145@";
    private static final String USER_USERNAME = "Hassan";
    private static final String USER_PASSWORD = "Hassan123";

    private JLabel loginTypeLabel;
    private JComboBox<String> loginTypeComboBox;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AirportManagementSystem() {
        setTitle("Airport Management System");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        initializeComponents();
        addComponentsToFrame();
        addActionListeners();
    }

    private void initializeComponents() {
        loginTypeLabel = new JLabel("Choose login type:");
        String[] loginTypes = {"Admin", "User"};
        loginTypeComboBox = new JComboBox<>(loginTypes);
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(loginTypeLabel);
        panel.add(loginTypeComboBox);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addActionListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String selectedLoginType = (String) loginTypeComboBox.getSelectedItem();
                int loginType = selectedLoginType.equals("Admin") ? 1 : 2;

                switch (loginType) {
                    case 1:
                        adminLogin(username, password);
                        break;
                    case 2:
                        userLogin(username, password);
                        break;
                    default:
                        JOptionPane.showMessageDialog(AirportManagementSystem.this,"Invalid choice. Exiting...");
                }
            }
        });
    }

    private void adminLogin(String username, String password) {
        if (isAdmin(username, password)) {
            JOptionPane.showMessageDialog(this, "Admin login successful!");
            // Call admin functionalities
            // For simplicity, let's assume we'll display a message for now
            AdminInterfaceGUI adminInterface = new AdminInterfaceGUI(this);
            adminInterface.setVisible(true);
            //JOptionPane.showMessageDialog(this.AdminInterfaceGUI, "Admin functionalities to be implemented.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin username or password. Exiting...");
        }
    }

    private void userLogin(String username, String password) {
        if (isUser(username, password)) {
            JOptionPane.showMessageDialog(this, "User login successful!");
            // Call user functionalities
            // For simplicity, let's assume we'll display a message for now
            JOptionPane.showMessageDialog(this, "User functionalities to be implemented.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid user username or password. Exiting...");
        }
    }

    private boolean isAdmin(String username, String password) {
        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }

    private boolean isUser(String username, String password) {
        return username.equals(USER_USERNAME) && password.equals(USER_PASSWORD);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AirportManagementSystem().setVisible(true);
            }
        });
    }

    public void addFlight(FlightManagement.Flight flight) {
    }
}
