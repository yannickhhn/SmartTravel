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

public class TransportationFileManager {

    public static String transportationToCSV(Transportation t){
        if (t instanceof Flight){
            Flight f = (Flight) t;
            return "FLIGHT;" + f.getTransportID() + ";" + f.getCompanyName() + ";" + f.getDepCity() + ";" + f.getACity() + ";" + f.getBaseFare() + ";" + f.getLuggage();
        } else if (t instanceof Train){
            Train tr = (Train) t;
            return "TRAIN;" + tr.getTransportID() + ";" + tr.getCompanyName() + ";" + tr.getDepCity() + ";" + tr.getACity() + ";" + tr.getBaseFare() + ";" + tr.getTrainType() + ";" + tr.getSeatClass();
        } else if (t instanceof Bus){
            Bus b = (Bus) t;
            return "BUS;" + b.getTransportID() + ";" + b.getCompanyName() + ";" + b.getDepCity() + ";" + b.getACity() + ";" + b.getBaseFare() + ";" + b.getStopNumber();
        } else {
            return "";
        }
    }

    // save transportations to file
    public static void saveTransportations(List<Transportation> transportations, int transCount, String filePath) throws IOException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath, true));
            for (Transportation t : transportations) {
                if (t != null) {
                    out.println(transportationToCSV(t));
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
                    String[] parts = line.split(";");
                    if (parts.length != 7) {
                        ErrorLogger.log("Invalid Line for transportation: " + line);
                        continue;
                    }
                    String type = parts[0];
                    String transportID = parts[1];
                    String companyName = parts[2];
                    String depCity = parts[3];
                    String aCity = parts[4];
                    double baseFare = Double.parseDouble(parts[5]);

                    if (!transportID.contains("TR3") || transportID.length() < 6) {
                        ErrorLogger.log("Invalid TransportID format " + line + ": " + transportID);
                        continue;
                    }

                    Transportation t = null;
                    if (type.equalsIgnoreCase("FLIGHT")) {
                        t = new Flight(companyName, depCity, aCity, Float.parseFloat(parts[6]));
                        t.setBaseFare(baseFare);
                    } else if (type.equalsIgnoreCase("TRAIN")) {
                        t = new Train(companyName, depCity, aCity, parts[6], "Economy");
                        t.setBaseFare(baseFare);
                    } else if (type.equalsIgnoreCase("BUS")) {
                        t = new Bus(companyName, depCity, aCity, Integer.parseInt(parts[6]));
                        t.setBaseFare(baseFare);
                    } else {
                        ErrorLogger.log("Invalid Transportation type: " + line);
                        continue;
                    }
                    transportations.add(t);
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
