import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FlightNoticeGUI extends JFrame {
    private List<FlightNotice> flightNotices;
    private JTextArea flightNoticesTextArea;

    public FlightNoticeGUI() {
        super("Flight Notice Board");
        flightNotices = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Text area to display flight notices
        flightNoticesTextArea = new JTextArea(10, 30);
        flightNoticesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(flightNoticesTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton addFlightButton = new JButton("Add Flight");
        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFlightNotice();
            }
        });
        JButton clearButton = new JButton("Clear Notices");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFlightNotices();
            }
        });
        buttonPanel.add(addFlightButton);
        buttonPanel.add(clearButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addFlightNotice() {
        // Dialog box to input flight details
        JTextField flightIdField = new JTextField();
        JTextField flightNameField = new JTextField();
        JTextField arrivalTimeField = new JTextField();
        JTextField departureTimeField = new JTextField();
        JTextField sourceField = new JTextField();
        JTextField destinationField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Flight ID:"));
        inputPanel.add(flightIdField);
        inputPanel.add(new JLabel("Flight Name:"));
        inputPanel.add(flightNameField);
        inputPanel.add(new JLabel("Arrival Time:"));
        inputPanel.add(arrivalTimeField);
        inputPanel.add(new JLabel("Departure Time:"));
        inputPanel.add(departureTimeField);
        inputPanel.add(new JLabel("Source:"));
        inputPanel.add(sourceField);
        inputPanel.add(new JLabel("Destination:"));
        inputPanel.add(destinationField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Add Flight Notice", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int flightId = Integer.parseInt(flightIdField.getText());
                String flightName = flightNameField.getText();
                String arrivalTime = arrivalTimeField.getText();
                String departureTime = departureTimeField.getText();
                String source = sourceField.getText();
                String destination = destinationField.getText();

                FlightNotice flightNotice = new FlightNotice(flightId, flightName, arrivalTime, departureTime, source, destination);
                flightNotices.add(flightNotice);
                updateFlightNoticesTextArea();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid flight ID. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFlightNotices() {
        flightNotices.clear();
        updateFlightNoticesTextArea();
    }

    private void updateFlightNoticesTextArea() {
        StringBuilder sb = new StringBuilder();
        for (FlightNotice notice : flightNotices) {
            sb.append(notice).append("\n\n");
        }
        flightNoticesTextArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlightNoticeGUI();
            }
        });
    }
}

class FlightNotice {
    private int flightId;
    private String flightName;
    private String arrivalTime;
    private String departureTime;
    private String source;
    private String destination;

    public FlightNotice(int flightId, String flightName, String arrivalTime, String departureTime, String source, String destination) {
        this.flightId = flightId;
        this.flightName = flightName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.source = source;
        this.destination = destination;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Flight ID: " + flightId + "\n" +
                "Flight Name: " + flightName + "\n" +
                "Arrival Time: " + arrivalTime + "\n" +
                "Departure Time: " + departureTime + "\n" +
                "Source: " + source + "\n" +
                "Destination: " + destination;
    }
}
