import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import java.util.Date;

public class AdminInterfaceGUI extends JFrame {
    private AirportManagementSystem ams;
    private FlightManagementGUI flights; // Declare the flights list
    private JTextField nameField, ageField, positionField;
    // private LinkedList<StaffMember> staffList; // Declare staffList
    private AirportTrafficManagementSystemGUI airportTrafficManagementSystemGUI;
    private BHP bhp;
    private TicketManagement ticket;
    private  PassengerManagement passanger;
    private EmergencyServicesGUI Emergency;
    private TicketManagementGUI Ticket;

    private AircraftManagementGUI Aircraft;
    private StaffManagementGUI StaffManagement;
    private LostAndFoundGUI LostAndFound;
    private EmergencyServicesGUI EmergencyServices;
    private SecurityManagementGUI SecurityManagement;




    
    // Declare foodTextArea and retailTextArea
    private JTextArea foodTextArea;
    private JTextArea retailTextArea;

    // Declare foodItems and retailItems lists
    private List<FoodItem> foodItems;
    private List<RetailItem> retailItems;

    private List<Cargo> cargoList; // Declare cargoList
    // private Queue<Feedback> feedbackQueue; // Declare feedbackQueue

    public AdminInterfaceGUI() {
        // Initialize text fields
        nameField = new JTextField();
        ageField = new JTextField();
        positionField = new JTextField();
        airportTrafficManagementSystemGUI = new AirportTrafficManagementSystemGUI();
        add(airportTrafficManagementSystemGUI);
        bhp = new BHP();
        flights = new FlightManagementGUI();

         ticket = new TicketManagement();
        Emergency = new EmergencyServicesGUI();
        StaffManagement = new StaffManagementGUI();
        LostAndFound = new LostAndFoundGUI();
        EmergencyServices = new EmergencyServicesGUI();
        SecurityManagement = new SecurityManagementGUI();



        // Rest of your constructor code...
    }

