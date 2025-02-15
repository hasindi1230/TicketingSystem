import java.util.Scanner;

public class Cli {
    public static void main(String[] args) {

        Configuration config1 = new Configuration();

        Scanner input = new Scanner(System.in);

        int MaxTicketsCapacity = CheckingPositiveInput(input, "Please enter the maximum ticket capacity:");
        config1.setMaxTicketCapacity(MaxTicketsCapacity);

        int totalTickets = ValidatingTotalTickets(input, config1.getMaxTicketCapacity(), "Please enter the total number of tickets available (This shouldn't exceed the maximum ticket capacity) : ");
        config1.setTotalTickets(totalTickets);

        int TicketReleaseRate = CheckingPositiveInput(input, "Please enter the ticket release rate:");
        config1.setTicketReleaseRate(TicketReleaseRate);

        int CustomerRetrievalRate = CheckingPositiveInput(input, "Please enter the customer retrieval rate:");
        config1.setCustomerRetrievalRate(CustomerRetrievalRate);

        System.out.println("\n");
        System.out.println("Configuration is completed!!");
        System.out.println("\n");
        System.out.println("Maximum ticket capacity: " + config1.getMaxTicketCapacity());
        System.out.println("Total number of tickets: " + config1.getTotalTickets());
        System.out.println("Ticket release rate: " + config1.getTicketReleaseRate());
        System.out.println("Customer retrieval rate: " + config1.getCustomerRetrievalRate());

        System.out.println("\n");

        //arranging the ticket pool
        TicketPool ticketPool = new TicketPool(config1.getTotalTickets(), config1.getMaxTicketCapacity());

        int num_of_vendors = 3;
        int release_per_vendor = config1.getTotalTickets() / num_of_vendors;

        //assuming there are 3 vendors in the ticket pool
        Vendor vendor1 = new Vendor(ticketPool, config1.getTicketReleaseRate(), 1, release_per_vendor);
        Vendor vendor2 = new Vendor(ticketPool, config1.getTicketReleaseRate(), 2 , release_per_vendor);
        Vendor vendor3 = new Vendor(ticketPool, config1.getTicketReleaseRate(), 3 , release_per_vendor);

        //assuming there are 3 customers in the ticket pool
        Customer customer1 = new Customer(ticketPool, config1.getCustomerRetrievalRate(), 1);
        Customer customer2 = new Customer(ticketPool, config1.getCustomerRetrievalRate(), 2);
        Customer customer3 = new Customer(ticketPool, config1.getCustomerRetrievalRate(), 3);

        Thread vendorThread1 = new Thread(vendor1);
        Thread vendorThread2 = new Thread(vendor2);
        Thread vendorThread3 = new Thread(vendor3);

        Thread customerThread1 = new Thread(customer1);
        Thread customerThread2 = new Thread(customer2);
        Thread customerThread3 = new Thread(customer3);

        vendorThread1.start();
        vendorThread2.start();
        vendorThread3.start();

        customerThread1.start();
        customerThread2.start();
        customerThread3.start();

        System.out.println("Saved to a text file");
        config1.save();
    }

    public static int CheckingPositiveInput(Scanner input, String message ){

        // In this case, -1 is used to signify that no valid input has been received yet.
        //The variable value is created to store the user’s input once it's validated.
        //It is initialized to -1, which is an invalid positive integer
        // (since valid positive integers start from 1).

        int value = -1;
        boolean validation = false;

        while(!validation){
            System.out.println(message);
            if(input.hasNextInt()){
                value = input.nextInt();
                if(value>0){
                    validation = true;
                }else{
                    System.out.println("Please enter an integer greater than 0");
                }
            }else{
                System.out.println("Invalid input. Please enter an integer greater than 0");
                input.next();
            }
        }
        return value;
    }

    public static int ValidatingTotalTickets(Scanner input, int MaxTicketCapacity, String message ){
        int value = -1;
        boolean validation = false;

        while(!validation){
            System.out.println(message);
            if (input.hasNextInt()){
                value = input.nextInt();
                if (value > 0 && value <= MaxTicketCapacity){
                    validation = true;
                }else{
                    System.out.println("Total number of tickets cannot exceed the maximum ticket capacity which is " + MaxTicketCapacity);
                }
            }else{
                System.out.println("Please enter an integer greater than 0");
            }
        }
        return value;
    }
}
