import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;

public class Manager {
    // Constants
    private static final int  Stock_of_Burgers = 50; // Total number of burgers in stock
    private static final int cashier1_CAPACITY = 2; // Capacity of Queue 1
    private static final int cashier2_CAPACITY = 3; // Capacity of Queue 2
    private static final int cashier3_CAPACITY = 5; // Capacity of Queue 3
    private static final double Burger_price = 650.00;// Price of a burger

    // Create three food queues for cashiers
    public static final FoodQueue cashier1 = new FoodQueue(cashier1_CAPACITY);
    public static final FoodQueue cashier2 = new FoodQueue(cashier2_CAPACITY);
    public static final FoodQueue cashier3 = new FoodQueue(cashier3_CAPACITY);


    // Initialize the stock of burgers
    private static int BurgersStock =  Stock_of_Burgers;
    // Waiting List
    private static final CircularQueue<Customer> Waiting_list = new CircularQueue<>(cashier1_CAPACITY + cashier2_CAPACITY + cashier3_CAPACITY);
    // The main method of the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Print the menu and options
            System.out.println("--------------------------------------------");
            System.out.println("*****  Burger_Food_Center  *****");
            System.out.println("--------------------------------------------");
            System.out.println("PLEASE SELECT AN OPTION");
            System.out.println("************************");
            System.out.println("100 or VFQ: view all Queues.");
            System.out.println("101 or VEQ: View all Empty Queues.");
            System.out.println("102 or ACQ: Add customer to a Queue.");
            System.out.println("103 or RCQ: Remove a customer from Queue. (From a specific location)");
            System.out.println("104 or PCQ: Remove a served customer.");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order (Do not use library sort routine)");
            System.out.println("106 or SPD: Store Program Data into files");
            System.out.println("107 or LPD: Load Program Data from file.");
            System.out.println("108 or STK: View Remaining burgers Stock.");
            System.out.println("109 or AFS: Add burgers to Stock.");
            System.out.println("110 or IFQ: Print income of each queue.");
            System.out.println("999 or EXT: Exit the Program.");
            System.out.println("--------------------------------------------");
            System.out.print("Enter your choice: ");

            // Read the user's input
            String input = scanner.nextLine();

