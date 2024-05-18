import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmergencyServicesGUI extends JFrame {
    private EmergencyServices emergencyServices = new EmergencyServices();
    private JTextArea displayArea = new JTextArea(10, 20);
    private JTextField requestInput = new JTextField(20);
    private JLabel requestCountLabel = new JLabel("Current Emergency Requests: 0");

    public EmergencyServicesGUI() {
        setLayout(new BorderLayout());
        setTitle("Emergency Services");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Buttons
        JButton addRequestButton = new JButton("Add Emergency Request");
        JButton handleRequestButton = new JButton("Handle Emergency Request");
        JButton clearRequestsButton = new JButton("Clear All Emergency Requests");
        JButton viewTopRequestButton = new JButton("View Top Emergency Request");
        JButton viewTopRequestWithoutRemovingButton = new JButton("View Top Emergency Request Without Removing");

        // Action Listeners
        addRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request = requestInput.getText();
                if (!request.isEmpty()) {
                    emergencyServices.addEmergencyRequest(request);
                    displayArea.append("Added: " + request + "\n");
                    updateRequestCount();
                    requestInput.setText("");
                }
            }
        });

        handleRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String handledRequest = emergencyServices.handleEmergencyRequest();
                displayArea.append("Handled: " + handledRequest + "\n");
                updateRequestCount();
            }
        });

        clearRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emergencyServices.clearEmergencyRequests();
                displayArea.append("All emergency requests cleared.\n");
                updateRequestCount();
            }
        });

        viewTopRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String topRequest = emergencyServices.viewTopEmergencyRequest();
                displayArea.append("Top request: " + topRequest + "\n");
            }
        });

        viewTopRequestWithoutRemovingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String topRequest = emergencyServices.viewTopEmergencyRequestWithoutRemoving();
                displayArea.append("Top request without removing: " + topRequest + "\n");
            }
        });

        // Display Area
        displayArea.setEditable(false);
        add(displayArea, BorderLayout.CENTER);

        // Request Input
        add(requestInput, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addRequestButton);
        buttonPanel.add(handleRequestButton);
        buttonPanel.add(clearRequestsButton);
        buttonPanel.add(viewTopRequestButton);
        buttonPanel.add(viewTopRequestWithoutRemovingButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Request Count Label
        add(requestCountLabel, BorderLayout.WEST);

        setVisible(true);
    }

    private void updateRequestCount() {
        requestCountLabel.setText("Current Emergency Requests: " + emergencyServices.getEmergencyRequestsCount());
        AbstractAction handleRequestButton = null;
        handleRequestButton.setEnabled(!emergencyServices.isEmergencyStackEmpty());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmergencyServicesGUI();
            }
        });
    }
}
