import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Project 5 -- HardwareHarborServer
 * <p>
 * This program will handle all processing
 *
 * @author Ella Budack, Hunter Specht
 * @version December 6 2023
 */
public class HardwareHarborServer implements Runnable {
    private Socket socket;
    public static final Object userDataGatekeeper = new Object();
    public static final Object sellerDataGatekeeper = new Object();
    public static final Object buyerDataGatekeeper = new Object();

    public HardwareHarborServer(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8264);

            while (true) {
                Socket socket = serverSocket.accept();
                HardwareHarborServer individualServer = new HardwareHarborServer(socket);
                new Thread(individualServer).start();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Server initialization failed", "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void run() {
        try (BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            try (PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true)) {
                User boater = HardwareHarbor.initializeApplication(serverReader, serverWriter);
                if (boater != null) {
                    if (boater instanceof Seller) {
                        ((Seller) boater).sellerMenu((Seller) boater, serverReader, serverWriter);
                    } else if (boater instanceof Buyer) {
                        ((Buyer) boater).buyerMenu((Buyer) boater, serverReader, serverWriter);
                    } else {
                        JOptionPane.showMessageDialog(null, "No buyer or seller specified",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                serverWriter.println("end");
                serverWriter.flush();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to client", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}