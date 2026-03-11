import java.util.*;

class TrieNode {
    HashMap<Character, TrieNode> children = new HashMap<>();
    List<String> queries = new ArrayList<>();
}

public class Problem7 {

    TrieNode root = new TrieNode();

    // query -> frequency
    HashMap<String, Integer> frequency = new HashMap<>();


    // Insert query into Trie
    public void insertQuery(String query) {

        TrieNode node = root;

        for (char c : query.toCharArray()) {

            node.children.putIfAbsent(c, new TrieNode());

            node = node.children.get(c);

            node.queries.add(query);
        }

        frequency.put(query, frequency.getOrDefault(query, 0) + 1);
    }


    // Search suggestions for prefix
    public List<String> search(String prefix) {

        TrieNode node = root;

        for (char c : prefix.toCharArray()) {

            if (!node.children.containsKey(c))
                return new ArrayList<>();

            node = node.children.get(c);
        }

        List<String> list = node.queries;

        // sort by frequency
        list.sort((a, b) -> frequency.get(b) - frequency.get(a));

        return list.subList(0, Math.min(10, list.size()));
    }


    public static void main(String[] args) {

        Problem7 system = new Problem7();

        system.insertQuery("java tutorial");
        system.insertQuery("javascript");
        system.insertQuery("java download");
        system.insertQuery("java tutorial");

        System.out.println(system.search("jav"));
    }
}