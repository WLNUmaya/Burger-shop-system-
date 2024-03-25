import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

public class Burger_Food_Center {
    //Constants
    private static final int Stock_of_Burgers= 50;
    private static final int Burgers_per_Client = 5;

    //Arrays for Queues
    private static String[] cashier1 = new String[2];
    private static String[] cashier2 = new String[3];
    private static String[] cashier3 = new String[5];

    private static int Burgers = Stock_of_Burgers;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Starting the while Loop
        while (true) {
            //giving menu option
            System.out.println("****  Burger_Food_Center  ****");
            System.out.println("--------------------------------------------");
            System.out.println("Please select an option");
            System.out.println("100 or VFQ: view all Queues.");
            System.out.println("101 or VEQ: View all Empty Queues.");
            System.out.println("102 or ACQ: Add customer to a Queue.");
            System.out.println("103 or RCQ: Remove a customer from Queue.(From a specific location)");
            System.out.println("104 or PCQ: Remove a served customer.");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order (Do not use library sort routine)");
            System.out.println("106 or SPD: Store Program Data into files");
            System.out.println("107 or LPD: Load Program Data from file.");
            System.out.println("108 or STK: View Remaining burgers Stock.");
            System.out.println("109 or AFS: Add burgers to Stock.");
            System.out.println("999 or EXT: Exit the Program.");
            System.out.println("--------------------------------------------");
            System.out.print("Enter your choice: ");



            //get input choice from user


            String input = scanner.nextLine().trim();

            switch (input) {
                case "100":
                case "VFQ":
                    ViewAllQueues();
                    break;
                case "101":
                case "VEQ":
                    ViewEmptyQueues();
                    break;
                case "102":
                case "ACQ":
                    AddCustomer(scanner);
                    break;
                case "103":
                case "RCQ":
                    RemoveCustomer(scanner);
                    break;
                case "104":
                case "PCQ":
                    RemoveServedCustomer();
                    break;
                case "105":
                case "VCS":
                    ViewCustomersSorted();
                    break;
                case "106":
                case "SPD":
                    storeProgramData();
                    break;
                case "107":
                case "LPD":
                    LoadData();
                    break;
                case "108":
                case "STK":
                    ViewRemainingStock();
                    break;
                case "109":
                case "AFS":
                    AddBurgersToStock(scanner);
                    break;
                case "999":
                case "EXT":
                    System.out.println("Exiting Burger_Food_Center program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    //View all queues
    private static void ViewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");
        System.out.println("Cashier 1: " + Arrays.toString(Queue_with_Symbols(cashier1)));
        System.out.println("Cashier 2: " + Arrays.toString(Queue_with_Symbols(cashier2)));
        System.out.println("Cashier 3: " + Arrays.toString(Queue_with_Symbols(cashier3)));
    }

    // Check if a queue is empty
    private static boolean isEmptyQueue(String[] queue) {
        for (String customer : queue) {
            if (customer != null) {
                return false;
            }
        }
        return true;
    }

    // View empty queues
    private static void ViewEmptyQueues() {
        System.out.println("Empty Queues:");
        if (isEmptyQueue(cashier1)) {
            System.out.println("Cashier 1");
        }
        if (isEmptyQueue(cashier2)) {
            System.out.println("Cashier 2");
        }
        if (isEmptyQueue(cashier3)) {
            System.out.println("Cashier 3");
        }
    }

    // Add symbols to represent the queue
    private static String[] Queue_with_Symbols(String[] queue) {
        String[] queueWithSymbols = new String[queue.length];
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                queueWithSymbols[i] = "X";
            } else {
                queueWithSymbols[i] = "O";
            }
        }
        return queueWithSymbols;
    }
    // Store program data to a file
    private static void storeProgramData() {
        try {
            FileWriter writer = new FileWriter("Burger_Food_Center_data.txt");

            // Write the data for QUEUE1
            for (String customer : cashier1) {
                if (customer != null) {
                    writer.write("Queue1:" + customer + "\n");
                }
            }

            // Write the data for QUEUE2
            for (String customer : cashier2) {
                if (customer != null) {
                    writer.write("Queue2:" + customer + "\n");
                }
            }

            // Write the data for QUEUE3
            for (String customer : cashier3) {
                if (customer != null) {
                    writer.write("Queue3:" + customer + "\n");
                }
            }

            writer.close();
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing program data: " + e.getMessage());
        }
    }

    private static void LoadData() {
        System.out.println("---Loading Data---");
        try {
            FileReader fileReader = new FileReader("Burger_Food_Center_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
            System.out.println("Data Loaded Successfully!");
        } catch (IOException e) {
            System.out.println("ERROR while loading data");
        }
    }



    // Add a customer to a cashier queue
    private static void AddCustomer(Scanner scanner) {
        System.out.print("Enter client name: ");
        String customerName = scanner.nextLine();
        if (customerName == null || customerName.isEmpty() || customerName.trim().isEmpty()) {
            System.out.println("Error: Client name cannot be empty.");
            return;
        }

        if (containsNumericValues(customerName)) {
            System.out.println("Error: Client name cannot contain numeric values.");
            return;
        }

        System.out.print("Enter preferred cashier (1, 2, or 3): ");
        int preferredCashier = scanner.nextInt();
        scanner.nextLine();

        String[] selectedCashier =null;

        if (preferredCashier == 1) { // Assign the selected cashier array based on user input
            selectedCashier = cashier1;
        } else if (preferredCashier == 2) {
            selectedCashier = cashier2;
        } else if (preferredCashier == 3) {
            selectedCashier = cashier3;
        } else {
            System.out.println("Invalid cashier choice. Customer not added.");
            return;
        }

        int availableIndex = getAvailableIndex(selectedCashier);

        if (availableIndex != -1) {
            selectedCashier[availableIndex] = customerName; // Add the customer to the selected cashier array

            System.out.println("Customer " + customerName + " added to Cashier " + preferredCashier + ".");
        } else {
            System.out.println("Cashier " + preferredCashier + " is full. Customer not added.");
        }

        if (Stock_of_Burgers <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + Stock_of_Burgers);
        }
        if (Stock_of_Burgers == 0) {
            System.out.println("Burger stock is empty...");
        }
    }

