//----------------------------------------------
//Assignment 1 
//Package Travel 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Travel;

import Client.client;
import exceptions.InvalidTripDataException;
import interfaces.Billable;
import interfaces.Identifiable;
import java.util.List;
import java.util.LinkedList;
import interfaces.CsvPersistable;


public class Trip implements Identifiable, Billable, CsvPersistable, Comparable<Trip> {
	private static int numId = 2000;
	private final String tripID;
	private String destination;
	private double duration;
	private double basePrice;
	private client client;
	private Transportation transportation;
	private Accomodation accomodation;
	static List<client> clientArray;



	// parameterized constructor
	public Trip(String destination, double duration, double basePrice, client client, Transportation transportation, Accomodation accomodation, List<client> clientArray) throws InvalidTripDataException {

		numId++;
		this.tripID = "T"+numId;
		Trip.clientArray = new LinkedList<>(clientArray);
		boolean clientFound = false;

		for (client c : clientArray){
			if (c != null && c.getID().equalsIgnoreCase(client.getID())){
				clientFound = true;
				break;
			}
		}

		if (duration<1 | duration>20)	{
			throw new InvalidTripDataException("Duration should be between 1 and 20 days");
		}	
		if(basePrice<100){
			throw new InvalidTripDataException("Base price should be at least $100");
		}

		if (client == null | !clientFound) {
			throw new InvalidTripDataException("Client not found");
		}

		this.duration = duration;
		this.basePrice = basePrice;
		this.client = client;
		this.destination = destination;
		this.transportation = transportation;
		this.accomodation = accomodation;
	}

	//default constructor 
	public Trip()throws InvalidTripDataException {
		this(null,0,0,null,null,null,null);
	}

	//copy constructor 
	public Trip(Trip t) throws InvalidTripDataException {
		
		this(t.destination,t.duration,t.basePrice,t.client,t.transportation,t.accomodation,clientArray);
		
	}

	// getter and setter
	// getter
	public String getDestination(){
		return this.destination;
	}
	public double getDuration(){
		return this.duration;
	}

	// this is supposed to be the getter for base price but the interface implements the same method so idk 
		/*public double getBasePrice(){
			return this.basePrice;
		}*/

	public client getClient(){
		client temp = this.client;
		return temp;
	}
	public String getTripID(){
		return this.tripID;
	}
	public Transportation getTransportation(){
		return this.transportation;
	}
	public Accomodation getAccomodation(){
		return this.accomodation;
	}

	//setter 
	public void setDestination(String d){
		this.destination = d;
	}
	public void setDuration(double du)throws InvalidTripDataException{
		if (du<1 | du>20)	{
			throw new InvalidTripDataException("Duration should be between 1 and 20 days");
		}
		this.duration = du;
	}
	public void setBasePrice(double bp) throws InvalidTripDataException{
		if(bp<100){
			throw new InvalidTripDataException("Base price should be at least $100");
		}
		this.basePrice = bp;
	}
	public void setClient(client cl) throws InvalidTripDataException{
		boolean clientFound = false;

		for (client c : clientArray){
				if (c != null && c.getID().equalsIgnoreCase(cl.getID())){
				clientFound = true;
				break;
			}
		}
		if (cl == null | !clientFound) {
			throw new InvalidTripDataException("Client not found");
		}
		this.client = cl;
	}
	
	public void setTransportation(Transportation t){
		this.transportation = t;
	}
	public void setAccomodation(Accomodation a){
		this.accomodation = a;
	}


	//Methods
	// toString Method 
	@Override
	public String toString(){
		double transportationCost = (this.transportation != null) ? this.transportation.calculateCost((int)this.duration) : 0;
		double accomodationCost = (this.accomodation != null) ? this.accomodation.calculateCost((int)this.duration) : 0;
		double baseCost = this.duration * this.basePrice;
		double totalCost = calculateTotalCost();
		
		return 	  "Trip ID: " + this.tripID +"\n"
				+ "Destination: " + this.destination + "\n"
				+ "Duration: " + this.duration + " days\n"
				+ "Client: " + this.client.toString() + "\n"
				+ "Transportation: " + (this.transportation != null ? this.transportation.toString() : "None") + "\n"
				+ "Accommodation: " + (this.accomodation != null ? this.accomodation.toString() : "None") + "\n"
				+ "\n--- Price Breakdown ---\n"
				+ String.format("Base Price: $%.2f (%d days x $%.2f/day)\n", baseCost, (int)this.duration, this.basePrice)
				+ String.format("Transportation: $%.2f\n", transportationCost)
				+ String.format("Accommodation: $%.2f\n", accomodationCost)
				+ String.format("Total Cost: $%.2f", totalCost);
	}

