package logs;

import api.WeatherApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

//Why do you write your own Logs append and not using the existing one!!!
public class LogsAppender {
    static List<String> newList = new ArrayList<>();
    final static Logger logger = LoggerFactory.getLogger(WeatherApi.class);
    public static String logMessage;


    public static void appendLog(String log){
        newList.add(log);
    }
    public static List<String> returnLogList(){
        return newList;
    }
    public static void invalidCityLog(String city){
        logMessage = city + " is invalid city name, file not created!\n";
        logger.error(logMessage);
        LogsAppender.appendLog(logMessage);
    }
    public static void overWriteFileLog(String city){
        logMessage = "File for " + city + " is being overwritten.";
        logger.info(logMessage);
        LogsAppender.appendLog(logMessage);
    }
    public static void ioExceptionLog(Exception error){
        logger.error(error.getMessage());
        LogsAppender.appendLog(error.getMessage());
    }
    public static void successfullyCreatedFileLog(String fileName){
        logMessage = "File " + fileName + " was successfully created!\n";
        logger.info(logMessage);
        LogsAppender.appendLog(logMessage);
    }
}
