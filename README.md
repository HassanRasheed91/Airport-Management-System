# Airport-Management-System
The Airport Management System is a comprehensive solution designed to streamline airport operations, offering a user-friendly interface for passengers and administrators. Key functionalities include flight management, ticket booking, passenger handling, and emergency services, all integrated within a robust Graphical User Interface (GUI). The system employs various data structures like LinkedHashSet, HashMap, LinkedList, and more to ensure efficient data management and operational fluidity.

## Table of Contents
1. [Abstract](#abstract)
2. [Flight Management](#flight-management)
3. [Ticket Management](#ticket-management)
4. [Passenger Management](#passenger-management)
5. [Aircraft Management](#aircraft-management)
6. [Emergency Services](#emergency-services)
7. [Lost and Found](#lost-and-found)
8. [Security Management](#security-management)
9. [Staff Management](#staff-management)
10. [Bag Handling System (BHP)](#bag-handling-system-bhp)
11. [Cargo Management](#cargo-management)
12. [Food and Retail Services Management](#food-and-retail-services-management)
13. [Contributors](#contributors)

## Flight Management
### Attributes
- **flightNumber**: Unique identifier for the flight.
- **source**: Departure location.
- **destination**: Arrival location.
- **departureTime**: Scheduled departure time.
- **status**: Enum representing flight status (e.g., ON_TIME, DELAYED, CANCELLED).
- **delayInMinutes**: Delay time in case of delay.

### Methods
- `addFlight()`: Adds a new flight.
- `updateFlight()`: Updates details of an existing flight.
- `deleteFlight()`: Deletes a flight.
- `displayFlightDetails()`: Displays details of a specific flight.
- `displayAllFlights()`: Displays details of all flights.
- `delayFlight()`: Delays a flight.
- `getNumberOfFlights()`: Returns total flight count.
- `getFlights()`: Returns iterable of all flights.

## Ticket Management
### Attributes
- **ticketNumber**
- **passengerName**
- **seatNumber**
- **flightNumber**

### Methods
- `bookTicket(int ticketNumber, String passengerName, String seatNumber, String flightNumber)`: Books a ticket.
- `cancelTicket(int ticketNumber)`: Cancels a ticket.
- `modifyTicket(int ticketNumber, String passengerName, String seatNumber, String flightNumber)`: Modifies a ticket.
- `displayTicketDetails(int ticketNumber)`: Displays details of a specific ticket.
- `displayAllTickets()`: Displays details of all tickets.
- `getTicketsByFlightNumber(String flightNumber)`: Retrieves tickets for a specific flight.
- `searchByFlightNumber(String flightNumber)`: Searches tickets by flight number.
- `getNumberOfTickets()`: Returns total ticket count.
- `undoLastBooking()`: Undoes the last booking action.

## Passenger Management
### Attributes
- **passengerName**
- **age**
- **address**
- **gender**

### Data Structures
- **passengers**: LinkedList
- **undoStack**: Stack
- **processingQueue**: Queue

### Methods
- `addPassenger()`: Adds a new passenger.
- `updatePassenger()`: Updates details of an existing passenger.
- `deletePassenger()`: Deletes a passenger.
- `undoLastDelete()`: Undoes the last delete action.
- `displayPassengerDetails()`: Displays details of a specific passenger.
- `displayAllPassengers()`: Displays details of all passengers.

## Aircraft Management
### Attributes
- **registrationNumber**
- **aircraftType**
- **seatingCapacity**
- **assignedCrewMember**

### Data Structures
- **aircrafts**: ArrayList

### Methods
- `addAircraft()`: Adds a new aircraft.
- `updateAircraft()`: Updates details of an existing aircraft.
- `deleteAircraft()`: Deletes an aircraft.
- `displayAircraftDetails()`: Displays details of a specific aircraft.
- `displayAllAircrafts()`: Displays details of all aircrafts.
- `assignCrewMembersToAircraft()`: Assigns crew members to an aircraft.
- `searchByRegistrationNumber()`: Searches for an aircraft by registration number.
- `getAircraftWithHighestCapacity()`: Retrieves the aircraft with the highest seating capacity.

## Emergency Services
### Attributes
- **emergencyStack**: Stack

### Methods
- `EmergencyServices()`: Initializes emergency services.
- `addEmergencyRequest(String request)`: Adds a new emergency request.
- `handleEmergencyRequest()`: Handles an emergency request.
- `clearEmergencyRequests()`: Clears all emergency requests.
- `getEmergencyRequestsCount()`: Returns the count of emergency requests.
- `isEmergencyStackEmpty()`: Checks if the emergency stack is empty.

## Lost and Found
### Attributes
- **lostItems**: TreeMap

### Methods
- `LostAndFound()`: Initializes lost and found services.
- `addLostItem()`: Adds a lost item.
- `findLostItem()`: Finds a lost item.
- `addMultipleLostItems()`: Adds multiple lost items.
- `isLostItemExists()`: Checks if a lost item exists.
- `getTotalLostItemsCount()`: Returns the total count of lost items.
- `getLostItems()`: Retrieves lost items.
- `searchLostItemsByCategory()`: Searches lost items by category.
- `removeFoundItem()`: Removes a found item.
- `getLostItemsByCategory()`: Retrieves lost items by category.

## Security Management
### Attributes
- **securityQueue**: PriorityQueue
- **completedTasks**: List

### Methods
- `SecurityManagement()`: Initializes security management.
- `addSecurityTask()`: Adds a new security task.
- `getNextSecurityTask()`: Retrieves the next security task.
- `processSecurityTasks()`: Processes security tasks.
- `getSecurityTasksCount()`: Returns the count of security tasks.
- `clearSecurityTasks()`: Clears all security tasks.
- `isHighPriorityTaskAvailable()`: Checks if a high-priority task is available.
- `isLowPriorityTaskAvailable()`: Checks if a low-priority task is available.
- `getCompletedTasks()`: Retrieves completed tasks.
- `getAllTasksInQueue()`: Retrieves all tasks in the queue.
- `isTaskInQueue()`: Checks if a task is in the queue.
- `getSecurityTasks()`: Retrieves security tasks.
- `sortSecurityTasks()`: Sorts security tasks.

## Staff Management
### Attributes
- **staffMap**: HashMap

### Methods
- `StaffManagement()`: Initializes staff management.
- `addStaff()`: Adds a new staff member.
- `removeStaff()`: Removes a staff member.
- `updateStaffDetails()`: Updates staff details.
- `getStaffDetails()`: Retrieves staff details.
- `isStaffExist()`: Checks if a staff member exists.
- `getTotalStaffCount()`: Returns the total count of staff.
- `clearAllStaff()`: Clears all staff records.
- `isStaffListEmpty()`: Checks if the staff list is empty.
- `updateStaffRole()`: Updates the role of a staff member.
- `searchStaffByName()`: Searches staff by name.
- `getAllStaffDetailsAsString()`: Retrieves all staff details as a string.

## Bag Handling System (BHP)
### Data Structures
- **bagInfoQueue**: Queue (LinkedList)

### Methods
- `BHP()`: Initializes bag handling system.
- `AddButtonListener()`: Adds a button listener.
- `RemoveButtonListener()`: Removes a button listener.
- `UpdateButtonListener()`: Updates a button listener.
- `SearchButtonListener()`: Searches for a bag.
- `ShowAllButtonListener()`: Displays all bags.
- `isNumeric()`: Checks if a value is numeric.
- `clearFields()`: Clears input fields.

## Cargo Management
### Data Structures
- **cargoList**: List<Cargo>

### Methods
- `Cargo(String name, double weight)`: Constructor for cargo.
- `getName()`: Retrieves the name of the cargo.
- `getWeight()`: Retrieves the weight of the cargo.
- `toString()`: Converts cargo details to string.

## ManageFoodAndRetailServices
### Data Structures
- **foodItems**: List<FoodItem>
- **retailItems**: List<RetailItem>

### Methods
- `ManageFoodAndRetailServices()`: Constructor for food and retail services management.
- `updateFoodTextArea()`: Updates the food text area.
- `updateRetailTextArea()`: Updates the retail text area.
- **Action Listeners**:
  - `addFoodButton.addActionListener()`: Adds an action listener for the food button.
  - `addRetailButton.addActionListener()`: Adds an action listener for the retail button.
