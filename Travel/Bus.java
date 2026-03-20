package Travel;

import exceptions.InvalidTransportDataException;

public class Bus extends Transportation{
	private String busCompany;
	private int stopNumber;
	private final double baseFare = 100.0;
	
	

	//parameterized constructor
	public Bus(String n, String d, String a,String busCompany, int stop) throws InvalidTransportDataException { // you can just call parent constructor 
		super(n,d,a); // calling parent constructor 

		numId++;
		this.transportID  = "TR"+numId;
		if (stop<1){
			throw new InvalidTransportDataException("Number of stops should be at least 1");
		}

		this.busCompany = busCompany;
		this.stopNumber = stop;
	}

	//default constructor 
	public Bus()throws InvalidTransportDataException {
		this(null,null,null,null,0);
	}

	//copy constructor 
	public Bus(Bus b) throws InvalidTransportDataException {
		this(b.getCompanyName(),b.getDepCity(),b.getACity(),b.busCompany,b.stopNumber);
	}

	//setters
	public void setBusCompany(String b){
		this.busCompany = b;
	}	
	public void setStopNumber(int s) throws InvalidTransportDataException{
		if (s<1){
			throw new InvalidTransportDataException("Number of stops should be at least 1");
		}
		this.stopNumber = s;
	}	

	//getters
	public String getBusCompany(){
		return this.busCompany;
	}		
	public int getStopNumber(){
		return this.stopNumber;
	}
	public double getBaseFare(){
		return this.baseFare;
	}


	//to string 
	@Override
	public String toString(){
		return "Company Name: " + super.getCompanyName()+"\n"
            + "Departure city: " + super.getDepCity()+"\n"
            + "Arrival city: " + super.getACity()+"\n"
			+ "Bus Company: " + this.busCompany +"\n"
			+ "Number of Stops: " + this.stopNumber + "\n";
	}

	// equals 
	@Override
	public boolean equals(Object t){
        if (!(t instanceof Bus)| t==null){ // use super constructor 
            return false;
        }
        Bus temp = (Bus) t;

        return super.getCompanyName().equalsIgnoreCase(temp.getCompanyName())
                & super.getDepCity().equalsIgnoreCase(temp.getDepCity())
                & super.getACity().equalsIgnoreCase(temp.getACity())
                & this.busCompany.equalsIgnoreCase(temp.busCompany)
                & this.stopNumber == temp.stopNumber;
    }

	@Override	
	public double calculateFare(){
		return this.baseFare*this.stopNumber;
	}

	@Override
    public double calculateCost(int numberOfDays){
        return this.calculateFare() * numberOfDays;
    }  
}
