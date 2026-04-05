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

    // CSV format:
    // TripID;ClientID;AccommodationID;TransportationID;Destination;DurationDays;BasePrice
    public static String tripToCSV(Trip trip) {
        String accommodationId = trip.getAccomodation() == null ? "" : trip.getAccomodation().getAccomodationID();
        String transportationId = trip.getTransportation() == null ? "" : trip.getTransportation().getTransportID();

        return trip.getTripID() + ";"
                + trip.getClient().getClientID() + ";"
                + accommodationId + ";"
                + transportationId + ";"
                + trip.getDestination() + ";"
                + trip.getDuration() + ";"
                + trip.getBasePrice();
    }

    // save trips to file
    public static void saveTrips(List<Trip> trips, int tripCount, String filePath) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath, false));
            for (Trip t : trips) {
                if (t != null) {
                    out.println(tripToCSV(t));
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

                String[] parts = line.split(";");
                if (parts.length != 7) {
                    ErrorLogger.log("Invalid line for Trip: " + lineNumber + ": " + line);
                    continue;
                }

                String clientId       = parts[1];
                String accommodationId = parts[2];
                String transportationId = parts[3];
                String destination    = parts[4];
                double duration       = Double.parseDouble(parts[5]);
                double basePrice      = Double.parseDouble(parts[6]);

                if (isMissingId(clientId)) {
                    throw new EntityNotFoundException("ClientID is mandatory (line " + lineNumber + ")");
                }

                boolean hasAccommodation  = !isMissingId(accommodationId);
                boolean hasTransportation = !isMissingId(transportationId);

                if (!hasAccommodation && !hasTransportation) {
                    throw new EntityNotFoundException(
                            "At least one of AccommodationID or TransportationID is required (line " + lineNumber + ")");
                }

                if (!clientId.contains("C1")) {
                    ErrorLogger.log("Invalid ClientID format " + line + ": " + clientId);
                    continue;
                }
                client resolvedClient = findClientById(clients, clientId);
                if (resolvedClient == null) {
                    throw new EntityNotFoundException("ClientID not found: " + clientId + " (line " + lineNumber + ")");
                }

                Accomodation resolvedAccommodation = null;
                if (hasAccommodation) {
                    if (!accommodationId.contains("A40")) {
                        ErrorLogger.log("Invalid AccommodationID format " + line + ": " + accommodationId);
                        continue;
                    }
                    resolvedAccommodation = findAccommodationById(accomodations, accommodationId);
                    if (resolvedAccommodation == null) {
                        throw new EntityNotFoundException(
                                "AccommodationID not found: " + accommodationId + " (line " + lineNumber + ")");
                    }
                }

                Transportation resolvedTransportation = null;
                if (hasTransportation) {
                    if (!transportationId.contains("TR30")) {
                        ErrorLogger.log("Invalid TransportID format " + line + ": " + transportationId);
                        continue;
                    }
                    resolvedTransportation = findTransportationById(transportations, transportationId);
                    if (resolvedTransportation == null) {
                        throw new EntityNotFoundException(
                                "TransportationID not found: " + transportationId + " (line " + lineNumber + ")");
                    }
                }

                try {
                    trips.add(new Trip(destination, duration, basePrice, resolvedClient,
                            resolvedTransportation, resolvedAccommodation, clients));
                } catch (InvalidTripDataException e) {
                    ErrorLogger.log("Invalid trip data at line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            ErrorLogger.log("Error occurred while loading trips: " + e.getMessage());
        }
    }

    private static client findClientById(List<client> clients, String id) {
        if (clients == null || isMissingId(id)) return null;
        for (client c : clients) {
            if (c != null && id.equalsIgnoreCase(c.getClientID())) return c;
        }
        return null;
    }

    private static Transportation findTransportationById(List<Transportation> transportations, String id) {
        if (transportations == null || isMissingId(id)) return null;
        for (Transportation t : transportations) {
            if (t != null && id.equalsIgnoreCase(t.getTransportID())) return t;
        }
        return null;
    }

    private static Accomodation findAccommodationById(List<Accomodation> accomodations, String id) {
        if (accomodations == null || isMissingId(id)) return null;
        for (Accomodation a : accomodations) {
            if (a != null && id.equalsIgnoreCase(a.getAccomodationID())) return a;
        }
        return null;
    }

    private static boolean isMissingId(String id) {
        return id == null || id.length() == 0 || id.equals("null") || id.equals("-");
    }

}
