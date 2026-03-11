import java.util.*;

class VideoData {
    String videoId;
    String content;

    VideoData(String id, String content) {
        this.videoId = id;
        this.content = content;
    }
}

public class Problem10 {

    // L1 Cache (10k) – Memory
    LinkedHashMap<String, VideoData> L1;

    // L2 Cache (100k) – SSD simulated
    LinkedHashMap<String, VideoData> L2;

    // L3 Database (all videos)
    HashMap<String, VideoData> database = new HashMap<>();

    // access tracking
    HashMap<String, Integer> accessCount = new HashMap<>();

    int l1Hits = 0;
    int l2Hits = 0;
    int l3Hits = 0;

    public Problem10() {

        L1 = new LinkedHashMap<>(10000, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, VideoData> e) {
                return size() > 10000;
            }
        };

        L2 = new LinkedHashMap<>(100000, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, VideoData> e) {
                return size() > 100000;
            }
        };
    }

    // Simulate database insert
    public void addVideoToDatabase(String id) {
        database.put(id, new VideoData(id, "VideoContent_" + id));
    }

    // Get video
    public VideoData getVideo(String videoId) {

        // L1 lookup
        if (L1.containsKey(videoId)) {
            l1Hits++;
            System.out.println("L1 Cache HIT");
            return L1.get(videoId);
        }

        // L2 lookup
        if (L2.containsKey(videoId)) {

            l2Hits++;
            System.out.println("L2 Cache HIT → Promoted to L1");

            VideoData data = L2.get(videoId);
            L1.put(videoId, data);

            return data;
        }

        // L3 database lookup
        if (database.containsKey(videoId)) {

            l3Hits++;
            System.out.println("L3 Database HIT → Added to L2");

            VideoData data = database.get(videoId);

            L2.put(videoId, data);

            accessCount.put(videoId,
                    accessCount.getOrDefault(videoId, 0) + 1);

            return data;
        }

        System.out.println("Video not found");
        return null;
    }

    // Cache invalidation
    public void invalidate(String videoId) {

        L1.remove(videoId);
        L2.remove(videoId);

        System.out.println("Cache invalidated for " + videoId);
    }

    // Statistics
    public void getStatistics() {

        int total = l1Hits + l2Hits + l3Hits;

        System.out.println("\nCache Statistics");

        if (total == 0) total = 1;

        System.out.println("L1 Hit Rate: " + (l1Hits * 100 / total) + "%");
        System.out.println("L2 Hit Rate: " + (l2Hits * 100 / total) + "%");
        System.out.println("L3 Hit Rate: " + (l3Hits * 100 / total) + "%");
    }

    public static void main(String[] args) {

        Problem10 cache = new Problem10();

        // Load database
        cache.addVideoToDatabase("video_123");
        cache.addVideoToDatabase("video_999");

        System.out.println("First Request:");
        cache.getVideo("video_123");

        System.out.println("\nSecond Request:");
        cache.getVideo("video_123");

        System.out.println("\nAnother Video:");
        cache.getVideo("video_999");

        cache.getStatistics();
    }
}