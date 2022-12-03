package weather.utils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Response {
    public static HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
