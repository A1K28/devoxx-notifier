package ge.bog;

import java.util.Locale;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        MailService mailService = new MailService();
        DevoxxService devoxxService = new DevoxxService();
        boolean wasSuccess = false;
        long lastExceptionTime = System.currentTimeMillis() - 60 * 60 * 1000;

        while (true) {
            long currentTime = System.currentTimeMillis();

            try {
                DevoxxResponse devoxxResponse = devoxxService.sendRequest();
                Optional<DevoxxResponse.TicketCategory> matched = devoxxResponse.getTicketCategories().stream()
                        .filter(ticketCategory -> ticketCategory.getName()
                                .toLowerCase(Locale.ROOT).contains("conference"))
                        .filter(ticketCategory -> {
                            Logger.info("Found item with name: " + ticketCategory.getName()
                                    + " & saleableAndLimitNotReached: " + ticketCategory.getSaleableAndLimitNotReached());
                            return ticketCategory.getSaleableAndLimitNotReached();
                        })
                        .findFirst();

                if (matched.isPresent()) {
                    Logger.info("Successfully found available tickets...");
                    mailService.notifyDevoxx(matched.get().getName());
                    wasSuccess = true;
                }
            } catch (Throwable e) {
                Logger.error("An exception has occurred. Still continuing the process...", e);
                // notify once every hour
                if (currentTime - lastExceptionTime >= 60 * 60 * 1000) {
                    try {
                        mailService.sendErrorMail(e.getMessage());
                        lastExceptionTime = currentTime;
                    } catch (Throwable e2) {
                        Logger.error("Could not send error mail", e);
                    }
                }
            }

            try {
                if (wasSuccess) {
                    // 5 mins
                    Logger.info("Sleeping for 5 minutes...");
                    Thread.sleep(5*60*1000);
                    wasSuccess = false;
                } else {
                    Logger.info("Sleeping for 10 seconds");
                    // 10 seconds
                    Thread.sleep(10*1000);
                }
            } catch (InterruptedException e) {
                Logger.error("Thread could not sleep", e);
            }
        }
    }
}