import java.util.*;

public class Problem2 {

    // productId -> stock count
    HashMap<String, Integer> inventory = new HashMap<>();

    // productId -> waiting list
    HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    // Add product
    public void addProduct(String productId, int stock) {
        inventory.put(productId, stock);
        waitingList.put(productId, new LinkedList<>());
    }

    // Check stock
    public int checkStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    // Purchase item (synchronized to avoid overselling)
    public synchronized String purchaseItem(String productId, int userId) {

        int stock = inventory.getOrDefault(productId, 0);

        if (stock > 0) {
            inventory.put(productId, stock - 1);
            return "Success, " + (stock - 1) + " units remaining";
        } else {
            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);
            return "Added to waiting list, position #" + queue.size();
        }
    }

    public static void main(String[] args) {

        Problem2 system = new Problem2();

        system.addProduct("IPHONE15_256GB", 10);

        System.out.println("Stock: " + system.checkStock("IPHONE15_256GB"));

        System.out.println(system.purchaseItem("IPHONE15_256GB", 101));
        System.out.println(system.purchaseItem("IPHONE15_256GB", 102));
        System.out.println(system.purchaseItem("IPHONE15_256GB", 103));

        // Stock finished
        System.out.println(system.purchaseItem("IPHONE15_256GB", 104));
        System.out.println(system.purchaseItem("IPHONE15_256GB", 105));
    }
}