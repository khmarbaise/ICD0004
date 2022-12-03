package weather.utils;

import api.dto.WeatherDTO;
import com.google.gson.*;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonFormatter {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static JsonObject toJsonObject(String api, String city) throws IOException, InterruptedException {
        HttpRequest getRequestForecast = Request.getRequest(api,city);
        HttpResponse<String> getResponse = Response.getResponse(getRequestForecast);
        JsonElement jsonElement = JsonParser.parseString(getResponse.body());
        String jsonFormat = gson.toJson(jsonElement);
        return new Gson().fromJson(jsonFormat, JsonObject.class);
    }
    public static JsonObject toJsonObject(WeatherDTO weather){
        String jsonFormat = gson.toJson(weather);
        return new Gson().fromJson(jsonFormat, JsonObject.class);
    }
    public static String toJsonString(WeatherDTO weather){
        return gson.toJson(weather);
    }
}
