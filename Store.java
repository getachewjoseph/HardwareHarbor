import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Project 5 -- Store
 * <p>
 * This program represents a store with a name and an array list of products, it also includes other methods like
 * adding/removing products from the store, and displaying the products in a store
 *
 * @author Joseph Getachew, Ella Budack, Hunter Specht
 * @version December 6 2023
 */
public class Store {
    private String name;
    private ArrayList<Listing> products;
    private double revenue;

    public Store(String name, ArrayList<Listing> products) {
        this.name = name;
        this.products = products;
        this.revenue = 0;
    }

    public Store(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Listing> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Listing> products) {
        this.products = products;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void addProduct(Listing listing) {
        this.products.add(listing);
    }

    public String displayStore() {
        StringBuilder displayStore = new StringBuilder();
        displayStore.append("Store: ").append(this.name).append("\n");
        for (Listing i : products) {
            displayStore.append(i.displayListing());
        }
        return displayStore.toString();
    }

    public Listing createListing(BufferedReader serverReader, PrintWriter serverWriter) throws IOException {
        serverWriter.println("itemName");
        serverWriter.flush();
        String productName = serverReader.readLine();
        if (productName.equals("JOptionPane == null")) {
            return null;
        }
        boolean priceCorrect;
        double price = 0.0;
        do {
            priceCorrect = true;
            serverWriter.println("itemPrice");
            serverWriter.flush();
            String productPrice = serverReader.readLine();
            if (productPrice.equals("JOptionPane == null")) {
                return null;
            }
            try {
                String formatPrice = String.format("%.2f", Double.parseDouble(productPrice));
                price = Double.parseDouble(formatPrice);
            } catch (Exception ex) {
                serverWriter.println("priceWrongFormat");
                serverWriter.flush();
                priceCorrect = false;
            }
        } while (!priceCorrect);
        serverWriter.println("itemDescription");
        serverWriter.flush();
        String description = serverReader.readLine();
        if (description.equals("JOptionPane == null")) {
            return null;
        }
        boolean quantityCorrect;
        int quantity = 0;
        do {
            serverWriter.println("itemQuantity");
            serverWriter.flush();
            String productQuantity = serverReader.readLine();
            if (productQuantity.equals("JOptionPane == null")) {
                return null;
            }
            try {
                String formatQuantity = String.format("%d", Integer.parseInt(productQuantity));
                quantity = Integer.parseInt(formatQuantity);
                quantityCorrect = true;
            } catch (Exception ex) {
                serverWriter.println("quantityWrongFormat");
                serverWriter.flush();
                quantityCorrect = false;
            }
        } while (!quantityCorrect);
        return new Listing(productName, price, description, this.name, quantity);
    }
}
