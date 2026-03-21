
package Travel;

import exceptions.InvalidAccommodationDataException;

public class Hotel extends Accomodation{
	private float rating;

	
	//parameterized constructor
	public Hotel(String name, String location, int nights, double price,float rating) throws InvalidAccommodationDataException{
		super(name, location, price, nights);
		if (rating < 0 || rating > 5) {
			throw new InvalidAccommodationDataException("Invalid rating value");
		}
		this.rating = rating;
	}

	// default constructor
	public Hotel() throws InvalidAccommodationDataException{
		this(null,null,0,0,0);
	}

	// copy constructor
	public Hotel(Hotel other) throws InvalidAccommodationDataException {
		this(other.getAccomodationName(),other.getLocation(),other.getNumberofNights(),other.getPrice(),other.rating);
	}

	//setters
	public void setRating(float r) throws InvalidAccommodationDataException{
		if (r < 0 || r > 5) {
			throw new InvalidAccommodationDataException("Invalid rating value");
		}
		this.rating = r;
	}

	//getters
	public float getRating(){
		return this.rating;
	}

	//methods
	@Override
	public String toString() {
		return super.toString()
				+"Rating : " + this.rating + "/5 Stars \n";
	}
	@Override
	public boolean equals(Object a){ 
		if (!(a instanceof Hotel)|a==null) {
            return false;
        }
		Hotel temp = (Hotel) a;
		return super.equals(temp)
			& this.rating == temp.rating;
	}
	@Override
	public double calculateCost(int numberOfDays){
		return super.getPrice()*numberOfDays;
	}
}
