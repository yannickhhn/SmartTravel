package Travel;
import exceptions.InvalidTransportDataException;

public class Train extends Transportation{
	private String trainType;
	private String seatClass;
	private final double baseFare = 150.0;

	

	//parameterized constructor
	public Train(String n, String d, String a,String trainType, String seatClass) throws InvalidTransportDataException {
	    super(n,d,a);
		numId++;
		this.transportID  = "TR"+numId;
        
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
		return "Company Name: " + super.getCompanyName()+"\n"
            + "Departure city: " + super.getDepCity()+"\n"
            + "Arrival city: " + super.getACity()+"\n"
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

        return super.getCompanyName().equalsIgnoreCase(temp.getCompanyName())
                & super.getDepCity().equalsIgnoreCase(temp.getDepCity())
                & super.getACity().equalsIgnoreCase(temp.getACity())
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
