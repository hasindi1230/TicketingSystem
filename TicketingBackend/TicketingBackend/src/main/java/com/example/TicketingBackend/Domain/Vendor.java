package com.example.TicketingBackend.Domain;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Queue;

public class Vendor implements Runnable {

    private TicketPool ticketPool;
    private int ticketReleaseRate;
    private int vendor_id;
    private int maximum_releases;
    private final Queue<String> threadLogs;

    private static volatile boolean running = true;


    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int vendor_id , int maximum_releases, Queue<String> threadLogs) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendor_id = vendor_id;
        this.maximum_releases = maximum_releases;
        this.threadLogs = threadLogs;
    }

    public static void stop(){
        running = false;
    }

    @Override
    public void run() {
        int releases = 0;

        while (releases < maximum_releases) {
            synchronized (ticketPool) {
                if(ticketPool.getTotalTickets() >= ticketPool.getMaxTicketCapacity()){
                    String logData = "Vendor " + vendor_id + " can't release more tickets. Ticket-pool is full.";

                    synchronized (threadLogs) {
                        threadLogs.add(logData);
                    }
                    break;
                }

                ticketPool.addTicket(vendor_id, ticketReleaseRate);
                releases++;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if(releases >= maximum_releases){
            String logData = "Vendor " + vendor_id + " has reached the maximum and can't release more tickets.";
            System.out.println(logData);
            synchronized (threadLogs) {
                threadLogs.add(logData);
            }
        }
    }
}

