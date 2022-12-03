package api.dto;

public class ForecastReport {
    private String date;
    private CurrentWeatherReport weather;
    public ForecastReport(){
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public CurrentWeatherReport getWeather() {
        return weather;
    }
    public void setWeather(CurrentWeatherReport value) {

        this.weather = value;
    }
}
