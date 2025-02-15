public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private int ticketReleaseRate;
    private int vendor_id;
    private int maximum_releases;


    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int vendor_id , int maximum_releases) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendor_id = vendor_id;
        this.maximum_releases = maximum_releases;

    }

    @Override
    public void run() {
        int releases = 0;

        while (releases < maximum_releases) {
            synchronized (ticketPool) {
                if(ticketPool.getTotalTickets() >= ticketPool.getMaxTicketCapacity()){
                    System.out.println("Vendor " + vendor_id + " can't release more tickets. Ticket-pool is full.");
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
            System.out.println("Vendor " + vendor_id + " has reached the maximum and can't release more tickets.");
        }

    }
}

