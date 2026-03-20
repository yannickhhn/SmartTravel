package Travel;

public abstract class Accomodation {
	protected static int numId = 4001;
	protected  String accId;
	private String name, location;
	private double price;

	// default constructor 
	public Accomodation(){
		numId++;
		this.accId = "A" + numId;
	}
	
	// parameterized constructor 
	public Accomodation(String n, String l, double price){
		numId++;
		this.accId = "A" + numId;
		this.name = n;
		this.location = l;
		this.price = price;
	}

	// copy constructor
	public Accomodation(Accomodation a){
		numId++;
		this.accId = "A" + numId;
		this.name = a.name;
		this.location = a.location;
		this.price = a.price;
	}
	
	// getters 
	public String getName(){
		return this.name;
	}
	public String getLocation(){
		return this.location;
	}
	public double getPrice(){
		return this.price;
	}

	//setters
	public void setName(String n){
		this.name = n;
	}
	public void setLocation(String l){
		this.location = l;
	}
	public void setPrice(double p){
		this.price = p;
	}

	// methods
	@Override
	public abstract String toString();
	public abstract boolean equals(Accomodation a);
	
	// calculate cost method 
	public double calculateCost(int numberOfDays){
		return this.price*numberOfDays;
	}
}

 