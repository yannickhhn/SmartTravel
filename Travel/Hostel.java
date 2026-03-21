package Travel;

import exceptions.InvalidAccommodationDataException;

public class Hostel extends Accomodation{
	private float bed;

	// default constructor
	public Hostel() throws InvalidAccommodationDataException{
		this(null,null,0,0,0);
	}

	// parameterized constructor 
	public Hostel(String name, String location, int nights,double price,float bed) throws InvalidAccommodationDataException{ 
		super(name, location, 0.1, nights);
		if (price<=0 || price>150){
			throw new InvalidAccommodationDataException("Hostels should be cheaper than 150 per night.");
		}
		
		if (bed < 0 || bed > 10) {
			throw new InvalidAccommodationDataException("Invalid number of bed");
		}
		super.setPrice(price);
		this.bed = bed;
	}
	
	// copy constructor
	public Hostel(Hostel other) throws InvalidAccommodationDataException {
		this(other.getAccomodationName(), other.getLocation(), other.getNumberofNights(), other.getPrice(), other.bed);
	}

	//setters 
	public void setBed(float b) throws InvalidAccommodationDataException{
		if (b < 0 || b > 10) {
			throw new InvalidAccommodationDataException("Invalid number of bed");
		}
		this.bed = b;
	}
	//getters
	public float getBed(){
		return this.bed;
	}


	//methods
	@Override
	public String toString() {
		return super.toString()
				+"Number of bed : " + this.bed+ "\n";
	}
	@Override
	public boolean equals(Object a){ 
		if (!(a instanceof Hostel) | a==null) {
            return false;
        }
		Hostel temp = (Hostel) a;
		return super.equals(temp)
		& this.bed == temp.bed;
	}
	
	@Override
	public double calculateCost(int numberOfDays){
		return super.getPrice()*numberOfDays;
	}
 }
