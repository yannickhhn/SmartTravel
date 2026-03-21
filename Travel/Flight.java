package Travel;

import exceptions.InvalidTransportDataException;

public class Flight extends Transportation{
    
	private float luggage;
    private final double baseFare = 250.0;

    

    // parameterized constructor
    public Flight(String n, String d, String a, float l) throws InvalidTransportDataException{
        super(n,d,a);

        if (l<0|l>200){
            throw new InvalidTransportDataException("Invalid luggage allowance");
        }
        this.luggage = l;
        
    }
    
    //default constructor 
    public Flight()throws InvalidTransportDataException{
        this(null,null,null,0);
    }

    //copy constructor 
	public Flight(Flight b) throws InvalidTransportDataException{
        this(b.getCompanyName(),b.getDepCity(),b.getACity(),b.luggage);
	}

   //setters 
    @Override
    public void setCompanyName(String a) throws InvalidTransportDataException {
        super.setCompanyName(a);
       
    }
    public void setLuggage(float l) throws InvalidTransportDataException{
        if (l < 0 || l > 200) {
            throw new InvalidTransportDataException("Invalid luggage allowance");
        }
        this.luggage = l;
    }
    //getters
    public String getAirline(){
        return super.getCompanyName();
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
        return "Airline: " + super.getCompanyName()+"\n"
            + "Departure city: " + super.getDepCity()+"\n"
            + "Arrival city: " + super.getACity()+"\n"
            + "Luggage Allowance: " + this.luggage + "kg\n";
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
