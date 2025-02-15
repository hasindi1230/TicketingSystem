import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration() {

    }
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate(){
        return ticketReleaseRate;
    }
    public void setTicketReleaseRate(int ticketReleaseRate){
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate(){
        return customerRetrievalRate;
    }
    public void setCustomerRetrievalRate(int customerRetrievalRate){
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity(){
        return maxTicketCapacity;
    }
    public void setMaxTicketCapacity(int maxTicketCapacity){
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void save(){
        String file_name = "configuration.txt";
        try{
            FileWriter my_file = new FileWriter(file_name);
            my_file.write("Maximum ticket capacity: " + getMaxTicketCapacity() + "\n");
            my_file.write("Total tickets: " + getTotalTickets() + "\n");
            my_file.write("Ticket release rate: " + getTicketReleaseRate() + "\n");
            my_file.write("Customer retrieval rate: " + getCustomerRetrievalRate());

            my_file.close();

        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
