import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Project 5 -- CartItemsGUI
 * <p>
 * This program is the GUI for cart items
 *
 * @author Hunter Specht, Ella Budack, Juan Rodriguez
 * @version December 6 2023
 */
public class CartItemsGUI extends JComponent implements Runnable {
    JFrame frame;
    JButton okButton;
    JTextArea cartItems;
    JScrollPane scroll;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CartItemsGUI());
    }
    public void run() {
        frame = new JFrame("hardwareHarbor");

        Container content = frame.getContentPane();

        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cartItems = new JTextArea(Statistics.displayCartItemsText.toString());
        scroll = new JScrollPane(cartItems);
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
