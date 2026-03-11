import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    long time;

    Transaction(int id, int amount, String merchant, String account, long time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

public class Problem9 {

    // Classic Two-Sum
    public static void findTwoSum(List<Transaction> list, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : list) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction prev = map.get(complement);

                System.out.println("Two-Sum Pair: (" + prev.id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }
    }

    // Two-Sum with 1 hour window
    public static void findTwoSumWindow(List<Transaction> list, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : list) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction prev = map.get(complement);

                if (Math.abs(t.time - prev.time) <= 3600000) {
                    System.out.println("Two-Sum (1hr window): (" + prev.id + ", " + t.id + ")");
                }
            }

            map.put(t.amount, t);
        }
    }

    // Duplicate detection
    public static void detectDuplicates(List<Transaction> list) {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : list) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> l = map.get(key);

            if (l.size() > 1) {

                System.out.println("Duplicate Transactions for " + key);

                for (Transaction t : l) {
                    System.out.println("Transaction ID: " + t.id + " Account: " + t.account);
                }
            }
        }
    }

    // K-Sum (example for 3 sum)
    public static void findKSum(int[] nums, int k, int target, int start, List<Integer> current) {

        if (k == 0 && target == 0) {
            System.out.println("K-Sum Match: " + current);
            return;
        }

        if (k == 0) return;

        for (int i = start; i < nums.length; i++) {

            current.add(nums[i]);

            findKSum(nums, k - 1, target - nums[i], i + 1, current);

            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, 500, "StoreA", "acc1", 1000));
        transactions.add(new Transaction(2, 300, "StoreB", "acc2", 2000));
        transactions.add(new Transaction(3, 200, "StoreC", "acc3", 2500));
        transactions.add(new Transaction(4, 500, "StoreA", "acc4", 3000));

        System.out.println("Classic Two Sum:");
        findTwoSum(transactions, 500);

        System.out.println("\nTwo Sum With Time Window:");
        findTwoSumWindow(transactions, 500);

        System.out.println("\nDuplicate Detection:");
        detectDuplicates(transactions);

        System.out.println("\nK-Sum Example:");
        int nums[] = {500, 300, 200};
        findKSum(nums, 3, 1000, 0, new ArrayList<>());
    }
}