package Travel;

import exceptions.InvalidTransportDataException;

public class Flight extends Transportation{
    private String airline;
	private float luggage;
    private final double baseFare = 250.0;

    

    // parameterized constructor
    public Flight(String n, String d, String a,String airline,float l) throws InvalidTransportDataException{
        super(n,d,a);
        numId++;
		this.transportID  = "TR"+numId;
        if (airline == null ||airline.length()==0||airline.length()>50){
            throw new InvalidTransportDataException("Invalid Airline name");
        }

        if (l<0|l>200){
            throw new InvalidTransportDataException("Invalid luggage allowance");
        }

        this.airline = airline;
        this.luggage = l;
    }
    
    //default constructor 
    public Flight()throws InvalidTransportDataException{
        this(null,null,null,null,0);
    }

    //copy constructor 
	public Flight(Flight b) throws InvalidTransportDataException{
		this(b.getCompanyName(),b.getDepCity(),b.getACity(),b.airline,b.luggage);
	}

   //setters 
    public void setAirline(String a) throws InvalidTransportDataException{
        if (a == null || a.length() == 0 || a.length() > 50) {
            throw new InvalidTransportDataException("Invalid Airline name");
        }
        this.airline = a;
    }   
    public void setLuggage(float l) throws InvalidTransportDataException{
        if (l < 0 || l > 200) {
            throw new InvalidTransportDataException("Invalid luggage allowance");
        }
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
