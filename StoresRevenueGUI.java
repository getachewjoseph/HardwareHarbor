import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Project 5 -- GUI
 * <p>
 * This program is the GUI for viewing Store Revenue
 *
 * @author Ella Budack, Joseph Getachew
 * @version December 10 2023
 */
public class StoresRevenueGUI extends JComponent implements Runnable {
    JFrame frame;
    JButton okButton;
    JTextArea storesRevenue;
    JScrollPane scroll;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new StoresRevenueGUI());
    }
    public void run() {
        frame = new JFrame("hardwareHarbor");

        Container content = frame.getContentPane();

        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        storesRevenue = new JTextArea(Statistics.displayStoresRevenuesText.toString());
        scroll = new JScrollPane(storesRevenue);
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
