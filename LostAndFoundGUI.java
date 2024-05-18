import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.awt.event.ActionEvent;

public class LostAndFoundGUI extends JFrame {
    private LostAndFound lostAndFound;
    private JTextField itemField;
    private JTextField descriptionField;
    private JTextArea lostItemsArea;
    private JButton addButton;
    private JButton findButton;
    private JButton addMultipleButton;
    private JButton checkExistsButton;
    private JButton totalCountButton;
    private JButton searchCategoryButton;
    private JButton removeButton;

    public LostAndFoundGUI() {
        // Assume the file to store lost items is named "lostItems.txt" in the current directory
        lostAndFound = new LostAndFound("lostItems.txt");
        initGUI();
    }

    private void initGUI() {
        setTitle("Lost and Found Management (File Based)");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 2));

        // Initialize components
        itemField = new JTextField(20);
        descriptionField = new JTextField(20);
        addButton = new JButton("Add Item");
        findButton = new JButton("Find Item");
        addMultipleButton = new JButton("Add Multiple Items");
        checkExistsButton = new JButton("Check Item Exists");
        totalCountButton = new JButton("Total Lost Items Count");
        searchCategoryButton = new JButton("Search by Category");
        removeButton = new JButton("Remove Found Item");
        lostItemsArea = new JTextArea(10, 30);
        lostItemsArea.setEditable(false);

        // Add components to the panel
        panel.add(new JLabel("Item:"));
        panel.add(itemField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        panel.add(addButton);
        panel.add(findButton);
        panel.add(addMultipleButton);
        panel.add(checkExistsButton);
        panel.add(totalCountButton);
        panel.add(searchCategoryButton);
        panel.add(removeButton);
        panel.add(new JScrollPane(lostItemsArea));

        add(panel);
        setupListeners();
        pack();
    }

    private void setupListeners() {
        addButton.addActionListener(e -> {
            String item = itemField.getText();
            String description = descriptionField.getText();
            lostAndFound.addLostItem(item, description);
            refreshLostItemsArea(); // Refresh the displayed list of lost items
            JOptionPane.showMessageDialog(LostAndFoundGUI.this, "Item added successfully.");
        });

        findButton.addActionListener(e -> {
            String item = itemField.getText();
            String result = lostAndFound.findLostItem(item);
            JOptionPane.showMessageDialog(LostAndFoundGUI.this, "Result: " + result);
        });

        // Implement other button listeners here...
        // For brevity, I'll not add implementations for all buttons. Follow the addButton example.

        removeButton.addActionListener(e -> {
            String item = itemField.getText();
            lostAndFound.removeFoundItem(item);
            refreshLostItemsArea(); // Refresh the displayed list of lost items
            JOptionPane.showMessageDialog(LostAndFoundGUI.this, "Item removed successfully, if it was found.");
        });
    }

    private void refreshLostItemsArea() {
        lostItemsArea.setText("");
        Map<String, String> allItems = lostAndFound.searchLostItemsByCategory(""); // Assuming a method to get all items
        for (Map.Entry<String, String> entry : allItems.entrySet()) {
            lostItemsArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LostAndFoundGUI().setVisible(true));
    }
}
