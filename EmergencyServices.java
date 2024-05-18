import java.util.Stack;

public class EmergencyServices {
    private Stack<String> emergencyStack;

    public EmergencyServices() { this.emergencyStack = new Stack<>(); }

    public void addEmergencyRequest(String request) {
        if (!request.trim().isEmpty()) {
            emergencyStack.push(request.trim());
        }
    }

    public String handleEmergencyRequest() {
        if (!emergencyStack.isEmpty()) {
            return emergencyStack.pop();
        } else {
            return "No pending emergency requests.";
        }
    }

    public void clearEmergencyRequests() { emergencyStack.clear(); }

    public int getEmergencyRequestsCount() { return emergencyStack.size(); }

    public boolean isEmergencyStackEmpty() { return emergencyStack.isEmpty(); }

    public String viewTopEmergencyRequest() {
        return emergencyStack.isEmpty() ? "No pending emergency requests." : emergencyStack.peek();
    }

    public String viewTopEmergencyRequestWithoutRemoving() {
        return emergencyStack.isEmpty() ? "No pending emergency requests." : emergencyStack.peek();
    }
}
