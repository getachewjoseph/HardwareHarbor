import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Project 5 -- ViewStoresGUI
 * <p>
 * This program is the GUI for viewing the stores
 *
 * @author Juan Rodriguez, Joseph Getachew
 * @version December 6 2023
 */
public class ViewStoresGUI extends JComponent implements Runnable {
    JFrame frame;
    JButton okButton;
    JTextArea stores;
    JScrollPane scroll;
    public static final BufferedReader reader;
    static {
        File storeDataSeller = new File("storeDataSeller.txt");
        try {
            storeDataSeller.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            reader = new BufferedReader(new FileReader(storeDataSeller));
        } catch (IOException ie) {
            throw new RuntimeException();
        }
    }

    public ViewStoresGUI() {
        ArrayList<Seller> sellers = new ArrayList<>();
        Seller boater = null;
        Store store = null;
        Listing listing = null;
        ArrayList<Listing> listings = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
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
                } else if (line.contains("@")){
                    String[] usernamePassword = line.split("\\|");
                    boater = new Seller(usernamePassword[0], usernamePassword[1], stores);
                    stores = new ArrayList<>();
                }
            }
            this.stores = new JTextArea();
            this.stores.setEditable(false);
            StringBuilder storeText = new StringBuilder();
            for (Store store1 : boater.getStores()) {
                storeText.append(store1.displayStore()).append("\n");
            }
            this.stores.setText(storeText.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewStoresGUI().run());
    }
    public void run() {
        frame = new JFrame("hardwareHarbor");

        Container content = frame.getContentPane();

        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        scroll = new JScrollPane(stores);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        content.add(scroll, BorderLayout.CENTER);

        okButton = new JButton("OK");
        okButton.addActionListener(actionListener);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(okButton);
        content.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {
                frame.dispose();
            }
        }
    };
}
