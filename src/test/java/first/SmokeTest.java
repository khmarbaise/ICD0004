package first;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;


class SmokeTest {

    static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    static final String FORECAST_API = "https://api.openweathermap.org/data/2.5/forecast?q=";

    static final String API_WEATHER_LAT_LON = "https://api.openweathermap.org/data/2.5/weather?";
    static final String API_FORECAST_LAT_LON = "https://api.openweathermap.org/data/2.5/forecast?";

    static final String CITY  = "Tallinn";
    static final String KEY_API = "&appid=ef619cd2265c8cb95d9f7934b5a29b36";

    static final String LAT = "lat=59.436962";
    static final String LON = "lon=24.753574";

    @Test
    void getWeatherWithLATLON () {
        given()
                .contentType(JSON.toString())
                .when()
                .get(API_WEATHER_LAT_LON + LAT + "&" + LON + KEY_API)
                .then()
                .statusCode(200);
    }

    @Test
    void getWeatherForecastWithLATLON () {
        given()
                .contentType(JSON.toString())
                .when()
                .get(API_FORECAST_LAT_LON + LAT + "&" + LON + KEY_API)
                .then()
                .statusCode(200);
    }

    @Test
    void getWeatherApiWithoutKey() {
        when()
                .get(API_URL)
                .then()
                .statusCode(401);
    }

    @Test
    void getWeatherHttp() {
        given()
                .contentType(JSON.toString())
                .when()
                .get(API_URL + CITY + KEY_API)
                .then()
                .statusCode(200);
    }

    @Test
    void getWeatherForecastHttp() {
        when()
                .get(FORECAST_API)
                .then()
                .statusCode(401);
    }

    @Test
    void getWeatherForecastApi() {
        given()
                .contentType(JSON.toString())
                .when()
                .get(FORECAST_API + CITY + KEY_API)
                .then()
                .statusCode(200);
    }
}
