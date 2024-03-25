public class Customer {
    private String firstName; // Variable to store the customer's first name
    private String lastName; // Variable to store the customer's last name
    private int burgersRequired; // Variable to store the number of burgers required by the customer

    // Constructor to initialize the Customer object with the provided details
    public Customer(String firstName, String lastName, int burgersRequired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.burgersRequired = burgersRequired;
    }

    // Method to get the full name of the customer
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Method to get the number of burgers required by the customer
    public int getBurgersRequired() {
        return burgersRequired;
    }
}