    private static boolean containsNumericValues(String text) {
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) { // Check if the customer name contains numeric values
                return true;
            }
        }
        return false;
    }
    private static int getAvailableIndex(String[] queue) {
        for (int i = 0; i < queue.length; i++) {
            //Check if the element at the current index is null
            if (queue[i] == null) {
                return i; //Return the index if an available slot is found
            }
        }
        return -1;// Return -1 if no available slot is found
    }

    private static void RemoveCustomer(Scanner scanner) {
        // Prompt the user to enter the cashier number
        System.out.print("Enter cashier number (1, 2, or 3): ");
        int cashierNumber = scanner.nextInt();
        scanner.nextLine();

        String[] selectedCashier = null;
        // Determine the selected cashier based on the cashier number

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

        // Check if the selected cashier's queue is empty
        if (isEmptyQueue(selectedCashier)) {
            System.out.println("Cashier " + cashierNumber + " is already empty. No customers to remove.");
        } else {

            // Prompt the user to enter the customer index to remove
            System.out.print("Enter customer index to remove (0 to " + (selectedCashier.length - 1) + "): ");
            int customerIndex = scanner.nextInt();
            scanner.nextLine();

            // Remove the customer at the specified index if it's valid
            if (customerIndex >= 0 && customerIndex < selectedCashier.length && selectedCashier[customerIndex] != null) {
                String removedCustomer = selectedCashier[customerIndex];
                selectedCashier[customerIndex] = null;
                System.out.println("Customer " + removedCustomer + " removed from Cashier " + cashierNumber + ".");
            } else {
                System.out.println("Invalid customer index. Customer not removed.");
            }
        }
    }

    private static void RemoveServedCustomer() {
        // Check if the cashier's queues are not empty
        if (!isEmptyQueue(cashier1)) {
            String removedCustomer = cashier1[0];
            shiftQueueLeft(cashier1);
            reduceBurgerStock(Burgers_per_Client);
            System.out.println("Customer " + removedCustomer + " removed from Cashier 1.");
        } else if (!isEmptyQueue(cashier2)) {
            String removedCustomer = cashier2[0];
            shiftQueueLeft(cashier2);
            reduceBurgerStock(Burgers_per_Client);
            System.out.println("Customer " + removedCustomer + " removed from Cashier 2.");
        } else if (!isEmptyQueue(cashier3)) {
            String removedCustomer = cashier3[0];
            shiftQueueLeft(cashier3);
            reduceBurgerStock(Burgers_per_Client);
            System.out.println("Customer " + removedCustomer + " removed from Cashier 3.");
        } else {
            System.out.println("All cashiers are empty. No customers to remove.");
        }
    }

    private static void reduceBurgerStock(int amount) {
        Burgers -= amount;
    }
    private static void shiftQueueLeft(String[] queue) {
        // Shift all elements of the queue one position to the left
        for (int i = 0; i < queue.length - 1; i++) {
            queue[i] = queue[i + 1];
        }
        // Set the last element to null
        queue[queue.length - 1] = null;
    }

    private static void ViewCustomersSorted() {
        // Create an array to hold all customers from all cashiers' queues

        String[] allCustomers = new String[cashier1.length + cashier2.length + cashier3.length];
        int index = 0;


        // Retrieve customers from cashiers and add them to the array
        for (String customer : cashier1) {
            if (customer != null) {
                allCustomers[index++] = customer;
            }
        }
        for (String customer : cashier2) {
            if (customer != null) {
                allCustomers[index++] = customer;
            }
        }
        for (String customer : cashier3) {
            if (customer != null) {
                allCustomers[index++] = customer;
            }
        }
        // Check if there are any customers in the array
        if (index == 0) {
            System.out.println("No customers in the queues.");
        } else {

            // Sort the customers array in alphabetical order
            sortCustomers(allCustomers, index);
            System.out.println("Customers Sorted in alphabetical order:");
            for (int i = 0; i < index; i++) {
                System.out.println(allCustomers[i]);
            }
        }
    }

    private static void sortCustomers(String[] customers, int size) {

        // Sort the customers array using bubble sort algorithm
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {

                // Compare adjacent elements and swap if necessary
                if (customers[j].compareTo(customers[j + 1]) > 0) {
                    String temp = customers[j];
                    customers[j] = customers[j + 1];
                    customers[j + 1] = temp;
                }
            }
        }
    }

    private static void ViewRemainingStock() {
        // Display the remaining stock of burgers
        System.out.println("Remaining burgers stock: " + Burgers);
    }

    private static void AddBurgersToStock(Scanner scanner) {

        // Prompt the user to enter the number of burgers to add
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine();
        // Add the specified number of burgers to the stock
        Burgers += burgersToAdd;
        System.out.println("Added " + burgersToAdd + " burgers to the stock. New stock: " + Burgers);
    }
}
