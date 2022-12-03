package api;
import api.dto.CurrentWeatherReport;
import api.dto.ForecastReport;
import api.dto.MainDetails;
import api.dto.WeatherDTO;
import com.google.gson.*;
import java.io.*;
import weather.utils.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;


public class WeatherApi {
    final static String mainApi = "https://api.openweathermap.org/data/2.5/weather?q=";
    final static String forecastApi = "https://api.openweathermap.org/data/2.5/forecast?q=";

    public WeatherDTO weatherMainAPI(String city) throws IOException, InterruptedException {
        WeatherDTO weather = new WeatherDTO();
        JsonObject jsonObj = JsonFormatter.toJsonObject(mainApi,city);
        MainDetails mainDetails = new MainDetails();
        mainDetails.setCity(jsonObj.get("name").getAsString());
        mainDetails.setCoordinates(jsonObj.getAsJsonObject("coord").get("lon").getAsString() + "," + jsonObj.getAsJsonObject("coord").get("lat").getAsString());
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        setElements(currentWeatherReport,jsonObj);
        currentWeatherReport.roundElements();
        currentWeatherReport.setDate();
        weather.setMainDetails(mainDetails);
        weather.setCurrentWeatherReport(currentWeatherReport);
        return weather;
    }

    public ArrayList<ForecastReport> forecastAPI(String city) throws IOException, InterruptedException {
        JsonObject jsonObj = JsonFormatter.toJsonObject(forecastApi,city);
        JsonArray jsonArray = jsonObj.getAsJsonArray("list");
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        WeatherDTO weatherDTO = new WeatherDTO();
        ForecastReport forecastReport = new ForecastReport();
        List<JsonElement> filteredJsonArray = StreamSupport.stream(jsonArray.spliterator(), false)
                .filter(e -> !Objects.equals(DateFormatter.formatDate(e.getAsJsonObject().get("dt_txt").getAsString().split(" ")[0]),
                        DateFormatter.returnCurrentDate())).toList();
        int elementsCounter = 1;
        for (JsonElement element: filteredJsonArray) {
            if(elementsCounter == 3){
                break;
            }
            String elementDate = DateFormatter.formatDate(element.getAsJsonObject().get("dt_txt").getAsString().split(" ")[0]);
            if (Objects.equals(elementDate, forecastReport.getDate())) {
                setElements(currentWeatherReport, element.getAsJsonObject());
            } else if (forecastReport.getDate() == null) {
                setElements(currentWeatherReport, element.getAsJsonObject());
                forecastReport.setDate(elementDate);
            } else {
                currentWeatherReport.roundElements();
                forecastReport.setWeather(currentWeatherReport);
                weatherDTO.setForecastReport(forecastReport);
                currentWeatherReport = new CurrentWeatherReport();
                forecastReport = new ForecastReport();
                setElements(currentWeatherReport, element.getAsJsonObject());
                forecastReport.setDate(elementDate);
                elementsCounter += 1;
            }

        }
        currentWeatherReport.roundElements();
        forecastReport.setWeather(currentWeatherReport);
        weatherDTO.setForecastReport(forecastReport);

        return weatherDTO.getForecastReport();
    }
    public static void setElements(CurrentWeatherReport currentWeatherReportObject, JsonObject jsonObject){
        currentWeatherReportObject.setTemperature(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
        currentWeatherReportObject.setHumidity(jsonObject.getAsJsonObject("main").get("humidity").getAsInt());
        currentWeatherReportObject.setPressure(jsonObject.getAsJsonObject("main").get("pressure").getAsInt());
    }
}
