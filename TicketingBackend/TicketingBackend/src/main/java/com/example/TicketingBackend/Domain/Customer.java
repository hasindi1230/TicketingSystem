package com.example.TicketingBackend.Domain;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Queue;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customer_id;
    private int customerRetrievalRate;
    private Queue<String> threadLogs;


    public Customer(TicketPool ticketPool, int customerRetrievalRate, int customer_id, Queue<String> threadLogs) {
        this.ticketPool = ticketPool;
        this.customer_id = customer_id;
        this.customerRetrievalRate = customerRetrievalRate;
        this.threadLogs = threadLogs;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (ticketPool) {
                if(ticketPool.totalTickets()>0){
                    ticketPool.removeTicket(customer_id, customerRetrievalRate);
                }else{
                    String logData = "Customer " + customer_id + " has no tickets to purchase.";
                    synchronized (threadLogs) {
                        threadLogs.add(logData);
                    }
                    break;
                }
            }
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                break;
            }

        }
    }
}

