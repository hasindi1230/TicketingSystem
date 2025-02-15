package com.example.TicketingBackend.Controller;

import com.example.TicketingBackend.Domain.Configuration;
import com.example.TicketingBackend.Domain.Vendor;
import com.example.TicketingBackend.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

//used when creating rest apis
@RestController
@RequestMapping("/restapi/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {

    private final ConcurrentLinkedQueue<String> threadLogs = new ConcurrentLinkedQueue<>();

    @Autowired
    private TicketService ticketService;
    private Configuration configuration;

    @Autowired
    public TicketController(TicketService ticketService, Configuration configuration) {
        this.ticketService = ticketService;
        this.configuration = configuration;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startingSystem(){
        String responseBody = ticketService.startingThreads(threadLogs);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem(){
        Vendor.stop();
        return new ResponseEntity<>("System Stopped",HttpStatus.OK);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<String>> getLogs(){
//        Converting a concurrent linkedQueue to a list
        List<String> logs = new ArrayList<>(ticketService.getThreadingLogs());
        if(!logs.isEmpty()){
            return new ResponseEntity<>(logs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/newConfig")
    public String setNewConfiguration(@RequestBody Configuration newConfiguration){
        configuration.setMaxTicketCapacity(newConfiguration.getMaxTicketCapacity());
        configuration.setTotalTickets(newConfiguration.getTotalTickets());
        configuration.setTicketReleaseRate(newConfiguration.getTicketReleaseRate());
        configuration.setCustomerRetrievalRate(newConfiguration.getCustomerRetrievalRate());

        ticketService.updatePool(newConfiguration.getTotalTickets(), newConfiguration.getMaxTicketCapacity());

        //saving to a text file
        saveNewConfiguration(newConfiguration);

        return "Configuration set";
        //return new ResponseEntity<>(configuration.toString(), HttpStatus.OK);
    }

    private void saveNewConfiguration(Configuration configuration){
        try{
            File lodDirectory = new File("logs");
            if(!lodDirectory.exists()){
                lodDirectory.mkdir();
            }

            File newConfigFile = new File(lodDirectory,  "Configuration.txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(newConfigFile,false))){
                writer.write("Max ticket capacity: " + configuration.getMaxTicketCapacity() + "\n");
                writer.write("Total tickets: " + configuration.getTotalTickets() + "\n");
                writer.write("Ticket release rate: " + configuration.getTicketReleaseRate() + "\n");
                writer.write("Customer retrieval rate: " + configuration.getCustomerRetrievalRate() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }


    @PostMapping("/logSave")
    public ResponseEntity<String> saveLogs(@RequestBody List<String> logs) {
        try{
            File logFile = new File("logs/ticketLogs.txt");

            File parentFile = logFile.getParentFile();
            if(!logFile.exists()){
                parentFile.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile , false))){
                for (String log : logs){
                    writer.write(log);
                    writer.newLine();
                }
            }
            return new ResponseEntity<>("Logs Saved", HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Log Save Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //to fetch current configuration
    @GetMapping("/configuration")
    public ResponseEntity <Configuration> getConfiguration(){
        Configuration configuration = ticketService.getConfiguration();
        return new ResponseEntity<>(configuration, HttpStatus.OK);
    }

//    To fetch current total tickets
    @GetMapping("/totalTickets")
    public ResponseEntity <Integer> getTotalTickets() {
        Integer totalTickets = ticketService.getTotalTickets();
        return new ResponseEntity<>(totalTickets, HttpStatus.OK);
    }

//    To fetch current maximum capacity
    @GetMapping("/maxCapacity")
    public ResponseEntity <Integer> getMaxTickets() {
        int maxTickets = ticketService.getMaxTicketCapacity();
        return new ResponseEntity<>(maxTickets, HttpStatus.OK);
    }

//    To fetch current available tickets
    @GetMapping("/availableTickets")
    public ResponseEntity <Integer> getAvailableTickets() {
        int availableTickets = ticketService.getAvailableTickets();
        return new ResponseEntity<>(availableTickets, HttpStatus.OK);
    }

//    To fetch current ticket release rate
    @GetMapping("/releaseRate")
    public ResponseEntity <Integer> getTicketReleaseRate() {
        int ticketReleaseRate = ticketService.getTicketReleaseRate();
        return new ResponseEntity<>(ticketReleaseRate, HttpStatus.OK);
    }

//    To fetch if the current pool is full
    @GetMapping("/isPoolFull")
    public ResponseEntity <Boolean> isTicketPoolFull(){
        boolean ticketPoolFull = ticketService.isTicketPoolFull();
        return new ResponseEntity<>(ticketPoolFull, HttpStatus.OK);
    }
}
