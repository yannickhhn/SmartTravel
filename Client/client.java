package Client;
//----------------------------------------------
//Assignment 1 
//Package Client 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------

public class client {
	
	private static int numID = 1000;
	private final String clientID;
	private String fName, lName,email;
	
	// default constructor 
	public client(){
		client.numID++;
		this.clientID = "C" + numID;
		this.fName = "";
		this.lName = "";
		this.email = "";
	}
	
	//parameterized consructor
	public client(String f,String l, String email) {
		client.numID++;
		this.clientID = "C" + numID;
		this.fName = f;
		this.lName = l;
		this.email = email;
	}
	
	//copy Constructor 
	public client (client c) {
		client.numID++;
		this.clientID = "C" + numID;
		this.fName = c.fName;
		this.lName = c.lName;
		this.email = c.email;
	}
	
	// methods 
	////Accessors & Mutators
	//////Accessors 
	public String getFName() {
		return this.fName;
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

	/////mutators 
	public void setFName(String f) {
		this.fName = f;
	}
	public void setLNname(String l) {
		this.lName = l;
	}
	public void setEmail(String e) {
		this.email = e;
	}
	
	//To string method 
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

}
