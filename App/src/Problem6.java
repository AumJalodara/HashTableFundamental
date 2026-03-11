import java.util.*;

class TokenBucket {

    int tokens;
    int maxTokens;
    long lastRefillTime;

    TokenBucket(int maxTokens) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // refill tokens every hour
    void refill() {

        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTime;

        if (elapsed >= 3600000) { // 1 hour
            tokens = maxTokens;
            lastRefillTime = now;
        }
    }
}

public class Problem6 {

    // clientId -> TokenBucket
    HashMap<String, TokenBucket> clients = new HashMap<>();

    int limit = 1000;

    // Check rate limit
    public synchronized String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(limit));

        TokenBucket bucket = clients.get(clientId);

        bucket.refill();

        if (bucket.tokens >0) {

            bucket.tokens--;

            return "Allowed (" + bucket.tokens + " requests remaining)";
        } else {

            return "Denied (0 requests remaining, retry later)";
        }
    }

    // Show client status
    public void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        int used = bucket.maxTokens - bucket.tokens;

        System.out.println(
                "{used: " + used +
                        ", limit: " + bucket.maxTokens +
                        ", remaining: " + bucket.tokens + "}"
        );
    }

    public static void main(String[] args) {

        Problem6 limiter = new Problem6();

        System.out.println(limiter.checkRateLimit("abc123"));
        System.out.println(limiter.checkRateLimit("abc123"));
        System.out.println(limiter.checkRateLimit("abc123"));

        limiter.getRateLimitStatus("abc123");
    }
}