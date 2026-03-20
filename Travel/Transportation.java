package Travel;

import exceptions.InvalidTransportDataException;

public abstract class Transportation {
	protected static int numId = 3000;
	protected String transportID;
	private String companyName,depCity,aCity;
	protected double price = 0.0;
	
	

	// parameterized constructor
	public Transportation(String n, String d, String a){ 
		numId++;
		this.transportID = "TR"+numId;
		this.companyName = n;
		this.depCity = d;
		this.aCity=a;
	}

	// default constructor 
	public Transportation(){
		this(null,null,null);
	}
	// copy constructor 
	public Transportation(Transportation t){
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

	//setters
	public void setCompanyName(String a){
		this.companyName = a;
	}
	public void setDepCity(String d){
		this.depCity = d;
	}
	public void setACity(String a){
		this.aCity = a;
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
	
	public abstract double calculateFare();

	// calculate cost method 
	public double calculateCost(int numberOfDays){
		 return this.price*numberOfDays;
	}

	// method to copy transportation array
	public static Transportation[] copyTransportationArray(Transportation[] original) throws InvalidTransportDataException {
		if (original == null) return null;
		Transportation[] copy = new Transportation[original.length];
		for (int i = 0; i < original.length; i++) {
			if (original[i] instanceof Flight)
				copy[i] = new Flight((Flight) original[i]);
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




