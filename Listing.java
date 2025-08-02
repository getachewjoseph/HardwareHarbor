/**
 * Project 5 -- Listing
 * <p>
 * This program represents a product listing. Products have prices, names, descriptions, store names (name of store
 * that they belong to), and quantity available
 *
 * @author Ella Budack, Joseph Getachew
 * @version December 6 2023
 */
public class Listing {
    private String name;
    private double price;
    private String description;
    private String storeName;
    private int quantity;

    public Listing(String name, double price, String description, String storeName, int quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.storeName = storeName;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String displayListing() {
        return String.format("Item: " + this.name + "\n" + "Price: %.2f\n",
                this.price);
    }

    public String marketplaceListing() {
        return String.format("Store: " + this.storeName + "\n" + "Item: " + this.name + "\n" + "Price: %.2f\n",
                this.price);
    }

    public String purchaseHistoryListing() {
        return String.format("Store: " + this.storeName + "\n" + "Item: " + this.name + "\n" + "Price: %.2f",
                this.price + "\n" + "Description: " + this.description + "\n");
    }

    public String exportFileListing() {
        return "Item: " + this.name + "\n" +
                String.format("Price: %.2f", this.price) + "\n" +
                "Description: " + this.description + "\n";
    }

    public String exportFilePurchaseHistory() {
        return "Item: " + this.name + "\n" +
                "Store: " + this.storeName + "\n" +
                String.format("Price: %.2f", this.price) + "\n" +
                "Description: " + this.description + "\n";
    }
}
