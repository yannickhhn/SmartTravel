package Persistence;
import Travel.Transportation;
import java.io.IOException;
import Travel.Flight;
import Travel.Train;
import Travel.Bus;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import Persistence.ErrorLogger;

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
        transportationList = transportations.clone();

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
        transportationList = transportations.clone();
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
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine(); 
            while(line!=null){
                try {
                    String [] parts = line.split(";");
                    if (parts.length!=7 && parts.length!=8){
                        ErrorLogger.log("Invalid Line");
                    } else {
                        String type = parts[0];
                        String companyName = parts[2];
                        String depCity = parts[3];
                        String aCity = parts[4];
                        double baseFare = Double.parseDouble(parts[5]);

                        if (type.equalsIgnoreCase("FLIGHT") && parts.length == 7) {
                            float luggage = Float.parseFloat(parts[6]);
                            transportationList[count] = new Flight(companyName, depCity, aCity, luggage);
                            transportationList[count].setBaseFare(baseFare);
                            count++;
                        } else if (type.equalsIgnoreCase("TRAIN") && parts.length == 8) {
                            String trainType = parts[6];
                            String seatClass = parts[7];
                            transportationList[count] = new Train(companyName, depCity, aCity, trainType, seatClass);
                            transportationList[count].setBaseFare(baseFare);
                            count++;
                        } else if (type.equalsIgnoreCase("BUS") && parts.length == 7) {
                            int stopNumber = Integer.parseInt(parts[6]);
                            transportationList[count] = new Bus(companyName, depCity, aCity, stopNumber);
                            transportationList[count].setBaseFare(baseFare);
                            count++;
                        } else {
                            ErrorLogger.log("Invalid Line");
                        }
                    }
                
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line: " + e.getMessage());
                }
                
            }
            reader.close();
        }catch (Exception e) {
            ErrorLogger.log("Error occurred while loading transportations: " + e.getMessage());
        }
        return count;
    }
}