	// equals 
	public boolean equals(Trip a){
		if (!(a instanceof Trip)| a==null){
            return false;
        }
		return this.destination.equalsIgnoreCase(a.destination)
			& this.duration == a.duration
			& this.basePrice  == a.basePrice
			& this.client.equals(a.client);
	}
	
	// calculate total cost method
	public double calculateTotalCost() {
		double transportationCost = (this.transportation != null) ? this.transportation.calculateCost((int)this.duration) : 0;
		double accomodationCost = (this.accomodation != null) ? this.accomodation.calculateCost((int)this.duration) : 0;
		
		return this.duration*this.basePrice + transportationCost + accomodationCost;
	}


	//Interface methods 
	//Identifiable interface method
	@Override
	public String getID() {
		return this.tripID;
	}

	//Billable interface method
	@Override
	public double getBasePrice() {
		return this.basePrice;
	}

	@Override
	public double getTotalCost() {
		return calculateTotalCost();
	}

	//CsvPersistable interface method
	@Override
	public String toCsvRow() {
		String accommodationId = this.accomodation == null ? "" : this.accomodation.getID();
        String transportationId = this.transportation == null ? "" : this.transportation.getID();

        return this.getID() + ";"
                + this.client.getID() + ";"
                + accommodationId + ";"
                + transportationId + ";"
                + this.destination + ";"
                + this.duration + ";"
                + this.basePrice;
	}

	// Comparable interface method - sort by total cost descending (highest revenue trips first)
	@Override
	public int compareTo(Trip other) {
		return Double.compare(other.calculateTotalCost(), this.calculateTotalCost());
	}

	public static Trip fromCsvRow(String csvRow, List<client> clients, List<Transportation> transportations, List<Accomodation> accomodations) throws InvalidTripDataException {
		String[] parts = csvRow.split(";");
		if (parts.length != 7) {
			throw new InvalidTripDataException("Invalid CSV format for Trip: " + csvRow);
		}

		String clientID = parts[1];
		String accommodationID = parts[2];
		String transportationID = parts[3];
		String destination = parts[4];
		double duration = Double.parseDouble(parts[5]);
		double basePrice = Double.parseDouble(parts[6]);

		// Validate client ID is present
		if (clientID == null || clientID.isEmpty()) {
			throw new InvalidTripDataException("ClientID is mandatory");
		}

		// Find client
		client resolvedClient = findClientById(clients, clientID);
		if (resolvedClient == null) {
			throw new InvalidTripDataException("ClientID not found: " + clientID);
		}

		// Find accommodation if present
		Accomodation resolvedAccommodation = null;
		if (accommodationID != null && !accommodationID.isEmpty()) {
			resolvedAccommodation = findAccommodationById(accomodations, accommodationID);
			if (resolvedAccommodation == null) {
				throw new InvalidTripDataException("AccommodationID not found: " + accommodationID);
			}
		}

		// Find transportation if present
		Transportation resolvedTransportation = null;
		if (transportationID != null && !transportationID.isEmpty()) {
			resolvedTransportation = findTransportationById(transportations, transportationID);
			if (resolvedTransportation == null) {
				throw new InvalidTripDataException("TransportationID not found: " + transportationID);
			}
		}

		return new Trip(destination, duration, basePrice, resolvedClient, resolvedTransportation, resolvedAccommodation, clients);
	}

	// Helper methods
	private static client findClientById(List<client> clients, String id) {
		if (clients == null || id == null || id.isEmpty()) return null;
		for (client c : clients) {
			if (c != null && id.equalsIgnoreCase(c.getID())) return c;
		}
		return null;
	}

	private static Transportation findTransportationById(List<Transportation> transportations, String id) {
		if (transportations == null || id == null || id.isEmpty()) return null;
		for (Transportation t : transportations) {
			if (t != null && id.equalsIgnoreCase(t.getID())) return t;
		}
		return null;
	}

	private static Accomodation findAccommodationById(List<Accomodation> accomodations, String id) {
		if (accomodations == null || id == null || id.isEmpty()) return null;
		for (Accomodation a : accomodations) {
			if (a != null && id.equalsIgnoreCase(a.getID())) return a;
		}
		return null;
	}
}
