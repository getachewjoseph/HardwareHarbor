/**
 * Project 4 -- Files
 * <p>
 * This program allows sellers to upload products to their store and export products from their store
 * It also allows customers to export their product history
 *
 * @author Ella Budack
 * @version November 13 2023
 */

/**
 * Project 5 -- Files
 * <p>
 * This program allows sellers to upload products to their store and export products from their store
 * It also allows customers to export their product history
 *
 * @author
 * @version November 13 2023
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Files {

    public static void uploadProduct(Seller boater, BufferedReader serverReader, PrintWriter serverWriter) {

        String storeName = "";
        int indexOfStore = -1;
        try {
            serverWriter.println("uploadStore");
            serverWriter.flush();
            String store = serverReader.readLine();
            if (store.equals("JOptionPane == null")) {
                return;
            }
            ArrayList<Listing> newProducts = Files.importProducts(serverReader, serverWriter);
            if (newProducts == null) {
                serverWriter.println("uploadFailed");
                serverWriter.flush();
            } else {
                for (int i = 0; i < boater.getStores().size(); i++) {
                    if (store.equals(boater.getStores().get(i).getName())) {
                        indexOfStore = i;
                    } else {
                        serverWriter.println("storeNotFound");
                        serverWriter.flush();
                    }
                }
                if (indexOfStore == -1) {
                    serverWriter.println("uploadFailed");
                    serverWriter.flush();
                } else {
                    for (int i = 0; i < newProducts.size(); i++) {
                        boater.getStores().get(indexOfStore).addProduct(newProducts.get(i));
                    }
                    serverWriter.println("uploadSuccessful");
                    serverWriter.flush();
                }
            }
        } catch (Exception ex) {
            serverWriter.println("uploadFailed");
            serverWriter.flush();
        }
    }
    public static ArrayList<Listing> importProducts(BufferedReader serverReader, PrintWriter serverWriter)
            throws IOException {
        ArrayList<Listing> importedProducts = new ArrayList<>();

        serverWriter.println("uploadInstructions");
        serverWriter.flush();

        serverWriter.println("uploadFilePath");
        serverWriter.flush();
        String filePath = serverReader.readLine();

        String fileName = filePath.substring((filePath.lastIndexOf('/') + 1));

        String extension = fileName.substring((fileName.lastIndexOf('.') + 1));

        if (extension.equalsIgnoreCase("csv")) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(filePath))) {
                String line = null;
                while ((line = bfr.readLine()) != null) {
                    String[] productDetails = line.split(",");
                    String productName = productDetails[0];
                    double price = Double.parseDouble(productDetails[1]);
                    String description = productDetails[2];
                    String storeName = productDetails[3];
                    int quantityAvailable = Integer.parseInt(productDetails[4]);
                    Listing product = new Listing(productName, price, description, storeName, quantityAvailable);
                    importedProducts.add(product);
                }
            } catch (Exception ex) {
                serverWriter.println("uploadFailed");
                serverWriter.flush();
            }
        } else {
            serverWriter.println("notCSV");
            serverWriter.flush();
            return null;
        }
        return importedProducts;
    }

    public static void exportProducts(Seller boater, BufferedReader serverReader, PrintWriter serverWriter) {
        try {
            serverWriter.println("exportStore");
            serverWriter.flush();
            String store = serverReader.readLine();
            if (store.equals("JOptionPane == null")) {
                return;
            }
            String storeName = "";
            int indexOfStore = -1;
            for (int i = 0; i < boater.getStores().size(); i++) {
                if (store.equals(boater.getStores().get(i).getName())) {
                    indexOfStore = i;
                }
            }
            if (indexOfStore == -1) {
                serverWriter.println("storeNotFound");
                serverWriter.flush();
            } else {
                Files.actuallyExportProducts(boater.getStores().get(indexOfStore).getProducts(), serverReader,
                        serverWriter);
            }
        } catch (Exception ex) {
            serverWriter.println("exportFailed");
            serverWriter.flush();
        }
    }
    public static void actuallyExportProducts(ArrayList<Listing> products, BufferedReader serverReader, PrintWriter
            serverWriter) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream((products.get(0).getStoreName() + ".txt"), true));
        pw.println(products.get(0).getStoreName() + " Products:");
        int itemCounter = 1;
        for (Listing product : products) {
            pw.println("Item " + itemCounter++ + ":");
            pw.println(product.exportFileListing());
        }
        pw.close();
        serverWriter.println("exportSuccessful");
        serverWriter.flush();
    }

    public static void exportPurchaseHistory(String name, ArrayList<Listing> purchaseHistory, BufferedReader
            serverReader, PrintWriter serverWriter) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream((name + "PurchaseHistory.txt")));
        pw.println(name + "'s Purchase History:");
        int itemCounter = 1;
        for (Listing listing : purchaseHistory) {
            pw.println("Item " + itemCounter++ + ":");
            pw.println(listing.exportFilePurchaseHistory());
        }
        pw.close();
        serverWriter.println("exportSuccessful");
        serverWriter.flush();
    }
}
