import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
/**
 * Project 5 -- MarketplaceGUI
 * <p>
 * This program is the GUI for the marketplace
 *
 * @author Hunter Specht, Joseph Getachew, Ella Budack, Juan Rodriguez
 * @version December 6 2023
 */
public class MarketplaceGUI extends JComponent implements Runnable {
    private static final String[] marketplaceOptions = {"Select an Option", "Filter Items", "Select Item to View",
            "Add Item to Cart"};
    private static final String[] filterOptions = {"By Name", "By Description", "By Store", "By Price", "By Quantity"};
    private static final String[] GTLT = {"Filter Price from Greatest to Least", "Filter Price from Least to Greatest"};
    private static final String[] GTLT2 = {"Greater than", "Less than"};
    JFrame frame;
    JButton okButton;
    JComboBox<String> dropdown;
    JTextArea marketplaceText;
    JScrollPane scroll;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MarketplaceGUI());
    }
    public void run() {
        frame = new JFrame("hardwareHarbor");

        Container content = frame.getContentPane();

        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        marketplaceText = new JTextArea(Buyer.displayMarketplaceText.toString());
        scroll = new JScrollPane(marketplaceText);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        content.add(scroll, BorderLayout.CENTER);

        okButton = new JButton("OK");
        okButton.addActionListener(actionListener);

        dropdown = new JComboBox<>(marketplaceOptions);
        dropdown.addActionListener(actionListener);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(okButton);
        content.add(bottomPanel, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel();
        topPanel.add(dropdown);
        content.add(topPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {
                frame.dispose();
            }
            if (e.getSource() == dropdown) {
                String dropdownChoice = (String) dropdown.getSelectedItem();
                switch(Objects.requireNonNull(dropdownChoice)) {
                    case "Filter Items" :
                        HardwareHarborClient.clientWriter.println("Filter Items");
                        String filterMethod = (String) JOptionPane.showInputDialog(null,
                                "How would you like to filter?", "hardwareHarbor",
                                JOptionPane.QUESTION_MESSAGE, null, filterOptions, filterOptions[0]);
                        if (filterMethod == null) {
                            filterMethod = "Exit";
                        }
                        HardwareHarborClient.clientWriter.println(filterMethod);
                        try {
                            while (true) {
                                String incomingLine = HardwareHarborClient.clientReader.readLine();
                                switch (incomingLine) {
                                    case "exitMarket" -> {
                                        frame.dispose();
                                        return;
                                    }
                                    case "keyword" -> {
                                        String keyword = JOptionPane.showInputDialog(null,
                                                "Please enter a keyword to filter items by: ",
                                                "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                        if (keyword == null) {
                                            keyword = "JOptionPane == null";
                                        }
                                        HardwareHarborClient.clientWriter.println(keyword);
                                        HardwareHarborClient.clientWriter.flush();
                                    }
                                    case "noResults" -> JOptionPane.showMessageDialog(null,
                                            "No results found", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    case "results" -> {
                                        JOptionPane.showMessageDialog(null, "Item(s) added " +
                                                        "to cart", "Error",
                                                JOptionPane.PLAIN_MESSAGE);
                                        frame.dispose();
                                        return;
                                    }
                                    case "gtlt" -> {
                                        String greaterLess = (String) JOptionPane.showInputDialog(null,
                                                "How would you like to sort?", "hardwareHarbor",
                                                JOptionPane.QUESTION_MESSAGE, null, GTLT, GTLT[0]);
                                        if (greaterLess == null) {
                                            greaterLess = "Exit";
                                        }
                                        HardwareHarborClient.clientWriter.println(greaterLess);
                                    }
                                    case "quantity" -> {
                                        String quantity = JOptionPane.showInputDialog(null,
                                                "What quantity would you like to sort by? ",
                                                "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                        if (quantity == null) {
                                            quantity = "JOptionPane == null";
                                        }
                                        HardwareHarborClient.clientWriter.println(quantity);
                                        HardwareHarborClient.clientWriter.flush();
                                    }
                                    case "gtlt2" -> {
                                        String greaterLess2 = (String) JOptionPane.showInputDialog(null,
                                                "Please select one fo the following options:",
                                                "hardwareHarbor",
                                                JOptionPane.QUESTION_MESSAGE, null, GTLT2, GTLT2[0]);
                                        if (greaterLess2 == null) {
                                            greaterLess2 = "Exit";
                                        }
                                        HardwareHarborClient.clientWriter.println(greaterLess2);
                                    }
                                    case "quantityInteger" -> JOptionPane.showMessageDialog(null,
                                            "Quantity must be a positive integer", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    case "Select Item to View" :
                        HardwareHarborClient.clientWriter.println("Select Item to View");
                        try {
                            while (true) {
                                String incomingLine = HardwareHarborClient.clientReader.readLine();
                                switch (incomingLine) {
                                    case "exitMarket" -> {
                                        frame.dispose();
                                        return;
                                    }
                                    case "storeName" -> {
                                        String keyword = JOptionPane.showInputDialog(null,
                                                "Please enter the name of the store that contains the " +
                                                        "product you'd like to view: ",
                                                "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                        if (keyword == null) {
                                            keyword = "JOptionPane == null";
                                        }
                                        HardwareHarborClient.clientWriter.println(keyword);
                                        HardwareHarborClient.clientWriter.flush();
                                    }
                                    case "itemName" -> {
                                        String keyword = JOptionPane.showInputDialog(null,
                                                "Please enter the name of the item you'd like to view: ",
                                                "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                        if (keyword == null) {
                                            keyword = "JOptionPane == null";
                                        }
                                        HardwareHarborClient.clientWriter.println(keyword);
                                        HardwareHarborClient.clientWriter.flush();
                                    }
                                    case "noResults" -> JOptionPane.showMessageDialog(null,
                                            "No results found", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    case "results" -> {
                                        SwingUtilities.invokeLater(new MarketplaceGUI());
                                        frame.dispose();
                                        return;
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    case "Add Item to Cart" :
                        HardwareHarborClient.clientWriter.println("Add Item to Cart");
                        try {
                            while (true) {
                                String incomingLine = HardwareHarborClient.clientReader.readLine();
                                switch (incomingLine) {
                                    case "exitMarket" -> {
                                        frame.dispose();
                                        return;
                                    }
                                    case "storeName" -> {
                                        String keyword = JOptionPane.showInputDialog(null,
                                                "Please enter the name of the store that contains the " +
                                                        "product you'd like to add: ",
                                                "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                        if (keyword == null) {
                                            keyword = "JOptionPane == null";
                                        }
                                        HardwareHarborClient.clientWriter.println(keyword);
                                        HardwareHarborClient.clientWriter.flush();
                                    }
                                    case "itemName" -> {
                                        String keyword = JOptionPane.showInputDialog(null,
                                                "Please enter the name of the item you'd like to add: ",
                                                "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                        if (keyword == null) {
                                            keyword = "JOptionPane == null";
                                        }
                                        HardwareHarborClient.clientWriter.println(keyword);
                                        HardwareHarborClient.clientWriter.flush();
                                    }
                                    case "noResults" -> JOptionPane.showMessageDialog(null,
                                            "No results found", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    case "results" -> {
                                        SwingUtilities.invokeLater(new MarketplaceGUI());
                                        frame.dispose();
                                        return;
                                    }
                                    case "howMany" -> {
                                        String howMany = JOptionPane.showInputDialog(null,
                                                "How many of this item would you like to add?",
                                                "hardwareHarbor", JOptionPane.QUESTION_MESSAGE);
                                        if (howMany == null) {
                                            howMany = "JOptionPane == null";
                                        }
                                        HardwareHarborClient.clientWriter.println(howMany);
                                        HardwareHarborClient.clientWriter.flush();
                                    }
                                    case "quantityInteger" -> JOptionPane.showMessageDialog(null,
                                            "Quantity must be a positive integer", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    case "notInStock" -> JOptionPane.showMessageDialog(null,
                                            "There aren't enough of that item in stock", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                }
            }
        }
    };
}
