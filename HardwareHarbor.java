import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Project 5 -- HardwareHarbor
 * <p>
 * This program represents the marketplace. It asks users to sign in or create an account upon start. It then shows
 * users certain menus with options based on their role: seller or buyer.
 *
 * @author Hunter Specht, Joseph Getachew, Ella Budack
 * @version December 6 2023
 */
public class HardwareHarbor {

    public static User initializeApplication(BufferedReader serverReader, PrintWriter serverWriter) throws IOException {
        File userData = new File("userData.txt");
        try {
            userData.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String initialOption = null;
        while (initialOption == null) {
            initialOption = serverReader.readLine();
        }
        switch (initialOption) {
            case "Create New Account" -> { //new account
                boolean uniqueUsername;
                boolean usernameNoSpace;
                boolean usernameAt;
                boolean usernameDotCom;
                String username = null;
                String password = null;
                do {
                    uniqueUsername = true;
                    usernameNoSpace = true;
                    usernameAt = true;
                    usernameDotCom = true;
                    serverWriter.println("username");
                    serverWriter.flush();
                    username = serverReader.readLine().toLowerCase();
                    if (username.equals("joptionpane == null")) {
                        return null;
                    }
                    if (username.contains(" ")) {
                        serverWriter.println("usernameSpaceError");
                        serverWriter.flush();
                        usernameNoSpace = false;
                    }
                    if (!username.contains("@")) {
                        serverWriter.println("username@Error");
                        serverWriter.flush();
                        usernameAt = false;
                    }
                    if (username.lastIndexOf('.') < username.indexOf('@') + 1 ||
                            username.lastIndexOf('.') == username.length() - 1) {
                        serverWriter.println("usernameDomainError");
                        serverWriter.flush();
                        usernameDotCom = false;
                    }
                    String usernamePassword = null;
                    synchronized (HardwareHarborServer.userDataGatekeeper) {
                        try (BufferedReader dataBR = new BufferedReader(new FileReader(userData))) {
                            while ((usernamePassword = dataBR.readLine()) != null && !usernamePassword.isEmpty()) {
                                if (username.equals(usernamePassword.substring(0, usernamePassword.indexOf(' ')))) {
                                    serverWriter.println("usernameUsedError");
                                    serverWriter.flush();
                                    uniqueUsername = false;
                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            serverWriter.println("genericError");
                            serverWriter.flush();
                            return null;
                        }
                    }
                } while (!uniqueUsername || !usernameNoSpace || !usernameAt || !usernameDotCom);

                boolean passwordRequirementsMet;
                do {
                    boolean capitalLetter = false;
                    boolean lowercaseLetter = false;
                    boolean number = false;
                    passwordRequirementsMet = true;
                    serverWriter.println("password");
                    serverWriter.flush();
                    password = serverReader.readLine();
                    if (password.equals("JOptionPane == null")) {
                        return null;
                    }
                    for (int i = 0; i < password.length(); i++) {
                        char character = password.charAt(i);
                        if (Character.isUpperCase(character)) {
                            capitalLetter = true;
                        } else if (Character.isLowerCase(character)) {
                            lowercaseLetter = true;
                        } else if (Character.isDigit(character)) {
                            number = true;
                        }
                    }
                    if (!capitalLetter || !lowercaseLetter || !number || password.length() < 8) {
                        serverWriter.println("passwordRequirementError");
                        serverWriter.flush();
                        passwordRequirementsMet = false;
                    }
                    if (password.contains(" ")) {
                        serverWriter.println("passwordSpaceError");
                        serverWriter.flush();
                        passwordRequirementsMet = false;
                    }
                } while (!passwordRequirementsMet);

                do {
                    serverWriter.println("buyerSeller");
                    serverWriter.flush();
                    String buyerSeller = serverReader.readLine();
                    switch (buyerSeller) {
                        case "Buyer" -> {
                            synchronized (HardwareHarborServer.userDataGatekeeper) {
                                try (PrintWriter dataPW = new PrintWriter(new BufferedWriter(new FileWriter(userData,
                                        true)))) {
                                    dataPW.println(username + " " + password + " buyer");
                                    return new Buyer(username, password, new ArrayList<>(), new ArrayList<>());
                                } catch (Exception ex) {
                                    serverWriter.println("genericError");
                                    serverWriter.flush();
                                }
                            }
                        }
                        case "Seller" -> {
                            synchronized (HardwareHarborServer.userDataGatekeeper) {
                                try (PrintWriter dataPW = new PrintWriter(new BufferedWriter(new FileWriter(userData,
                                        true)))) {
                                    dataPW.println(username + " " + password + " seller");
                                    return new Seller(username, password, new ArrayList<>());
                                } catch (Exception ex) {
                                    serverWriter.println("genericError");
                                    serverWriter.flush();
                                }
                            }
                        }
                        case "Exit" -> {
                            return null;
                        }
                    }
                } while (true);
            }
            case "Sign In" -> { //sign in
                boolean accountFound = true;
                do {
                    serverWriter.println("username");
                    serverWriter.flush();
                    String username = serverReader.readLine();
                    if (username.equals("JOptionPane == null")) {
                        return null;
                    }
                    serverWriter.println("signInPassword");
                    serverWriter.flush();
                    String password = serverReader.readLine();
                    String usernamePassword = null;
                    synchronized (HardwareHarborServer.userDataGatekeeper) {
                        try (BufferedReader dataBR = new BufferedReader(new FileReader(userData))) {
                            while ((usernamePassword = dataBR.readLine()) != null && !usernamePassword.isEmpty()) {
                                if (usernamePassword.substring(0, username.length()).equalsIgnoreCase(username)
                                        && usernamePassword.substring(username.length() + 1, username.length()
                                        + password.length() + 1).equals(password)) {
                                    serverWriter.println("accountFound");
                                    serverWriter.flush();
                                    if (usernamePassword.substring(username.length()
                                            + password.length() + 2).equals("buyer")) {
                                        return new Buyer(username, password,
                                                new ArrayList<>(), new ArrayList<>());
                                    } else if (usernamePassword.substring(username.length()
                                            + password.length() + 2).equals("seller")) {
                                        return new Seller(username, password, new ArrayList<>());
                                    } else {
                                        serverWriter.println("accountSearchError");
                                        serverWriter.flush();
                                    }
                                } else {
                                    accountFound = false;
                                }
                            }
                        } catch (Exception ex) {
                            serverWriter.println("genericError");
                            serverWriter.flush();
                            return null;
                        }
                    }
                    serverWriter.println("accountNotFound");
                    serverWriter.flush();
                } while (!accountFound);
            }
            case "Exit" -> { //exit
                return null;
            }
        }
        return null;
    }

    public static ArrayList<Seller> readDataSeller(BufferedReader serverReader, PrintWriter serverWriter) {
        File storeDataSeller = new File("storeDataSeller.txt");
        try {
            storeDataSeller.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ArrayList<Seller> sellers = new ArrayList<>();
        Seller boater = null;
        Store store = null;
        Listing listing = null;
        ArrayList<Listing> listings = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();
        String line = null;
        synchronized (HardwareHarborServer.sellerDataGatekeeper) {
            try (BufferedReader bfr = new BufferedReader(new FileReader("storeDataSeller.txt"))) {
                while ((line = bfr.readLine()) != null) {
                    if (line.charAt(0) == ']') {
                        if (!line.equals("] no listings")) {
                            String[] listingArray = line.split("\\|");
                            listing = new Listing(listingArray[1], Double.parseDouble(listingArray[2]),
                                    listingArray[3], listingArray[4], Integer.parseInt(listingArray[5]));
                            listings.add(listing);
                        }
                        store.setProducts(listings);
                    } else if (line.charAt(0) == '[') {
                        String[] storeArray = line.split("\\|");
                        store = new Store(storeArray[1]);
                        store.setRevenue(Double.parseDouble(storeArray[2]));
                        listings = new ArrayList<>();
                    } else if (line.equals("end store")) {
                        stores.add(store);
                        boater.setStores(stores);
                    } else if (line.equals("end seller")) {
                        sellers.add(boater);
                    } else {
                        String[] usernamePassword = line.split("\\|");
                        boater = new Seller(usernamePassword[0], usernamePassword[1], stores);
                        stores = new ArrayList<>();
                    }
                }
                return sellers;
            } catch (Exception ex) {
                serverWriter.println("sellerDataError");
                serverWriter.flush();
            }
        }
        return new ArrayList<>();
    }

    public static ArrayList<Buyer> readDataBuyer(BufferedReader serverReader, PrintWriter serverWriter) {
        File storeDataBuyer = new File("storeDataBuyer.txt");
        try {
            storeDataBuyer.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ArrayList<Buyer> buyers = new ArrayList<>();
        Buyer boater = null;
        Listing listing = null;
        ArrayList<Listing> cart = new ArrayList<>();
        ArrayList<Listing> purchaseHistory = new ArrayList<>();
        String line = null;
        synchronized (HardwareHarborServer.buyerDataGatekeeper) {
            try (BufferedReader bfr = new BufferedReader(new FileReader("storeDataBuyer.txt"))) {
                while ((line = bfr.readLine()) != null) {
                    if (line.charAt(0) == ']') {
                        if (!line.equals("] no items")) {
                            String[] phListings = line.split("\\|");
                            listing = new Listing(phListings[1], Double.parseDouble(phListings[2]),
                                    phListings[3], phListings[4], Integer.parseInt(phListings[5]));
                            purchaseHistory.add(listing);
                        }
                    } else if (line.charAt(0) == '[') {
                        if (!line.equals("[ no items")) {
                            String[] cartListings = line.split("\\|");
                            listing = new Listing(cartListings[1], Double.parseDouble(cartListings[2]),
                                    cartListings[3], cartListings[4], Integer.parseInt(cartListings[5]));
                            cart.add(listing);
                        }
                    } else if (line.equals("end buyer")) {
                        boater.setCart(cart);
                        boater.setPurchaseHistory(purchaseHistory);
                        buyers.add(boater);
                    } else {
                        String[] usernamePassword = line.split("\\|");
                        boater = new Buyer(usernamePassword[0], usernamePassword[1], cart, purchaseHistory);
                        cart = new ArrayList<>();
                        purchaseHistory = new ArrayList<>();
                    }
                }
                return buyers;
            } catch (Exception ex) {
                serverWriter.println("buyerDataError");
                serverWriter.flush();
            }
        }
        return new ArrayList<Buyer>();
    }
}
