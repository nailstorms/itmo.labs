package com.company;

import lab6.DatagramReceiver;
import lab7.Auth;
import lab7.ServerGUI;
import lab5.*;


import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.time.format.DateTimeParseException;


public class Main {

    public static final String filepath = System.getenv("PathToIOFile");
    public static final String IPAddress = System.getenv("IPAddress");
    public static final int PortNumber = Integer.parseInt(System.getenv("PortNumber"));

    public static void main(String[] args) {

        DatagramReceiver receiver = new DatagramReceiver();
        InteractiveMode mode = new InteractiveMode();

        ServerGUI serverGUI = new ServerGUI();
        Auth login = new Auth(() -> serverGUI.setVisible(true));
        login.setVisible(true);

        if(login.hasPassedTheCheck()) {
            EventQueue.invokeLater(() -> serverGUI.setVisible(true));
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                mode.saveToFile(new File(filepath));
            }
        });

        mode.inputFromFile(new File(filepath));
        serverGUI.addListeners(mode.getNpcs());

        new Thread(()-> {
            while (true) {
                String data = receiver.receiveData();
                if (data.equals("save"))
                    receiver.transmitCollection(mode.getNpcs());
            }
        }).start();

        while (true) {
//            String data = receiver.receiveData();

            if(serverGUI.isWantedToBeClosed()) System.exit(0);

            new Thread(() -> {
                String datax = "";
                if(serverGUI.isWantedToBeExecuted()) {
                    datax = serverGUI.commandToExecute();
                }
                if(!datax.equals("no")) {
                    String[] cmd = datax.split(" ", 2);
                    String command = cmd[0];
                    String additionalData;
                    if (cmd.length == 2)
                        additionalData = cmd[1];
                    else
                        additionalData = "";

                    switch (command.toLowerCase()) {
                        case "save": {
                            mode.saveToFile(new File(filepath));
                            break;
                        }
                        case "clear": {
                            mode.clear();
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(),"Collection");
                            serverGUI.setOperationsStatus("Collection cleared.");
                            break;
                        }
                        case "add": {
                            try {
                                serverGUI.setOperationsStatus(mode.addElement(additionalData));
                                serverGUI.addListeners(mode.getNpcs());
                                serverGUI.createTree(mode.getNpcs(), "Collection");
                            } catch (DateTimeParseException exc) {
                                serverGUI.setOperationsStatus("Incorrect date format.");
                            }
                            finally {
                                break;
                            }
                        }

                        case "change": {
                            try {
                                serverGUI.setOperationsStatus(mode.changeElement(additionalData));
                                serverGUI.addListeners(mode.getNpcs());
                                serverGUI.createTree(mode.getNpcs(), "Collection");
                            } catch (DateTimeParseException exc) {
                                serverGUI.setOperationsStatus("Incorrect date format.");
                            }
                            finally {
                                break;
                            }
                        }
                        case "remove": {
                            serverGUI.setOperationsStatus(mode.removeElement(additionalData));
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(),"Collection");
                            break;
                        }
                        case "remove_first": {
                            serverGUI.setOperationsStatus(mode.removeFirst());
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(),"Collection");
                            break;
                        }
                    }
                }

//                receiver.transmitMessage(mode.commandVariants(command, additionalData));

            }).run();

        }

    }
}
