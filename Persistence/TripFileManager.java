package Persistence;
import Client.client;
import Travel.Accomodation;
import Travel.Transportation;
import Travel.Trip;
import exceptions.EntityNotFoundException;
import exceptions.InvalidTripDataException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TripFileManager {
    private static Trip[] trips;

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

    // method to save trip array to file
    public static void saveTrips(Trip[] tripList, int tripCount,String filePath){
        trips= tripList.clone();
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath,true));
            for (int i =0; i<tripList.length;i++){
                if (tripList[i] != null) {
                    out.println(tripToCSV(tripList[i]));
                    tripCount++;
                } 
            }
            out.close();
        } catch  (Exception e) {
            
            ErrorLogger.log("Error occurred while saving trips: " + e.getMessage());
        }
    }

    // Load trips from file
    public static int loadTrips(Trip[] tripList, String filePath, client[] clients,Transportation[] transportations, Accomodation[] accomodations) throws EntityNotFoundException {
        trips = tripList.clone();
        int count = firstEmptyIndex(tripList);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            int lineNumber = 0;

            while (line != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length != 7) {
                    ErrorLogger.log("Invalid trip line format at line " + lineNumber + ": " + line);
                    continue;
                }

                String tripIdFromFile = parts[0];
                String clientId = parts[1];
                String accommodationId = parts[2];
                String transportationId = parts[3];
                String destination = parts[4];
                double duration = Double.parseDouble(parts[5]);
                double basePrice = Double.parseDouble(parts[6]);
    

                if (isMissingId(clientId)) {
                    throw new EntityNotFoundException("ClientID is mandatory (line " + lineNumber + ")");
                }

                boolean hasAccommodation = !isMissingId(accommodationId);
                boolean hasTransportation = !isMissingId(transportationId);

                if (!hasAccommodation && !hasTransportation) {
                    throw new EntityNotFoundException(
                            "At least one of AccommodationID or TransportationID is required (line " + lineNumber + ")"
                    );
                }

                client resolvedClient = findClientById(clients, clientId);
                if (resolvedClient == null) {
                    throw new EntityNotFoundException(
                            "ClientID not found: " + clientId + " (line " + lineNumber + ")"
                    );
                }

                Accomodation resolvedAccommodation = null;
                if (hasAccommodation) {
                    resolvedAccommodation = findAccommodationById(accomodations, accommodationId);
                    if (resolvedAccommodation == null) {
                        throw new EntityNotFoundException(
                                "AccommodationID not found: " + accommodationId + " (line " + lineNumber + ")"
                        );
                    }
                }

                Transportation resolvedTransportation = null;
                if (hasTransportation) {
                    resolvedTransportation = findTransportationById(transportations, transportationId);
                    if (resolvedTransportation == null) {
                        throw new EntityNotFoundException(
                                "TransportationID not found: " + transportationId + " (line " + lineNumber + ")"
                        );
                    }
                }

                if (count >= tripList.length) {
                    ErrorLogger.log("Trip array is full. Remaining file rows cannot be loaded.");
                    break;
                }

                try {
                    tripList[count] = new Trip(
                            destination,
                            duration,
                            basePrice,
                            resolvedClient,
                            resolvedTransportation,
                            resolvedAccommodation,
                            clients
                    );

                    count++;
                } catch (InvalidTripDataException e) {
                    ErrorLogger.log("Invalid trip data at line " + lineNumber + ": " + e.getMessage());
                }
            }
            reader.close();
        } catch (IOException e) {
            ErrorLogger.log("Error occurred while loading trips: " + e.getMessage());
        }

        return count;
    }

    private static int firstEmptyIndex(Trip[] tripList) {
        for (int i = 0; i < tripList.length; i++) {
            if (tripList[i] == null) {
                return i;
            }
        }
        return tripList.length;
    }

    private static client findClientById(client[] clients, String id) {
        if (clients == null || isMissingId(id)) {
            return null;
        }

        for (client c : clients) {
            if (c != null && id.equalsIgnoreCase(c.getClientID())) {
                return c;
            }
        }
        return null;
    }

    private static Transportation findTransportationById(Transportation[] transportations, String id) {
        if (transportations == null || isMissingId(id)) {
            return null;
        }

        for (Transportation t : transportations) {
            if (t != null && id.equalsIgnoreCase(t.getTransportID())) {
                return t;
            }
        }
        return null;
    }

    private static Accomodation findAccommodationById(Accomodation[] accomodations, String id) {
        if (accomodations == null || isMissingId(id)) {
            return null;
        }

        for (Accomodation a : accomodations) {
            if (a != null && id.equalsIgnoreCase(a.getAccomodationID())) {
                return a;
            }
        }
        return null;
    }

    private static boolean isMissingId(String id) {
        if (id == null) {
            return true;
        }else if ((id.length() == 0) ||  (id.equals("null") || (id.equals("-")))) {
            return true;
        }
        return false;
    }
}
