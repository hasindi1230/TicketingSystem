import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    //list to save available tickets

    private List<Integer> available_tickets;
    private int maxTicketCapacity;

    public TicketPool(int totalTickets ,int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.available_tickets = Collections.synchronizedList(new LinkedList<Integer>());

        for(int i=0; i<totalTickets; i++){
            available_tickets.add(i);
        }
    }

    //adding a ticket by vendor to the ticket pool
    public synchronized void addTicket(int vendor_id, int ticketReleaseRate) {
        int current_available_tickets = available_tickets.size();

        if(current_available_tickets + ticketReleaseRate <= maxTicketCapacity ) {

            int next_ticket_number = current_available_tickets + 1;
            for(int i=0; i<ticketReleaseRate; i++) {
                available_tickets.add(next_ticket_number) ;
            }
            current_available_tickets = available_tickets.size();
            System.out.println("Vendor " + vendor_id + " released " + ticketReleaseRate + " tickets to the ticket pool. Total tickets: " + current_available_tickets) ;
        }else{
            System.out.println("vendor " + vendor_id + " cannot release more tickets since it has reached the maximum ticket capacity.");
        }

    }

    //customers to retrieve the available tickets
    public synchronized void removeTicket(int customer_id, int customerRetrievalRate) {
        int current_available_tickets = available_tickets.size();

        if (current_available_tickets==0) {
            System.out.println("No tickets available to purchase.");
            return;
        }

        int ticketsToRemove = Math.min(customerRetrievalRate, current_available_tickets);
        for (int i=0; i<ticketsToRemove; i++){
            available_tickets.remove(0);
        }
        current_available_tickets = available_tickets.size();
        System.out.println("Customer "+ customer_id + " removed " + customerRetrievalRate + " tickets. Remaining tickets: " + current_available_tickets) ;

    }

    public synchronized int totalTickets(){
        return available_tickets.size() ;
    }

    public boolean isTicketPoolFull(){
        return available_tickets.size() >= maxTicketCapacity;
    }

    public int getTotalTickets(){
        return totalTickets();
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

}

