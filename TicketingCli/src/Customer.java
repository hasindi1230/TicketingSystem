public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customer_id;
    private int customerRetrievalRate;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int customer_id) {
        this.ticketPool = ticketPool;
        this.customer_id = customer_id;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (ticketPool) {
                if(ticketPool.totalTickets()>0){
                    ticketPool.removeTicket(customer_id, customerRetrievalRate);
                }else{
                    System.out.println("Customer " + customer_id + " has no tickets to purchase.");
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
