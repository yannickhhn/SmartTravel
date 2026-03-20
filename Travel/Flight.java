package Travel;

public class Flight extends Transportation{
    private String airline;
	private float luggage;
    private final double baseFare = 250.0;

    //default constructor 
    public Flight(){
        numId++;
		this.transportID = "TR"+numId;
    }

    // parameterized constructor
    public Flight(String n, String d, String a,String airline,float l){
        numId++;
		this.transportID  = "TR"+numId;
        super.setCompanyName(n);
        super.setDepCity(d);
        super.setACity(a);
        this.airline = airline;
        this.luggage = l;
    }
 

    //copy constructor 
	public Flight(Flight b){
		numId++;
		this.transportID  = "TR"+numId;
        super.setCompanyName(b.getCompanyName());
        super.setDepCity(b.getDepCity());
        super.setACity(b.getACity());
		this.airline = b.airline;
		this.luggage = b.luggage;
	}

   //setters 
    public void setAirline(String a){
        this.airline = a;
    }   
    public void setLuggage(float l){
        this.luggage = l;
    }
    //getters
    public String getAirline(){
        return this.airline;
    }
    public float getLuggage(){
        return this.luggage;
    }
    public double getBaseFare(){
        return this.baseFare;
    }
    // toString method 
    @Override 
    public String toString(){
        return "Company Name: " + super.getCompanyName()+"\n"
            + "Departure city: " + super.getDepCity()+"\n"
            + "Arrival city: " + super.getACity()+"\n"
            + "Airline: " + this.airline +"\n"
            + "Luggage Allowance: " + this.luggage + "\n";
    }

    @Override 
    public boolean equals(Object t){ // should be type Object - overriden from object 
        if (!(t instanceof Flight) | t==null){
            return false;
        }
        Flight temp = (Flight) t;

        return super.getCompanyName().equalsIgnoreCase(temp.getCompanyName())
                & super.getDepCity().equalsIgnoreCase(temp.getDepCity())
                & super.getACity().equalsIgnoreCase(temp.getACity())
                & this.airline.equalsIgnoreCase(temp.airline)
                & this.luggage == temp.luggage;
    }

    @Override
    
    public double calculateFare(){
        return this.baseFare + (this.luggage * 20);
    }
    
    @Override
    public double calculateCost(int numberOfDays){
        return this.calculateFare() * numberOfDays;
    }   

  


}
