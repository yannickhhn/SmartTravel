package Travel;

public class Train extends Transportation{
	private String trainType;
	private String seatClass;
	private final double baseFare = 150.0;

	//default constructor 
	public Train(){
		numId++;
		this.transportID  = "TR"+numId;
	}

	//parameterized constructor
	public Train(String n, String d, String a,String trainType, String seatClass){
		numId++;
		this.transportID  = "TR"+numId;
        super.setCompanyName(n);
        super.setDepCity(d);
        super.setACity(a);
		this.trainType = trainType;
		this.seatClass = seatClass;
	}

	//copy constructor 
	public Train(Train b){
		numId++;
		this.transportID  = "TR"+numId;
        super.setCompanyName(b.getCompanyName());
        super.setDepCity(b.getDepCity());
        super.setACity(b.getACity());
		this.trainType = b.trainType;
		this.seatClass = b.seatClass;
	}

	//setters
	public void setTrainType(String t){
		this.trainType = t;
	}	
	public void setSeatClass(String s){
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
			+ "Class: " + this.seatClass;
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
