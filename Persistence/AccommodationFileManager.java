package Persistence;
import Travel.Accomodation;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import Travel.Hostel;
import Travel.Hotel;
import java.io.BufferedReader;
import java.io.FileReader;
import Persistence.ErrorLogger;
import Travel.Accomodation;

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
       accomodationArray = accomodation.clone();
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
        accomodationArray = accomodation.clone();
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
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine(); 
            while(line!=null){
                try {
                    String [] parts = line.split(";");

                    if (parts.length!=6){
                        ErrorLogger.log("Invalid Line");
                    }

                    if (parts[0].equalsIgnoreCase("HOTEL")){
                        accomodationArray[count] = new Hotel(parts[2],parts[3],Integer.parseInt(parts[4]),Double.parseDouble(parts[5]),Float.parseFloat(parts[5]));
                        count++;
                    } else if (parts[0].equalsIgnoreCase("HOSTEL")){
                        accomodationArray[count] = new Hostel(parts[2],parts[3],Integer.parseInt(parts[4]),Double.parseDouble(parts[5]),Float.parseFloat(parts[5]));
                        count++;
                    }
                    
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
            reader.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while loading accomodation: " + e.getMessage());
        }
        return count;
    }
}
