package main;
import exceptions.FileNotFoundException;
import exceptions.InvalidFileFormatException;
import weather.*;
import weather.utils.BufferedReaderFormatter;

import java.io.*;
import java.util.Scanner;


public class WeatherMain {
    public static void main(String[] args) throws InvalidFileFormatException, FileNotFoundException, IOException {
        Scanner sc= new Scanner(System.in);
        System.out.print("To insert city name - 1, to insert path to file with cities - 2: \n");
        String answer = sc.nextLine();
        if (answer.equals("1")){
            System.out.print("Enter a city name:\n");
            BufferedReader cityName = BufferedReaderFormatter.returnBufferedReader(sc.nextLine());
            WeatherHandler.readFile(cityName);

        }else if (answer.equals("2")){
            System.out.print("Enter a full path to file name with city names:\n");
            WeatherHandler.openFile(sc.nextLine());
        }else{
            System.out.println("Error, try to put the number between 1 and 2");
        }
    }
}
