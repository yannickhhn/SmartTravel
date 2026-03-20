package Travel;

public class Hostel extends Accomodation{
	private float bed;

	// default constructor
	public Hostel(){
		numId++;
		this.accId = "A" + numId;
		super.setName ("");
		super.setLocation("");
		super.setPrice(0.0);
		this.bed = 1;
	}

	// parameterized constructor 
	public Hostel(String name, String location, double price,float bed){
		numId++;
		this.accId = "A" + numId;
		super.setName (name);
		super.setLocation(location);
		super.setPrice(price);
		this.bed = bed;
	}
	
	// copy constructor
	public Hostel(Hostel other) {
		super(other.getName(), other.getLocation(), other.getPrice());
		this.bed = other.bed;
	}

	//methods
	@Override
	public String toString() {
		return "Accomodation ID: "+ this.accId +"\n"
				+"Hostel Name: " + super.getName()+ "\n"
				+"Hostel Location: " + super.getLocation()+"\n"
				+"Price : " + super.getPrice()+"\n"
				+"Number of bed : " + this.bed;
	}
	@Override
	public boolean equals(Accomodation a){ 
		if (!(a instanceof Hostel) | a==null) {
            return false;
        }
		Hostel temp = (Hostel) a;
		return super.getName().equalsIgnoreCase(a.getName()) 
		& super.getLocation().equalsIgnoreCase(a.getLocation())
		& super.getPrice() == a.getPrice()
		& this.bed == temp.bed;
	}
	
	@Override
	public double calculateCost(int numberOfDays){
		return super.getPrice()*numberOfDays;
	}
 }
