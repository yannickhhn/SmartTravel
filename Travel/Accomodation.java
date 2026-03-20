package Travel;

import exceptions.InvalidAccommodationDataException;

public abstract class Accomodation {
	protected static int numId = 4001;
	protected  String accId;
	private String name, location;
	private double price;
	private int numberofNights;
	
	
	// parameterized constructor 
	public Accomodation(String n, String l, double price,int numberofNights)throws InvalidAccommodationDataException {
		 if (n == null || n.length() == 0 || n.length() > 50) {
			throw new InvalidAccommodationDataException("Invalid accommodation name");
		}

		if (l == null || l.length() == 0 || l.length() > 50) {
			throw new InvalidAccommodationDataException("Invalid accommodation location");
		}

		if (price <= 0) {
			throw new InvalidAccommodationDataException("Price cannot be negative");
		}
		if (numberofNights <= 0) {
			throw new InvalidAccommodationDataException("Invalid nights of stay");
		}
		numId++;
		this.accId = "A" + numId;
		this.name = n;
		this.location = l;
		this.price = price;
		this.numberofNights = numberofNights;
	}

	// default constructor 
	public Accomodation() throws InvalidAccommodationDataException{
		this(null,null,0,0);
	}

	// copy constructor
	public Accomodation(Accomodation a) throws InvalidAccommodationDataException{
		this(a.name,a.location,a.price,a.numberofNights);
	}
	
	// getters 
	public String getName(){
		return this.name;
	}
	public String getLocation(){
		return this.location;
	}
	public double getPrice(){
		return this.price;
	}
	public int getNumberofNights(){
		return this.numberofNights;
	}
	//setters
	public void setName(String n)throws InvalidAccommodationDataException {
		if (n == null || n.length() == 0 || n.length() > 50) {
			throw new InvalidAccommodationDataException("Invalid accommodation name");
		}
		this.name = n;
	}
	public void setLocation(String l)throws InvalidAccommodationDataException {
		if (l == null || l.length() == 0 || l.length() > 50) {
			throw new InvalidAccommodationDataException("Invalid accommodation location");
		}
		this.location = l;
	}
	public void setPrice(double p)throws InvalidAccommodationDataException {
		if (p <= 0) {
			throw new InvalidAccommodationDataException("Price cannot be negative");
		}
		this.price = p;
	}
	public void setNumberofNights(int n)throws InvalidAccommodationDataException {
		if (n <= 0) {
			throw new InvalidAccommodationDataException("Invalid nights of stay");
		}
		this.numberofNights = n;
	}

	// methods
	@Override
	public abstract String toString();
	@Override
	public abstract boolean equals(Object a);
	
	// calculate cost method 
	public double calculateCost(int numberOfDays){
		return this.price*numberOfDays;
	}
}

 