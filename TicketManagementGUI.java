import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TicketManagementGUI extends JFrame {
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private JTextField ticketNumberField, passengerNameField, seatNumberField, flightNumberField, searchField;
    private JTextArea outputArea;

    public TicketManagementGUI() {
        setTitle("Ticket Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create table and model
        String[] columnNames = {"Ticket Number", "Passenger Name", "Seat Number", "Flight Number"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create input fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Ticket Number:"));
        ticketNumberField = new JTextField();
        inputPanel.add(ticketNumberField);
        inputPanel.add(new JLabel("Passenger Name:"));
        passengerNameField = new JTextField();
        inputPanel.add(passengerNameField);
        inputPanel.add(new JLabel("Seat Number:"));
        seatNumberField = new JTextField();
        inputPanel.add(seatNumberField);
        inputPanel.add(new JLabel("Flight Number:"));
        flightNumberField = new JTextField();
        inputPanel.add(flightNumberField);
        inputPanel.add(new JLabel("Search:"));
        searchField = new JTextField();
        inputPanel.add(searchField);
        add(inputPanel, BorderLayout.NORTH);

        // Create buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());
        buttonPanel.add(addButton);
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateButtonListener());
        buttonPanel.add(updateButton);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonPanel.add(deleteButton);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        buttonPanel.add(searchButton);
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new UndoButtonListener());
        buttonPanel.add(undoButton);
        JButton displayAllButton = new JButton("Display All");
        displayAllButton.addActionListener(new DisplayAllButtonListener());
        buttonPanel.add(displayAllButton);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonListener());
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create output area
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        add(outputScrollPane, BorderLayout.EAST);

        // Load tickets from file
        TicketManagement.loadTicketsFromFile("tickets.txt");
        loadTicketsToTable();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadTicketsToTable() {
        tableModel.setRowCount(0);
        List<TicketManagement> tickets = TicketManagement.getTicketsFromMemory();
        for (TicketManagement ticket : tickets) {
            Object[] rowData = {
                    ticket.getTicketNumber(),
                    ticket.getPassengerName(),
                    ticket.getSeatNumber(),
                    ticket.getFlightNumber()
            };
            tableModel.addRow(rowData);
        }
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int ticketNumber = Integer.parseInt(ticketNumberField.getText());
            String passengerName = passengerNameField.getText();
            String seatNumber = seatNumberField.getText();
            String flightNumber = flightNumberField.getText();

            TicketManagement ticket = TicketManagement.bookTicket(ticketNumber, passengerName, seatNumber, flightNumber);
            if (ticket != null) {
                Object[] rowData = {
                        ticket.getTicketNumber(),
                        ticket.getPassengerName(),
                        ticket.getSeatNumber(),
                        ticket.getFlightNumber()
                };
                tableModel.addRow(rowData);
                outputArea.append("Ticket added successfully.\n");
            } else {
                outputArea.append("Ticket number is already in use.\n");
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ticketTable.getSelectedRow();
            if (selectedRow >= 0) {
                int ticketNumber = Integer.parseInt(ticketNumberField.getText());
                String passengerName = passengerNameField.getText();
                String seatNumber = seatNumberField.getText();
                String flightNumber = flightNumberField.getText();

                TicketManagement ticket = TicketManagement.findTicket(ticketNumber);
                if (ticket != null) {
                    ticket.setPassengerName(passengerName);
                    ticket.setSeatNumber(seatNumber);
                    ticket.setFlightNumber(flightNumber);

                    tableModel.setValueAt(ticketNumber, selectedRow, 0);
                    tableModel.setValueAt(passengerName, selectedRow, 1);
                    tableModel.setValueAt(seatNumber, selectedRow, 2);
                    tableModel.setValueAt(flightNumber, selectedRow, 3);

                    outputArea.append("Ticket updated successfully.\n");
                } else {
                    outputArea.append("Ticket not found.\n");
                }
            } else {
                outputArea.append("Please select a ticket to update.\n");
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ticketTable.getSelectedRow();
            if (selectedRow >= 0) {
                int ticketNumber = (int) tableModel.getValueAt(selectedRow, 0);
                TicketManagement.tickets.remove(ticketNumber);
                tableModel.removeRow(selectedRow);
                outputArea.append("Ticket deleted successfully.\n");
            } else {
                outputArea.append("Please select a ticket to delete.\n");
            }
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String flightNumber = searchField.getText();
            List<TicketManagement> tickets = TicketManagement.searchByFlightNumber(flightNumber);
            if (!tickets.isEmpty()) {
                outputArea.append("Tickets found for flight " + flightNumber + ":\n");
                for (TicketManagement ticket : tickets) {
                    outputArea.append(ticket.toString() + "\n");
                }
            } else {
                outputArea.append("No tickets found for flight " + flightNumber + ".\n");
            }
        }
    }

    private class UndoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicketManagement.undoLastBooking();
            loadTicketsToTable();
            outputArea.append("Last booking undone.\n");
        }
    }

    private class DisplayAllButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            outputArea.setText(TicketManagement.displayAllTickets());
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicketManagement.clearAllTicketsInMemory();
            TicketManagement.clearPersistedTicketData("tickets.txt");
            tableModel.setRowCount(0);
            outputArea.setText("All ticket data has been cleared.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicketManagementGUI();
            }
        });
    }
}

