package api.dto;

import java.util.ArrayList;

public class WeatherDTO {
    private MainDetails mainDetails;
    private CurrentWeatherReport currentWeatherReport;
    private final ArrayList<ForecastReport> forecastReport;

    public MainDetails getMainDetails() {
        return mainDetails;
    }
    public WeatherDTO(){
        this.forecastReport = new ArrayList<>();
    }
    public void setMainDetails(MainDetails value) {
        this.mainDetails = value;
    }

    public CurrentWeatherReport getCurrentWeatherReport() {
        return currentWeatherReport;
    }
    public void setCurrentWeatherReport(CurrentWeatherReport value) {
        this.currentWeatherReport = value;
    }

    public ArrayList<ForecastReport> getForecastReport() {

        return forecastReport;
    }
    public void setForecastReport(ForecastReport value) {

        forecastReport.add(value);
    }
}
