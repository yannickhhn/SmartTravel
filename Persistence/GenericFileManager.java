//----------------------------------------------
//Assignment 3
//Package Persistence
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Persistence;

import Client.client;
import Travel.Accomodation;
import Travel.Transportation;
import Travel.Trip;
import interfaces.CsvPersistable;
import java.io.*;
import java.util.*;

public class GenericFileManager<T extends CsvPersistable> {
    
    
   
    public static <T extends CsvPersistable> List<T> load(String filepath, Class<T> clazz) throws Exception {
        return load(filepath, clazz, null, null, null);
    }
    
    /**
     * Generic load method with support for Trip dependencies
     * @param <T> Type parameter extending CsvPersistable
     * @param filepath Path to the CSV file
     * @param clazz Class type to load
     * @param clients List of clients (only used for Trip loading)
     * @param transportations List of transportations (only used for Trip loading)
     * @param accommodations List of accommodations (only used for Trip loading)
     * @return List of loaded items
     * @throws Exception if loading fails
     */
    @SuppressWarnings("unchecked")
    public static <T extends CsvPersistable> List<T> load(String filepath, Class<T> clazz, 
                                                           List<client> clients, 
                                                           List<Transportation> transportations,
                                                           List<Accomodation> accommodations) throws Exception {
        
        List<T> items = new LinkedList<>();
        
        if (filepath == null || filepath.isBlank()) {
            ErrorLogger.log("File path is null or empty");
            return items;
        }
        
        String className = clazz.getSimpleName();
        
        try {
            Scanner reader = new Scanner(new FileReader(filepath));
            
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                
                try {
                    if (className.equals("client")) {
                        client c = client.fromCsvRow(line);
                        if (c != null) {
                            items.add((T) c);
                        }
                    } 
                    else if (className.equals("Hotel") || className.equals("Hostel") || className.equals("Accomodation")) {
                        Accomodation a = Accomodation.fromCsvRow(line);
                        if (a != null) {
                            items.add((T) a);
                        }
                    } 
                    else if (className.equals("Transportation") || className.equals("Bus") || 
                             className.equals("Flight") || className.equals("Train")) {
                        Transportation t = Transportation.fromCsvRow(line);
                        if (t != null) {
                            items.add((T) t);
                        }
                    }
                    else if (className.equals("Trip")) {
                        // Use provided dependencies for Trip
                        if (clients != null && transportations != null && accommodations != null) {
                            Trip trip = Trip.fromCsvRow(line, clients, transportations, accommodations);
                            if (trip != null) {
                                items.add((T) trip);
                            }
                        } else {
                            ErrorLogger.log("Trip dependencies not provided. Load clients, transportations, and accommodations first.");
                        }
                    }
                    else {
                        ErrorLogger.log("Unknown type: " + className);
                    }
                    
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
            reader.close();
            
        } catch (FileNotFoundException e) {
            ErrorLogger.log("File not found: " + filepath);
        } catch (IOException e) {
            ErrorLogger.log("IO Error: " + e.getMessage());
        }
        
        return items;
    }
    
    public static <T extends CsvPersistable> void save(List<T> items, String filepath) throws Exception {
        
        if (filepath == null || filepath.isBlank()) {
            ErrorLogger.log("File path is null or empty");
            return;
        }
        
        if (items == null || items.isEmpty()) {
            ErrorLogger.log("Items list is null or empty");
            return;
        }
        
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filepath, false));
            
            for (T item : items) {
                if (item != null) {
                    String csvRow = item.toCsvRow();
                    if (csvRow != null && !csvRow.isBlank()) {
                        out.println(csvRow);
                    }
                }
            }
            out.close();
            
        } catch (IOException e) {
            ErrorLogger.log("Error saving to file: " + e.getMessage());
        }
    }
}
