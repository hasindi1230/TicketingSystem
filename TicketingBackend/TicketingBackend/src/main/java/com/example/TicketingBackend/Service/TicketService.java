package com.example.TicketingBackend.Service;
import com.example.TicketingBackend.Domain.Configuration;
import com.example.TicketingBackend.Domain.Customer;
import com.example.TicketingBackend.Domain.TicketPool;

import com.example.TicketingBackend.Domain.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TicketService {

    private TicketPool ticketPool;
    private final Configuration configuration;
    private final ConcurrentLinkedQueue<String> threadLogs;

    @Autowired
    //in here we use DI to inject configuration object into ticketService.
    //so that can get access to parameters
    public TicketService(Configuration configuration) {
        this.configuration = configuration;
        this.threadLogs = new ConcurrentLinkedQueue<>();
        this.ticketPool = new TicketPool(configuration.getTotalTickets() , configuration.getMaxTicketCapacity(), threadLogs);
    }

    public int getTotalTickets() {
        return ticketPool.getTotalTickets();
    }

    public int getMaxTicketCapacity() {
        return ticketPool.getMaxTicketCapacity();
    }

    public int getAvailableTickets() {
        return ticketPool.getAvailableTickets();
    }

    public int getTicketReleaseRate(){
        return configuration.getTicketReleaseRate();
    }

    public int getCustomerRetrievalRate(){
        return configuration.getCustomerRetrievalRate();
    }

    public boolean isTicketPoolFull() {
        return ticketPool.isTicketPoolFull();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String setNewConfiguration(Configuration newConfig){
        configuration.setTotalTickets(newConfig.getTotalTickets());
        configuration.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());
        configuration.setTicketReleaseRate(newConfig.getTicketReleaseRate());
        configuration.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());

        ticketPool = new TicketPool(newConfig.getTotalTickets() , newConfig.getMaxTicketCapacity(), threadLogs);

        return "Configuration updated";
    }

    public void updatePool(int totalTickets, int maxTicketCapacity) {
        ticketPool.updatePool(totalTickets, maxTicketCapacity);
    }


    public ConcurrentLinkedQueue<String> getThreadingLogs(){
        return threadLogs;
    }

    public String startingThreads(ConcurrentLinkedQueue<String> threadLogs){
        int num_of_vendors = 3;
        int release_per_vendor = configuration.getTotalTickets() / num_of_vendors;

        List<String> threadList = new ArrayList<>(this.threadLogs);

        //assuming there are 3 vendors in the ticket pool
        Vendor vendor1 = new Vendor(ticketPool, configuration.getTicketReleaseRate(), 1, release_per_vendor, threadLogs);
        Vendor vendor2 = new Vendor(ticketPool, configuration.getTicketReleaseRate(), 2, release_per_vendor, threadLogs);
        Vendor vendor3 = new Vendor(ticketPool, configuration.getTicketReleaseRate(), 3, release_per_vendor, threadLogs);

        //assuming there are 3 customers in the ticket pool
        Customer customer1 = new Customer(ticketPool, configuration.getCustomerRetrievalRate(), 1, threadLogs);
        Customer customer2 = new Customer(ticketPool, configuration.getCustomerRetrievalRate(), 2, threadLogs);
        Customer customer3 = new Customer(ticketPool, configuration.getCustomerRetrievalRate(), 3, threadLogs);

        Thread vendorThread1 = new Thread(vendor1);
        Thread vendorThread2 = new Thread(vendor2);
        Thread vendorThread3 = new Thread(vendor3);

        Thread customerThread1 = new Thread( customer1);
        Thread customerThread2 = new Thread( customer2);
        Thread customerThread3 = new Thread( customer3);

        vendorThread1.start();
        vendorThread2.start();
        vendorThread3.start();

        customerThread1.start();
        customerThread2.start();
        customerThread3.start();

        return "Threads started.";
    }

}

