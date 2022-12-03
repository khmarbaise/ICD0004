import api.WeatherApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import api.dto.CurrentWeatherReport;
import api.dto.ForecastReport;
import api.dto.WeatherDTO;
import org.junit.Assert;
import org.junit.Before;;
import org.junit.Test;
import weather.utils.Request;
import weather.utils.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class IntegrationTest {
    String fileWithWrongCity;
    String rightApi;
    String wrongApi;
    String city = "Tallinn";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Date currentDate = new Date();
    String currentDateFormated = sdf.format(currentDate);
    String lastDate;
    JsonObject weatherMainJsonObject = new JsonObject();
    JsonObject foreCastJsonObject = new JsonObject();
    JsonArray foreCastJsonArray = new JsonArray();

    @Before
    public void setUp() {
        fileWithWrongCity = "src/test/java/cities.txt";
        rightApi = "https://api.openweathermap.org/data/2.5/weather?q=";
        wrongApi = "https://api.openweathermap.org/dataa/2.5/wrong!!";
    }
    @Before
    public void setUpLastForecastDate() throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(currentDateFormated));
        c.add(Calendar.DATE, 3);  // number of days to add
        lastDate = sdf.format(c.getTime());
    }
    @Before
    public void setUpWeatherMain() throws IOException, InterruptedException {
        WeatherApi weatherApi = new WeatherApi();
        WeatherDTO weatherDTO  = weatherApi.weatherMainAPI(city);
        weatherMainJsonObject = JsonFormatter.toJsonObject(weatherDTO);
    }
    @Before
    public void setUpForecastReport() throws IOException, InterruptedException {
        WeatherDTO weatherMain = new WeatherDTO();
        WeatherApi weatherApi = new WeatherApi();
        ArrayList<ForecastReport> forecastReportList = weatherApi.forecastAPI(city);
        for(ForecastReport forecastReport1 : forecastReportList){
            weatherMain.setForecastReport(forecastReport1);
        }
        foreCastJsonObject = JsonFormatter.toJsonObject(weatherMain);
        foreCastJsonArray = foreCastJsonObject.getAsJsonArray("forecastReport");
    }
    @Test
    public void checkApi() throws IOException, InterruptedException {
        HttpRequest request = Request.getRequest(rightApi, city);
        HttpResponse<String> response = Response.getResponse(request);
        Assert.assertEquals(200, response.statusCode());
    }
    @Test
    public void checkNotWorkingApi() throws IOException, InterruptedException {
        HttpRequest request = Request.getRequest(wrongApi, city);
        HttpResponse<String> response = Response.getResponse(request);
        Assert.assertEquals(401, response.statusCode());
    }
    @Test
    public void testWeatherMainContainsAllElements(){
        Assert.assertNotEquals(null,weatherMainJsonObject.getAsJsonObject("currentWeatherReport").get("date"));
        Assert.assertNotEquals(null,weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("temperature"));
        Assert.assertNotEquals(null,weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("humidity"));
        Assert.assertNotEquals(null,weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("pressure"));
    }
    @Test
    public void testWeatherMainDate() {
        Assert.assertEquals(currentDateFormated, weatherMainJsonObject.getAsJsonObject("currentWeatherReport").get("date").getAsString());
    }
    @Test
    public void testWeatherMainCity(){
        Assert.assertEquals(city, weatherMainJsonObject.getAsJsonObject("mainDetails").get("city").getAsString());
    }
    @Test
    public void testWeatherMainCoordinates() {
        Assert.assertEquals("24.7535,59.437", weatherMainJsonObject.getAsJsonObject("mainDetails").get("coordinates").getAsString());
    }
    @Test
    public void testRoundElementsMethod() {
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        currentWeatherReport.setTemperature(278.40);
        Assert.assertEquals(5.25,currentWeatherReport.getTemperature(),0);
        currentWeatherReport.roundElements();
        Assert.assertEquals(5,currentWeatherReport.getTemperature(),0);
        currentWeatherReport.setTemperature(290.40);
        currentWeatherReport.roundElements();
        Assert.assertEquals(11,currentWeatherReport.getTemperature(),0);
    }

    @Test
    public void testForecastReportElementsCount(){
        int daysCounter = 0;
        for(JsonElement ignored : foreCastJsonArray){
            daysCounter += 1;
        }
        Assert.assertEquals(3,daysCounter);
    }
    @Test
    public void testForecastReportLastDateCheck(){
        int counter = 0;
        JsonObject lastJsonObject = new JsonObject();
        for(JsonElement element: foreCastJsonArray){
            if(counter == foreCastJsonArray.size() - 1){
                lastJsonObject = element.getAsJsonObject();
            }
            counter += 1;
        }
        Assert.assertEquals(lastDate, lastJsonObject.get("date").getAsString());
    }
    @Test
    public void testForecastContainsAllElements(){
        for(JsonElement element: foreCastJsonArray){
            JsonObject jsonObject = element.getAsJsonObject();
            Assert.assertNotEquals(null,jsonObject.get("date"));
            Assert.assertNotEquals(null,jsonObject.getAsJsonObject("weather").get("humidity"));
            Assert.assertNotEquals(null,jsonObject.getAsJsonObject("weather").get("temperature"));
            Assert.assertNotEquals(null,jsonObject.getAsJsonObject("weather").get("pressure"));
        }
    }
}
