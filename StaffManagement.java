import java.io.*;
import java.util.*;

public class StaffManagement {
    private File staffFile;

    public StaffManagement(String file) {
        this.staffFile = new File("staffDetails.txt");
        try {
            staffFile.createNewFile(); // Ensure the file exists
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStaff(int id, String name, String role) {
        String details = id + "," + name + "," + role;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(staffFile, true))) {
            bw.write(details);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeStaff(int id) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(staffFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(id + ",")) {
                    updatedLines.add(line);
                }
            }
            rewriteFile(updatedLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStaffDetails(int id, String newName, String newRole) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(staffFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    line = id + "," + newName + "," + newRole;
                }
                updatedLines.add(line);
            }
            rewriteFile(updatedLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStaffDetails(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(staffFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Staff not found.";
    }

    public boolean isStaffExist(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(staffFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTotalStaffCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(staffFile))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void clearAllStaff() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(staffFile))) {
            bw.write(""); // Clear the content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStaffListEmpty() {
        return getTotalStaffCount() == 0;
    }

    public String searchStaffByName(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(staffFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("," + name + ",")) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Staff not found.";
    }

    public String getAllStaffDetailsAsString() {
        StringBuilder allStaffDetails = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(staffFile))) {
            br.lines().forEach(line -> allStaffDetails.append(line).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allStaffDetails.toString();
    }

    private void rewriteFile(List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(staffFile, false))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
