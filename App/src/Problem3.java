import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long expiryTime;

    DNSEntry(String domain, String ipAddress, int ttlSeconds) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class Problem3 {

    // LRU Cache using LinkedHashMap
    LinkedHashMap<String, DNSEntry> cache;

    int capacity;
    int hits = 0;
    int misses = 0;

    public Problem3(int capacity) {

        this.capacity = capacity;

        cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > capacity;
            }
        };
    }

    // Simulate upstream DNS lookup
    private String queryUpstreamDNS(String domain) {

        // Fake IP generator
        return "172.217." + new Random().nextInt(255) + "." + new Random().nextInt(255);
    }

    // Resolve domain
    public String resolve(String domain) {

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                return "Cache HIT → " + entry.ipAddress;
            } else {
                cache.remove(domain);
            }
        }

        misses++;

        // Cache miss → query upstream
        String ip = queryUpstreamDNS(domain);

        DNSEntry newEntry = new DNSEntry(domain, ip, 5); // TTL = 5 seconds
        cache.put(domain, newEntry);

        return "Cache MISS → Query upstream → " + ip;
    }

    // Cache statistics
    public void getCacheStats() {

        int total = hits + misses;

        double hitRate = (total == 0) ? 0 : ((double) hits / total) * 100;

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        Problem3 dns = new Problem3(3);

        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));

        Thread.sleep(6000); // Wait for TTL expiry

        System.out.println(dns.resolve("google.com"));

        dns.getCacheStats();
    }
}