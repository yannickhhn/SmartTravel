//----------------------------------------------
//Assignment 3
//Package Driver
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Driver;

import java.util.*;
import Travel.*;
import Client.client;
import exceptions.*;
import service.SmartTravelService;
import service.RecentList;
import Persistence.ErrorLogger;
import Persistence.GenericFileManager;
import visualization.TripChartGenerator;
import visualization.DashboardGenerator;


public class driver {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		SmartTravelService service = new SmartTravelService();

		int choice;
		do {
			System.out.print("""
					Welcome to SmartTravel
	                1 - Menu
	                2 - Testing Scenario
	                3 - Exit
					> """);

			choice = scanner.nextInt();
			switch (choice) {

				case 1: // Long menu display
					int choice2;
					do {
						System.out.print("""

							1- Client Management
							2- Trip Management
							3- Transportation Management
							4- Accomodation Management
							5- Additional Operations
							6- Visualization
							7- Advanced Analytics
							8- Load all data
							9- Save all data
							10- Generate dashboard
							11- Back to Main menu
							12- exit

									> """);
						choice2 = scanner.nextInt();

						switch (choice2) {
							case 1: // client management
								int cChoice;
								do {
									System.out.print("""
											1- Add client
											2- Edit a client
											3- Delete a client
											4- List all clients
											5- Back
										> """);
									cChoice = scanner.nextInt();
									switch (cChoice) {
										case 1: // add client
											try {
												System.out.print("Client first name:\n> "); String f = scanner.next();
												System.out.print("Client last name:\n> "); String l = scanner.next();
												System.out.print("Client email address:\n> "); String email = scanner.next();
												service.addClient(f, l, email);
											} catch (Exception e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 2: // edit a client
											System.out.print("Please enter Client ID: ");
											String tempID = scanner.next();
											try {
												client c = service.findClientByID(tempID);
												if (c == null) throw new EntityNotFoundException("Client not found.");
												System.out.println("Please enter new information below.");
												System.out.print("Client first name:\n> "); c.setFName(scanner.next());
												System.out.print("Client last name:\n> "); c.setLName(scanner.next());
												System.out.print("Client email address:\n> "); c.setEmail(scanner.next());
												System.out.println("Client information updated successfully.");
											} catch (EntityNotFoundException | InvalidClientDataException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											} catch (Exception e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 3: // delete a client
											System.out.print("Please enter Client ID: ");
											String delID = scanner.next();
											try {
												client d = service.findClientByID(delID);
												if (d == null) throw new EntityNotFoundException("Client not found.");
												service.deleteClient(d);
												System.out.println("Client deleted successfully.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 4: // list all clients
											List<client> clients = service.getClient();
											if (clients.isEmpty()) {
												System.out.println("No clients available.");
											} else {
												for (client c : clients) {
													System.out.println(c.toString());
												}
											}
											break;

										case 5: // back
											break;
										default:
											System.out.println("Invalid option.\n");
											break;
									}
								} while (cChoice != 5);
								break;

							case 2: // trip management
								int tchoice;
								do {
									System.out.print("""

										1- Create a trip
										2- Edit trip information
										3- Cancel a trip
										4- List all trips
										5- List all trips for a specific client
										6- Back
											> """);
									tchoice = scanner.nextInt();
									switch (tchoice) {
										case 1: // create a trip
											Transportation transport = null;
											Accomodation accom = null;
											System.out.println("Enter Client ID: "); String cID = scanner.next();

											try {
												client c = service.findClientByID(cID);
												if (c == null) throw new EntityNotFoundException("Client not found.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
												break;
											}

											System.out.println("Enter Destination: ");  String d = scanner.next();
											System.out.println("Enter Duration (days): ");  double du = scanner.nextDouble();
											System.out.println("Base Price ($): "); double bp = scanner.nextDouble();

											System.out.println("Would you like to add transportation to this trip? (Y/N)");
											String t = scanner.next();

											if (t.equalsIgnoreCase("Y")) {
												System.out.println("Transportation options:\n1- Flight\n2- Train\n3- Bus"); int tType = scanner.nextInt();
												switch (tType) {
													case 1: // flight
														System.out.println("Enter airline company: "); String cName = scanner.next();
														System.out.println("Departure city: "); String dep = scanner.next();
														System.out.println("Arrival city: "); String arr = scanner.next();
														System.out.println("Luggage allowance (kg): "); float luggage = scanner.nextFloat();
														try {
															transport = new Flight(cName, dep, arr, luggage);
														} catch (InvalidTransportDataException fe) {
															System.out.println("Error: " + fe.getMessage());
															ErrorLogger.log("Error: " + fe.getMessage());
														}
														break;
													case 2: // train
														System.out.println("Enter company name: "); cName = scanner.next();
														System.out.println("Departure city: "); dep = scanner.next();
														System.out.println("Arrival city: "); arr = scanner.next();
														System.out.println("Train type: "); String trainType = scanner.next();
														System.out.println("Seat class: "); String seatClass = scanner.next();
														try {
															transport = new Train(cName, dep, arr, trainType, seatClass);
														} catch (InvalidTransportDataException fe) {
															System.out.println("Error: " + fe.getMessage());
															ErrorLogger.log("Error: " + fe.getMessage());
														}
														break;
													case 3: // bus
														System.out.println("Enter bus company: "); cName = scanner.next();
														System.out.println("Departure city: "); dep = scanner.next();
														System.out.println("Arrival city: "); arr = scanner.next();
														System.out.println("Number of stops: "); int stops = scanner.nextInt();
														try {
															transport = new Bus(cName, dep, arr, stops);
														} catch (InvalidTransportDataException fe) {
															System.out.println("Error: " + fe.getMessage());
															ErrorLogger.log("Error: " + fe.getMessage());
														}
														break;
													default:
														System.out.println("Invalid transportation type.");
												}
											} else {
												System.out.println("No transportation will be added to this trip.");
											}

											System.out.println("Would you like to add accomodation to this trip? (Y/N)");
											String a = scanner.next();

											if (a.equalsIgnoreCase("Y")) {
												System.out.println("Accomodation options:\n1- Hotel\n2- Hostel"); int aType = scanner.nextInt();
												switch (aType) {
													case 1: // hotel
														System.out.println("Enter hotel name: "); String hName = scanner.next();
														System.out.println("Location: "); String loc = scanner.next();
														System.out.println("Duration of stay (nights): "); int accDuration = scanner.nextInt();
														System.out.println("Price per night ($): "); double price = scanner.nextDouble();
														System.out.println("Rating (out of 5): "); float rating = scanner.nextFloat();
														try {
															accom = new Hotel(hName, loc, accDuration, price, rating);
														} catch (InvalidAccommodationDataException fe) {
															System.out.println("Error: " + fe.getMessage());
															ErrorLogger.log("Error: " + fe.getMessage());
														}
														break;
													case 2: // hostel
														System.out.println("Enter hostel name: "); hName = scanner.next();
														System.out.println("Location: "); loc = scanner.next();
														System.out.println("Duration of stay (nights): "); int accDuration2 = scanner.nextInt();
														System.out.println("Price per night ($): "); price = scanner.nextDouble();
														System.out.println("Number of beds in a room: "); float beds = scanner.nextFloat();
														try {
															accom = new Hostel(hName, loc, accDuration2, price, beds);
														} catch (InvalidAccommodationDataException fe) {
															System.out.println("Error: " + fe.getMessage());
															ErrorLogger.log("Error: " + fe.getMessage());
														}
														break;
													default:
														System.out.println("Invalid accomodation type.");
												}
											} else {
												System.out.println("No accomodation will be added to this trip.");
											}

											service.createTrip(cID, d, du, bp, transport, accom);
											break;

										case 2: // edit trip info
											System.out.println("Please enter Client ID:"); String tId = scanner.next();
											try {
												boolean tripFound = false;
												client tempC = service.findClientByID(tId);
												if (tempC == null) throw new EntityNotFoundException("Client not found.");
												List<Trip> trips = service.getTrips();
												for (int i = 0; i < trips.size(); i++) {
													if (trips.get(i) != null && tempC.getClientID().equalsIgnoreCase(trips.get(i).getClient().getClientID())) {
														tripFound = true;
														System.out.println("Corresponding Trip:");
														System.out.println(trips.get(i).toString());
														System.out.println("Please enter new details below:\n");
														try {
															System.out.println("Destination: ");  trips.get(i).setDestination(scanner.next());
															System.out.println("Duration (days): ");  trips.get(i).setDuration(scanner.nextDouble());
															System.out.println("Base Price ($): "); trips.get(i).setBasePrice(scanner.nextDouble());
														} catch (InvalidTripDataException e) {
															System.out.println("Error: " + e.getMessage());
															ErrorLogger.log("Error: " + e.getMessage());
															break;
														}
														System.out.println("Trip information updated successfully.");
													}
												}
												if (!tripFound) throw new EntityNotFoundException("Trip not found.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 3: // cancel a trip
											System.out.print("Please enter Trip ID:\n> ");
											tId = scanner.next();
											try {
												boolean tripFound = false;
												List<Trip> trips = service.getTrips();
												for (int i = 0; i < trips.size(); i++) {
													if (trips.get(i) != null && trips.get(i).getTripID().equalsIgnoreCase(tId)) {
														trips.remove(i);
														System.out.println("Trip Cancelled Successfully.");
														tripFound = true;
														break;
													}
												}
												if (!tripFound) throw new EntityNotFoundException("Trip not found.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 4: // list all trips
											List<Trip> trips = service.getTrips();
											if (trips.isEmpty()) {
												System.out.println("There are no available trips.");
											} else {
												for (Trip trip : trips) {
													System.out.println(trip.toString());
												}
											}
											break;

										case 5: // list all trips for specific client
											System.out.print("Please enter Client ID:\n> ");
											String clientId = scanner.next();
											try {
												client clientToList = service.findClientByID(clientId);
												if (clientToList == null) throw new EntityNotFoundException("Client not found.");
												boolean empty = true;
												for (Trip trip : service.getTrips()) {
													if (trip != null && clientToList.getClientID().equalsIgnoreCase(trip.getClient().getClientID())) {
														System.out.println(trip.toString());
														empty = false;
													}
												}
												if (empty) throw new EntityNotFoundException("There are no trips for this client.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 6: // back
											break;
										default:
											break;
									}
								} while (tchoice != 6);
								break;

							case 3: // Transportation Management
								int choice3;
								do {
									System.out.print("""
											1- Add transportation option
											2- Remove transportation option
											3- List transportation options by type
											4- Back
										> """);
									choice3 = scanner.nextInt();
									switch (choice3) {
										case 1: // add transportation to a trip
											System.out.print("Please enter Client ID:\n> ");
											String clientId = scanner.next();
											try {
												boolean found = false;
												client tempClient = service.findClientByID(clientId);
												if (tempClient == null) throw new EntityNotFoundException("Client not found.");
												List<Trip> trips = service.getTrips();
												for (int i = 0; i < trips.size(); i++) {
													if (trips.get(i) != null && trips.get(i).getClient().getClientID().equalsIgnoreCase(tempClient.getClientID())) {
														System.out.println("Trip found:");
														System.out.println(trips.get(i).toString());
														System.out.println("Enter transportation details:\n");
														System.out.println("Enter company name: "); String cName = scanner.next();
														System.out.println("Departure city: "); String dep = scanner.next();
														System.out.println("Arrival city: "); String arr = scanner.next();
														System.out.println("Transportation type (1- Flight, 2- Train, 3- Bus): "); int tType = scanner.nextInt();
														Transportation transport = null;
														switch (tType) {
															case 1:
																System.out.println("Luggage allowance (kg): "); float luggage = scanner.nextFloat();
																try { transport = new Flight(cName, dep, arr, luggage); }
																catch (InvalidTransportDataException e) { System.out.println("Error: " + e.getMessage()); ErrorLogger.log("Error: " + e.getMessage()); }
																break;
															case 2:
																System.out.println("Train type: "); String trainType = scanner.next();
																System.out.println("Seat class: "); String seatClass = scanner.next();
																try { transport = new Train(cName, dep, arr, trainType, seatClass); }
																catch (InvalidTransportDataException e) { System.out.println("Error: " + e.getMessage()); ErrorLogger.log("Error: " + e.getMessage()); }
																break;
															case 3:
																System.out.println("Number of stops: "); int stops = scanner.nextInt();
																try { transport = new Bus(cName, dep, arr, stops); }
																catch (InvalidTransportDataException e) { System.out.println("Error: " + e.getMessage()); ErrorLogger.log("Error: " + e.getMessage()); }
																break;
															default:
																System.out.println("Invalid transportation type.");
														}
														trips.get(i).setTransportation(transport);
														if (transport != null) service.getTransportation().add(transport);
														found = true;
														System.out.println("Transportation added successfully.");
														System.out.println(trips.get(i).toString());
														break;
													}
												}
												if (!found) throw new EntityNotFoundException("Client not found.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 2: // remove transportation from a trip
											System.out.print("Please enter Client ID:\n> ");
											clientId = scanner.next();
											try {
												boolean found = false;
												client tempClient = service.findClientByID(clientId);
												if (tempClient == null) throw new EntityNotFoundException("Client not found.");
												for (Trip trip : service.getTrips()) {
													if (trip != null && trip.getClient().getClientID().equalsIgnoreCase(tempClient.getClientID())) {
														trip.setTransportation(null);
														found = true;
														System.out.println("Transportation removed successfully.");
														System.out.println(trip.toString());
														break;
													}
												}
												if (!found) throw new EntityNotFoundException("Client not found.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 3: // list transportation by type
											System.out.print("Which Category of transportation would you like to see?\n1- Flight\n2- Train\n3- Bus\n> ");
											int tType = scanner.nextInt();
											List<Transportation> transports = service.getTransportation();
											if (tType == 1) {
												System.out.println("Flights:");
												for (Transportation tr : transports) {
													if (tr instanceof Flight) System.out.println(tr.toString());
												}
											} else if (tType == 2) {
												System.out.println("Trains:");
												for (Transportation tr : transports) {
													if (tr instanceof Train) System.out.println(tr.toString());
												}
											} else if (tType == 3) {
												System.out.println("Buses:");
												for (Transportation tr : transports) {
													if (tr instanceof Bus) System.out.println(tr.toString());
												}
											}
											break;

										case 4: // back
											break;
										default:
											System.out.println("Invalid option.");
											break;
									}
								} while (choice3 != 4);
								break;

							case 4: // Accomodation management
								int choice4;
								do {
									System.out.print("""
											1- Add accomodation option
											2- Remove accomodation option
											3- List accomodation options by type
											4- Back
										> """);
									choice4 = scanner.nextInt();
									switch (choice4) {
										case 1: // add accomodation to a trip
											System.out.print("Please enter Client ID:\n> ");
											String clientId = scanner.next();
											try {
												boolean found = false;
												client tempClient = service.findClientByID(clientId);
												if (tempClient == null) throw new EntityNotFoundException("Client not found.");
												List<Trip> trips = service.getTrips();
												for (int i = 0; i < trips.size(); i++) {
													if (trips.get(i) != null && trips.get(i).getClient().getClientID().equalsIgnoreCase(tempClient.getClientID())) {
														System.out.println("Trip found:");
														System.out.println(trips.get(i).toString());
														System.out.println("Enter accomodation details:\n");
														Accomodation accom = null;
														System.out.println("Enter accomodation name: "); String aName = scanner.next();
														System.out.println("Location: "); String loc = scanner.next();
														System.out.println("Duration of stay (nights): "); int accDuration = scanner.nextInt();
														System.out.println("Price per night ($): "); double price = scanner.nextDouble();
														System.out.println("Accomodation type (1- Hotel, 2- Hostel): "); int aType = scanner.nextInt();
														switch (aType) {
															case 1:
																System.out.println("Rating (out of 5): "); float rating = scanner.nextFloat();
																try { accom = new Hotel(aName, loc, accDuration, price, rating); }
																catch (InvalidAccommodationDataException e) { System.out.println("Error: " + e.getMessage()); ErrorLogger.log("Error: " + e.getMessage()); }
																break;
															case 2:
																System.out.println("Number of beds in a room: "); float beds = scanner.nextFloat();
																try { accom = new Hostel(aName, loc, accDuration, price, beds); }
																catch (InvalidAccommodationDataException e) { System.out.println("Error: " + e.getMessage()); ErrorLogger.log("Error: " + e.getMessage()); }
																break;
															default:
																System.out.println("Invalid accomodation type.");
														}
														trips.get(i).setAccomodation(accom);
														if (accom != null) service.getAccomodation().add(accom);
														found = true;
														System.out.println("Accomodation added successfully.");
														System.out.println(trips.get(i).toString());
														break;
													}
												}
												if (!found) throw new EntityNotFoundException("Trip not found.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 2: // remove accomodation from a trip
											System.out.print("Please enter Client ID:\n> ");
											clientId = scanner.next();
											try {
												boolean found = false;
												client tempClient = service.findClientByID(clientId);
												if (tempClient == null) throw new EntityNotFoundException("Client not found.");
												for (Trip trip : service.getTrips()) {
													if (trip != null && trip.getClient().getClientID().equalsIgnoreCase(tempClient.getClientID())) {
														trip.setAccomodation(null);
														found = true;
														System.out.println("Accomodation removed successfully.");
														System.out.println(trip.toString());
														break;
													}
												}
												if (!found) throw new EntityNotFoundException("Trip not found.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 3: // list accomodation options by type
											System.out.print("Which Category of accomodation would you like to see?\n1- Hotel\n2- Hostel\n> ");
											int aType = scanner.nextInt();
											List<Accomodation> accomodations = service.getAccomodation();
											if (aType == 1) {
												System.out.println("Hotels:");
												for (Accomodation ac : accomodations) {
													if (ac instanceof Hotel) System.out.println(ac.toString());
												}
											} else if (aType == 2) {
												System.out.println("Hostels:");
												for (Accomodation ac : accomodations) {
													if (ac instanceof Hostel) System.out.println(ac.toString());
												}
											}
											break;

										case 4: // back
											break;
										default:
											System.out.println("Invalid option.");
											break;
									}
								} while (choice4 != 4);
								break;

							case 5: // Additional operations
								int choice5;
								do {
									System.out.print("""

											1- Display the most expensive trip
											2- Calculate and display the total cost of a trip
											3- Create a deep Copy of Transportation Array
											4- Create a deep Copy of Accomodation Array
											5- Back
											> """);
									choice5 = scanner.nextInt();
									switch (choice5) {
										case 1: // display most expensive trip
											List<Trip> trips = service.getTrips();
											double maxCost = 0;
											Trip expensiveTrip = null;
											for (Trip trip : trips) {
												if (trip != null) {
													double totalCost = trip.calculateTotalCost();
													if (totalCost > maxCost) {
														maxCost = totalCost;
														expensiveTrip = trip;
													}
												}
											}
											if (expensiveTrip != null) {
												System.out.println("Most expensive trip: " + expensiveTrip.toString());
											} else {
												System.out.println("No trips available.");
											}
											break;

										case 2: // calculate and display total cost of a trip
											System.out.print("Please enter Client ID:\n> ");
											String tId = scanner.next();
											try {
												boolean found = false;
												client tempClient = service.findClientByID(tId);
												if (tempClient == null) throw new EntityNotFoundException("Client not found.");
												for (Trip trip : service.getTrips()) {
													if (trip != null && trip.getClient().getClientID().equalsIgnoreCase(tempClient.getClientID())) {
														System.out.println("Total cost of the trip: $" + trip.calculateTotalCost());
														found = true;
														break;
													}
												}
												if (!found) throw new EntityNotFoundException("Trip not found.");
											} catch (EntityNotFoundException e) {
												System.out.println(e.getMessage());
												ErrorLogger.log(e.getMessage());
											}
											break;

										case 3: // create deep copy of transportation array
											try {
												Transportation[] transArray = service.getTransportation().toArray(new Transportation[0]);
												Transportation[] transCopy = Transportation.copyTransportationArray(transArray);
												System.out.println("Deep copy of transportation array created successfully (" + transCopy.length + " items).");
											} catch (InvalidTransportDataException e) {
												System.out.println("Error creating deep copy: " + e.getMessage());
												ErrorLogger.log("Error creating deep copy: " + e.getMessage());
											}
											break;

										case 4: // create deep copy of accomodation array
											try {
												Accomodation[] accomArray = service.getAccomodation().toArray(new Accomodation[0]);
												Accomodation[] accomCopy = Accomodation.copyAccomodationArray(accomArray);
												System.out.println("Deep copy of accomodation array created successfully (" + accomCopy.length + " items).");
											} catch (InvalidAccommodationDataException e) {
												System.out.println("Error creating deep copy: " + e.getMessage());
												ErrorLogger.log("Error creating deep copy: " + e.getMessage());
											}
											break;

										case 5: // back
											break;
										default:
											System.out.println("Invalid option.");
											break;
									}
								} while (choice5 != 5);
								break;

							case 6: // visualization
								try {
									TripChartGenerator.generateCostBarChart(service.getTrips());
									TripChartGenerator.generateDestinationPieChart(service.getTrips());
									TripChartGenerator.generateDurationLineChart(service.getTrips());
								} catch (Exception e) {
									System.out.println("Error generating charts: " + e.getMessage());
								}
								break;

							case 7: // Advanced Analytics
								int choice7;
								do {
									System.out.print("""

										1- Trips by Destination
										2- Trips by Cost Range
										3- Top Clients by Spending
										4- Recent Trips
										5- Smart Sort Collections
										6- Back to main menu
											> """);
									choice7 = scanner.nextInt();
									switch (choice7) {
										case 1: // 7.1 Trips by Destination (Predicate filter)
											System.out.print("Enter destination to filter by:\n> ");
											String dest = scanner.next();
											List<Trip> filtered = service.getTripRepo().filter(trip -> trip.getDestination().equalsIgnoreCase(dest));
											if (filtered.isEmpty()) {
												System.out.println("No trips found for destination: " + dest);
											} else {
												System.out.println("Trips to " + dest + ":");
												for (Trip trip : filtered) System.out.println(trip.toString());
											}
											break;

										case 2: // 7.2 Trips by Cost Range (Predicate range)
											System.out.print("Enter minimum cost:\n> "); double minCost = scanner.nextDouble();
											System.out.print("Enter maximum cost:\n> "); double maxCost = scanner.nextDouble();
											List<Trip> inRange = service.getTripRepo().filter(trip -> trip.calculateTotalCost() >= minCost && trip.calculateTotalCost() <= maxCost);
											if (inRange.isEmpty()) {
												System.out.println("No trips found in range $" + minCost + " - $" + maxCost);
											} else {
												System.out.println("Trips between $" + minCost + " and $" + maxCost + ":");
												for (Trip trip : inRange) System.out.printf("  %s - $%.2f%n", trip.getTripID(), trip.calculateTotalCost());
											}
											break;

										case 3: // 7.3 Top Clients by Spending (RecentList + natural sort)
											List<client> sortedClients = service.getClientRepo().getSorted();
											System.out.println("Top clients by spending (most valuable first):");

											try{
											for (client c : sortedClients) {
												System.out.printf("  %s - %s %s: $%.2f%n", c.getClientID(), c.getFName(), c.getLName(), service.getTotalSpentOnClient(c.getID()));
											}
											} catch (EntityNotFoundException e) {
												System.out.println("Error during sorting: " + e.getMessage());
												ErrorLogger.log("Error during sorting: " + e.getMessage());
											}
											break;

										case 4: // 7.4 Recent Trips (RecentList demo)
											RecentList<Trip> recentTrips = new RecentList<>();
											for (Trip trip : service.getTrips()) recentTrips.addRecent(trip);
											System.out.println("Recent trips (most recent first):");
											recentTrips.printRecent(recentTrips.size());
											break;

										case 5: // 7.5 Smart Sort Collections (business natural order)
											System.out.println("--- Trips sorted by total cost ---");
											List<Trip> sortedTrips = service.getTripRepo().getSorted();
											try{
											for (Trip trip : sortedTrips)
												System.out.printf("  %s -> %s: $%.2f%n", trip.getTripID(), trip.getDestination(), trip.calculateTotalCost());
											System.out.println("--- Clients sorted by total spent  ---");
											List<client> sortedC = service.getClientRepo().getSorted();
											for (client c : sortedC)
												System.out.printf("  %s - %s %s: $%.2f%n", c.getClientID(), c.getFName(), c.getLName(), service.getTotalSpentOnClient(c.getID()));
											
											} catch (EntityNotFoundException e) {
												System.out.println("Error during sorting: " + e.getMessage());
												ErrorLogger.log("Error during sorting: " + e.getMessage());
											}
											break;

										case 6: // 7.6 Back
											break;
										default:
											System.out.println("Invalid option.");
											break;
									}
								} while (choice7 != 6);
								break;

							case 8: // load all data
								try {
									service.loadAllData();
									System.out.println("All data loaded successfully.");
								} catch (Exception e) {
									System.out.println("Error loading data: " + e.getMessage());
									ErrorLogger.log("Error loading data: " + e.getMessage());
								}
								break;

							case 9: // save all data
								try {
									service.saveAllData();
									System.out.println("All data saved successfully.");
								} catch (Exception e) {
									System.out.println("Error saving data: " + e.getMessage());
									ErrorLogger.log("Error saving data: " + e.getMessage());
								}
								break;

							case 10: // generate dashboard
								try {
									DashboardGenerator.generateDashboard(service);
								} catch (Exception e) {
									System.out.println("Error generating dashboard: " + e.getMessage());
								}
								break;

							case 11: // back to main menu
								break;

							case 12: // exit
								System.out.println("Thank you for using SmartTravel. Goodbye!");
								System.exit(0);
								break;

							default:
								System.out.println("Invalid option.");
								break;
						}
					} while (choice2 != 12);
					break;

				case 2: // hardcoded testing scenario
					List<client> testClients = new ArrayList<>();
					List<Transportation> testTransports = new ArrayList<>();
					List<Accomodation> testAccoms = new ArrayList<>();
					List<Trip> testTrips = new ArrayList<>();

					try {
						client c0 = new client("Alice", "Martin", "alice.martin@email.com", testClients);
						testClients.add(c0);
						client c1 = new client("Bob", "Smith", "bob.smith@email.com", testClients);
						testClients.add(c1);
						client c2 = new client("Claire", "Dubois", "claire.dubois@email.com", testClients);
						testClients.add(c2);

						testTransports.add(new Flight("Air Canada", "Montreal", "Paris", 23.0f));
						testTransports.add(new Flight("Delta", "New York", "London", 30.0f));
						testTransports.add(new Bus("Greyhound", "Montreal", "Toronto", 5));
						testTransports.add(new Bus("Megabus", "Toronto", "Ottawa", 3));
						testTransports.add(new Train("VIA Rail", "Montreal", "Quebec", "Express", "Business"));
						testTransports.add(new Train("Amtrak", "New York", "Boston", "Regional", "Economy"));

						testAccoms.add(new Hotel("Marriott Paris", "Paris", 7, 199.99, 4.5f));
						testAccoms.add(new Hotel("The Standard", "London", 5, 149.99, 4.0f));
						testAccoms.add(new Hostel("St Christopher's", "Paris", 3, 35.00, 6.0f));
						testAccoms.add(new Hostel("HI Toronto", "Toronto", 3, 28.50, 4.0f));

						testTrips.add(new Trip("Paris", 7.0, 500.0, testClients.get(0), testTransports.get(0), testAccoms.get(0), testClients));
						testTrips.add(new Trip("London", 5.0, 400.0, testClients.get(1), testTransports.get(1), testAccoms.get(1), testClients));
						testTrips.add(new Trip("Toronto", 3.0, 200.0, testClients.get(2), testTransports.get(2), testAccoms.get(3), testClients));
					} catch (InvalidClientDataException | InvalidTransportDataException | InvalidAccommodationDataException | InvalidTripDataException e) {
						System.out.println("Error during test scenario setup: " + e.getMessage());
						break;
					}

					System.out.println("Objects created successfully.");

					int sChoice;
					do {
						System.out.print("""

							1- Display all objects
							2- Test equals()
							3- Total cost per trip
							4- Find most expensive trip
							5- Deep copy transportation array
							6- Back
						> """);
						sChoice = scanner.nextInt();
						switch (sChoice) {
							case 1: // display all using toString()
								System.out.println("-- Clients --");
								for (client c : testClients) System.out.println(c);
								System.out.println("-- Transportation --");
								for (Transportation tr : testTransports) System.out.println(tr);
								System.out.println("-- Accommodation --");
								for (Accomodation ac : testAccoms) System.out.println(ac);
								System.out.println("-- Trips --");
								for (Trip trip : testTrips) System.out.println(trip);
								break;

							case 2: // test equals()
								try {
									Flight flightCopy = new Flight("Air Canada", "Montreal", "Paris", 23.0f);
									Hotel hotelCopy = new Hotel("Marriott Paris", "Paris", 7, 199.99, 4.5f);
									System.out.println("Flight vs Bus (diff class):     " + testTransports.get(0).equals(testTransports.get(2)));
									System.out.println("Flight vs Flight (diff attr):   " + testTransports.get(0).equals(testTransports.get(1)));
									System.out.println("Flight vs Flight (same attr):   " + testTransports.get(0).equals(flightCopy));
									System.out.println("Hotel vs Hostel (diff class):   " + testAccoms.get(0).equals(testAccoms.get(2)));
									System.out.println("Hotel vs Hotel (diff attr):     " + testAccoms.get(0).equals(testAccoms.get(1)));
									System.out.println("Hotel vs Hotel (same attr):     " + testAccoms.get(0).equals(hotelCopy));
								} catch (InvalidTransportDataException | InvalidAccommodationDataException e) {
									System.out.println("Error: " + e.getMessage());
								}
								break;

							case 3: // total cost per trip
								System.out.println("Total cost per trip:");
								for (Trip trip : testTrips)
									System.out.println("  " + trip.getTripID() + ": $" + trip.calculateTotalCost());
								break;

							case 4: // most expensive trip
								Trip mostExpensive = testTrips.get(0);
								for (int i = 1; i < testTrips.size(); i++)
									if (testTrips.get(i).calculateTotalCost() > mostExpensive.calculateTotalCost())
										mostExpensive = testTrips.get(i);
								System.out.println("Most expensive trip:");
								System.out.println(mostExpensive);
								System.out.printf("Total cost: $%.2f%n", mostExpensive.calculateTotalCost());
								break;

							case 5: // deep copy transportation array
								try {
									Transportation[] transArray = testTransports.toArray(new Transportation[0]);
									Transportation[] transCopy = Transportation.copyTransportationArray(transArray);
									transCopy[0].setCompanyName("MODIFIED AIRLINE");
									System.out.println("-- Original array (unchanged) --");
									for (Transportation tr : testTransports) System.out.println(tr.getCompanyName());
									System.out.println("-- Copied array (modified) --");
									for (Transportation tr : transCopy) System.out.println(tr.getCompanyName());
								} catch (InvalidTransportDataException e) {
									System.out.println("Error: " + e.getMessage());
								}
								break;

							case 6:
								break;

							default:
								System.out.println("Invalid option.");
						}
					} while (sChoice != 6);
					break;

				case 3: // exit
					System.out.println("Exiting SmartTravel Application. Goodbye!");
					System.exit(0);
					break;

				default:
					System.out.println("Invalid option.");
					break;
			}
		} while (choice != 3);

		scanner.close();
	}

}
