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
import java.util.Scanner;

public class TransportationFileManager {
    private static Transportation[] transportationList;

    // method to transform transportation object to csv format
    public static String transportationToCSV(Transportation t){
        if (t instanceof Flight){
            Flight f = (Flight) t;
            return "FLIGHT;" +f.getTransportID()+ ";" + f.getCompanyName() + ";" + f.getDepCity() + ";" + f.getACity() + ";" + f.getBaseFare()+ ";" + f.getLuggage();
        } else if (t instanceof Train){
           Train tr = (Train) t;
            return "TRAIN;" + tr.getTransportID()+ ";" + tr.getCompanyName() + ";" + tr.getDepCity() + ";" + tr.getACity() + ";" + tr.getBaseFare() + ";" + tr.getTrainType() + ";" + tr.getSeatClass();
    } else if (t instanceof Bus){
            Bus b = (Bus) t;
            return "BUS;" + b.getTransportID()+ ";" + b.getCompanyName() + ";" + b.getDepCity() + ";" + b.getACity() + ";" + b.getBaseFare() + ";" + b.getStopNumber();
        } else {
            return "";
        }
    }

    //save into file bruh kill me 
    public static void saveTransportations(Transportation[] transportations, int TransCount, String filePath) throws IOException {
        transportationList = transportations;

        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath,true));
            for (int i =0; i<transportations.length;i++){
                if (transportationList[i] != null) {
                    out.println(transportationToCSV(transportationList[i]));
                    TransCount++;
                }
            }
            out.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while saving transportations: " + e.getMessage());
        }
    }

    //load from file 
    public static int loadTransportations(Transportation[] transportations, String filePath) throws IOException {
        transportationList = transportations;
        int count = 0;
        if (transportationList == null) {
           count = 0; // default size
        } else{
            for (int i = 0; i<transportationList.length;i++){
                if (transportationList[i]==null){
                    count = i; //check if empty blablabla
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
                    if (parts.length!=7){
                        ErrorLogger.log("Invalid Line for transportation: " + line);
                    } else {
                        String type = parts[0];
                        String transportID = parts[1];
                        String companyName = parts[2];
                        String depCity = parts[3];
                        String aCity = parts[4];
                        double baseFare = Double.parseDouble(parts[5]);

                         if (!transportID.contains("TR3") || transportID.length() < 6) {
                            ErrorLogger.log("Invalid TransportID format  " + line + ": " + transportID);
                            continue;
                        } else{

                            if (type.equalsIgnoreCase("FLIGHT") ) {
                                float luggage = Float.parseFloat(parts[6]);
                                transportationList[count] = new Flight(companyName, depCity, aCity, luggage);
                                transportationList[count].setBaseFare(baseFare);
                                count++;
                            } else if (type.equalsIgnoreCase("TRAIN") ) {
                                String trainType = parts[6];
                                String seatClass = "Economy";
                                transportationList[count] = new Train(companyName, depCity, aCity, trainType, seatClass);
                                transportationList[count].setBaseFare(baseFare);
                                count++;
                            } else if (type.equalsIgnoreCase("BUS")) {
                                int stopNumber = Integer.parseInt(parts[6]);
                                transportationList[count] = new Bus(companyName, depCity, aCity, stopNumber);
                                transportationList[count].setBaseFare(baseFare);
                                count++;
                            } else {
                                ErrorLogger.log("Invalid Transportation type : " + line);
                                continue;
                            }
                        }
                    }
                
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line: " + e.getMessage() + " - " + e.getStackTrace()[0].getFileName());
                    continue;
                }
                
            }
            reader.close();
        }catch (Exception e) {
            ErrorLogger.log("Error occurred while loading transportations: " + e.getMessage());
        }
        return count;
    }
}
