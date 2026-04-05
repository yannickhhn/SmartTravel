//----------------------------------------------
//Assignment 1
//Package Persistence
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Persistence;
import Travel.Accomodation;
import Travel.Hostel;
import Travel.Hotel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class AccommodationFileManager {

    public static String accomodationToCSV(Accomodation a){
        if (a instanceof Hotel){
            Hotel h = (Hotel) a;
            return "HOTEL;" + h.getAccomodationID() + ";" + h.getAccomodationName() + ";" + h.getLocation() + ";" + h.getPrice() + ";" + h.getNumberofNights() + ";" + h.getRating();
        } else if (a instanceof Hostel){
            Hostel h = (Hostel) a;
            return "HOSTEL;" + h.getAccomodationID() + ";" + h.getAccomodationName() + ";" + h.getLocation() + ";" + h.getPrice() + ";" + h.getNumberofNights() + ";" + h.getBed();
        } else {
            return "";
        }
    }

    // save accommodations to file
    public static void saveAccomodations(List<Accomodation> accommodations, int accommodationCount, String filePath) throws IOException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath, true));
            for (Accomodation a : accommodations) {
                if (a != null) {
                    out.println(accomodationToCSV(a));
                }
            }
            out.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while saving accomodation: " + e.getMessage());
        }
    }

    // load accommodations from file into the list
    public static void loadAccomodation(List<Accomodation> accommodations, String filePath) throws IOException {
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 6) {
                        ErrorLogger.log("Invalid Line for accomodation: " + line);
                        continue;
                    }
                    if (parts[0].equalsIgnoreCase("HOTEL")) {
                        accommodations.add(new Hotel(parts[2], parts[3], 1, Double.parseDouble(parts[4]), Float.parseFloat(parts[5])));
                    } else if (parts[0].equalsIgnoreCase("HOSTEL")) {
                        accommodations.add(new Hostel(parts[2], parts[3], 1, Double.parseDouble(parts[4]), Float.parseFloat(parts[5])));
                    }
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
            reader.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while loading accomodation: " + e.getMessage());
        }
    }
}
