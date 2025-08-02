import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Project 5 -- Seller
 * <p>
 * This program represents a seller. It extends User. A seller has a username, password, and an array list of stores.
 * Sellers can also add stores
 *
 * @author Joseph Getachew, Hunter Specht
 * @version December 6 2023
 */
public class Seller extends User {
    private ArrayList<Store> stores;
    public static String displayStoresText = ":)";

    public Seller (String username, String password, ArrayList<Store> stores) {
        super(username, password);
        this.stores = stores;
    }

    public Seller() {
        super();
        this.stores = new ArrayList<>();
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void sellerMenu(Seller boater, BufferedReader serverReader, PrintWriter serverWriter) throws IOException {
        for (Seller seller : HardwareHarbor.readDataSeller(serverReader, serverWriter)) {
            if (boater.getUsername().equals(seller.getUsername())) {
                boater = seller;
            }
        }
        while (true) {
            serverWriter.println("sellerMenu");
            serverWriter.flush();
            String sellerMenuChoice = serverReader.readLine();
            switch (sellerMenuChoice) {
                case "View Stores" -> {
                    if (boater.stores.isEmpty()) {
                        serverWriter.println("storesEmpty");
                        serverWriter.flush();
                    } else {
                        displayStoresText = "";
                        for (Store store : this.stores) {
                            displayStoresText += store.displayStore();
                        }
                        serverWriter.println("displayStores");
                        serverWriter.flush();
                        serverWriter.println(displayStoresText);
                        serverWriter.flush();
                        serverWriter.println("stop");
                        serverWriter.flush();
                    }
                }
                case "Create New Store" -> {
                    serverWriter.println("storeName");
                    serverWriter.flush();
                    String storeName = serverReader.readLine();
                    if (storeName.equals("JOptionPane == null")) {
                        break;
                    }
                    serverWriter.println("addListingOption");
                    serverWriter.flush();
                    String addListingOption = serverReader.readLine();
                    switch (addListingOption) {
                        case "Yes" -> {
                            Store store = new Store(storeName);
                            ArrayList<Listing> products = new ArrayList<>();
                            boolean addAnotherListing = true;
                            do {
                                Listing listing = store.createListing(serverReader, serverWriter);
                                if (listing == null) {
                                    break;
                                } else {
                                    products.add(listing);
                                }
                                store.setProducts(products);
                                serverWriter.println("addAnotherListingOption");
                                serverWriter.flush();
                                String anotherListingOption = serverReader.readLine();
                                if (!anotherListingOption.equals("Yes")) {
                                    addAnotherListing = false;
                                }
                            } while (addAnotherListing);
                            boater.stores.add(new Store(storeName, products));
                        }
                        case "No", "Exit" -> {
                            boater.stores.add(new Store(storeName));
                        }
                    }
                    storeDataSeller(serverReader, serverWriter, boater);
                    serverWriter.println("storeAdded");
                    serverWriter.flush();
                }
                case "Delete Store" -> {
                    boolean removalChosen;
                    do {
                        removalChosen = true;
                        serverWriter.println("storeRemoval");
                        serverWriter.flush();
                        String storeRemoval = serverReader.readLine();
                        if (storeRemoval.equals("JOptionPane == null")) {
                            break;
                        }
                        int counter = 0;
                        for (int i = 0; i < boater.stores.size(); i++) {
                            if (boater.stores.get(i).getName().equals(storeRemoval)) {
                                boater.stores.remove(i);
                                counter++;
                            }
                        }
                        if (counter == 0) {
                            serverWriter.println("noStoresFound");
                            serverWriter.flush();
                            serverWriter.println("removeDifferentStore");
                            serverWriter.flush();
                            String removalAgain = serverReader.readLine();
                            switch (removalAgain) {
                                case "Yes" -> removalChosen = false;
                                case "No", "Exit" -> {
                                }
                            }
                        } else {
                            storeDataSeller(serverReader, serverWriter, boater);
                            serverWriter.println("storeRemoved");
                            serverWriter.flush();
                        }
                    } while (!removalChosen);
                }
                case "Store Statistics" -> Statistics.displaySellerDashboard(boater.getStores(), serverReader,
                        serverWriter);
                case "Create New Listing" -> {
                    serverWriter.println("createNewListing");
                    serverWriter.flush();
                    String storeName2 = serverReader.readLine();
                    if (storeName2.equals("JOptionPane == null")) {
                        break;
                    }
                    Listing newProduct = Seller.createUnassignedListing(serverReader, serverWriter, storeName2);
                    if (newProduct != null) {
                        for (int i = 0; i < boater.stores.size(); i++) {
                            if (storeName2.equals(boater.stores.get(i).getName())) {
                                boater.stores.get(i).addProduct(newProduct);
                            }
                        }
                    }
                    storeDataSeller(serverReader, serverWriter, boater);
                    serverWriter.println("itemAdded");
                    serverWriter.flush();
                }
                case "Edit Listing" -> {
                    boolean editChoiceMade;
                    editChoiceMade = true;
                    serverWriter.println("editListing");
                    serverWriter.flush();
                    String editChoice = serverReader.readLine();
                    switch (editChoice) {
                        case "Edit Listing":
                            serverWriter.println("itemStore");
                            serverWriter.flush();
                            String storeName3 = serverReader.readLine();
                            if (storeName3.equals("JOptionPane == null")) {
                                break;
                            }
                            boolean storeExists = false;
                            int storeIndex = -1;
                            for (int i = 0; i < boater.stores.size(); i++) {
                                if (storeName3.equals(boater.stores.get(i).getName())) {
                                    storeExists = true;
                                    storeIndex = i;
                                    break;
                                }
                            }
                            if (!storeExists) {
                                serverWriter.println("storeNotFound");
                                serverWriter.flush();
                                break;
                            }
                            serverWriter.println("itemName");
                            serverWriter.flush();
                            String productName = serverReader.readLine();
                            if (productName.equals("JOptionPane == null")) {
                                break;
                            }
                            boolean itemExists = false;
                            int itemIndex = -1;
                            for (int i = 0; i < boater.stores.get(storeIndex).getProducts().size(); i++) {
                                if (productName.equals(boater.stores.get(storeIndex).getProducts().get(i).getName())) {
                                    itemExists = true;
                                    itemIndex = i;
                                    break;
                                }
                            }
                            if (!itemExists) {
                                serverWriter.println("itemNotFound");
                                serverWriter.flush();
                                break;
                            }
                            boolean priceCorrect;
                            double price = 0.0;
                            do {
                                priceCorrect = true;
                                serverWriter.println("itemNewPrice");
                                serverWriter.flush();
                                String productPrice = serverReader.readLine();
                                if (productPrice.equals("JOptionPane == null")) {
                                    break;
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
                            serverWriter.println("itemNewDescription");
                            serverWriter.flush();
                            String description = serverReader.readLine();
                            if (description.equals("JOptionPane == null")) {
                                break;
                            }
                            boolean quantityCorrect;
                            int quantity = 0;
                            do {
                                serverWriter.println("itemNewQuantity");
                                serverWriter.flush();
                                String productQuantity = serverReader.readLine();
                                if (productQuantity.equals("JOptionPane == null")) {
                                    break;
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
                            Listing listing = new Listing(productName, price, description,
                                    storeName3, quantity);
                            boater.stores.get(storeIndex).getProducts().set(itemIndex, listing);
                            storeDataSeller(serverReader, serverWriter, boater);
                            break;
                        case "Remove Listing":
                            serverWriter.println("itemStore");
                            serverWriter.flush();
                            String storeName4 = serverReader.readLine();
                            boolean storeExists2 = false;
                            int storeIndex2 = -1;
                            for (int i = 0; i < boater.stores.size(); i++) {
                                if (storeName4.equals(boater.stores.get(i).getName())) {
                                    storeExists2 = true;
                                    storeIndex2 = i;
                                    break;
                                }
                            }
                            if (!storeExists2) {
                                serverWriter.println("storeNotFound");
                                serverWriter.flush();
                                break;
                            }
                            serverWriter.println("itemName");
                            serverWriter.flush();
                            String productName2 = serverReader.readLine();
                            boolean itemExists2 = false;
                            int itemIndex2 = -1;
                            for (int i = 0; i < boater.stores.get(storeIndex2).getProducts().size(); i++) {
                                if (productName2.equals(boater.stores.get(storeIndex2).getProducts().get(i).getName()))
                                {
                                    itemExists2 = true;
                                    itemIndex2 = i;
                                    break;
                                }
                            }
                            if (!itemExists2) {
                                serverWriter.println("itemNotFound");
                                serverWriter.flush();
                                break;
                            }
                            boater.stores.get(storeIndex2).getProducts().remove(itemIndex2);
                            storeDataSeller(serverReader, serverWriter, boater);
                            break;
                        case "Exit" :
                            break;
                    }
                }
                case "Upload Products" -> {
                    Files.uploadProduct(boater, serverReader, serverWriter);
                    storeDataSeller(serverReader, serverWriter, boater);
                }
                case "Export Products" -> {
                    Files.exportProducts(boater, serverReader, serverWriter);
                    storeDataSeller(serverReader, serverWriter, boater);
                }
                case "Edit Account" -> {
                    serverWriter.println("editAccountChoice");
                    serverWriter.flush();
                    String editAccountChoice = serverReader.readLine();
                    switch (editAccountChoice) {
                        case "Change Password" -> {
                            File userDataTemp = new File("userDataTemp.txt");
                            try {
                                userDataTemp.createNewFile();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            boolean passwordRequirementsMet;
                            String newPassword;
                            do {
                                boolean capitalLetter = false;
                                boolean lowercaseLetter = false;
                                boolean number = false;
                                passwordRequirementsMet = true;
                                serverWriter.println("password");
                                serverWriter.flush();
                                newPassword = serverReader.readLine();
                                for (int i = 0; i < newPassword.length(); i++) {
                                    char character = newPassword.charAt(i);
                                    if (Character.isUpperCase(character)) {
                                        capitalLetter = true;
                                    } else if (Character.isLowerCase(character)) {
                                        lowercaseLetter = true;
                                    } else if (Character.isDigit(character)) {
                                        number = true;
                                    }
                                }
                                if (!capitalLetter || !lowercaseLetter || !number || newPassword.length() < 8) {
                                    serverWriter.println("passwordRequirementError");
                                    serverWriter.flush();
                                    passwordRequirementsMet = false;
                                }
                                if (newPassword.contains(" ")) {
                                    serverWriter.println("passwordSpaceError");
                                    serverWriter.flush();
                                    passwordRequirementsMet = false;
                                }
                            } while (!passwordRequirementsMet);
                            synchronized (HardwareHarborServer.userDataGatekeeper) {
                                try (BufferedReader dataBFR = new BufferedReader(new
                                        FileReader("userData.txt"))) {
                                    try (BufferedReader dataBFR2 = new BufferedReader(new
                                            FileReader("userDataTemp.txt"))) {
                                        String line = null;
                                        while ((line = dataBFR.readLine()) != null) {
                                            try (PrintWriter dataPW2 = new
                                                    PrintWriter(new BufferedWriter(new
                                                    FileWriter("userDataTemp.txt", true)))) {
                                                if (line.equals(boater.getUsername() + " " + boater.getPassword() +
                                                        " " + "seller")) {
                                                    dataPW2.println(boater.getUsername() + " " + newPassword +
                                                            " seller");
                                                    dataPW2.flush();
                                                } else {
                                                    dataPW2.println(line);
                                                    dataPW2.flush();
                                                }
                                            } catch (Exception ex) {
                                                serverWriter.println("passwordChangeUnsuccessful");
                                                serverWriter.flush();
                                            }
                                        }
                                        line = dataBFR2.readLine();
                                        try (PrintWriter dataPW = new PrintWriter(new BufferedWriter(new
                                                FileWriter("userData.txt")))) {
                                            dataPW.println(line);
                                            dataPW.flush();
                                        } catch (Exception ex) {
                                            serverWriter.println("passwordChangeUnsuccessful");
                                            serverWriter.flush();
                                        }
                                        while ((line = dataBFR2.readLine()) != null) {
                                            try (PrintWriter dataPW = new
                                                    PrintWriter(new BufferedWriter(new
                                                    FileWriter("userData.txt", true)))) {
                                                dataPW.println(line);
                                                dataPW.flush();
                                            } catch (Exception ex) {
                                                serverWriter.println("passwordChangeUnsuccessful");
                                                serverWriter.flush();
                                            }
                                        }
                                        serverWriter.println("passwordChangeSuccessful");
                                        serverWriter.flush();
                                    } catch (Exception ex) {
                                        serverWriter.println("passwordChangeUnsuccessful");
                                        serverWriter.flush();
                                    }
                                } catch (Exception ex) {
                                    serverWriter.println("passwordChangeUnsuccessful");
                                    serverWriter.flush();
                                }
                                userDataTemp.delete();
                            }
                            boater.setPassword(newPassword);
                        }
                        case "Delete Account" -> {
                            File userDataTemp = new File("userDataTemp.txt");
                            try {
                                userDataTemp.createNewFile();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            synchronized (HardwareHarborServer.userDataGatekeeper) {
                                try (BufferedReader dataBFR = new BufferedReader(new
                                        FileReader("userData.txt"))) {
                                    try (BufferedReader dataBFR2 = new BufferedReader(new
                                            FileReader("userDataTemp.txt"))) {
                                        String line = null;
                                        while ((line = dataBFR.readLine()) != null) {
                                            try (PrintWriter dataPW2 = new PrintWriter(new
                                                    BufferedWriter(new
                                                    FileWriter("userDataTemp.txt", true)))) {
                                                if (!line.equals(boater.getUsername() + " " +
                                                        boater.getPassword() + " " + "seller")) {
                                                    dataPW2.println(line);
                                                    dataPW2.flush();
                                                }
                                            } catch (Exception ex) {
                                                serverWriter.println("accountDeletionUnsuccessful");
                                                serverWriter.flush();
                                            }
                                        }
                                        line = dataBFR2.readLine();
                                        try (PrintWriter dataPW = new PrintWriter(new BufferedWriter(new
                                                FileWriter("userData.txt")))) {
                                            dataPW.println(line);
                                            dataPW.flush();
                                        } catch (Exception ex) {
                                            serverWriter.println("accountDeletionUnsuccessful");
                                            serverWriter.flush();
                                        }
                                        while ((line = dataBFR2.readLine()) != null) {
                                            try (PrintWriter dataPW = new PrintWriter(new BufferedWriter(new
                                                    FileWriter("userData.txt", true)))) {
                                                dataPW.println(line);
                                                dataPW.flush();
                                            } catch (Exception ex) {
                                                serverWriter.println("accountDeletionUnsuccessful");
                                                serverWriter.flush();
                                            }
                                        }
                                        serverWriter.println("accountDeletionSuccessful");
                                        serverWriter.flush();
                                    } catch (Exception ex) {
                                        serverWriter.println("accountDeletionUnsuccessful");
                                        serverWriter.flush();
                                    }
                                } catch (Exception ex) {
                                    serverWriter.println("accountDeletionUnsuccessful");
                                    serverWriter.flush();
                                }
                                userDataTemp.delete();
                            }
                            return;
                        }
                    }
                }
                case "Logout", "Exit" -> {
                    storeDataSeller(serverReader, serverWriter, boater);
                    return;
                }
                default -> {
                    return;
                }
            }
        }
    }

    public static Listing createUnassignedListing(BufferedReader serverReader, PrintWriter serverWriter,
                                                  String storeName) throws IOException {
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
        return new Listing(productName, price, description, storeName, quantity);
    }

    public static void storeDataSeller(BufferedReader serverReader, PrintWriter serverWriter, Seller seller) {
        File storeDataSeller = new File("storeDataSeller.txt");
        try {
            storeDataSeller.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        synchronized (HardwareHarborServer.sellerDataGatekeeper) {
            ArrayList<Seller> sellers = HardwareHarbor.readDataSeller(serverReader, serverWriter);
            boolean rewrite = false;
            for (int i = 0; i < sellers.size(); i++) {
                if (seller.getUsername().equals(sellers.get(i).getUsername())) {
                    rewrite = true;
                    break;
                }
            }
            if (rewrite) {
                File storeDataSellerTemp = new File("storeDataSellerTemp.txt");
                try {
                    storeDataSellerTemp.createNewFile();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try (BufferedReader storedDataTempBR = new BufferedReader(new
                        FileReader("storeDataSellerTemp.txt"))) {
                    try (BufferedReader storedDataBR = new BufferedReader(new
                            FileReader("storeDataSeller.txt"))) {
                        try (PrintWriter SellerTempPW = new PrintWriter(new BufferedWriter(new
                                FileWriter("storeDataSellerTemp.txt", true)))) {
                            String line;
                            while ((line = storedDataBR.readLine()) != null) {
                                if (!line.equals(seller.getUsername() + "|" + seller.getPassword())) {
                                    SellerTempPW.println(line);
                                    SellerTempPW.flush();
                                } else {
                                    while ((line = storedDataBR.readLine()) != null) {
                                        if (line.charAt(0) != '[' && line.charAt(0) != ']' &&
                                                !line.equals("end seller") && !line.equals("end store")) {
                                            SellerTempPW.println(line);
                                            break;
                                        }
                                    }
                                }
                            }
                            try (PrintWriter SellerPW = new PrintWriter(new BufferedWriter(new
                                    FileWriter("storeDataSeller.txt")))) {
                            } catch (Exception ex) {
                                serverWriter.println("sellerDataSaveError");
                                serverWriter.flush();
                            }
                            try (PrintWriter SellerPW = new PrintWriter(new BufferedWriter(new
                                    FileWriter("storeDataSeller.txt", true)))) {
                                while ((line = storedDataTempBR.readLine()) != null) {
                                    SellerPW.println(line);
                                    SellerPW.flush();
                                }
                            } catch (Exception ex) {
                                serverWriter.println("sellerDataSaveError");
                                serverWriter.flush();
                            }
                        } catch (Exception ex) {
                            serverWriter.println("sellerDataSaveError");
                            serverWriter.flush();
                        }
                    } catch (Exception ex) {
                        serverWriter.println("sellerDataSaveError");
                        serverWriter.flush();
                    }
                } catch (Exception ex) {
                    serverWriter.println("sellerDataSaveError");
                    serverWriter.flush();
                }
                storeDataSellerTemp.delete();
            }
            try (PrintWriter SellerPW = new PrintWriter(new BufferedWriter(new
                    FileWriter("storeDataSeller.txt", true)))) {
                SellerPW.println(seller.storeSeller());
                SellerPW.flush();
            } catch (Exception ex) {
                serverWriter.println("sellerDataSaveError");
                serverWriter.flush();
            }
        }
    }

    public String storeSeller() {
        StringBuilder sellerData = new StringBuilder(this.getUsername() + "|" + this.getPassword() + "\n");
        for (Store store : this.stores) {
            sellerData.append("[|").append(store.getName()).append("|").append(store.getRevenue()).append("\n");
            for (Listing listing : store.getProducts()) {
                if (store.getProducts().isEmpty()) {
                    sellerData.append("] no listings\n");
                } else {
                    sellerData.append("]|").append(listing.getName()).append("|").append(listing.getPrice()).append("|").
                            append(listing.getDescription()).append("|").append(listing.getStoreName()).append("|").
                            append(listing.getQuantity()).append("\n");
                }
            }
            sellerData.append("end store\n");
        }
        sellerData.append("end seller");
        return sellerData.toString();
    }
}
