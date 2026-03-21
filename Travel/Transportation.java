package Travel;
import exceptions.InvalidTransportDataException;

public abstract class Transportation {
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
	@Override
	public String toString(){
		return "Company Name: " + this.getCompanyName()+"\n"
            + "Departure city: " + this.getDepCity()+"\n"
            + "Arrival city: " + this.getACity()+"\n";
	} 

	@Override
	public abstract boolean equals(Object t);
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
}




