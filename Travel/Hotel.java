
package Travel;

public class Hotel extends Accomodation{
	private float rating;

	// default constructor
	public Hotel(){
		numId++;
		this.accId = "A" + numId;
		super.setName ("");
		super.setLocation("");
		super.setPrice(0.0);
		this.rating = 0;
	}
	//parameterized constructor
	public Hotel(String name, String location, double price,float rating){
		numId++;
		this.accId = "A" + numId;
		super.setName (name);
		super.setLocation(location);
		super.setPrice(price);
		this.rating = rating;
	}
	// copy constructor
	public Hotel(Hotel other) {
		super(other.getName(), other.getLocation(), other.getPrice());
		this.rating = other.rating;
	}

	//methods
	@Override
	public String toString() {
		return "Accomodation ID: "+ this.accId +"\n"
				+"Hotel Name: " + super.getName()+ "\n"
				+"Hotel Location: " + super.getLocation()+"\n"
				+"Price : " + super.getPrice()+"\n"
				+"Rating : " + this.rating + "/5";
	}
	@Override
	public boolean equals(Accomodation a){ 
		if (!(a instanceof Hotel)|a==null) {
            return false;
        }
		Hotel temp = (Hotel) a;
		return super.getName().equalsIgnoreCase(a.getName()) 
		& super.getLocation().equalsIgnoreCase(a.getLocation())
		& super.getPrice() == a.getPrice()
		& this.rating == temp.rating;
	}
	@Override
	public double calculateCost(int numberOfDays){
		return super.getPrice()*numberOfDays;
	}
}
