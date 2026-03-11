import java.util.*;

public class Problem1 {

    // Stores username -> userId
    HashMap<String, Integer> users = new HashMap<>();

    // Stores username -> number of attempts
    HashMap<String, Integer> attempts = new HashMap<>();

    int userIdCounter = 1;

    // Register user
    public void registerUser(String username) {
        users.put(username, userIdCounter++);
    }

    // Check availability
    public boolean checkAvailability(String username) {

        // track attempts
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);

        return !users.containsKey(username);
    }

    // Suggest alternatives
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;

            if (!users.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        // another suggestion format
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {

        String most = "";
        int max = 0;

        for (String name : attempts.keySet()) {
            if (attempts.get(name) > max) {
                max = attempts.get(name);
                most = name;
            }
        }

        return most + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

      Problem1 p = new Problem1();

        p.registerUser("john_doe");
        p.registerUser("admin");

        System.out.println(p.checkAvailability("john_doe"));
        System.out.println(p.checkAvailability("jane_smith"));

        System.out.println(p.suggestAlternatives("john_doe"));

        System.out.println(p.getMostAttempted());
    }
}