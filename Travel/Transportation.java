//----------------------------------------------
//Assignment 1 
//Package Travel 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Travel;
import Persistence.ErrorLogger;
import exceptions.InvalidTransportDataException;
import interfaces.CsvPersistable;
import interfaces.Identifiable;

public abstract class Transportation implements Identifiable, CsvPersistable, Comparable<Transportation> {
	protected static int numId = 3000;
	protected String transportID;
	private String companyName,depCity,aCity;
	protected double price = 0.0;
	
	
	// parameterized constructor
	public Transportation(String n, String d, String a)throws InvalidTransportDataException{ 
		numId++;
		this.transportID = "TR"+numId;
		if (n == null || n.length() == 0 || n.length() > 50) {
			throw new InvalidTransportDataException("Invalid transportation company name");
		}

		if (d == null || d.length() == 0 || d.length() > 50) {
			throw new InvalidTransportDataException("Invalid departure city");
		}

		if ((a == null || a.length() == 0 || a.length() > 50) || a.equals(d)) {
			throw new InvalidTransportDataException("Invalid arrival city");
		}
		this.companyName = n;
		this.depCity = d;
		this.aCity=a;
	}

	// default constructor 
	public Transportation() throws InvalidTransportDataException{
		this(null,null,null);
	}
	// copy constructor 
	public Transportation(Transportation t) throws InvalidTransportDataException{
		this(t.companyName,t.depCity,t.aCity);
	}

	// getters 
	public String getCompanyName(){
		return this.companyName;
	}
	public String getDepCity(){
		return this.depCity;
	}
	public String getACity(){
		return this.aCity;
	}
	public String getTransportID(){
		return this.transportID;
	}

	//setters
	public void setCompanyName(String a)throws InvalidTransportDataException {
		if (a == null || a.length() == 0 || a.length() > 50) {
			throw new InvalidTransportDataException("Invalid transportation company name");
		}

		this.companyName = a;
	}
	public void setDepCity(String d)	throws InvalidTransportDataException{
		if (d == null || d.length() == 0 || d.length() > 50) {
			throw new InvalidTransportDataException("Invalid departure city");
		}
		this.depCity = d;
	}
	public void setACity(String a)throws InvalidTransportDataException{
		if (a == null || a.length() == 0 || a.length() > 50) {
			throw new InvalidTransportDataException("Invalid arrival city");
		}
		this.aCity = a;
	}
	public void setBaseFare(double fare) {
		this.price = fare;
	}

	 
	//methods 
	//interface method
	@Override
	public String getID() {
		return this.transportID;
	}

	//class methods 
	@Override
	public String toString(){
		return "Company Name: " + this.getCompanyName()+"\n"
            + "Departure city: " + this.getDepCity()+"\n"
            + "Arrival city: " + this.getACity()+"\n";
	} 

	@Override
	public  boolean equals(Object t){
		if (!(t instanceof Transportation) | t==null) {
			return false;
		}
		Transportation temp = (Transportation) t;
		return this.getCompanyName().equalsIgnoreCase(temp.getCompanyName()) 
		& this.getDepCity().equalsIgnoreCase(temp.getDepCity())
		& this.getACity().equalsIgnoreCase(temp.getACity());
	}

	public abstract double calculateFare(); // was this suposed to be abstract? or should we make it concrete and override it in the subclasses?
		

	// calculate cost method 
	public double calculateCost(int numberOfDays){ // go back to this later after figuring out the fares issues 
		 return this.price*numberOfDays;
	}

	// method to copy transportation array
	public static Transportation[] copyTransportationArray(Transportation[] original) throws InvalidTransportDataException {
		if (original == null) return null;
		Transportation[] copy = new Transportation[original.length];
		for (int i = 0; i < original.length; i++) {
			if (original[i] instanceof Flight flight)
				copy[i] = new Flight(flight);
			else if (original[i] instanceof Bus)
				copy[i] = new Bus((Bus) original[i]);
			else if (original[i] instanceof Train)
				copy[i] = new Train((Train) original[i]);
			else
				copy[i] = null;
		}
		return copy;
	}

	// method to convert transportation to csv format
	@Override
	public String toCsvRow() {
		 if (this instanceof Flight){
			Flight f = (Flight) this;
            return "FLIGHT;" + f.getID() + ";" + f.getCompanyName() + ";" + f.getDepCity() + ";" + f.getACity() + ";" + f.getBaseFare() + ";" + f.getLuggage();
        } else if (this instanceof Train){
            Train tr = (Train) this;
            return "TRAIN;" + tr.getID() + ";" + tr.getCompanyName() + ";" + tr.getDepCity() + ";" + tr.getACity() + ";" + tr.getBaseFare() + ";" + tr.getTrainType() + ";" + tr.getSeatClass();
        } else if (this instanceof Bus ){
			Bus b = (Bus) this;
            return "BUS;" + b.getID() + ";" + b.getCompanyName() + ";" + b.getDepCity() + ";" + b.getACity() + ";" + b.getBaseFare() + ";" + b.getStopNumber();
        } else {
            return "";
        }
	}

	// Comparable interface method - sort by base price descending (premium transport first)
	@Override
	public int compareTo(Transportation other) {
		return Double.compare(other.price, this.price);
	}

	public static Transportation fromCsvRow(String csvRow) throws InvalidTransportDataException {
		String[] parts = csvRow.split(";");
		if (parts.length !=7) {
			throw new InvalidTransportDataException("Invalid CSV row for transportation: " + csvRow);
		}
		String type = parts[0];
		String id = parts[1];
		String companyName = parts[2];
		String depCity = parts[3];
		String aCity = parts[4];
		double baseFare = Double.parseDouble(parts[5]);

		if (!id.contains("TR3") || id.length() < 6) {
			ErrorLogger.log("Invalid TransportID format: " + id);
			throw new InvalidTransportDataException("Invalid TransportID format: " + id);
		}	

		switch (type.toUpperCase()) {
			case "FLIGHT":
				Flight flight = new Flight(companyName, depCity, aCity,  Float.parseFloat(parts[6]));
				flight.setBaseFare(baseFare);
				return flight;

			case "TRAIN":
				Train train = new Train(companyName, depCity, aCity,  parts[6],"Economy");
				train.setBaseFare(baseFare);
				return train;

			case "BUS":
				Bus bus = new Bus(companyName, depCity, aCity, Integer.parseInt(parts[6]));
				bus.setBaseFare(baseFare);
				return bus;

			default:
				throw new InvalidTransportDataException("Unknown transportation type: " + type);
		}
	}
}




