import exceptions.FileNotFoundException;
import exceptions.InvalidFileFormatException;
import logs.LogsAppender;
import api.WeatherApi;
import api.dto.CurrentWeatherReport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import weather.WeatherHandler;
import weather.utils.DateFormatter;

import java.io.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitTest {
    String wrongFile;
    String wrongFileType;
    String fileWithWrongCity;
    String stringToLookFor;
    LogsAppender logsAppender;
    String invalidCityName;
    String filePath;
    String date;
    WeatherApi weatherApiObject;

    @Before
    public void setUp() {
        invalidCityName = "Tallinnaaan";
        weatherApiObject = new WeatherApi();
        logsAppender = new LogsAppender();
        wrongFile = "file.txt";
        wrongFileType = "file.csv";
        fileWithWrongCity = "src/test/java/cities.txt";
        stringToLookFor = "Lisbon";
        filePath = "StaticForecast.json";
        date = "1997-08-25";
    }

    @Rule
    public SystemOutRule systemOutRule1 = new SystemOutRule().enableLog();

    @Test
    public void testWrongFileFormat(){
        Exception exception = assertThrows(InvalidFileFormatException.class, () -> {
            WeatherHandler.openFile(wrongFileType);
        });
        Assert.assertEquals("Invalid file format!",exception.getMessage());
    }

    @Test
    public void testWrongFileName(){
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            WeatherHandler.openFile(wrongFile);
        });
        Assert.assertEquals("File not found!", exception.getMessage());
    }

    @Test
    public void testWrongCityName() throws IOException {
            String errorMessage = invalidCityName + " is invalid city name, file not created!\n";
            BufferedReader r = new BufferedReader(new StringReader(invalidCityName));
            WeatherHandler.readFile(r);
            Assert.assertTrue(LogsAppender.returnLogList().size() > 0);
            Assert.assertEquals(errorMessage,LogsAppender.returnLogList().get(LogsAppender.returnLogList().size() - 1));
    }
    @Test
    public void testDateFormatter(){
        Assert.assertEquals("25-08-1997", DateFormatter.formatDate(date));
    }
    @Test
    public void testCurrentWeatherReportTemp() {
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        currentWeatherReport.setTemperature(277.15);
        Assert.assertEquals(4, currentWeatherReport.getTemperature(), 0);
        currentWeatherReport.setTemperature(279.15);
        Assert.assertEquals(5, currentWeatherReport.getTemperature(), 0);
    }
    @Test
    public void testCurrentWeatherReportHumidity() {
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        currentWeatherReport.setHumidity(60);
        Assert.assertEquals(60, currentWeatherReport.getHumidity(), 0);
        currentWeatherReport.setHumidity(90);
        Assert.assertEquals(75, currentWeatherReport.getHumidity(), 0);
    }
    @Test
    public void testCurrentWeatherReportPreassure() {
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        currentWeatherReport.setPressure(1000);
        Assert.assertEquals(1000, currentWeatherReport.getPressure(), 0);
        currentWeatherReport.setPressure(1200);
        Assert.assertEquals(1100, currentWeatherReport.getPressure(), 0);
    }
}