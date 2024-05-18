import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Define FoodItem class for managing food items
class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Define RetailItem class for managing retail items
class RetailItem {
    private String name;
    private double price;

    public RetailItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// public class ManageFoodAndRetailServices extends JFrame {
//     private List<FoodItem> foodItems;
//     private List<RetailItem> retailItems;

//     private JTextArea foodTextArea;
//     private JTextArea retailTextArea;

//     public ManageFoodAndRetailServices() {
//         super("Food and Retail Services");

//         foodItems = new ArrayList<>();
//         retailItems = new ArrayList<>();

//         // Create GUI components
//         JPanel panel = new JPanel(new GridLayout(3, 2));

//         JLabel foodLabel = new JLabel("Food Item:");
//         JTextField foodNameField = new JTextField();
//         JLabel foodPriceLabel = new JLabel("Price:");
//         JTextField foodPriceField = new JTextField();
//         JButton addFoodButton = new JButton("Add Food Item");
//         foodTextArea = new JTextArea(10, 30);
//         foodTextArea.setEditable(false);

//         JLabel retailLabel = new JLabel("Retail Item:");
//         JTextField retailNameField = new JTextField();
//         JLabel retailPriceLabel = new JLabel("Price:");
//         JTextField retailPriceField = new JTextField();
//         JButton addRetailButton = new JButton("Add Retail Item");
//         retailTextArea = new JTextArea(10, 30);
//         retailTextArea.setEditable(false);

//         // Add components to the panel
//         panel.add(foodLabel);
//         panel.add(foodNameField);
//         panel.add(foodPriceLabel);
//         panel.add(foodPriceField);
//         panel.add(addFoodButton);
//         panel.add(foodTextArea);

//         panel.add(retailLabel);
//         panel.add(retailNameField);
//         panel.add(retailPriceLabel);
//         panel.add(retailPriceField);
//         panel.add(addRetailButton);
//         panel.add(retailTextArea);

//         // Add panel to frame
//         add(panel);

//         // Set frame properties
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         pack();
//         setLocationRelativeTo(null);
//         setVisible(true);

//         // Action listener for adding food item
//         addFoodButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 String name = foodNameField.getText();
//                 double price = Double.parseDouble(foodPriceField.getText());
//                 foodItems.add(new FoodItem(name, price));
//                 updateFoodTextArea();
//             }
//         });

//         // Action listener for adding retail item
//         addRetailButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 String name = retailNameField.getText();
//                 double price = Double.parseDouble(retailPriceField.getText());
//                 retailItems.add(new RetailItem(name, price));
//                 updateRetailTextArea();
//             }
//         });
//     }

//     // Update food text area
//     private void updateFoodTextArea() {
//         StringBuilder sb = new StringBuilder();
//         for (FoodItem item : foodItems) {
//             sb.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
//         }
//         foodTextArea.setText(sb.toString());
//     }

//     // Update retail text area
//     private void updateRetailTextArea() {
//         StringBuilder sb = new StringBuilder();
//         for (RetailItem item : retailItems) {
//             sb.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
//         }
//         retailTextArea.setText(sb.toString());
//     }

//     // Main method for testing
//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new ManageFoodAndRetailServices();
//             }
//         });
//     }
// }
