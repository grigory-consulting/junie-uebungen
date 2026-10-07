package de.devadze.junie.ue4_legacy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Begrenzt Anfragen pro Nutzer: höchstens maxRequests Anfragen innerhalb von windowMillis.
 * Gleitendes Fenster, Zeitstempel in Millisekunden.
 *
 * Seit 2019 im Einsatz im Gateway, Tests gibt es keine.
 */
public class RateLimiter {

    private final int maxRequests;
    private final long windowMillis;
    private final Map<String, List<Long>> history = new HashMap<>();

    public RateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    public boolean allow(String userId) {
        return allow(userId, System.currentTimeMillis());
    }

    // nowMillis als Parameter, damit man es testen kann (wurde nie gemacht)
    public boolean allow(String userId, long nowMillis) {
        List<Long> h = history.computeIfAbsent(userId, k -> new ArrayList<>());
        long cutoff = nowMillis - windowMillis;

        // alte Einträge wegwerfen
        while (!h.isEmpty() && h.get(0) < cutoff) {
            h.remove(0);
        }

        if (h.size() > maxRequests) {
            return false;
        }

        h.add(nowMillis);
        return true;
    }

    public int remaining(String userId, long nowMillis) {
        List<Long> h = history.getOrDefault(userId, List.of());
        long cutoff = nowMillis - windowMillis;
        int active = 0;
        for (long t : h) {
            if (t > cutoff) {
                active++;
            }
        }
        return maxRequests - active;
    }
}
