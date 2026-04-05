//----------------------------------------------
//Assignment 1
//Package Persistence
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Persistence;
import Travel.Bus;
import Travel.Flight;
import Travel.Train;
import Travel.Transportation;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class TransportationFileManager{

    // save transportations to file
    public static void saveTransportations(List<Transportation> transportations, int transCount, String filePath) throws IOException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath, true));
            for (Transportation t : transportations) {
                if (t != null) {
                    out.println(t.toCsvRow());
                }
            }
            out.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while saving transportations: " + e.getMessage());
        }
    }

    // load transportations from file into the list
    public static void loadTransportations(List<Transportation> transportations, String filePath) throws IOException {
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                try {
                    transportations.add(Transportation.fromCsvRow(line));
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line: " + e.getMessage());
                }
            }
            reader.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while loading transportations: " + e.getMessage());
        }
    }
}
