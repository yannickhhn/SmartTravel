//----------------------------------------------
//Assignment 1
//Package Persistence
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Persistence;
import Client.client;
import Travel.Accomodation;
import Travel.Transportation;
import Travel.Trip;
import exceptions.EntityNotFoundException;
import exceptions.InvalidTripDataException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class TripFileManager {


    // save trips to file
    public static void saveTrips(List<Trip> trips, int tripCount, String filePath) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath, false));
            for (Trip t : trips) {
                if (t != null) {
                    out.println(t.toCsvRow());
                }
            }
            out.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while saving trips: " + e.getMessage());
        }
    }

    // load trips from file into the list
    public static void loadTrips(List<Trip> trips, String filePath, List<client> clients,
                                 List<Transportation> transportations, List<Accomodation> accomodations)
            throws EntityNotFoundException {
                
        try (Scanner reader = new Scanner(new FileReader(filePath))) {
            int lineNumber = 0;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    Trip trip = Trip.fromCsvRow(line, clients, transportations, accomodations);
                    trips.add(trip);
                } catch (InvalidTripDataException e) {
                    ErrorLogger.log("Invalid trip at line " + lineNumber + ": " + e.getMessage());
                } catch (NumberFormatException e) {
                    ErrorLogger.log("Invalid number format at line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            ErrorLogger.log("Error occurred while loading trips: " + e.getMessage());
        }
    }

}
