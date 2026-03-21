package Travel;

import Client.client;
import exceptions.InvalidTripDataException;


public class Trip {
	private static int numId = 2000;
	private final String tripID;
	private String destination;
	private double duration;
	private double basePrice;
	private client client;
	private Transportation transportation;
	private Accomodation accomodation;
	static client[] clientArray;



	// parameterized constructor 
	public Trip(String destination, double duration, double basePrice, client client, Transportation transportation, Accomodation accomodation, client[] clientArray) throws InvalidTripDataException {

		numId++;
		this.tripID = "T"+numId;
		clientArray = clientArray.clone();
		boolean clientFound = false;

		for (int i=0; i<clientArray.length; i++){
			if (clientArray[i].getClientID().equalsIgnoreCase(client.getClientID())){
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
	public double getBasePrice(){
		return this.basePrice;
	}
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

		for (int i=0; i<clientArray.length; i++){
			if (clientArray[i].getClientID().equalsIgnoreCase(cl.getClientID())){
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

	// toString Method 
	@Override
	public String toString(){
		return 	  "Trip ID: " + this.tripID +"\n"
				+ "Destination: " + this.destination + "\n"
				+ "Duration: " + this.duration + "\n"
				+ "Base Price: " + this.basePrice +"\n"
				+ "Client: " + this.client.toString();
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
	

	public double calculateTotalCost() {
		return this.duration*this.basePrice + this.transportation.calculateCost((int)this.duration) + this.accomodation.calculateCost((int)this.duration);
	}

	

}
