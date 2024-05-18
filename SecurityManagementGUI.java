import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class SecurityManagementGUI extends JFrame {
    private SecurityManagement securityManagement;
    private JTextField taskField;
    private JTextArea taskQueueArea;
    private JTextArea completedTasksArea;
    private JButton addTaskButton;
    private JButton processTasksButton;
    private JButton sortTasksButton;
    private JButton printTasksButton;
    private JButton markTaskAsCompletedButton;
    private JButton checkHighPriorityTaskButton;
    private JButton checkLowPriorityTaskButton;
    private JButton clearTasksButton;
    private JButton getTaskCountButton;
    private JButton addMultipleTasksButton;

    public SecurityManagementGUI() {
        securityManagement = new SecurityManagement(); // Ensure this class is implemented with file handling
        initializeComponents();
        layoutComponents();
        attachEventHandlers();
    }

    private void initializeComponents() {
        setTitle("Enhanced Security Management System - File Based");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        taskField = new JTextField();
        taskQueueArea = new JTextArea();
        completedTasksArea = new JTextArea();
        addTaskButton = new JButton("Add Task");
        processTasksButton = new JButton("Process Tasks");
        sortTasksButton = new JButton("Sort Tasks");
        printTasksButton = new JButton("Print All Tasks");
        markTaskAsCompletedButton = new JButton("Mark Task as Completed");
        checkHighPriorityTaskButton = new JButton("Check High Priority Task");
        checkLowPriorityTaskButton = new JButton("Check Low Priority Task");
        clearTasksButton = new JButton("Clear Tasks");
        getTaskCountButton = new JButton("Get Task Count");
        addMultipleTasksButton = new JButton("Add Multiple Tasks");
        taskQueueArea.setEditable(false);
        completedTasksArea.setEditable(false);
    }

    private void layoutComponents() {
        JPanel taskPanel = new JPanel(new GridLayout(1, 3));
        taskPanel.add(taskField);
        taskPanel.add(addTaskButton);
        taskPanel.add(addMultipleTasksButton);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(processTasksButton);
        controlPanel.add(sortTasksButton);
        controlPanel.add(printTasksButton);
        controlPanel.add(markTaskAsCompletedButton);
        controlPanel.add(checkHighPriorityTaskButton);
        controlPanel.add(checkLowPriorityTaskButton);
        controlPanel.add(clearTasksButton);
        controlPanel.add(getTaskCountButton);

        add(taskPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskQueueArea), BorderLayout.CENTER);
        add(new JScrollPane(completedTasksArea), BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void attachEventHandlers() {
        addTaskButton.addActionListener(e -> {
            String task = taskField.getText();
            if (!task.isEmpty()) {
                securityManagement.addSecurityTask(task);
                taskField.setText("");
                updateTaskQueueArea();
            }
        });

        addMultipleTasksButton.addActionListener(e -> {
            List<String> tasks = List.of("High: Task 1", "Medium: Task 2", "Low: Task 3"); // Example tasks
            securityManagement.addSecurityTasks(tasks);
            updateTaskQueueArea();
        });

        processTasksButton.addActionListener(e -> {
            securityManagement.processSecurityTasks();
            updateTaskQueueArea();
            updateCompletedTasksArea();
        });

        sortTasksButton.addActionListener(e -> {
            securityManagement.sortSecurityTasks();
            updateTaskQueueArea();
        });

        printTasksButton.addActionListener(e -> updateAllTasksArea());

        markTaskAsCompletedButton.addActionListener(e -> {
            String task = JOptionPane.showInputDialog(this, "Enter task to mark as completed:");
            if (task != null && !task.trim().isEmpty()) {
                if (securityManagement.markTaskAsCompleted(task)) {
                    JOptionPane.showMessageDialog(this, "Task marked as completed.");
                } else {
                    JOptionPane.showMessageDialog(this, "Task not found.");
                }
                updateTaskQueueArea();
                updateCompletedTasksArea();
            }
        });

        checkHighPriorityTaskButton.addActionListener(e -> {
            boolean available = securityManagement.isHighPriorityTaskAvailable();
            JOptionPane.showMessageDialog(this, available ? "High priority task is available." : "No high priority task available.");
        });

        checkLowPriorityTaskButton.addActionListener(e -> {
            boolean available = securityManagement.isLowPriorityTaskAvailable();
            JOptionPane.showMessageDialog(this, available ? "Low priority task is available." : "No low priority task available.");
        });

        clearTasksButton.addActionListener(e -> {
            securityManagement.clearSecurityTasks();
            updateTaskQueueArea();
            updateCompletedTasksArea();
        });

        getTaskCountButton.addActionListener(e -> {
            int count = securityManagement.getSecurityTasksCount();
            JOptionPane.showMessageDialog(this, "Current task count: " + count);
        });
    }

    private void updateTaskQueueArea() {
        taskQueueArea.setText("");
        securityManagement.getSecurityTasks().forEach(task -> taskQueueArea.append(task + "\n"));
    }

    private void updateCompletedTasksArea() {
        completedTasksArea.setText("");
        securityManagement.getCompletedTasks().forEach(task -> completedTasksArea.append(task + "\n"));
    }

    private void updateAllTasksArea() {
        taskQueueArea.setText("");
        List<String> allTasks = securityManagement.getAllTasksInQueue();
        allTasks.addAll(securityManagement.getCompletedTasks());
        allTasks.forEach(task -> taskQueueArea.append(task + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SecurityManagementGUI().setVisible(true));
    }
}