            input = input.trim().toLowerCase();
            // Process the user's choice
            switch (input) {
                case "100":
                case "vfq":
                    viewAllQueues();
                    break;
                case "101":
                case "veq":
                    viewEmptyQueues();
                    break;
                case "102":
                case "acq":
                    addCustomer(scanner);
                    break;
                case "103":
                case "rcq":
                    removeCustomer(scanner);
                    break;
                case "104":
                case "pcq":
                    removeServedCustomer();
                    break;
                case "105":
                case "vcs":
                    viewCustomersSorted();
                    break;
                case "106":
                case "spd":
                    storeProgramData();
                    break;
                case "107":
                case "lpd":
                    loadData();
                    break;
                case "108":
                case "stk":
                    viewRemainingStock();
                    break;
                case "109":
                case "afs":
                    addBurgersToStock(scanner);
                    break;
                case "110":
                case "ifq":
                    printIncome();
                    break;
                case "999":
                case "ext":
                    System.out.println("Exiting Burger_Food_Center ...");
                    System.out.println("Thanks for your visit");
                    System.out.println("Come Again");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // View all queues
    private static void viewAllQueues() {
        System.out.println();
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        System.out.println("Cashier 1: " + cashier1);
        System.out.println("Cashier 2: " + cashier2);
        System.out.println("Cashier 3: " + cashier3);
        System.out.println();
        System.out.println("O-Occupied  X– Not Occupied");
    }

    // View empty queues
    private static void viewEmptyQueues() {
        System.out.println("Empty Cashiers:");
        if (cashier1.isEmpty()) {
            System.out.println("Cashier 1");
        }
        if (cashier2.isEmpty()) {
            System.out.println("Cashier 2");
        }
        if (cashier3.isEmpty()) {
            System.out.println("Cashier 3");
        }
    }

    // Store program data
    private static void storeProgramData() {
        try {
            FileWriter writer = new FileWriter("Manager_data.txt");
            writer.write(cashier1.getDataString("Cashier1:"));
            writer.write(cashier2.getDataString("Cashier2:"));
            writer.write(cashier3.getDataString("Cashier3:"));
            writer.close();
            System.out.println("Manager data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing Manager data: " + e.getMessage());
        }
    }

    // Load program data
    private static void loadData() {
        System.out.println("---Loading Data---");
        try {
            FileReader fileReader = new FileReader("Manager_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            // Read and display each line of the program data
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
            System.out.println(" Manager Data Loaded Successfully!");
        } catch (IOException e) {
            System.out.println("ERROR while loading Manager data");
        }
    }

    // Add customer to a queue
    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer first name: ");
        String firstName = scanner.nextLine();

        // Validate first name
        while (!isStringOnly(firstName)) {
            System.out.println("Invalid input. Please enter a valid string for the first name.");
            System.out.print("Enter customer first name: ");
            firstName = scanner.nextLine();
        }

        System.out.print("Enter customer last name: ");
        String lastName = scanner.nextLine();

        // Validate last name
        while (!isStringOnly(lastName)) {
            System.out.println("Invalid input. Please enter a valid string for the last name.");
            System.out.print("Enter customer last name: ");
            lastName = scanner.nextLine();
        }

        int burgersRequired;
        while (true) {
            System.out.print("Enter the number of burgers required: ");
            String burgersInput = scanner.nextLine();

            try {
                burgersRequired = Integer.parseInt(burgersInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        // Create a customer object with the provided information
        Customer customer = new Customer(firstName, lastName, burgersRequired);
        FoodQueue shortestQueue = getShortestQueue();

        if (shortestQueue != null && shortestQueue.getSize() < shortestQueue.getCapacity())
        {
            // Add the customer to the shortest queue
            shortestQueue.addCustomer(customer);
            System.out.println("Customer " + customer.getFullName() + " added to Cashier " + shortestQueue.getCashierNumber() + ".");
        } else {
            if (!Waiting_list.isFull()) {
                // Add the customer to the waiting list if all cashiers are full
                Waiting_list.enqueue(customer);

                System.out.println("All cashiers are full. Added customer " + customer.getFullName() + " to the waiting list.");
            } else {
                System.out.println("All cashiers and waiting list are full. Customer not added.");
            }
        }
        // Check burger stock
        if (BurgersStock <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + BurgersStock);
        }
        if (BurgersStock == 0) {
            System.out.println("Burger stock is empty...");
        }
    }

    // Check if a string consists of only letters
    private static boolean isStringOnly(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    // Get the shortest queue among all cashiers
    private static FoodQueue getShortestQueue() {
        FoodQueue shortestQueue = null;

        // Check if QUEUE1 is shorter than its capacity
        if (cashier1.getSize() < cashier1.getCapacity()) {
            shortestQueue = cashier1;
        }
        // Check if QUEUE2 is shorter than its capacity and shorter than the current shortest queue
        if (cashier2.getSize() < cashier2.getCapacity() && (shortestQueue == null || cashier2.getSize() < shortestQueue.getSize())) {
            shortestQueue = cashier2;
        }
        // Check if QUEUE3 is shorter than its capacity and shorter than the current shortest queue
        if (cashier3.getSize() < cashier3.getCapacity() && (shortestQueue == null || cashier3.getSize() < shortestQueue.getSize())) {
            shortestQueue = cashier3;
        }

        return shortestQueue;
    }

    // Remove a customer from a specific cashier queue
    private static void removeCustomer(Scanner scanner) {
        System.out.print("Enter cashier number (1, 2, or 3): ");
        int cashierNumber = scanner.nextInt();
        scanner.nextLine();

        // Determine the selected cashier based on the user input
        FoodQueue selectedCashier = null;
        switch (cashierNumber) {
            case 1:
                selectedCashier = cashier1;
                break;
            case 2:
                selectedCashier = cashier2;
                break;
            case 3:
                selectedCashier = cashier3;
                break;
            default:
                System.out.println("Invalid cashier number. Customer not removed.");
                return;
        }

        if (selectedCashier.isEmpty()) {
            // Check if the selected cashier is already empty
            System.out.println("Cashier " + cashierNumber + " is already empty. No customers to remove.");
        } else {
            System.out.print("Enter customer index to remove (0 to " + (selectedCashier.getSize() - 1) + "): ");
            int customerIndex = scanner.nextInt();
            scanner.nextLine();

            if (selectedCashier.isValidIndex(customerIndex)) {
                // Check if the entered customer index is valid for the selected cashier
                Customer removedCustomer = selectedCashier.removeCustomer(customerIndex);
                System.out.println("Customer " + removedCustomer.getFullName() + " removed from Cashier " + cashierNumber + ".");
            } else {
                // The entered customer index is invalid
                System.out.println("Invalid customer index. Customer not removed.");
            }
        }
    }

    // Remove a served customer
    private static void removeServedCustomer() {
        if (!cashier1.isEmpty()) {
            // Remove customer from Cashier 1
            Customer removedCustomer = cashier1.removeCustomer(0);
            // Reduce burger stock
            reduceBurgerStock(removedCustomer.getBurgersRequired());
            System.out.println("Customer " + removedCustomer.getFullName() + " removed from Cashier 1.");
        } else if (!cashier2.isEmpty()) {
            // Remove customer from Cashier 2
            Customer removedCustomer = cashier2.removeCustomer(0);
            // Reduce burger stock
            reduceBurgerStock(removedCustomer.getBurgersRequired());
            System.out.println("Customer " + removedCustomer.getFullName() + " removed from Cashier 2.");
        } else if (!cashier3.isEmpty()) {
            // Remove customer from Cashier 3
            Customer removedCustomer = cashier3.removeCustomer(0);
            // Reduce burger stock
            reduceBurgerStock(removedCustomer.getBurgersRequired());
            System.out.println("Customer " + removedCustomer.getFullName() + " removed from Cashier 3.");
        } else {
            // No customers in the cashier queues
            System.out.println("All cashiers and waiting list are empty. No customers to remove.");
            return;
        }

        // Check if there are customers in the waiting list
        if (!Waiting_list.isEmpty()) {
            // Get the next customer from the waiting list
            Customer nextCustomer = Waiting_list.dequeue();
            // Get the shortest cashier queue
            FoodQueue shortestQueue = getShortestQueue();
            if (shortestQueue != null) {
                // Add the next customer to the shortest queue
                shortestQueue.addCustomer(nextCustomer);
                System.out.println("Next customer from the waiting list, " + nextCustomer.getFullName() + ", added to Cashier " + shortestQueue.getCashierNumber() + ".");
            } else {
                System.out.println("All cashiers are full. Next customer from the waiting list not added.");
            }

            // Reduce burger stock
            reduceBurgerStock(nextCustomer.getBurgersRequired());
            System.out.println("Customer " + nextCustomer.getFullName() + " removed from the waiting list.");
        }

        // Check burger stock
        if (BurgersStock <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + BurgersStock);
        }
        if (BurgersStock == 0) {
            System.out.println("Burger stock is empty...");
        }
    }

    // Reduce the burger stock by a given amount
    private static void reduceBurgerStock(int amount) {
        if (BurgersStock >= amount) {
            BurgersStock -= amount;
        } else {
            System.out.println("Cannot remove a customer. No burgers in stock.");
        }
    }

    // View all customers sorted in alphabetical order
    private static void viewCustomersSorted() {
        ArrayList<Customer> allCustomers = new ArrayList<>();

        allCustomers.addAll(cashier1.getCustomers());
        allCustomers.addAll(cashier2.getCustomers());
        allCustomers.addAll(cashier3.getCustomers());

        if (allCustomers.isEmpty()) {
            // Check if the list of customers is empty
            System.out.println("Cashier is empty.");
        } else {
            sortCustomers(allCustomers);
            System.out.println("All Customers Sorted in alphabetical order:");
            // Display the sorted customers' names
            for (Customer customer : allCustomers) {
                System.out.println(customer.getFullName());
            }
        }
    }

    // Sort the customers in alphabetical order
    private static void sortCustomers(ArrayList<Customer> customers) {
        Collections.sort(customers, new Comparator<Customer>() {
                    public int compare(Customer c1, Customer c2) {
                        // Compare the full names of two customers
                        return c1.getFullName().compareTo(c2.getFullName());
                    }
                }
        );
    }

    // View the remaining burger stock
    private static void viewRemainingStock() {
        System.out.println("Remaining burgers stock: " + BurgersStock);
    }

    // Add burgers to the stock
    private static void addBurgersToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine();

        BurgersStock += burgersToAdd;
        System.out.println("Added " + burgersToAdd + " burgers to the stock.");
        System.out.println("New stock: " + BurgersStock);
    }

    // Print the income of each queue
    private static void printIncome() {
        double income1 = cashier1.getIncome(Burger_price);
        double income2 = cashier2.getIncome(Burger_price);
        double income3 = cashier3.getIncome(Burger_price);

        System.out.println("Income of each queue:");
        System.out.println("Cashier 1: " + income1);
        System.out.println("Cashier 2: " + income2);
        System.out.println("Cashier 3: " + income3);
    }
}

// Circular Queue implementation
class CircularQueue<T> {
    private Object[] elements;
    private int front;
    private int rear;
    private int capacity;
    private int size;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.front = -1;
        this.rear = -1;
        this.size = 0;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Check if the queue is full
    public boolean isFull() {
        return size == capacity;
    }

    // Get the current size of the queue
    public int getSize() {
        return size;
    }

    // Enqueue an item into the queue
    public void enqueue(T item) {
        if (isFull()) {
            throw new IllegalStateException("CircularQueue is full.");
        }

        if (isEmpty()) {
            front = 0;
        }

        rear = (rear + 1) % capacity;
        elements[rear] = item;
        size++;
    }

    // Dequeue an item from the queue
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T item = (T) elements[front];
        elements[front] = null;

        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % capacity;
        }

        size--;
        return item;
    }
}
