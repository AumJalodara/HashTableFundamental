import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class Problem5 {

    // page -> total visits
    HashMap<String, Integer> pageViews = new HashMap<>();

    // page -> unique visitors
    HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();

    // source -> count
    HashMap<String, Integer> trafficSources = new HashMap<>();


    // Process page view event
    public void processEvent(Event e) {

        // update page views
        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);

        // update unique visitors
        uniqueVisitors.putIfAbsent(e.url, new HashSet<>());
        uniqueVisitors.get(e.url).add(e.userId);

        // update traffic source
        trafficSources.put(e.source,
                trafficSources.getOrDefault(e.source, 0) + 1);
    }


    // Display dashboard
    public void getDashboard() {

        System.out.println("Top Pages:");

        // priority queue for top pages
        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        int count = 0;

        while (!pq.isEmpty() && count < 10) {

            Map.Entry<String, Integer> entry = pq.poll();
            String page = entry.getKey();
            int views = entry.getValue();

            int unique = uniqueVisitors.get(page).size();

            System.out.println((count + 1) + ". " + page +
                    " - " + views + " views (" + unique + " unique)");

            count++;
        }


        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int c : trafficSources.values())
            total += c;

        for (String source : trafficSources.keySet()) {

            int countSource = trafficSources.get(source);

            double percent = (countSource * 100.0) / total;

            System.out.println(source + ": " +
                    String.format("%.2f", percent) + "%");
        }
    }


    public static void main(String[] args) {

        Problem5 analytics = new Problem5();

        analytics.processEvent(new Event("/article/breaking-news", "user1", "google"));
        analytics.processEvent(new Event("/article/breaking-news", "user2", "facebook"));
        analytics.processEvent(new Event("/sports/championship", "user3", "google"));
        analytics.processEvent(new Event("/sports/championship", "user4", "direct"));
        analytics.processEvent(new Event("/sports/championship", "user3", "google"));

        analytics.getDashboard();
    }
}