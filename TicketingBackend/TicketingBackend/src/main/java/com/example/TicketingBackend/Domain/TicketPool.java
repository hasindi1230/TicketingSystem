package com.example.TicketingBackend.Domain;

import com.example.TicketingBackend.Service.TicketService;

import java.util.*;

public class TicketPool {
    //list to save available tickets
    private int totalTickets;
    private List<Integer> available_tickets;
    private int maxTicketCapacity;
    private Queue<String> threadLogs;

    public TicketPool(int totalTickets ,int maxTicketCapacity , Queue<String> threadLogs) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.available_tickets = Collections.synchronizedList(new LinkedList<Integer>());
        this.threadLogs = threadLogs;

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
            String logData = "Vendor " + vendor_id + " released " + ticketReleaseRate + " tickets to the ticket pool. Total tickets: " + current_available_tickets ;
            threadLogs.add(logData);
        }else{
            String logData = "vendor " + vendor_id + " cannot release more tickets since it has reached the maximum ticket capacity.";
            threadLogs.add(logData);
        }

    }

    //customers to retrieve the available tickets
    public synchronized void removeTicket(int customer_id, int customerRetrievalRate) {
        int current_available_tickets = available_tickets.size();

        if (current_available_tickets==0) {
            String logData = "No tickets available to purchase.";
            threadLogs.add(logData);
            return;
        }

        int ticketsToRemove = Math.min(customerRetrievalRate, current_available_tickets);
        for (int i=0; i<ticketsToRemove; i++){
            available_tickets.remove(0);
        }
        current_available_tickets = available_tickets.size();
        String logData = "Customer "+ customer_id + " removed " + customerRetrievalRate + " tickets. Remaining tickets: " + current_available_tickets;
        threadLogs.add(logData);

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

    public int getAvailableTickets(){
        return available_tickets.size();
    }


    public void updatePool(int totalTickets, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        available_tickets.clear();

        for(int i=0; i< totalTickets; i++){
            available_tickets.add(i);
        }
        System.out.println("Ticket pool updated.");
    }
}

