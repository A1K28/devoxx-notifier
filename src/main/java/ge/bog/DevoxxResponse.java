package ge.bog;

import java.util.List;

public class DevoxxResponse {
    private List<TicketCategory> ticketCategories;
    public static class TicketCategory {
        private String id;
        private String name;
        private Boolean saleableAndLimitNotReached;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getSaleableAndLimitNotReached() {
            return saleableAndLimitNotReached;
        }

        public void setSaleableAndLimitNotReached(Boolean saleableAndLimitNotReached) {
            this.saleableAndLimitNotReached = saleableAndLimitNotReached;
        }
    }

    public List<TicketCategory> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(List<TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }
}
