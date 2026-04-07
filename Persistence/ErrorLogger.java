//----------------------------------------------
//Assignment 1 
//Package Persistence 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Persistence;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogger {
    
    private static String ErrorLogFilePath = "output/logs/errors.txt";

    public static void log(String errorMessage){
        try {
            
            PrintWriter out = new PrintWriter(new FileWriter(ErrorLogFilePath, true));
            out.println(errorMessage);
            out.close();
        } catch (IOException e) {
            System.out.println("Error logging failed: " + e.getMessage());
        }
    }
}
