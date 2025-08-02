import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 5 -- Buyer
 * <p>
 * This program represents a buyer. It extends User. A buyer has a username, password, shopping cart,
 * and purchase history
 *
 * @author Hunter Specht, Joseph Getachew
 * @version December 6 2023
 */
public class Buyer extends User {
    private ArrayList<Listing> cart;
    private ArrayList<Listing> purchaseHistory;
    public static double cartTotal;
    public static String contactSellerName;
    public static StringBuilder displayMarketplaceText = new StringBuilder();
    public static StringBuilder displayPurchaseHistoryText = new StringBuilder();
    public static StringBuilder displayCartText = new StringBuilder();

    public Buyer (String username, String password, ArrayList<Listing> cart, ArrayList<Listing> purchaseHistory) {
        super(username, password);
        this.cart = cart;
        this.purchaseHistory = purchaseHistory;
    }

    public Buyer() {
        super();
        this.cart = new ArrayList<>();
        this.purchaseHistory = new ArrayList<>();
    }

    public ArrayList<Listing> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Listing> cart) {
        this.cart = cart;
    }

    public void addItemToCart(Listing item, int quantityInCart) {
        for (int i = 0; i < quantityInCart; i++) {
            this.cart.add(item);
        }
    }

    public void removeItemFromCart(Listing item, int quantityToRemove, BufferedReader serverReader,
                                   PrintWriter serverWriter) {
        int numberOfItemsToBeRemovedInCart = 0;
        for (Listing listing : this.cart) {
            if (listing.getName().equals(item.getName()) && listing.getDescription().equals(item.getDescription())) {
                numberOfItemsToBeRemovedInCart++;
            }
        }
        if (numberOfItemsToBeRemovedInCart == 0) {
            serverWriter.println("itemNotFound");
            serverWriter.flush();
        } else if (quantityToRemove > numberOfItemsToBeRemovedInCart) {
            serverWriter.println("removeTooMany");
            serverWriter.flush();
        } else {
            for (int i = 0; i < quantityToRemove; i++) {
                this.cart.remove(item);
            }
            serverWriter.println("itemsRemoved");
            serverWriter.flush();
        }
    }

    public void purchaseCart(BufferedReader serverReader, PrintWriter serverWriter) {
        cartTotal = 0.0;
        boolean listingFound = false;
        ArrayList<Seller> sellers = HardwareHarbor.readDataSeller(serverReader, serverWriter);
        for (Listing listing : this.cart) {
            for (Seller seller : sellers) {
                for (Store store : seller.getStores()) {
                    for (Listing listingInStore : store.getProducts()) {
                        if (listing.getName().equals(listingInStore.getName()) &&
                                listing.getDescription().equals(listingInStore.getDescription())) {
                            listingInStore.setQuantity(listingInStore.getQuantity() - 1);
                            store.setRevenue(store.getRevenue() + listingInStore.getPrice());
                            Seller.storeDataSeller(serverReader, serverWriter, seller);
                            listingFound = true;
                            break;
                        }
                    }
                    if (listingFound) {
                        break;
                    }
                }
                if (listingFound) {
                    break;
                }
            }
            this.purchaseHistory.add(listing);
            cartTotal += listing.getPrice();
        }
        this.cart.clear();
        serverWriter.println("purchaseCart");
        serverWriter.flush();
    }

    public ArrayList<Listing> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(ArrayList<Listing> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public void buyerMenu(Buyer boater, BufferedReader serverReader, PrintWriter serverWriter) throws IOException {
        while (true) {
            boolean buyerMenuChoiceMade = true;
            do {
                serverWriter.println("buyerMenu");
                serverWriter.flush();
                String buyerMenuChoice = serverReader.readLine();
                switch (buyerMenuChoice) {
                    case "View Marketplace":
                        ArrayList<Seller> sellers = HardwareHarbor.readDataSeller(serverReader, serverWriter);
                        int itemCounter = 0;
                        displayMarketplaceText = new StringBuilder();
                        for (int i = 0; i < sellers.size(); i++) {
                            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                                    displayMarketplaceText.append(sellers.get(i).getStores().get(j).getProducts().
                                            get(k).marketplaceListing());
                                    itemCounter++;
                                }
                            }
                        }
                        if (itemCounter == 0) {
                            serverWriter.println("marketEmpty");
                            serverWriter.flush();
                            break;
                        } else {
                            serverWriter.println("displayMarket");
                            serverWriter.flush();
                            while (true) {
                                String marketplaceMenuAnswer = serverReader.readLine();
                                switch (marketplaceMenuAnswer) {
                                    case "Filter Items":
                                        String sortMenuAnswer = serverReader.readLine();
                                        switch (sortMenuAnswer) {
                                            case "By Name": {
                                                serverWriter.println("keyword");
                                                serverWriter.flush();
                                                String keywordName = serverReader.readLine();
                                                if (keywordName.equals("JOptionPane == null")) {
                                                    break;
                                                }
                                                boolean resultsExist = false;
                                                displayMarketplaceText = new StringBuilder();
                                                for (Seller seller : sellers) {
                                                    for (Store store : seller.getStores()) {
                                                        for (Listing item : store.getProducts()) {
                                                            if (item.getName().contains(keywordName)) {
                                                                displayMarketplaceText.append(item.
                                                                        marketplaceListing());
                                                                resultsExist = true;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (!resultsExist) {
                                                    serverWriter.println("noResults");
                                                    serverWriter.flush();
                                                    serverWriter.println("exitMarket");
                                                    serverWriter.flush();
                                                } else {
                                                    serverWriter.println("results");
                                                    serverWriter.flush();
                                                }
                                                break;
                                            }
                                            case "By Description": {
                                                serverWriter.println("keyword");
                                                serverWriter.flush();
                                                String keywordName = serverReader.readLine();
                                                if (keywordName.equals("JOptionPane == null")) {
                                                    break;
                                                }
                                                boolean resultsExist = false;
                                                displayMarketplaceText = new StringBuilder();
                                                for (Seller seller : sellers) {
                                                    for (Store store : seller.getStores()) {
                                                        for (Listing item : store.getProducts()) {
                                                            if (item.getDescription().contains(keywordName)) {
                                                                displayMarketplaceText.append(item.
                                                                        marketplaceListing());
                                                                resultsExist = true;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (!resultsExist) {
                                                    serverWriter.println("noResults");
                                                    serverWriter.flush();
                                                    serverWriter.println("exitMarket");
                                                    serverWriter.flush();
                                                } else {
                                                    serverWriter.println("results");
                                                    serverWriter.flush();
                                                }
                                                break;
                                            }
                                            case "By Store": {
                                                serverWriter.println("keyword");
                                                serverWriter.flush();
                                                String keywordName = serverReader.readLine();
                                                if (keywordName.equals("JOptionPane == null")) {
                                                    break;
                                                }
                                                boolean resultsExist = false;
                                                displayMarketplaceText = new StringBuilder();
                                                for (Seller seller : sellers) {
                                                    for (Store store : seller.getStores()) {
                                                        if (store.getName().contains(keywordName)) {
                                                            displayMarketplaceText.append(store.displayStore());
                                                            resultsExist = true;
                                                        }
                                                    }
                                                }
                                                if (!resultsExist) {
                                                    serverWriter.println("noResults");
                                                    serverWriter.flush();
                                                    serverWriter.println("exitMarket");
                                                    serverWriter.flush();
                                                } else {
                                                    serverWriter.println("results");
                                                    serverWriter.flush();
                                                }
                                                break;
                                            }
                                            case "By Price": {
                                                serverWriter.println("gtlt");
                                                String PriceSortChoice = serverReader.readLine();
                                                switch (PriceSortChoice) {
                                                    case "Filter Price from Greatest to Least" -> {
                                                        ArrayList<Listing> items = new ArrayList<>();
                                                        for (Seller seller : sellers) {
                                                            for (Store store : seller.getStores()) {
                                                                items.addAll(store.getProducts());
                                                            }
                                                        }
                                                        for (int i = 0; i < items.size() - 1; i++) {
                                                            for (int j = 0; j < items.size() - i - 1; j++) {
                                                                if (items.get(j).getPrice() < items.get(j + 1).
                                                                        getPrice()) {
                                                                    Listing itemTemp = items.get(j);
                                                                    items.set(j, items.get(j + 1));
                                                                    items.set(j + 1, itemTemp);
                                                                }
                                                            }
                                                        }
                                                        displayMarketplaceText = new StringBuilder();
                                                        for (Listing item : items) {
                                                            displayMarketplaceText.append(item.marketplaceListing());
                                                        }
                                                        serverWriter.println("results");
                                                        serverWriter.flush();
                                                    }
                                                    case "Filter Price from Least to Greatest" -> {
                                                        ArrayList<Listing> items = new ArrayList<>();
                                                        for (Seller seller : sellers) {
                                                            for (Store store : seller.getStores()) {
                                                                items.addAll(store.getProducts());
                                                            }
                                                        }
                                                        for (int i = 0; i < items.size() - 1; i++) {
                                                            for (int j = 0; j < items.size() - i - 1; j++) {
                                                                if (items.get(j).getPrice() > items.get(j + 1).
                                                                        getPrice()) {
                                                                    Listing itemTemp = items.get(j);
                                                                    items.set(j, items.get(j + 1));
                                                                    items.set(j + 1, itemTemp);
                                                                }
                                                            }
                                                        }
                                                        displayMarketplaceText = new StringBuilder();
                                                        for (Listing item : items) {
                                                            displayMarketplaceText.append(item.marketplaceListing());
                                                        }
                                                        serverWriter.println("results");
                                                        serverWriter.flush();
                                                    }
                                                    case "Exit" -> {
                                                    }
                                                }
                                                break;
                                            }
                                            case "By Quantity": {
                                                boolean filterQuantityRepeat;
                                                do {
                                                    filterQuantityRepeat = false;
                                                    serverWriter.println("quantity");
                                                    serverWriter.flush();
                                                    String quantity = serverReader.readLine();
                                                    try {
                                                        int quantityNum = Integer.parseInt(quantity);
                                                        serverWriter.println("qtlt2");
                                                        serverWriter.flush();
                                                        String gtltAnswer = serverReader.readLine();
                                                        switch (gtltAnswer) {
                                                            case "Greater than" -> {
                                                                boolean resultsExist = false;
                                                                displayMarketplaceText = new StringBuilder();
                                                                for (Seller seller : sellers) {
                                                                    for (Store store : seller.getStores()) {
                                                                        for (Listing item : store.getProducts()) {
                                                                            if (item.getQuantity() > quantityNum) {
                                                                                displayMarketplaceText.append(item.
                                                                                        marketplaceListing());
                                                                                resultsExist = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                if (!resultsExist) {
                                                                    serverWriter.println("noResults");
                                                                    serverWriter.flush();
                                                                    serverWriter.println("exitMarket");
                                                                    serverWriter.flush();
                                                                }
                                                            }
                                                            case "Less than" -> {
                                                                boolean resultsExist = false;
                                                                for (Seller seller : sellers) {
                                                                    for (Store store : seller.getStores()) {
                                                                        for (Listing item : store.getProducts()) {
                                                                            if (item.getQuantity() < quantityNum) {
                                                                                item.marketplaceListing();
                                                                                resultsExist = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                if (!resultsExist) {
                                                                    serverWriter.println("noResults");
                                                                    serverWriter.flush();
                                                                    serverWriter.println("exitMarket");
                                                                    serverWriter.flush();
                                                                } else {
                                                                    serverWriter.println("results");
                                                                    serverWriter.flush();
                                                                }
                                                            }
                                                            case "Exit" -> {
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        serverWriter.println("quantityInteger");
                                                        serverWriter.flush();
                                                        filterQuantityRepeat = true;
                                                    }
                                                } while (filterQuantityRepeat);
                                                break;
                                            }
                                            case "Exit": {
                                                break;
                                            }
                                        }
                                        break;
                                    case "Select Item to View":
                                        serverWriter.println("storeName");
                                        serverWriter.flush();
                                        String storeOfItem = serverReader.readLine();
                                        if (storeOfItem.equals("JOptionPane == null")) {
                                            break;
                                        }
                                        serverWriter.println("itemName");
                                        serverWriter.flush();
                                        String nameOfItem = serverReader.readLine();
                                        if (nameOfItem.equals("JOptionPane == null")) {
                                            break;
                                        }
                                        boolean itemExists = false;
                                        displayMarketplaceText = new StringBuilder();
                                        for (Seller seller : sellers) {
                                            for (Store store : seller.getStores()) {
                                                for (Listing listing : store.getProducts()) {
                                                    if (nameOfItem.equals(listing.getName()) && storeOfItem.equals(
                                                            listing.getStoreName())) {
                                                        displayMarketplaceText.append(listing.purchaseHistoryListing());
                                                        itemExists = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (itemExists) {
                                                break;
                                            }
                                        }
                                        if (!itemExists) {
                                            serverWriter.println("noResults");
                                            serverWriter.flush();
                                            serverWriter.println("exitMarket");
                                            serverWriter.flush();
                                        } else {
                                            serverWriter.println("results");
                                            serverWriter.flush();
                                        }
                                        break;
                                    case "Add Item to Cart":
                                        serverWriter.println("storeName");
                                        serverWriter.flush();
                                        String storeOfItem2 = serverReader.readLine();
                                        if (storeOfItem2.equals("JOptionPane == null")) {
                                            break;
                                        }
                                        serverWriter.println("itemName");
                                        serverWriter.flush();
                                        String nameOfItem2 = serverReader.readLine();
                                        if (nameOfItem2.equals("JOptionPane == null")) {
                                            break;
                                        }
                                        boolean itemExists2 = false;
                                        Listing addToCart = null;
                                        for (Seller seller : sellers) {
                                            for (Store store : seller.getStores()) {
                                                for (Listing listing : store.getProducts()) {
                                                    if (nameOfItem2.equals(listing.getName()) && storeOfItem2.equals(
                                                            listing.getStoreName())) {
                                                        addToCart = listing;
                                                        itemExists2 = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (itemExists2) {
                                                break;
                                            }
                                        }
                                        if (!itemExists2) {
                                            serverWriter.println("noResults");
                                            serverWriter.flush();
                                            serverWriter.println("exitMarket");
                                            serverWriter.flush();
                                        } else {
                                            serverWriter.println("howMany");
                                            serverWriter.flush();
                                            String quantity = serverReader.readLine();
                                            boolean quantityCorrect;
                                            do {
                                                quantityCorrect = true;
                                                try {
                                                    int quantityInt = Integer.parseInt(quantity);
                                                    if (quantityInt <= 0) {
                                                        serverWriter.println("quantityInteger");
                                                        serverWriter.flush();
                                                    } else if (quantityInt > addToCart.getQuantity()) {
                                                        serverWriter.println("notInStock");
                                                        serverWriter.flush();
                                                    } else {
                                                        this.addItemToCart(addToCart, quantityInt);
                                                        serverWriter.println("results");
                                                        serverWriter.flush();
                                                    }
                                                } catch (Exception ex) {
                                                    serverWriter.println("quantityInteger");
                                                    serverWriter.flush();
                                                    quantityCorrect = false;
                                                }
                                            } while (!quantityCorrect);
                                        }
                                        break;
                                    case "Exit":
                                        break;
                                }
                            }
                        }
                    case "View Purchase History":
                        int purchaseHistoryItemCounter = 0;
                        displayPurchaseHistoryText = new StringBuilder();
                        for (int i = 0; i < this.purchaseHistory.size(); i++) {
                            displayPurchaseHistoryText.append(this.purchaseHistory.get(i).purchaseHistoryListing());
                            purchaseHistoryItemCounter++;
                        }
                        if (purchaseHistoryItemCounter == 0) {
                            serverWriter.println("phEmpty");
                            serverWriter.flush();
                        } else {
                            serverWriter.println("displayPh");
                            serverWriter.flush();
                        }
                        break;
                    case "Cart":
                        serverWriter.println("cartMenu");
                        serverWriter.flush();
                        String cartChoice = serverReader.readLine();
                        switch (cartChoice) {
                            case "View Cart":
                                displayCartText = new StringBuilder();
                                displayCartText.append(this.getUsername()).append("'s Cart:\n");
                                int cartItemCounter = 0;
                                for (int i = 0; i < this.cart.size(); i++) {
                                    displayCartText.append("Item ").append(cartItemCounter++).append(":");
                                    displayCartText.append(this.cart.get(i).purchaseHistoryListing());
                                }
                                double cartTotal = 0;
                                for (Listing i : this.cart) {
                                    cartTotal += i.getPrice();
                                }
                                displayCartText.append("Cart total: $").append(cartTotal);
                                if (cartItemCounter == 0) {
                                    serverWriter.println("cartEmpty");
                                    serverWriter.flush();
                                } else {
                                    serverWriter.println("displayCart");
                                    serverWriter.flush();
                                }
                            case "Remove Item from Cart":
                                serverWriter.println("itemName");
                                serverWriter.flush();
                                String itemName = serverReader.readLine();
                                Listing item = null;
                                boolean itemExists = false;
                                for (Listing itemInCart : this.cart) {
                                    if (itemInCart.getName().equals(itemName)) {
                                        item = itemInCart;
                                        itemExists = true;
                                        break;
                                    }
                                }
                                if (itemExists) {
                                    boolean tryAgain = true;
                                    while (tryAgain) {
                                        tryAgain = false;
                                        serverWriter.println("howMany");
                                        serverWriter.flush();
                                        String quantity = serverReader.readLine();
                                        if (quantity.equals("JOptionPane == null")) {
                                            break;
                                        }
                                        try {
                                            int quantityToRemove = Integer.parseInt(quantity);
                                            this.removeItemFromCart(item, quantityToRemove, serverReader, serverWriter);
                                        } catch (Exception e) {
                                            serverWriter.println("quantityWrongFormat");
                                            serverWriter.flush();
                                            tryAgain = true;
                                        }
                                    }
                                } else {
                                    serverWriter.println("itemNotFound");
                                    serverWriter.flush();
                                }
                                break;
                            case "Checkout":
                                if (this.cart.isEmpty()) {
                                    serverWriter.println("cartEmpty");
                                    serverWriter.flush();;
                                } else {
                                    this.purchaseCart(serverReader, serverWriter);
                                }
                                break;
                            case "Exit":
                                break;
                        }
                        break;
                    case "Contact Seller":
                        serverWriter.println("contactSeller");
                        serverWriter.flush();
                        String storeName = serverReader.readLine();
                        boolean sellerExists = false;
                        sellers = HardwareHarbor.readDataSeller(serverReader, serverWriter);
                        for (Seller seller : sellers) {
                            for (Store store : seller.getStores()) {
                                if (store.getName().equals(storeName)) {
                                    sellerExists = true;
                                    contactSellerName = seller.getUsername();
                                } else {
                                    sellerExists = false;
                                }
                            }
                        }
                        if (!sellerExists) {
                            serverWriter.println("sellerNotFound");
                            serverWriter.flush();
                        }
                        serverWriter.println("sellerFound");
                        serverWriter.flush();
                        break;
                    case "Export Purchase History":
                        try {
                            Files.exportPurchaseHistory(this.getUsername(), this.purchaseHistory, serverReader,
                                    serverWriter);
                        } catch (Exception ex) {
                            serverWriter.println("exportFailed");
                            serverWriter.flush();
                        }
                        break;
                    case "Edit Account":
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
                                                            " " + "buyer")) {
                                                        dataPW2.println(boater.getUsername() + " " + newPassword +
                                                                " buyer");
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
                                                        BufferedWriter(new FileWriter("userDataTemp.txt",
                                                        true)))) {
                                                    if (!line.equals(boater.getUsername() + " " + boater.getPassword()
                                                            + " " + "buyer")) {
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
                    case "Logout", "Exit" :
                        this.storeDataBuyer(serverReader, serverWriter);
                        return;
                }
            } while (!buyerMenuChoiceMade);
        }
    }

    public void storeDataBuyer(BufferedReader serverReader, PrintWriter serverWriter) {
        File storeDataBuyer = new File("storeDataBuyer.txt");
        try {
            storeDataBuyer.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        synchronized (HardwareHarborServer.buyerDataGatekeeper) {
            ArrayList<Buyer> buyers = HardwareHarbor.readDataBuyer(serverReader, serverWriter);
            boolean rewrite = false;
            for (int i = 0; i < buyers.size(); i++) {
                if (this.getUsername().equals(buyers.get(i).getUsername())) {
                    rewrite = true;
                    break;
                }
            }
            if (rewrite) {
                File storeDataBuyerTemp = new File("storeDataBuyerTemp.txt");
                try {
                    storeDataBuyerTemp.createNewFile();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try (BufferedReader storedDataTempBR = new BufferedReader(new
                        FileReader("storeDataBuyerTemp.txt"))) {
                    try (BufferedReader storedDataBR = new BufferedReader(new
                            FileReader("storeDataBuyer.txt"))) {
                        try (PrintWriter BuyerTempPW = new PrintWriter(new BufferedWriter(new
                                FileWriter("storeDataBuyerTemp.txt", true)))) {
                            String line;
                            while ((line = storedDataBR.readLine()) != null) {
                                if (!line.equals(this.getUsername() + "|" + this.getPassword())) {
                                    BuyerTempPW.println(line);
                                    BuyerTempPW.flush();
                                } else {
                                    while ((line = storedDataBR.readLine()) != null) {
                                        if (line.charAt(0) != '[' && line.charAt(0) != ']' &&
                                                !line.equals("end buyer")) {
                                            BuyerTempPW.println(line);
                                            break;
                                        }
                                    }
                                }
                            }
                            try (PrintWriter BuyerPW = new PrintWriter(new BufferedWriter(new
                                    FileWriter("storeDataBuyer.txt")))) {
                            } catch (Exception ex) {
                                serverWriter.println("buyerDataSaveError");
                                serverWriter.flush();
                            }
                            try (PrintWriter BuyerPW = new PrintWriter(new BufferedWriter(new
                                    FileWriter("storeDataBuyer.txt", true)))) {
                                while ((line = storedDataTempBR.readLine()) != null) {
                                    BuyerPW.println(line);
                                    BuyerPW.flush();
                                }
                            } catch (Exception ex) {
                                serverWriter.println("buyerDataSaveError");
                                serverWriter.flush();
                            }
                        } catch (Exception ex) {
                            serverWriter.println("buyerDataSaveError");
                            serverWriter.flush();
                        }
                    } catch (Exception ex) {
                        serverWriter.println("buyerDataSaveError");
                        serverWriter.flush();
                    }
                } catch (Exception ex) {
                    serverWriter.println("buyerDataSaveError");
                    serverWriter.flush();
                }
                storeDataBuyerTemp.delete();
            }
            try (PrintWriter BuyerPW = new PrintWriter(new BufferedWriter(new
                    FileWriter("storeDataBuyer.txt", true)))) {
                BuyerPW.println(this.storeBuyer());
                BuyerPW.flush();
            } catch (Exception ex) {
                serverWriter.println("buyerDataSaveError");
                serverWriter.flush();
            }
        }
    }

    public String storeBuyer() {
        StringBuilder buyerData = new StringBuilder();
        buyerData = new StringBuilder(this.getUsername() + "|" + this.getPassword() + "\n");
        if (this.cart.isEmpty()) {
            buyerData.append("[ no items\n");
        } else {
            for (Listing listing : this.cart) {
                buyerData.append("[|").append(listing.getName()).append("|").append(listing.getPrice()).append("|").
                        append(listing.getDescription()).append("|").append(listing.getStoreName()).append("|").
                        append(listing.getQuantity()).append("\n");
            }
        }
        if (this.getPurchaseHistory().isEmpty()) {
            buyerData.append("] no items\n");
        } else {
            for (Listing listing : this.getPurchaseHistory()) {
                buyerData.append("]|").append(listing.getName()).append("|").append(listing.getPrice()).append("|").
                        append(listing.getDescription()).append("|").append(listing.getStoreName()).append("|").
                        append(listing.getQuantity()).append("\n");
            }
        }
        buyerData.append("end buyer");
        return buyerData.toString();
    }
}