    public AdminInterfaceGUI(AirportManagementSystem ams) {
        super("Admin Interface");

        // nameField = new JTextField();
        // ageField = new JTextField();
        // positionField = new JTextField();

        this.ams = ams;
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // flights = new ArrayList<>(); // Initialize the flights list
        // staffList = new LinkedList<>(); // Initialize staffList
        foodItems = new ArrayList<>(); // Initialize foodItems list
        retailItems = new ArrayList<>(); // Initialize retailItems list
        cargoList = new ArrayList<>(); // Initialize cargoList
        // feedbackQueue = new Queue<>(10); // Initialize feedbackQueue

        JPanel panel = new JPanel(new GridLayout(6, 1));

        JButton FlightButton = new JButton("Flight Manamgent System");
        FlightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                addFlight();
            }
        });

        JButton AirportTraffic = new JButton("AirportTrafficManagementSystem");
        AirportTraffic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                AirportTraffic(); // Call the AirportTraffic method here
            }
        });

        JButton manageFoodRetailButton = new JButton("Manage Food and Retail Services");
        manageFoodRetailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                manageFoodAndRetailServices();
            }
        });

        JButton BHP = new JButton("Bag Handling Procees");
        BHP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                BHP();
            }
        });

        JButton manageCargoButton = new JButton("Manage Cargo");
        manageCargoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                manageCargo();
            }
        });

        JButton Aircarft = new JButton("Aircarft");
        Aircarft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Aircarft();
            }
        });

        JButton Ticket = new JButton("Ticket Managment system ");
        Ticket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Ticket();
            }
        });

        JButton StaffManagement = new JButton("Staff Managment system ");
        StaffManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                StaffManagement();
            }
        });

        JButton PassengerManagement = new JButton("Passenger Managment system ");
        PassengerManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                PassengerManagement();
            }
        });

        JButton EmergencyServices = new JButton("EmergencyServices Managment system ");
        EmergencyServices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                EmergencyServices();
            }
        });

        JButton LostAndFound = new JButton("LostAndFound Managment system ");
        LostAndFound.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                LostAndFound();
            }
        });

        JButton SecurityManagement = new JButton("Security Managment system ");
        SecurityManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                SecurityManagement();
            }
        });

        

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        panel.add(FlightButton);
        // panel.add(addStaffButton);
        panel.add(manageFoodRetailButton);
        // panel.add(viewFeedbackButton);
        panel.add(manageCargoButton);
        panel.add(AirportTraffic);
        panel.add(BHP);
        panel.add(Ticket);
        panel.add(PassengerManagement);
        panel.add(Aircarft);
        panel.add(StaffManagement);
        panel.add(LostAndFound);
        panel.add(EmergencyServices);
        panel.add(SecurityManagement);








        // panel.add(ticketButton);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }



    private void updateFoodTextArea() {
        StringBuilder sb = new StringBuilder();
        for (FoodItem item : foodItems) {
            sb.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
        }
        foodTextArea.setText(sb.toString());
    }

    private void updateRetailTextArea() {
        StringBuilder sb = new StringBuilder();
        for (RetailItem item : retailItems) {
            sb.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
        }
        retailTextArea.setText(sb.toString());
    }

    private void manageFoodAndRetailServices() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel foodLabel = new JLabel("Food Item:");
        JTextField foodNameField = new JTextField();
        JLabel foodPriceLabel = new JLabel("Price:");
        JTextField foodPriceField = new JTextField();
        JButton addFoodButton = new JButton("Add Food Item");
        foodTextArea = new JTextArea(10, 30);
        foodTextArea.setEditable(false);

        JLabel retailLabel = new JLabel("Retail Item:");
        JTextField retailNameField = new JTextField();
        JLabel retailPriceLabel = new JLabel("Price:");
        JTextField retailPriceField = new JTextField();
        JButton addRetailButton = new JButton("Add Retail Item");
        retailTextArea = new JTextArea(10, 30);
        retailTextArea.setEditable(false);

        // Add components to the panel
        panel.add(foodLabel);
        panel.add(foodNameField);
        panel.add(foodPriceLabel);
        panel.add(foodPriceField);
        panel.add(addFoodButton);
        panel.add(foodTextArea);

        panel.add(retailLabel);
        panel.add(retailNameField);
        panel.add(retailPriceLabel);
        panel.add(retailPriceField);
        panel.add(addRetailButton);
        panel.add(retailTextArea);

        // Add panel to frame
        add(panel);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Action listener for adding food item
        addFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = foodNameField.getText();
                String priceStr = foodPriceField.getText();
                if (!priceStr.isEmpty()) {
                    try {
                        double price = Double.parseDouble(priceStr);
                        FoodItem foodItem = new FoodItem(name, price);
                        // Add food item to your food management system
                        // For example:
                        // foodManagementSystem.addFoodItem(foodItem);
                        foodItems.add(foodItem);
                        updateFoodTextArea();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid price format. Please enter a valid number.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a price for the food item.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for adding retail item
        addRetailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = retailNameField.getText();
                String priceStr = retailPriceField.getText();
                if (!priceStr.isEmpty()) {
                    try {
                        double price = Double.parseDouble(priceStr);
                        RetailItem retailItem = new RetailItem(name, price);
                        // Add retail item to your retail management system
                        // For example:
                        // retailManagementSystem.addRetailItem(retailItem);
                        retailItems.add(retailItem);
                        updateRetailTextArea();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid price format. Please enter a valid number.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a price for the retail item.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void addFlight() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        FlightManagementGUI FlightGUI = new FlightManagementGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        FlightGUI.setSize(600, 400);

        // Set the window to be visible
        FlightGUI.setVisible(true);
    }

    private void SecurityManagement() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        SecurityManagementGUI SecurityManagement = new SecurityManagementGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        SecurityManagement.setSize(600, 400);

        // Set the window to be visible
        SecurityManagement.setVisible(true);
    }

    private void EmergencyServices() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        EmergencyServicesGUI EmergencyServices = new EmergencyServicesGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        EmergencyServices.setSize(600, 400);

        // Set the window to be visible
        EmergencyServices.setVisible(true);
    }

    private void StaffManagement() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        StaffManagementGUI StaffManagement = new StaffManagementGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        StaffManagement.setSize(600, 400);

        // Set the window to be visible
        StaffManagement.setVisible(true);
    }


    private void LostAndFound() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        LostAndFoundGUI LostAndFound = new LostAndFoundGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        LostAndFound.setSize(600, 400);

        // Set the window to be visible
        LostAndFound.setVisible(true);
    }

    private void Aircarft() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        AircraftManagementGUI aircraft = new AircraftManagementGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        aircraft.setSize(600, 400);

        // Set the window to be visible
        aircraft.setVisible(true);
    }

    private void PassengerManagement() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        PassengerManagementGUI Passenger = new PassengerManagementGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        Passenger.setSize(600, 400);

        // Set the window to be visible
        Passenger.setVisible(true);
    }
    private void Ticket() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        TicketManagementGUI Ticket = new TicketManagementGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        Ticket.setSize(600, 400);

        // Set the window to be visible
        Ticket.setVisible(true);
    }
    private void AirportTraffic() {
        // Create a new instance of AirportTrafficManagementSystemGUI
        AirportTrafficManagementSystemGUI trafficGUI = new AirportTrafficManagementSystemGUI();

        // Set up the layout and components for the traffic management GUI
        // For example, you can add buttons, labels, text fields, etc.

        // Set the size of the window
        trafficGUI.setSize(600, 400);

        // Set the window to be visible
        trafficGUI.setVisible(true);
    }

    private void BHP() {
        // Instantiate the Bag Handling Process GUI
        BHP bhpFrame = new BHP();

        // Set up the content of the Bag Handling Process GUI (e.g., panels, buttons,
        // labels)
        // Add necessary components to bhpFrame.getContentPane()...

        // Set size, visibility, and other properties of the JFrame
        bhpFrame.setSize(600, 400); // Set the size as needed
        bhpFrame.setLocationRelativeTo(null); // Center the JFrame on the screen
        bhpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose the JFrame on close
        bhpFrame.setVisible(true); // Make the Bag Handling Process GUI visible
    }

    // private void TicketManagement() {
    // // Instantiate the TicketManagement class
    // ticket = new TicketManagement();

    // // Perform any additional actions related to ticket management as needed
    // // For example, if TicketManagement has a method to show its GUI, you can
    // call it here
    // ticket.showTicketManagementGUI();
    // }

    private void manageCargo() {
        // Create a dialog box to ask the user for the cargo operation
        String[] options = { "Add Cargo", "Remove Cargo", "Display Cargo" };
        String selectedOption = (String) JOptionPane.showInputDialog(this,
                "Select a cargo operation:", "Manage Cargo",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // Handle the selected operation
        if (selectedOption != null) {
            switch (selectedOption) {
                case "Add Cargo":
                    addCargo();
                    break;
                case "Remove Cargo":
                    removeCargo();
                    break;
                case "Display Cargo":
                    displayCargo();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid option selected.");
                    break;
            }
        }
    }

    // Add Cargo Method
    private void addCargo() {
        // Create a panel to hold the input fields
        JPanel panel = new JPanel(new GridLayout(2, 2));

        // Add input fields for cargo details
        JTextField cargoNameField = new JTextField();
        JTextField cargoWeightField = new JTextField();

        panel.add(new JLabel("Cargo Name:"));
        panel.add(cargoNameField);
        panel.add(new JLabel("Cargo Weight:"));
        panel.add(cargoWeightField);

        // Show the dialog to the user
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Cargo", JOptionPane.OK_CANCEL_OPTION);

        // If the user clicks OK, add the new cargo
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Retrieve data from input fields
                String name = cargoNameField.getText();
                double weight = Double.parseDouble(cargoWeightField.getText());

                // Create a new Cargo object and add it to the cargo list
                Cargo cargo = new Cargo(name, weight);
                cargoList.add(cargo);

                JOptionPane.showMessageDialog(this, "Cargo added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid data.");
            }
        }
    }

    // Remove Cargo Method
    private void removeCargo() {
        // Check if there are cargos to remove
        if (cargoList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No cargo to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create an array to hold cargo names for the selection dialog
        String[] cargoNames = new String[cargoList.size()];
        for (int i = 0; i < cargoList.size(); i++) {
            cargoNames[i] = cargoList.get(i).getName();
        }

        // Prompt the user to select a cargo to remove
        String selectedCargoName = (String) JOptionPane.showInputDialog(this,
                "Select a cargo to remove:", "Remove Cargo",
                JOptionPane.PLAIN_MESSAGE, null, cargoNames, cargoNames[0]);

        // If the user cancels or closes the dialog, do nothing
        if (selectedCargoName == null) {
            return;
        }

        // Find the cargo object with the selected name and remove it from the list
        for (Cargo cargo : cargoList) {
            if (cargo.getName().equals(selectedCargoName)) {
                cargoList.remove(cargo);
                JOptionPane.showMessageDialog(this, "Cargo removed successfully!");
                return;
            }
        }

        // If cargo with the selected name was not found
        JOptionPane.showMessageDialog(this, "Cargo not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Display Cargo Method
    private void displayCargo() {
        // Check if there are cargos to display
        if (cargoList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No cargo to display.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a StringBuilder to build the cargo information
        StringBuilder cargoInfo = new StringBuilder();
        for (Cargo cargo : cargoList) {
            cargoInfo.append(cargo.toString()).append("\n"); // Append cargo details
        }

        // Show the cargo information in a message dialog
        JOptionPane.showMessageDialog(this, cargoInfo.toString(), "Cargo List", JOptionPane.INFORMATION_MESSAGE);
    }

    private void NoticeBoard() {
        JPanel noticePanel = new JPanel(new GridLayout(0, 2));

        // Add labels for flight notice details
        noticePanel.add(new JLabel("Flight ID:"));
        noticePanel.add(new JLabel("Flight Name:"));
        noticePanel.add(new JLabel("Arrival Time:"));
        noticePanel.add(new JLabel("Departure Time:"));
        noticePanel.add(new JLabel("Source:"));
        noticePanel.add(new JLabel("Destination:"));

        // // Add flight notice details
        // for (Flight1 flight : flights) {
        // noticePanel.add(new JLabel(flight.getFlightNumber()));
        // noticePanel.add(new JLabel(flight.getFlightName()));
        // noticePanel.add(new JLabel(String.valueOf(flight.getArrivalTime())));
        // noticePanel.add(new JLabel(String.valueOf(flight.getDepartureTime())));
        // noticePanel.add(new JLabel(flight.getSource()));
        // noticePanel.add(new JLabel(flight.getDestination()));
        // }

        // Add notice panel to the frame
        add(noticePanel);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AirportManagementSystem ams = new AirportManagementSystem();
                new AdminInterfaceGUI(ams);
            }
        });
    }
}
