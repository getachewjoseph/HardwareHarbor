import javax.swing.*;
import java.io.*;
import java.net.Socket;
/**
 * Project 5 -- HardwareHarborClient
 * <p>
 * This program represents the client side of the marketplace.
 *
 * @author Ella Budack, Hunter Specht
 * @version December 6 2023
 */
public class HardwareHarborClient {
    private static final Socket socket;
    static {
        try {
            socket = new Socket("localhost", 8264);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final BufferedReader clientReader;
    static {
        try {
            clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final PrintWriter clientWriter;
    static {
        try {
            clientWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String[] initializationOptions = {"Create New Account", "Sign In",
            "Exit"};
    private static final String[] buyerSellerOptions = {"Buyer", "Seller"};
    private static final String[] sellerMenuOptions = {"View Stores", "Create New Store", "Delete Store",
            "Store Statistics", "Create New Listing", "Edit Listing", "Upload Products", "Export Products",
            "Edit Account", "Logout"};
    private static final String[] yesNoOption = {"Yes", "No"};
    private static final String[] sellerDashboard = {"View Store Sales", "View Items in Cart", "Exit"};
    private static final String[] editListing = {"Edit Listing", "Remove Listing"};
    private static final String[] editAccount = {"Change Password", "Delete Account"};
    private static final String[] buyerMenuOptions = {"View Marketplace", "View Purchase History", "Cart",
            "Contact Seller", "Export Purchase History", "Edit Account", "Logout"};
    private static final String[] cartMenuOptions = {"View Cart", "Remove Item from Cart", "Checkout"};

    public static void main(String[] args) {
        try (socket) {
            try (clientReader) {
                try (clientWriter) {
                    JOptionPane.showMessageDialog(null, "Welcome to the hardwareHarbor!",
                            "hardwareHarbor", JOptionPane.PLAIN_MESSAGE);
                    String initialOption = (String) JOptionPane.showInputDialog(null, "Please " +
                                    "select one of the following options.", "hardwareHarbor",
                            JOptionPane.QUESTION_MESSAGE, null, initializationOptions, initializationOptions[0]);
                    if (initialOption != null) {
                        clientWriter.println(initialOption);
                        clientWriter.flush();
                    } else {
                        clientWriter.println("Exit");
                        clientWriter.flush();
                    }
                    String incomingLine;
                    while (true) {
                        incomingLine = clientReader.readLine();
                        System.out.println("client read line: " + incomingLine);
                        switch (incomingLine) {
                            case "end" -> {
                                return;
                            }
                            case "genericError" -> {
                                JOptionPane.showMessageDialog(null, "An error has occurred",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            case "sellerDataSaveError" -> {
                                JOptionPane.showMessageDialog(null, "An error has occurred " +
                                                "while saving Seller data", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "username" -> {
                                String username = JOptionPane.showInputDialog(null, "Please " +
                                                "enter your username: ",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (username == null) {
                                    username = "JOptionPane == null";
                                }
                                clientWriter.println(username);
                                clientWriter.flush();
                            }
                            case "usernameSpaceError" -> {
                                JOptionPane.showMessageDialog(null, "Your email cannot " +
                                                "contain a space", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "username@Error" -> {
                                JOptionPane.showMessageDialog(null, "Your username must " +
                                                "contain an \"@\" symbol", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            case "usernameDomainError" -> {
                                JOptionPane.showMessageDialog(null, "Your username must end " +
                                                "in a valid domain extension, such as " +
                                                "\".com\"", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            case "usernameUsedError" -> {
                                JOptionPane.showMessageDialog(null, "An account with this " +
                                                "username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            case "password" -> {
                                String password = JOptionPane.showInputDialog(null, "Passwords " +
                                                "must be at least 8 characters and contain at least one capital " +
                                                "letter, one lowercase letter, and one number.\n" +
                                                "Please enter your password:",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (password == null) {
                                    password = "JOptionPane == null";
                                }
                                clientWriter.println(password);
                                clientWriter.flush();
                            }
                            case "passwordRequirementError" -> {
                                JOptionPane.showMessageDialog(null, "Your password does not " +
                                                "meet the requirements", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "passwordSpaceError" -> {
                                JOptionPane.showMessageDialog(null, "Your password cannot " +
                                                "contain a space", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "buyerSeller" -> {
                                String buyerSeller = (String) JOptionPane.showInputDialog(null,
                                        "Are you a buyer or a seller?", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, buyerSellerOptions,
                                        buyerSellerOptions[0]);
                                if (buyerSeller == null) {
                                    buyerSeller = "Exit";
                                }
                                clientWriter.println(buyerSeller);
                                clientWriter.flush();
                            }
                            case "signInPassword" -> {
                                String password = JOptionPane.showInputDialog(null, "Please " +
                                                "enter your password (case sensitive):",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (password == null) {
                                    password = "JOptionPane == null";
                                }
                                clientWriter.println(password);
                                clientWriter.flush();
                            }
                            case "accountFound" -> {
                                JOptionPane.showMessageDialog(null, "Account found",
                                        "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "accountNotFound" -> {
                                JOptionPane.showMessageDialog(null, "Account not found",
                                        "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "accountSearchError" -> {
                                JOptionPane.showMessageDialog(null, "Account not initialized " +
                                                "properly", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "sellerDataError" -> {
                                JOptionPane.showMessageDialog(null, "An error has occurred " +
                                                "while retrieving Seller data", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "sellerMenu" -> {
                                String sellerMenu = (String) JOptionPane.showInputDialog(null,
                                        "Please select one of the following options:", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, sellerMenuOptions,
                                        sellerMenuOptions[0]);
                                if (sellerMenu == null) {
                                    sellerMenu = "Exit";
                                }
                                clientWriter.println(sellerMenu);
                                clientWriter.flush();
                            }
                            case "storesEmpty" -> {
                                JOptionPane.showMessageDialog(null, "No stores to display",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "displayStores" -> {
                                StringBuilder storesText = new StringBuilder();
                                String line;
                                while (!(line = clientReader.readLine()).equals("stop")) {
                                    storesText.append(line);
                                }
                                SwingUtilities.invokeLater(new ViewStoresGUI());
                            }
                            case "storeName" -> {
                                String storeName = JOptionPane.showInputDialog(null, "What is " +
                                                "the name of the new store?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (storeName == null) {
                                    storeName = "JOptionPane == null";
                                }
                                clientWriter.println(storeName);
                                clientWriter.flush();
                            }
                            case "addListingOption" -> {
                                String addListing = (String) JOptionPane.showInputDialog(null,
                                        "Would you like to add a listing?", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, yesNoOption, yesNoOption[0]);
                                if (addListing == null) {
                                    addListing = "Exit";
                                }
                                clientWriter.println(addListing);
                                clientWriter.flush();
                            }
                            case "itemName" -> {
                                String itemName = JOptionPane.showInputDialog(null, "What is " +
                                                "the name of the item?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (itemName == null) {
                                    itemName = "JOptionPane == null";
                                }
                                clientWriter.println(itemName);
                                clientWriter.flush();
                            }
                            case "itemPrice" -> {
                                String itemPrice = JOptionPane.showInputDialog(null, "What is " +
                                                "the price of the item?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (itemPrice == null) {
                                    itemPrice = "JOptionPane == null";
                                }
                                clientWriter.println(itemPrice);
                                clientWriter.flush();
                            }
                            case "priceWrongFormat" -> {
                                JOptionPane.showMessageDialog(null, "You must enter the price " +
                                                "in the form $XX.XX", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "itemDescription" -> {
                                String itemDescription = JOptionPane.showInputDialog(null, "What " +
                                                "is the description of the item?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (itemDescription == null) {
                                    itemDescription = "JOptionPane == null";
                                }
                                clientWriter.println(itemDescription);
                                clientWriter.flush();
                            }
                            case "itemQuantity" -> {
                                String itemQuantity = JOptionPane.showInputDialog(null, "What " +
                                                "is the quantity of the item?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (itemQuantity == null) {
                                    itemQuantity = "JOptionPane == null";
                                }
                                clientWriter.println(itemQuantity);
                                clientWriter.flush();
                            }
                            case "quantityWrongFormat" -> {
                                JOptionPane.showMessageDialog(null, "You must enter the " +
                                                "quantity as an integer", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "addAnotherListingOption" -> {
                                String addAnotherListing = (String) JOptionPane.showInputDialog(null,
                                        "Would you like to add another listing?", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, yesNoOption, yesNoOption[0]);
                                if (addAnotherListing == null) {
                                    addAnotherListing = "Exit";
                                }
                                clientWriter.println(addAnotherListing);
                                clientWriter.flush();
                            }
                            case "storeAdded" -> {
                                JOptionPane.showMessageDialog(null, "Store added successfully",
                                        "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "storeRemoval" -> {
                                String storeRemoval = JOptionPane.showInputDialog(null, "What " +
                                                "is the name of the store you would like to remove?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (storeRemoval == null) {
                                    storeRemoval = "JOptionPane == null";
                                }
                                clientWriter.println(storeRemoval);
                                clientWriter.flush();
                            }
                            case "noStoresFound" -> {
                                JOptionPane.showMessageDialog(null, "No stores found with " +
                                                "that name", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "removeDifferentStore" -> {
                                String removeDifferentStore = (String) JOptionPane.showInputDialog(null,
                                        "Would you like to remove a different store?", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, yesNoOption, yesNoOption[0]);
                                if (removeDifferentStore == null) {
                                    removeDifferentStore = "Exit";
                                }
                                clientWriter.println(removeDifferentStore);
                                clientWriter.flush();
                            }
                            case "storeRemoved" -> {
                                JOptionPane.showMessageDialog(null, "Store removed successfully",
                                        "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "buyerDataError" -> {
                                JOptionPane.showMessageDialog(null, "An error has occurred while" +
                                                " retrieving Buyer data", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "sellerDashboard" -> {
                                String sellerDash = (String) JOptionPane.showInputDialog(null,
                                        "Please select one of the following options:", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, sellerDashboard, sellerDashboard[0]);
                                if (sellerDash == null) {
                                    sellerDash = "Exit";
                                }
                                clientWriter.println(sellerDash);
                                clientWriter.flush();
                            }
                            case "storesRevenue" -> {
                                SwingUtilities.invokeLater(new StoresRevenueGUI());
                            }
                            case "cartItems" -> {
                                SwingUtilities.invokeLater(new CartItemsGUI());
                            }
                            case "createNewListing" -> {
                                String createNewListing = JOptionPane.showInputDialog(null, "What" +
                                                " store would you like to add items to?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (createNewListing == null) {
                                    createNewListing = "JOptionPane == null";
                                }
                                clientWriter.println(createNewListing);
                                clientWriter.flush();
                            }
                            case "itemAdded" -> {
                                JOptionPane.showMessageDialog(null, "Item added successfully",
                                        "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "editListing" -> {
                                String editListingString = (String) JOptionPane.showInputDialog(null,
                                        "Please select one of the following options:", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, editListing, editListing[0]);
                                if (editListingString == null) {
                                    editListingString = "Exit";
                                }
                                clientWriter.println(editListingString);
                                clientWriter.flush();
                            }
                            case "itemStore" -> {
                                String itemStore = JOptionPane.showInputDialog(null, "What store " +
                                                "is the item in?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (itemStore == null) {
                                    itemStore = "JOptionPane == null";
                                }
                                clientWriter.println(itemStore);
                                clientWriter.flush();
                            }
                            case "storeNotFound" -> {
                                JOptionPane.showMessageDialog(null, "Store not found",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "itemNotFound" -> {
                                JOptionPane.showMessageDialog(null, "Item not found",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "itemNewPrice" -> {
                                String itemNewPrice = JOptionPane.showInputDialog(null, "What " +
                                                "is the new price of the item?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (itemNewPrice == null) {
                                    itemNewPrice = "JOptionPane == null";
                                }
                                clientWriter.println(itemNewPrice);
                                clientWriter.flush();
                            }
                            case "itemNewDescription" -> {
                                String itemNewDescription = JOptionPane.showInputDialog(null,
                                        "What is the new description of the item?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (itemNewDescription == null) {
                                    itemNewDescription = "JOptionPane == null";
                                }
                                clientWriter.println(itemNewDescription);
                                clientWriter.flush();
                            }
                            case "itemNewQuantity" -> {
                                String itemNewQuantity = JOptionPane.showInputDialog(null,
                                        "What is the new quantity of the item?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (itemNewQuantity == null) {
                                    itemNewQuantity = "JOptionPane == null";
                                }
                                clientWriter.println(itemNewQuantity);
                                clientWriter.flush();
                            }
                            case "uploadStore" -> {
                                String uploadStore = JOptionPane.showInputDialog(null, "Which " +
                                                "store would you like to upload to?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (uploadStore == null) {
                                    uploadStore = "JOptionPane == null";
                                }
                                clientWriter.println(uploadStore);
                                clientWriter.flush();
                            }
                            case "uploadInstructions" -> {
                                JOptionPane.showMessageDialog(null, "Ensure your file is a " +
                                                ".csv file and your products are in the following format:\n" +
                                                "Product Name,Product Price,Product Description,Store Name,Quantity " +
                                                "Available\n" +
                                                "Note: Each .csv file should contain products for one store",
                                        "hardwareHarbor",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                            case "uploadFilePath" -> {
                                String uploadFilePath = JOptionPane.showInputDialog(null,
                                        "PLease enter file path (using / to separate the folders)",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (uploadFilePath == null) {
                                    uploadFilePath = "JOptionPane == null";
                                }
                                clientWriter.println(uploadFilePath);
                                clientWriter.flush();
                            }
                            case "uploadFailed" -> {
                                JOptionPane.showMessageDialog(null, "Upload failed", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "uploadSuccessful" -> {
                                JOptionPane.showMessageDialog(null, "Upload successful",
                                        "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "notCSV" -> {
                                JOptionPane.showMessageDialog(null, "File is not a .csv file",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "exportStore" -> {
                                String uploadStore = JOptionPane.showInputDialog(null, "Which " +
                                                "store would you like to export?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (uploadStore == null) {
                                    uploadStore = "JOptionPane == null";
                                }
                                clientWriter.println(uploadStore);
                                clientWriter.flush();
                            }
                            case "exportFailed" -> {
                                JOptionPane.showMessageDialog(null, "Export failed", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "exportSuccessful" -> {
                                JOptionPane.showMessageDialog(null, "Export successful",
                                        "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "editAccountChoice" -> {
                                String editAccountChoice = (String) JOptionPane.showInputDialog(null,
                                        "Please select one of the following options:", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, editAccount, editAccount[0]);
                                if (editAccountChoice == null) {
                                    editAccountChoice = "Exit";
                                }
                                clientWriter.println(editAccountChoice);
                                clientWriter.flush();
                            }
                            case "passwordChangeUnsuccessful" -> {
                                JOptionPane.showMessageDialog(null, "Password change " +
                                                "unsuccessful", "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "passwordChangeSuccessful" -> {
                                JOptionPane.showMessageDialog(null, "Your password has been " +
                                                "changed", "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "accountDeletionUnsuccessful" -> {
                                JOptionPane.showMessageDialog(null, "Account deletion " +
                                                "unsuccessful", "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "accountDeletionSuccessful" -> {
                                JOptionPane.showMessageDialog(null, "Your account has been " +
                                                "deleted", "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "buyerMenu" -> {
                                String sellerMenu = (String) JOptionPane.showInputDialog(null,
                                        "Please select one of the following options:", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, buyerMenuOptions, buyerMenuOptions[0]);
                                if (sellerMenu == null) {
                                    sellerMenu = "Exit";
                                }
                                clientWriter.println(sellerMenu);
                                clientWriter.flush();
                            }
                            case "marketEmpty" -> {
                                JOptionPane.showMessageDialog(null, "There are no items on the " +
                                                "marketplace", "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "displayMarket" -> {
                                SwingUtilities.invokeLater(new MarketplaceGUI());
                                clientWriter.println("Exit");
                            }
                            case "phEmpty" -> {
                                JOptionPane.showMessageDialog(null, "There are no items in your " +
                                                "purchase history", "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "displayPh" -> {
                                SwingUtilities.invokeLater(new PhGUI());
                            }
                            case "cartMenu" -> {
                                String cartMenu = (String) JOptionPane.showInputDialog(null,
                                        "Please select one of the following options:", "hardwareHarbor",
                                        JOptionPane.QUESTION_MESSAGE, null, cartMenuOptions, cartMenuOptions[0]);
                                if (cartMenu == null) {
                                    cartMenu = "Exit";
                                }
                                clientWriter.println(cartMenu);
                                clientWriter.flush();
                            }
                            case "cartEmpty" -> {
                                JOptionPane.showMessageDialog(null, "There are no items " +
                                                "in your cart", "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "displayCart" -> {
                                SwingUtilities.invokeLater(new CartGUI());
                            }
                            case "howMany" -> {
                                String uploadStore = JOptionPane.showInputDialog(null, "How " +
                                                "many of this item would you like to remove?",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (uploadStore == null) {
                                    uploadStore = "JOptionPane == null";
                                }
                                clientWriter.println(uploadStore);
                                clientWriter.flush();
                            }
                            case "removeTooMany" -> {
                                JOptionPane.showMessageDialog(null, "You cannot remove " +
                                                "more of that item than is in your cart", "hardwareHarbor",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            case "itemsRemoved" -> {
                                JOptionPane.showMessageDialog(null, "Item(s) removed " +
                                                "successfully", "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "purchaseCart" -> {
                                JOptionPane.showMessageDialog(null, "Purchase successful!\n " +
                                                "Total Price: " + Buyer.cartTotal, "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "contactSeller" -> {
                                String uploadStore = JOptionPane.showInputDialog(null, "Please " +
                                                "enter the name of the store whose owner you'd like to contact:",
                                        "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                if (uploadStore == null) {
                                    uploadStore = "JOptionPane == null";
                                }
                                clientWriter.println(uploadStore);
                                clientWriter.flush();
                            }
                            case "sellerNotFound" -> {
                                JOptionPane.showMessageDialog(null, "No seller with given name",
                                        "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "sellerFound" -> {
                                JOptionPane.showMessageDialog(null, "Sellers email for contact: "
                                                + Buyer.contactSellerName, "hardwareHarbor",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            case "buyerDataSaveError" -> {
                                JOptionPane.showMessageDialog(null, "An error has occurred " +
                                                "while saving Buyer data", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to server", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
