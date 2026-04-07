//----------------------------------------------
//Assignment 1 
//Package Travel 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Travel;

import exceptions.InvalidAccommodationDataException;
import interfaces.CsvPersistable;
import interfaces.Identifiable;

public abstract class Accomodation implements Identifiable, CsvPersistable, Comparable<Accomodation> {
	protected static int numId = 4000;
	protected  String accId;
	private String AccomodationName, location;
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
		this.AccomodationName = n;
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
		this(a.AccomodationName,a.location,a.price,a.numberofNights);
	}
	
	// getters 
	public String getAccomodationID(){
		return this.accId;
	}
	public String getAccomodationName(){
		return this.AccomodationName;
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
	public void setAccomodationName(String n)throws InvalidAccommodationDataException {
		if (n == null || n.length() == 0 || n.length() > 50) {
			throw new InvalidAccommodationDataException("Invalid accommodation name");
		}
		this.AccomodationName = n;
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

	// static method to copy transportation array
	public static Accomodation[] copyAccomodationArray(Accomodation[] original) throws	 InvalidAccommodationDataException {
		if (original == null) {
			throw new InvalidAccommodationDataException("Original array cannot be null");
		}
		Accomodation[] copy = new Accomodation[original.length];
		for (int i = 0; i < original.length; i++) {
			if (original[i] instanceof Hotel) {
				copy[i] = new Hotel((Hotel) original[i]);
			} else if (original[i] instanceof Hostel) {
				copy[i] = new Hostel((Hostel) original[i]);
			} else {
				throw new InvalidAccommodationDataException("Unknown accommodation type at index " + i);
			}
		}
		return copy;
	}
	// methods
	@Override
	public  String toString(){
		return "Accomodation ID : " + accId + '\n' +
				"AccomodationName : " + AccomodationName + '\n' +
				"Location : " + location + '\n' +
				"Price : " + price + '\n' +
				"Number of Nights : " + numberofNights +
				'\n';
	}
	@Override
	public  boolean equals(Object a){
		if (a == null || !(a instanceof Accomodation)) {
			return false;
		}
		Accomodation other = (Accomodation) a;
		return this.accId.equals(other.accId) &&
				this.AccomodationName.equals(other.AccomodationName) &&
				this.location.equals(other.location) &&
				this.price == other.price &&
				this.numberofNights == other.numberofNights;
	}
	
	// calculate cost method 
	public double calculateCost(int numberOfDays){
		return this.price*numberOfDays;
	}
	
	// Identifiable interface method
	@Override
	public String getID() {
		return this.accId;
	}

	// Comparable interface method - sort by price per night descending (luxury options first)
	@Override
	public int compareTo(Accomodation other) {
		return Double.compare(other.getPrice(), this.getPrice());
	}

	// interface method to convert accomodation to csv format
	@Override
	public String toCsvRow() {
		 if (this instanceof Hotel){
            Hotel h = (Hotel) this;
            return "HOTEL;" + h.getID() + ";" + h.getAccomodationName() + ";" + h.getLocation() + ";" + h.getPrice() + ";" + h.getNumberofNights();
        } else if (this instanceof Hostel){
            Hostel h = (Hostel) this;
            return "HOSTEL;" + h.getID() + ";" + h.getAccomodationName() + ";" + h.getLocation() + ";" + h.getPrice() + ";" + h.getNumberofNights();
        } else {
            return "";
        }
	}

 
	public static Accomodation fromCsvRow(String csvRow) throws InvalidAccommodationDataException {
		String[] parts = csvRow.split(";");
		if (parts.length !=6) {
			throw new InvalidAccommodationDataException("Invalid CSV row for accommodation: " + csvRow);
		}

		switch (parts[0].toUpperCase()) {
			case "HOTEL":
				Hotel hotel = new Hotel(parts[2], parts[3], Integer.parseInt(parts[5]), Double.parseDouble(parts[4]), 2);
				return hotel;
			case "HOSTEL":
				Hostel hostel = new Hostel(parts[2], parts[3], Integer.parseInt(parts[5]), Double.parseDouble(parts[4]), 1);
				return hostel;
			default:
				throw new InvalidAccommodationDataException("Unknown accommodation type: " + parts[0]);
		}
	}
}
 
