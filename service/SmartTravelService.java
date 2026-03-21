package service;

import java.io.IOException;

import Client.client;
import Travel.Accomodation;
import Travel.Transportation;
import Travel.Trip;
import exceptions.DuplicateEmailException;
import exceptions.InvalidClientDataException;
import exceptions.InvalidTripDataException;
import exceptions.EntityNotFoundException;
import Persistence.*;


public class SmartTravelService {
    client [] clArr = new client[100];
	Trip [] tripArr = new Trip[200];
	Transportation [] transArr = new Transportation[50];
	Accomodation [] accomArr = new Accomodation[50];

    //getters for arrays 
    public client[] getClient(){
        return clArr;
    }
    public client getClient(int i){
        return clArr[i];
    }
    public Trip[] getTrips(){
        return tripArr;
    }
    public Trip getTrip(int i){
        return tripArr[i];
    }
    public Transportation[] getTransportation(){
        return transArr;
    }
    public Accomodation[] getAccomodation(){
        return accomArr;    
    }
    public int getClientCount(){
        int count = 0;
        for (int i = 0; i < clArr.length; i++) {
            if (clArr[i] != null) {
                count++;
            }
        }
        return count;
    }
    public int getTripCount(){
        int count = 0;
        for (int i = 0; i < tripArr.length; i++) {
            if (tripArr[i] != null) {
                count++;
            }
        }
        return count;
    }
    
    public void addClient(String f, String l, String email) {
        
        boolean created = false;

        for (int i = 0;i<clArr.length;i++){
            if (clArr[i]==null){
                try {
                    clArr[i] = new client(f,l,email,clArr);
                    created =true;
                    System.out.println("New client information: ");
                    System.out.println(clArr[i].toString());
                } catch (InvalidClientDataException | DuplicateEmailException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            }
        }
        if (created){
            System.out.println("Client added successfully.");
        } else if (!created){
            System.out.println("Please try again");
        }
    }


    public void createTrip(String clientID,String destination, double duration, double basePrice, Transportation transportation, Accomodation accomodation) {
        boolean created = false;

        for (int i = 0; i < tripArr.length; i++) {
            if (tripArr[i] == null) {
                try {
                    tripArr[i] = new Trip(destination, duration, basePrice, findClientByID(clientID), transportation, accomodation, clArr);
                    created = true;
                    
                } catch (InvalidTripDataException | EntityNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            }
        }
        for (int i = 0 ; i<transArr.length;i++){
            if (transArr[i] == null && transportation != null){
                transArr[i] = transportation;
                break;
            }
        }
        for (int i = 0 ; i<accomArr.length;i++){
            if (accomArr[i] == null && accomodation != null){
                accomArr[i] = accomodation;
                break;
            }
        }

        if (created) {
            System.out.println("Trip created successfully.");
        } else if (!created) {
            System.out.println("Please try again");
        }
        
    }

    public boolean clientExists(String clientID) throws EntityNotFoundException{

        boolean found = false;
        for (int i = 0; i < clArr.length; i++) {
            if (clArr[i] != null && clArr[i].getClientID().equalsIgnoreCase(clientID)) {
                found = true;
                System.out.println("Client found");
                break;
            }else{
                throw new EntityNotFoundException("Client Not Found");
            }
        }

        return found;
    }

    public client findClientByID(String clientID) throws EntityNotFoundException{
        client temp = null; 


        for (int i = 0; i < clArr.length; i++) {
            if (clArr[i] != null && clArr[i].getClientID().equalsIgnoreCase(clientID)) {
                temp = clArr[i];
            }else{
                temp = null;
                System.out.println("Client not found");
            }
        }
        return temp;
    }

    
    public  void loadAllData() throws IOException{
        try{
            //load clients from file 
            ClientFileManager.loadClients(clArr, "clients.csv");
            //load trips from file
            TripFileManager.loadTrips(tripArr, "trips.csv", clArr, transArr, accomArr);
            //load transportation from file
            TransportationFileManager.loadTransportations(transArr, "transportations.csv");
            //load accomodation from file
            AccommodationFileManager.loadAccomodation(accomArr, "accomodations.csv");
        }catch (Exception e){
            System.out.println("Error loading data: " + e.getMessage());    
        }
    }

    public void saveAllData() throws IOException{

        try{
            // save clients       
            ClientFileManager.saveClients(clArr, 0, "clients.csv");
                    
            // save trips 
            TripFileManager.saveTrips(tripArr, 0, "trips.csv");

            // save transportation
            TransportationFileManager.saveTransportations(transArr, 0, "transportations.csv");

            // save accomodation
            AccommodationFileManager.saveAccomodations(accomArr, 0, "accomodations.csv");

        }catch (Exception e){
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public double calculateTripTotal(int index){
        return tripArr[index].calculateTotalCost();
    }

    public double getTotalSpentOnClient(String clientID) throws EntityNotFoundException {
        double totalSpent = 0;
        boolean clientFound = false;
        
        // Verify client exists
        for (int i = 0; i < clArr.length; i++) {
            if (clArr[i] != null && clArr[i].getClientID().equalsIgnoreCase(clientID)) {
                clientFound = true;
                break;
            }
        }
        
        if (!clientFound) {
            throw new EntityNotFoundException("Client not found");
        }
        
        // Sum costs of all trips for this client
        for (int i = 0; i < tripArr.length; i++) {
            if (tripArr[i] != null && tripArr[i].getClient().getClientID().equalsIgnoreCase(clientID)) {
                totalSpent += tripArr[i].calculateTotalCost();
            }
        }
        
        return totalSpent;
    }

    public void listAllData(){
        for (int i = 0; i<tripArr.length;i++){
            if (tripArr[i] != null){
                System.out.println(TripFileManager.tripToCSV(tripArr[i]));
                
            }
        }
    }

}
