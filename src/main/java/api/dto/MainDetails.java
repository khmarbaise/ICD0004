package api.dto;

public class MainDetails {
    String city;
    String coordinates;
    String temperatureUnit;

    public MainDetails() {
        this.city = "";
        this.coordinates = "";
        this.temperatureUnit = "Celcius";
    }
    public void setCity(String value) {
        city = value;
    }
    public void setCoordinates(String value) {
        coordinates = value;
    }
}