//----------------------------------------------
//Assignment 1 
//Package Travel 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Travel;
import exceptions.InvalidTransportDataException;

public class Train extends Transportation {
	private String trainType;
	private String seatClass;
	private final double baseFare = 150.0;

	

	//parameterized constructor
	public Train(String n, String d, String a,String trainType, String seatClass) throws InvalidTransportDataException {
	    super(n,d,a);
		
        
		if ( trainType == null || trainType.length() == 0 || trainType.length() > 50) { // change this if we want to make it specific (ex; express, bullet, blablabla)
			throw new InvalidTransportDataException("Invalid train type");
		}
		if ( seatClass == null || seatClass.length() == 0 || seatClass.length() > 50) {
			throw new InvalidTransportDataException("Invalid seat class");
		}
		this.trainType = trainType;
		this.seatClass = seatClass;
	}

	//default constructor 
	public Train() throws InvalidTransportDataException {
		this(null,null,null,null,null);
	}

	//copy constructor 
	public Train(Train b) throws InvalidTransportDataException {
		this(b.getCompanyName(),b.getDepCity(),b.getACity(),b.trainType,b.seatClass);
	}

	//setters
	public void setTrainType(String t)throws InvalidTransportDataException{
		if (t == null || t.length() == 0 || t.length() > 50) { 
			throw new InvalidTransportDataException("Invalid train type");
		}
		this.trainType = t;
	}	
	public void setSeatClass(String s)throws InvalidTransportDataException{ // for now keep it simple and see if we have time
		if (s == null || s.length() == 0 || s.length() > 50) {
			throw new InvalidTransportDataException("Invalid seat class");
		}
		this.seatClass = s;
	}
	//getters
	public String getTrainType(){
		return this.trainType;
	}		
	public String getSeatClass(){
		return this.seatClass;
	}
	public double getBaseFare(){
		return this.baseFare;
	}
	//to string 
	@Override
	public String toString(){
		return super.toString()
			+ "Train type: " + this.trainType +"\n"
			+ "Class: " + this.seatClass+ "\n";
	}

	// equals 
	@Override
	public boolean equals(Object t){
        if (!(t instanceof Train)| t==null){
            return false;
        }
        Train temp = (Train) t;

        return super.equals(temp)
                & this.trainType.equalsIgnoreCase(temp.trainType)
                & this.seatClass.equalsIgnoreCase( temp.seatClass);
    }

	@Override
	public double calculateFare(){
		double fare = this.baseFare;
		if (this.trainType.equalsIgnoreCase("Express")){
			fare += 50;
		}
		if (this.seatClass.equalsIgnoreCase("First Class")){
			fare += 100;
		}
		return fare;
	}

	@Override
    public double calculateCost(int numberOfDays){
        return this.calculateFare() * numberOfDays;
    }
}
