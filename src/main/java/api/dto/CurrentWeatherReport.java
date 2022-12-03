package api.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentWeatherReport {

    private String date;
    private double temperature;
    private double humidity;
    private double pressure;

    public String getDate() {

        return date;
    }
    public void setDate() {
        //I strongly recommend to use Instant instead of Date.
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.date = formatter.format(date);
    }

    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(Double value) {
        double temp = value - 273.15;
        if(temperature == 0){
            this.temperature = temp;
        }else{
            this.temperature = (temperature + temp) / 2;
        }
    }
    public void roundElements(){
        this.pressure = Math.round(pressure);
        this.temperature = Math.round(temperature);
        this.humidity = Math.round(humidity);
    }

    public double getHumidity() {

        return humidity;
    }
    public void setHumidity(Integer value) {
        if(humidity == 0){
            this.humidity = value;
        }else{
            this.humidity = (humidity + value) / 2;
        }
    }

    public double getPressure() {
        return pressure;
    }
    public void setPressure(Integer value) {
        if(pressure == 0){
            this.pressure = value;
        }else{
            this.pressure = (pressure + value) / 2;
        }
    }
}