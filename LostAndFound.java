import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LostAndFound{
    private File file;
    private final String delimiter = ",";

    public LostAndFound(String filename) {
        this.file = new File(filename);
        try {
            file.createNewFile(); // Create the file if it doesn't exist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLostItem(String item, String description) {
        try (FileWriter fw = new FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(item + delimiter + description);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String findLostItem(String item) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                if (parts[0].equals(item)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Item not found.";
    }

    public void addMultipleLostItems(Map<String, String> items) {
        items.forEach(this::addLostItem);
    }

    public boolean isLostItemExists(String item) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(item + delimiter)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTotalLostItemsCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Map<String, String> searchLostItemsByCategory(String category) {
        Map<String, String> searchResults = new TreeMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                if (parts[1].contains(category)) {
                    searchResults.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public void removeFoundItem(String item) {
        File tempFile = new File(file.getAbsolutePath() + ".tmp");
        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(item + delimiter) && !found) {
                    found = true; // Skip this line
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
            if (!found) {
                System.out.println("Item not found in storage.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!file.delete() || !tempFile.renameTo(file)) {
            System.out.println("Could not update the lost items file.");
        }
    }

    // This method is not needed in the file-based implementation but provided for API consistency.
    public Map<String, String> getLostItemsByCategory(String category) {
        return searchLostItemsByCategory(category);
    }
}
