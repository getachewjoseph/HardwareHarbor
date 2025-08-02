import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Project 5 -- Statistics
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 * The users of hardwareHarbor will be able to view and sort their sales
 * The buyers can also view where and what they have bought
 * Sellers can view their revenue
 *
 * @author Juan Rodriguez, Hunter Specht
 * @version December 6 2023
 */
public class Statistics {
    public static StringBuilder displayStoresRevenuesText;
    public static StringBuilder displayCartItemsText;
    public static void displaySellerDashboard(ArrayList<Store> stores, BufferedReader serverReader, PrintWriter
            serverWriter) throws IOException {
        ArrayList<Buyer> buyers = HardwareHarbor.readDataBuyer(serverReader, serverWriter);
        boolean statsRepeat = true;
        do {
            serverWriter.println("sellerDashboard");
            serverWriter.flush();
            String StatsChoice = serverReader.readLine();
            switch (StatsChoice) {
                case "View Store Sales":
                    if (stores.isEmpty()) {
                        serverWriter.println("storesEmpty");
                        serverWriter.flush();
                    } else {
                        for (Store store : stores) {
                            displayStoresRevenuesText = new StringBuilder();
                            displayStoresRevenuesText.append(store.getName()).append(":\n");
                            for (Listing listing : store.getProducts()) {
                                for (Buyer buyer : buyers) {
                                    for (Listing phListing : buyer.getPurchaseHistory()) {
                                        if (listing.getName().equals(phListing.getName()) && listing.getDescription().
                                                equals(phListing.getDescription())) {
                                            displayStoresRevenuesText.append(buyer.getUsername()).append(" ").
                                                    append(listing.getName()).append(" ").append(listing.getPrice()).
                                                    append("\n");
                                        }
                                    }
                                }
                            }
                            displayStoresRevenuesText.append("Total store revenue: ").append(store.getRevenue()).
                                    append("\n");
                            serverWriter.println("storesRevenue");
                            serverWriter.flush();
                        }
                    }
                    break;
                case "View Items in Cart":
                    if (stores.isEmpty()) {
                        serverWriter.println("storesEmpty");
                        serverWriter.flush();
                    } else {
                        for (Store store : stores) {
                            StringBuilder displayCartItemsText = new StringBuilder();
                            displayCartItemsText.append(store.getName()).append(":\n");
                            for (Listing listing : store.getProducts()) {
                                for (Buyer buyer : buyers) {
                                    for (Listing cartListing : buyer.getCart()) {
                                        if (listing.getName().equals(cartListing.getName()) && listing.getDescription().
                                                equals(cartListing.getDescription())) {
                                            displayCartItemsText.append(buyer.getUsername()).append(" ").append(listing.
                                                    getName()).append(" ").append(listing.getPrice()).append("\n");
                                        }
                                    }
                                }
                            }
                            serverWriter.println("cartItems");
                            serverWriter.flush();
                        }
                    }
                    break;
                case "Exit" :
                    statsRepeat = false;
                    break;
            }
        } while (statsRepeat);
    }
}