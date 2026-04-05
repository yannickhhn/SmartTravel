//----------------------------------------------
//Assignment 1 
//Package Travel 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Travel;

import exceptions.InvalidTransportDataException;


public class Bus extends Transportation {
	private int stopNumber;
	private final double baseFare = 100.0;
	
	

	//parameterized constructor
	public Bus(String n, String d, String a, int stop) throws InvalidTransportDataException { // you can just call parent constructor 
		super(n,d,a); // calling parent constructor 
		if (stop<1){
			throw new InvalidTransportDataException("Number of stops should be at least 1");
		}
		this.stopNumber = stop;
	}

	//default constructor 
	public Bus()throws InvalidTransportDataException {
		this(null,null,null,0);
	}

	//copy constructor 
	public Bus(Bus b) throws InvalidTransportDataException {
		this(b.getCompanyName(),b.getDepCity(),b.getACity(),b.stopNumber);
	}

	//setters
	@Override
	public void setCompanyName(String a) throws InvalidTransportDataException {
		super.setCompanyName(a);

	}
	public void setStopNumber(int s) throws InvalidTransportDataException{
		if (s<1){
			throw new InvalidTransportDataException("Number of stops should be at least 1");
		}
		this.stopNumber = s;
	}	

	//getters

	public int getStopNumber(){
		return this.stopNumber;
	}
	public double getBaseFare(){
		return this.baseFare;
	}


	//to string 
	@Override
	public String toString(){
		return super.toString()
            + "Number of Stops: " + this.stopNumber + "\n";
	}

	// equals 
	@Override
	public boolean equals(Object t){
        if (!(t instanceof Bus)| t==null){ // use super constructor 
            return false;
        }
        Bus temp = (Bus) t;

        return super.equals(temp)
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
