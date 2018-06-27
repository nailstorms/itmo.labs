package com.company;

import lab7.Auth;
import lab7.ClientGUI;
import lab7.DatagramTransfer;

import java.awt.*;
import java.io.IOException;


public class Main {
    public static final String IPAddress = System.getenv("IPAddress");
    public static final int PortNumber = Integer.parseInt(System.getenv("PortNumber"));

    public static void main(String[] args) {
        try {
            ClientGUI clientGUI = new ClientGUI();
            Auth login = new Auth(() -> clientGUI.setVisible(true));
            login.setVisible(true);

            if(login.hasPassedTheCheck()) {
                EventQueue.invokeLater(() -> clientGUI.setVisible(true));
            }
            while (true) {
               DatagramTransfer transfer = new DatagramTransfer(IPAddress, PortNumber);
               if(clientGUI.isDataAcquirePressed()) {
                   try {
                       transfer.transmitData("save");
                       String[] dataForGUI = null;
                       dataForGUI = (transfer.receiveData());
                       if (dataForGUI[0].equals("No response from server. Try again later."))
                           throw new IOException("");
                       if (dataForGUI[0].equals("Class has not been found."))
                           throw new ArrayIndexOutOfBoundsException("");
                       if(dataForGUI[0].equals("An I/O exception has occurred. Please try again."))
                           throw new NumberFormatException("");
                       clientGUI.startAnimation(clientGUI.drawNPCs(dataForGUI), dataForGUI);
                       clientGUI.filterCreate(dataForGUI);
                   } catch (IOException exc) {
                       clientGUI.setServerStatusMessage(clientGUI.getServerUnav());
                   } catch (ArrayIndexOutOfBoundsException exc) {
                       clientGUI.setServerStatusMessage(clientGUI.getEmptyColl());
                   } catch (NumberFormatException exc) {
                       clientGUI.setStatusMessage(clientGUI.getIoError());
                   }
               }
               clientGUI.setDataAcquire();
            }
        } catch (IOException exc) {
            exc.printStackTrace();
            System.err.println("An I/O exception has occurred. Please try again.");
        }
    }

}
