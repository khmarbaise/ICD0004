package weather.utils;

import api.WeatherApi;
import logs.LogsAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class Request {

    final static Logger logger = LoggerFactory.getLogger(WeatherApi.class);
    public static HttpRequest getRequest(String weatherApi, String city){
        HttpRequest httpRequest = null;
        try {
            httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(weatherApi + city + "&appid=ef619cd2265c8cb95d9f7934b5a29b36"))
                    .GET().build();
        }catch (URISyntaxException e){
            logger.error(e.getMessage());
            LogsAppender.appendLog(e.getMessage());
        }
        return httpRequest;
    }
}
