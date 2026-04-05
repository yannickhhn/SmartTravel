package Client;
//----------------------------------------------
//Assignment 1 
//Package Client 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------

import exceptions.DuplicateEmailException;
import exceptions.InvalidClientDataException;
import java.util.List;
import java.util.LinkedList;
import interfaces.Identifiable;
import interfaces.CsvPersistable;

public class client implements Identifiable, CsvPersistable, Comparable<client> {

	private static int numID = 1000;
	private final String clientID;
	private String fName, lName,email;
	private double totalSpent = 0;
	private static List<client> clientArray;



	//parameterized consructor
	public client(String f,String l, String email, List<client> clients) throws InvalidClientDataException{

		client.numID++;
		this.clientID = "C" + numID;
		clientArray = new LinkedList<>(clients);
		if (f.length()>=50 | f.length()==0) {
			throw new InvalidClientDataException("First Name should be between 1 and 50 characters");
		}

		if (l.length()>=50 | l.length()==0) {
			throw new InvalidClientDataException("Last Name should be between 1 and 50 characters");
		}

		if (email.length()>=100|!email.contains("@")|!email.contains(".")|email.contains(" ")){
			throw new InvalidClientDataException("Invalid email format");
		}
		if (clients != null) {
			for (client c : clients) {
				if (c != null && c.getEmail().equalsIgnoreCase(email)) {
					throw new DuplicateEmailException("Email already exists");
				}
			}
		}

		this.fName = f;
		this.lName = l;
		this.email = email;
	}
	
	//copy Constructor 
	public client (client c) throws InvalidClientDataException {
		this(c.fName,c.lName,c.email,clientArray);
	}

	// default constructor 
	public client() throws InvalidClientDataException {
		this(null,null,null,clientArray);
	}
	
	

	//////Accessors
	public String getFName() {
		return this.fName;
	}
	public double getTotalSpent() {
		return this.totalSpent;
	}
	public String getLName() {
		return this.lName;
	}
	public String getEmail() {
		return this.email;
	}
	public String getClientID(){
		return this.clientID;
	}

	public void addToTotalSpent(double amount) {
		this.totalSpent += amount;
	}

	/////mutators
	public void setFName(String f) throws InvalidClientDataException {
		if (f.length()>=50 | f.length()==0) {
			throw new InvalidClientDataException("First Name should be between 1 and 50 characters");
		}
		this.fName = f;
	}
	public void setLName(String l) throws InvalidClientDataException {
		if (l.length()>=50 | l.length()==0) {
			throw new InvalidClientDataException("Last Name should be between 1 and 50 characters");
		}
		this.lName = l;
	}
	public void setEmail(String e) throws InvalidClientDataException {
		if (e.length()>=100|!e.contains("@")|!e.contains(".")|e.contains(" ")){
			throw new InvalidClientDataException("Invalid email format");
		}
		this.email = e;
	}
	

	
	//To string method 
	@Override
	public String toString() {
		return "Client ID: " + this.clientID + "\n"
				+ "Name : " + fName + " " + lName + "\n"
				+ "Email Adress: " + email+"\n";
	}
	
	// equals method 
	public boolean equals(client c) {
		boolean b = false;
		if (c == null || !(c instanceof client) ) {
			b = false;
		}else if ((this.fName.equalsIgnoreCase(c.fName))&&(this.lName.equalsIgnoreCase(c.lName))&&(this.email.equalsIgnoreCase(c.email))){
			b = true;
		}
		return b;
	}

	// interface method
	//identifiable interface method
	@Override
	public String getID()	{
		return this.clientID;
	}

	//csv persistable interface method
	@Override
	public String toCsvRow() {
		return this.clientID + ";" + this.fName + ";" + this.lName + ";" + this.email;
	}

	public static client fromCsvRow(String csvLine) throws InvalidClientDataException {
		if (csvLine == null || csvLine.isBlank()) {
			throw new InvalidClientDataException("CSV line is empty");
		}
		String[] parts = csvLine.split(";");
		if (parts.length != 4) {
			throw new InvalidClientDataException("Invalid CSV format: expected 4 fields, got " + parts.length);
		}
		return new client(parts[1], parts[2], parts[3], new LinkedList<>());
	}

	// comparable interface method — sort by total spent descending (most valuable clients first)
	@Override
	public int compareTo(client other) {
		return Double.compare(other.totalSpent, this.totalSpent);
	}

}
