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
import java.util.Scanner;

public class AccommodationFileManager {
    private static Accomodation[] accomodationArray;
    static int accommodationCount;

    // method to convert accomodation object to csv format
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

    // method to save accomodation array to file
    public static void saveAccomodations(Accomodation[] accomodation, int accommodationCount, String filePath) throws IOException {
       accomodationArray = accomodation;
       AccommodationFileManager.accommodationCount = accommodationCount;

        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath,true));
            for (int i = 0; i<accomodation.length;i++){
                if (accomodationArray[i] != null) {
                    out.println(accomodationToCSV(accomodationArray[i]));
                    accommodationCount++;
                }
            }
            out.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while saving accomodation: " + e.getMessage());
        }

    }

    //method to load accomodation from file
    public static int loadAccomodation(Accomodation[] accomodation, String filePath) throws IOException {
        accomodationArray = accomodation;
        int count = 0;
        if (accomodationArray == null) {
           count = 0; // default size
        } else{
            for (int i = 0; i<accomodationArray.length;i++){
                if (accomodationArray[i]==null){
                    count = i; // checks if the array is empty , if not, appends the accomodation at the end of the array 
                    break;          
                }
            }
        }
        try{
            Scanner reader = new Scanner(new FileReader(filePath));
            
            while(reader.hasNextLine()){
                String line = reader.nextLine(); 
                try {
                    String [] parts = line.split(";");

                    if (parts.length!=6){
                        ErrorLogger.log("Invalid Line for accomodation: " + line);
                        continue;
                    }

                    if (parts[0].equalsIgnoreCase("HOTEL")){
                        accomodationArray[count] = new Hotel(parts[2],parts[3],1,Double.parseDouble(parts[4]),Float.parseFloat(parts[5]));
                        count++;
                    } else if (parts[0].equalsIgnoreCase("HOSTEL")){
                        accomodationArray[count] = new Hostel(parts[2],parts[3],1,Double.parseDouble(parts[4]),Float.parseFloat(parts[5]));
                        count++;
                    }
                    
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line: " + line + " - " + e.getMessage());
                    continue;
                }
            }
            reader.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while loading accomodation: " + e.getMessage());
        }
        return count;
    }
}
