//----------------------------------------------
//Assignment 1
//Package service
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package service;

import Client.client;
import Persistence.*;
import Travel.Accomodation;
import Travel.Transportation;
import Travel.Trip;
import exceptions.DuplicateEmailException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidClientDataException;
import exceptions.InvalidTripDataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SmartTravelService {
    List<client> clArr = new ArrayList<>();
    List<Trip> tripArr = new ArrayList<>();
    List<Transportation> transArr = new ArrayList<>();
    List<Accomodation> accomArr = new ArrayList<>();

    // getters
    public List<client> getClient() {
        return clArr;
    }
    public client getClient(int i) {
        return clArr.get(i);
    }
    public List<Trip> getTrips() {
        return tripArr;
    }
    public Trip getTrip(int i) {
        return tripArr.get(i);
    }
    public List<Transportation> getTransportation() {
        return transArr;
    }
    public List<Accomodation> getAccomodation() {
        return accomArr;
    }
    public int getClientCount() {
        return clArr.size();
    }
    public int getTripCount() {
        return tripArr.size();
    }

    public void addClient(String f, String l, String email) {
        try {
            client c = new client(f, l, email, clArr);
            clArr.add(c);
            System.out.println("New client information: ");
            System.out.println(c.toString());
            System.out.println("Client added successfully.");
        } catch (InvalidClientDataException | DuplicateEmailException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please try again");
        }
    }

    public void deleteClient(client c) {
        clArr.remove(c);
        tripArr.removeIf(trip -> trip.getClient().equals(c));
    }

    public void createTrip(String clientID, String destination, double duration, double basePrice, Transportation transportation, Accomodation accomodation) {
        try {
            Trip trip = new Trip(destination, duration, basePrice, findClientByID(clientID), transportation, accomodation, clArr);
            tripArr.add(trip);
            System.out.println("Trip created successfully.");
        } catch (InvalidTripDataException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please try again");
        }
        if (transportation != null) transArr.add(transportation);
        if (accomodation != null) accomArr.add(accomodation);
    }

    public boolean clientExists(String clientID) throws EntityNotFoundException {
        boolean found = false;
        for (int i = 0; i < clArr.size(); i++) {
            if (clArr.get(i) != null && clArr.get(i).getID().equalsIgnoreCase(clientID)) {
                found = true;
                System.out.println("Client found");
                break;
            } else {
                throw new EntityNotFoundException("Client Not Found");
            }
        }
        return found;
    }

    public client findClientByID(String clientID) throws EntityNotFoundException {
        for (int i = 0; i < clArr.size(); i++) {
            if (clArr.get(i) != null && clArr.get(i).getID().equalsIgnoreCase(clientID)) {
                return clArr.get(i);
            }
        }
        return null;
    }

    public void loadAllData() throws IOException, InvalidTripDataException, InvalidClientDataException, DuplicateEmailException, EntityNotFoundException {
        try {
            clArr.clear();
            clArr.addAll(GenericFileManager.load("A3_249/output/data/clients.csv", client.class));

            accomArr.clear();
            accomArr.addAll(GenericFileManager.load("A3_249/output/data/accommodations.csv", Accomodation.class));

            transArr.clear();
            transArr.addAll(GenericFileManager.load("A3_249/output/data/transports.csv", Transportation.class));

            
            tripArr.addAll(GenericFileManager.load("A3_249/output/data/trips.csv", Trip.class, clArr, transArr, accomArr));

        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage() + " from file " + e.getStackTrace()[0].getFileName());
            ErrorLogger.log("Error loading data: " + e.getMessage());
        }
    }

    public void saveAllData() throws IOException, InvalidTripDataException, InvalidClientDataException, DuplicateEmailException, EntityNotFoundException {
        try {
            GenericFileManager.save(clArr, "A3_249/output/data/clients.csv");
            GenericFileManager.save(transArr, "A3_249/output/data/transports.csv");
            GenericFileManager.save(accomArr, "A3_249/output/data/accommodations.csv");
            GenericFileManager.save(tripArr, "A3_249/output/data/trips.csv");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
            ErrorLogger.log("Error saving data: " + e.getMessage());
        }
    }

    public double calculateTripTotal(int index) {
        return tripArr.get(index).calculateTotalCost();
    }

    public double getTotalSpentOnClient(String clientID) throws EntityNotFoundException {
        double totalSpent = 0;
        boolean clientFound = false;

        for (int i = 0; i < clArr.size(); i++) {
            if (clArr.get(i) != null && clArr.get(i).getID().equalsIgnoreCase(clientID)) {
                clientFound = true;
                break;
            }
        }

        if (!clientFound) {
            throw new EntityNotFoundException("Client not found");
        }

        for (int i = 0; i < tripArr.size(); i++) {
            if (tripArr.get(i) != null && tripArr.get(i).getClient().getID().equalsIgnoreCase(clientID)) {
                totalSpent += tripArr.get(i).calculateTotalCost();
            }
        }

        return totalSpent;
    }

    public String listAllData() {
        String list = "";
        for (int i = 0; i < tripArr.size(); i++) {
            if (tripArr.get(i) != null) {
                list += tripArr.get(i).toCsvRow() + "\n";
            }
        }
        return list;
    }

}
