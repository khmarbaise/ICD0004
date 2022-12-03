package weather.utils;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

public class BufferedReaderFormatter {
    public static BufferedReader returnBufferedReader(String cityName){
        Reader inputString = new StringReader(cityName);
        BufferedReader reader = new BufferedReader(inputString);
        return reader;
    }
}
