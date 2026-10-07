package de.devadze.junie.ue2_apiclient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

/** Zeigt, wie sich der Fake-Server ansprechen lässt. Die Client-Tests schreiben Sie in Übung 2. */
class FakeRateServerTest {

    @Test
    void answersKnownPairWithJson() throws Exception {
        try (FakeRateServer server = FakeRateServer.start()) {
            server.addRate("EUR", "USD", "1.0842");

            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder(server.baseUri().resolve("/rates?base=EUR&quote=USD")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode());
            assertEquals("{\"base\":\"EUR\",\"quote\":\"USD\",\"rate\":1.0842,\"date\":\"2026-10-14\"}",
                    response.body());
        }
    }

    @Test
    void answersUnknownPairWith404() throws Exception {
        try (FakeRateServer server = FakeRateServer.start()) {
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder(server.baseUri().resolve("/rates?base=EUR&quote=XYZ")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());

            assertEquals(404, response.statusCode());
        }
    }
}
