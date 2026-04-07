# SmartTravel - Travel Management System

## Overview

A Java travel management system for managing clients, trips, transportation, and accommodations. Features include client management, trip booking, CSV persistence, error logging, and a dashboard for visualizing travel data.

## Features

- **Client Management**: Add, edit, delete clients with auto-generated IDs (C-prefix)
- **Trip Management**: Create trips with transportation and accommodation; calculate total costs; auto-generated IDs (T-prefix)
- **Transportation**: Flights, Trains, Buses with specific attributes
- **Accommodations**: Hotels and Hostels with pricing and ratings
- **Data Persistence**: Load/save data from/to CSV files with error logging
- **Visualization**: Dashboard and trip analytics with charts
- **Recent Trips**: Tracks 10 most recent trips using LinkedList

## Why LinkedList for Recent Trips?

The `RecentList` class uses a **LinkedList** instead of an **ArrayList** because:
- **LinkedList**: O(1) for `addFirst()` and `removeLast()` operations
- **ArrayList**: O(n) for front insertions (requires shifting all elements)

Since recent trips are frequently added to the front and removed from the end (FIFO with capacity limit), LinkedList provides constant-time performance for these operations, making it more efficient than ArrayList.

## System Requirements

- Java JDK 21 or higher
- JFreeChart library (for visualization)

## Folder Structure

```
A3_249/
├── src/
│   ├── Client/
│   ├── Driver/
│   ├── Travel/
│   ├── Persistence/
│   ├── service/
│   ├── exceptions/
│   ├── interfaces/
│   ├── repository/
│   └── visualization/
├── bin/
├── output/
└── README.md
```


### Main Menu
When you start SmartTravel, you'll see:
```
Welcome to SmartTravel     
1 - Menu 
2 - Testing Scenario 
3 - Exit 
```

### Menu Options (Option 1)
```
1- Client Management 
2- Trip Management 
3- Transportation Management 
4- Accomodation Management 
5- Additional Operations 
6- Visualization 
7- List all data Summary 
8- Load all data 
9- Save all data
10- Generate dashboard 
11- Back to main menu
12- Exit
```

### Client Management Workflow
```
1- Add client           → Enter name, email
2- Edit a client        → Enter client ID, update details
3- Delete a client      → Enter client ID
4- List all clients     → View all registered clients
5- Back
```

### Trip Management Workflow
```
1- Create a trip        → Enter client ID, destination, duration, base price
                         → Optional: Add flight/train/bus
                         → Optional: Add hotel/hostel
2- Edit trip info       → Enter client ID, update trip details
3- Cancel a trip        → Enter trip ID
4- List all trips       → View all trips
5- List trips by client → Enter client ID
6- Back
```


### Adding a Client
1. Select **1 - Client Management**
2. Select **1 - Add client**
3. Enter first name, last name, and email
4. Client ID is auto-generated (C-prefix)
5. System prevents duplicate emails

### Creating a Trip
1. Select **2 - Trip Management**
2. Select **1 - Create a trip**
3. Enter Client ID
4. Enter destination, duration, and base price
5. (Optional) Add transportation:
   - Choose: Flight, Train, or Bus
   - Enter relevant details
6. (Optional) Add accommodation:
   - Choose: Hotel or Hostel
   - Enter relevant details
7. Trip ID is auto-generated (T-prefix)

### Loading Data
1. Select **8 - Load all data**
2. System loads from CSV files in the root directory:
   - `clients.csv`
   - `transports.csv`
   - `accommodations.csv`
   - `trips.csv`

### Saving Data
1. Select **9 - Save all data**
2. System saves current data to:
   - `saved_clients.csv`
   - `saved_transports.csv`
   - `saved_accommodations.csv`
   - `saved_trips.csv`

### Generate Dashboard
1. Select **10 - Generate dashboard**
2. System creates an HTML dashboard in `output/dashboard/`
3. Dashboard includes charts and analytics

## Error Handling

SmartTravel includes comprehensive error handling:
- **DuplicateEmailException**: Prevents duplicate client emails
- **InvalidClientDataException**: Validates client information
- **InvalidTripDataException**: Validates trip data (e.g., base price ≥ $100)
- **InvalidTransportDataException**: Validates transportation data
- **InvalidAccommodationDataException**: Validates accommodation data
- **EntityNotFoundException**: Handles missing clients/trips

All errors are logged to `error.txt` and displayed on screen.

