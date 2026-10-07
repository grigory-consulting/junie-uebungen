package de.devadze.junie.ue2_apiclient;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lokale Fake-API für Wechselkurse, nur für Tests. Läuft auf 127.0.0.1 mit freiem Port.
 *
 * <p>Endpunkt {@code GET /rates?base=EUR&quote=USD}:
 * <ul>
 *   <li>bekanntes Paar: Status 200, Body
 *       {@code {"base":"EUR","quote":"USD","rate":1.0842,"date":"2026-10-14"}}</li>
 *   <li>unbekanntes Paar: Status 404, Body {@code {"error":"unknown currency pair"}}</li>
 *   <li>fehlender Parameter: Status 400, Body {@code {"error":"missing parameter"}}</li>
 * </ul>
 *
 * <p>Für Fehlerfälle lässt sich eine feste Antwort ({@link #respondWith}) und eine
 * Verzögerung ({@link #delay}) setzen.
 *
 * <pre>{@code
 * try (FakeRateServer server = FakeRateServer.start()) {
 *     server.addRate("EUR", "USD", "1.0842");
 *     var client = new HttpExchangeRateClient(server.baseUri(), Duration.ofSeconds(2));
 *     ExchangeRate rate = client.fetchRate("EUR", "USD");
 * }
 * }</pre>
 */
public final class FakeRateServer implements AutoCloseable {

    /** Stichtag, den der Fake-Server für alle Kurse meldet. */
    public static final String DATE = "2026-10-14";

    private final HttpServer server;
    private final ExecutorService executor;
    private final Map<String, String> rates = new ConcurrentHashMap<>();
    private final AtomicInteger requestCount = new AtomicInteger();
    private volatile Integer fixedStatus;
    private volatile String fixedBody;
    private volatile Duration delay = Duration.ZERO;

    private FakeRateServer(HttpServer server, ExecutorService executor) {
        this.server = server;
        this.executor = executor;
    }

    /** Startet den Server auf einem freien Port. */
    public static FakeRateServer start() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
        ExecutorService executor = Executors.newCachedThreadPool(runnable -> {
            Thread thread = new Thread(runnable, "fake-rate-server");
            thread.setDaemon(true);
            return thread;
        });
        FakeRateServer fake = new FakeRateServer(httpServer, executor);
        httpServer.createContext("/rates", fake::handle);
        httpServer.setExecutor(executor);
        httpServer.start();
        return fake;
    }

    /** Basis-URI ohne abschließenden Schrägstrich, z. B. {@code http://127.0.0.1:54321}. */
    public URI baseUri() {
        return URI.create("http://127.0.0.1:" + server.getAddress().getPort());
    }

    /** Legt einen Kurs an, z. B. {@code addRate("EUR", "USD", "1.0842")}. */
    public void addRate(String base, String quote, String rate) {
        rates.put(base + "/" + quote, rate);
    }

    /** Beantwortet ab jetzt jede Anfrage mit genau diesem Status und Body. */
    public void respondWith(int status, String body) {
        this.fixedStatus = status;
        this.fixedBody = body;
    }

    /** Wartet vor jeder Antwort die angegebene Zeit (für Timeout-Tests). */
    public void delay(Duration delay) {
        this.delay = delay;
    }

    /** Anzahl der bisher eingegangenen Anfragen. */
    public int requestCount() {
        return requestCount.get();
    }

    @Override
    public void close() {
        server.stop(0);
        executor.shutdownNow();
    }

    private void handle(HttpExchange exchange) throws IOException {
        requestCount.incrementAndGet();
        try {
            if (!delay.isZero()) {
                Thread.sleep(delay.toMillis());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            exchange.close();
            return;
        }
        if (fixedStatus != null) {
            send(exchange, fixedStatus, fixedBody);
            return;
        }
        if (!"GET".equals(exchange.getRequestMethod())) {
            send(exchange, 405, "{\"error\":\"method not allowed\"}");
            return;
        }
        Map<String, String> query = parseQuery(exchange.getRequestURI().getRawQuery());
        String base = query.get("base");
        String quote = query.get("quote");
        if (base == null || quote == null) {
            send(exchange, 400, "{\"error\":\"missing parameter\"}");
            return;
        }
        String rate = rates.get(base + "/" + quote);
        if (rate == null) {
            send(exchange, 404, "{\"error\":\"unknown currency pair\"}");
            return;
        }
        String body = "{\"base\":\"" + base + "\",\"quote\":\"" + quote + "\",\"rate\":" + rate
                + ",\"date\":\"" + DATE + "\"}";
        send(exchange, 200, body);
    }

    private static Map<String, String> parseQuery(String rawQuery) {
        Map<String, String> result = new HashMap<>();
        if (rawQuery == null || rawQuery.isEmpty()) {
            return result;
        }
        for (String pair : rawQuery.split("&")) {
            int eq = pair.indexOf('=');
            if (eq > 0) {
                String key = URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8);
                String value = URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8);
                result.put(key, value);
            }
        }
        return result;
    }

    private static void send(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length == 0 ? -1 : bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }
}
