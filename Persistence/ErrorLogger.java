package Persistence;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogger {
    
    private static String ErrorLogFilePath = "error.txt";

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
