package first;

import api.WeatherApi;
import api.dto.CurrentWeatherReport;
import api.dto.ForecastReport;
import api.dto.WeatherDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weather.utils.JsonFormatter;
import weather.utils.Request;
import weather.utils.Response;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

//Follow Naming convention: *IT.java is an integration test which is executed by maven-failsafe-plugin
class ThisIsAnIT {
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

    //QUESTION: Why do you have so many before parts here?
    @BeforeAll
    public void setUp() {
        fileWithWrongCity = "src/test/java/cities.txt";
        rightApi = "https://api.openweathermap.org/data/2.5/weather?q=";
        wrongApi = "https://api.openweathermap.org/dataa/2.5/wrong!!";
    }
    @BeforeEach
    public void setUpLastForecastDate() throws ParseException {
        //QUESTION: Why using old Calendar instead of Instant ?? (since JDK8+)
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(currentDateFormated));
        c.add(Calendar.DATE, 3);  // number of days to add
        lastDate = sdf.format(c.getTime());
    }
    @BeforeEach
    public void setUpWeatherMain() throws IOException, InterruptedException {
        WeatherApi weatherApi = new WeatherApi();
        WeatherDTO weatherDTO  = weatherApi.weatherMainAPI(city);
        weatherMainJsonObject = JsonFormatter.toJsonObject(weatherDTO);
    }
    @BeforeEach
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
    void checkApi() throws IOException, InterruptedException {
        var request = Request.getRequest(rightApi, city);
        var response = Response.getResponse(request);
        assertThat(response.statusCode()).isEqualTo(200);
    }
    @Test
    void checkNotWorkingApi() throws IOException, InterruptedException {
        HttpRequest request = Request.getRequest(wrongApi, city);
        HttpResponse<String> response = Response.getResponse(request);
        assertThat(response.statusCode()).isEqualTo(401);
    }
    @Test
    void testWeatherMainContainsAllElements(){
        assertThat(weatherMainJsonObject.getAsJsonObject("currentWeatherReport").get("date")).isNull();
        assertThat(weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("temperature")).isTrue();
        assertThat(weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("humidity")).isTrue();
        assertThat(weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("humidity")).isTrue();
    }
    @Test
    void testWeatherMainDate() {
        assertThat(weatherMainJsonObject.getAsJsonObject("currentWeatherReport").get("date").getAsString()).isEqualTo(currentDateFormated);
    }
    @Test
    void testWeatherMainCity(){
        assertThat(weatherMainJsonObject.getAsJsonObject("mainDetails").get("city").getAsString()).isEqualTo(city);
    }
    @Test
    void testWeatherMainCoordinates() {
        assertThat(weatherMainJsonObject.getAsJsonObject("mainDetails").get("coordinates").getAsString()).isEqualTo("24.7535,59.437");
    }
    @Test
    void testRoundElementsMethod() {
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        currentWeatherReport.setTemperature(278.40);
        //Never compare floating point with 0 without offset (epsilon)
        assertThat(currentWeatherReport.getTemperature()).isEqualTo(5.25d, Offset.offset(10E-12));
        currentWeatherReport.roundElements();
        assertThat(currentWeatherReport.getTemperature()).isEqualTo(5.0d, Offset.offset(10E-12));
        currentWeatherReport.setTemperature(290.40);
        currentWeatherReport.roundElements();
        assertThat(currentWeatherReport.getTemperature()).isEqualTo(11.0d, Offset.offset(10E-12));
    }

    @Test
    void testForecastReportElementsCount(){
        int daysCounter = 0;
        for(JsonElement ignored : foreCastJsonArray){
            daysCounter += 1;
        }
        assertThat(daysCounter).isEqualTo(3);
    }
    @Test
    void testForecastReportLastDateCheck(){
        int counter = 0;
        JsonObject lastJsonObject = new JsonObject();
        for(JsonElement element: foreCastJsonArray){
            if(counter == foreCastJsonArray.size() - 1){
                lastJsonObject = element.getAsJsonObject();
            }
            counter += 1;
        }
        assertThat(lastJsonObject.get("date").getAsString()).isEqualTo(lastDate);
    }
    @Test
    void testForecastContainsAllElements(){
        for(JsonElement element: foreCastJsonArray){
            JsonObject jsonObject = element.getAsJsonObject();
            assertThat(jsonObject.get("date")).isNull();
            assertThat(jsonObject.getAsJsonObject("weather").get("humidity")).isNull();
            assertThat(jsonObject.getAsJsonObject("weather").get("temperature")).isNull();
            assertThat(jsonObject.getAsJsonObject("weather").get("pressure")).isNull();
        }
    }
}
