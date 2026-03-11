import java.util.*;

public class Problem4 {

    // nGram -> set of document IDs
    HashMap<String, Set<String>> index = new HashMap<>();

    int n = 5; // 5-gram

    // Extract n-grams from text
    public List<String> extractNGrams(String text) {

        List<String> ngrams = new ArrayList<>();

        String[] words = text.split(" ");

        for (int i = 0; i <= words.length - n; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < n; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    // Add document to database
    public void addDocument(String docId, String text) {

        List<String> ngrams = extractNGrams(text);

        for (String gram : ngrams) {

            index.putIfAbsent(gram, new HashSet<>());

            index.get(gram).add(docId);
        }
    }

    // Analyze new document
    public void analyzeDocument(String docId, String text) {

        List<String> ngrams = extractNGrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {

            if (index.containsKey(gram)) {

                for (String otherDoc : index.get(gram)) {

                    matchCount.put(otherDoc,
                            matchCount.getOrDefault(otherDoc, 0) + 1);
                }
            }
        }

        System.out.println("Extracted " + ngrams.size() + " n-grams");

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);

            double similarity =
                    (matches * 100.0) / ngrams.size();

            System.out.println(
                    "Found " + matches + " matching n-grams with "
                            + doc + " → Similarity: " + similarity + "%");
        }
    }

    public static void main(String[] args) {

        Problem4 detector = new Problem4();

        detector.addDocument(
                "essay_089",
                "data structures and algorithms are important in computer science"
        );

        detector.addDocument(
                "essay_092",
                "data structures and algorithms are important concepts in programming"
        );

        detector.analyzeDocument(
                "essay_123",
                "data structures and algorithms are important in modern computing"
        );
    }
}