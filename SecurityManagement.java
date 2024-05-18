import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SecurityManagement {
    private final String taskFilePath = "tasks.txt";
    private final String completedTaskFilePath = "completed_tasks.txt";

    public SecurityManagement() {
        // Initialize files if they don't exist
        initializeFile(taskFilePath);
        initializeFile(completedTaskFilePath);
    }

    private void initializeFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSecurityTask(String task) {
        addTaskToFile(taskFilePath, task);
    }

    public String getNextSecurityTask() {
        try {
            List<String> tasks = readTasksFromFile(taskFilePath);
            if (!tasks.isEmpty()) {
                String nextTask = tasks.get(0);
                tasks.remove(0);
                overwriteFileWithTasks(taskFilePath, tasks);
                return nextTask;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void processSecurityTasks() {
        try {
            List<String> tasks = readTasksFromFile(taskFilePath);
            for (String task : tasks) {
                System.out.println("Processing security task: " + task);
                markTaskAsCompleted(task);
            }
            clearSecurityTasks(); // Use the clear method to avoid code duplication
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSecurityTasks(List<String> tasks) {
        tasks.forEach(this::addSecurityTask);
    }

    public int getSecurityTasksCount() {
        try {
            return readTasksFromFile(taskFilePath).size();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void clearSecurityTasks() {
        try (PrintWriter writer = new PrintWriter(taskFilePath)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getCompletedTasks() {
        try {
            return readTasksFromFile(completedTaskFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean markTaskAsCompleted(String task) {
        try {
            List<String> tasks = readTasksFromFile(taskFilePath);
            if (tasks.remove(task)) {
                overwriteFileWithTasks(taskFilePath, tasks);
                addTaskToFile(completedTaskFilePath, task);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sortSecurityTasks() {
        try {
            List<String> tasks = readTasksFromFile(taskFilePath);
            Collections.sort(tasks, Comparator.comparing(task -> task.split(": ")[0]));
            overwriteFileWithTasks(taskFilePath, tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isHighPriorityTaskAvailable() {
        try {
            return readTasksFromFile(taskFilePath).stream().anyMatch(task -> task.startsWith("High"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLowPriorityTaskAvailable() {
        try {
            return readTasksFromFile(taskFilePath).stream().anyMatch(task -> task.startsWith("Low"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getSecurityTasks() {
        try {
            return readTasksFromFile(taskFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllTasksInQueue() {
        List<String> allTasks = new ArrayList<>();
        allTasks.addAll(getSecurityTasks());
        allTasks.addAll(getCompletedTasks());
        return allTasks;
    }

    private void addTaskToFile(String filePath, String task) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(task);
        } catch (IOException e) {
            System.err.println("An error occurred while adding a task.");
            e.printStackTrace();
        }
    }

    private List<String> readTasksFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    private void overwriteFileWithTasks(String filePath, List<String> tasks) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (String task : tasks) {
                out.println(task);
            }
        }
    }
}
