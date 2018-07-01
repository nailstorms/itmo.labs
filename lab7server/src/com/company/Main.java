package com.company;

import lab3.NPC;
import lab5.InteractiveMode;
import lab6.DatagramReceiver;
import lab7.Auth;
import lab7.ServerGUI;
import lab8.orm.DB;
import lab8.orm.SQL;

import java.awt.*;
import java.io.File;
import java.time.format.DateTimeParseException;


public class Main {

    public static final String filepath = System.getenv("PathToIOFile");
    public static final String IPAddress = System.getenv("IPAddress");
    public static final int PortNumber = Integer.parseInt(System.getenv("PortNumber"));

    public static void main(String[] args) {

        SQL<NPC> sql = new SQL<>(NPC.class);
        DB db = new DB("jdbc:postgresql://localhost:5432/lab8", "nailstorm", "qwerty");

        db.execute(sql.createTable());


        DatagramReceiver receiver = new DatagramReceiver();
        InteractiveMode mode = new InteractiveMode();

        ServerGUI serverGUI = new ServerGUI();
        Auth login = new Auth(() -> serverGUI.setVisible(true));
        login.setVisible(true);

        if (login.hasPassedTheCheck()) {
            EventQueue.invokeLater(() -> serverGUI.setVisible(true));
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                mode.saveToFile(new File(filepath));
            }
        });


        new Thread(() -> {
            while (true) {
                String data = receiver.receiveData();
                if (data.equals("save"))
                    receiver.transmitCollection(mode.getNpcs());
                else {
                    String[] logPass = data.split("/", 3);
                    for (String logPasses : logPass) {
                        System.out.println(logPasses);
                    }
                    if (logPass[0].equals("login")) {
                        if (login.checkClientLogin(logPass[1], logPass[2]))
                            receiver.transmitMessage("Success");
                        else
                            receiver.transmitMessage("Wrong");
                    } else if (logPass[0].equals("register")) {
                        if (login.register(logPass[1], logPass[2]))
                            receiver.transmitMessage("Success");
                        else
                            receiver.transmitMessage("Wrong");
                    }
                }
            }
        }).start();


        mode.inputFromDB();
        serverGUI.setNpcId(mode.getCurrId()+1);
        serverGUI.addListeners(mode.getNpcs());
        serverGUI.createTree(mode.getNpcs(), "Collection");
        while (true) {
//            String data = receiver.receiveData();

            if (serverGUI.isWantedToBeClosed()) System.exit(0);

            new Thread(() -> {
                String datax = "";
                synchronized (serverGUI) {
                    if (serverGUI.isWantedToBeExecuted()) {
                        datax = serverGUI.commandToExecute();
                    }
                }
                if (!datax.equals("no")) {
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
                        case "read": {
                            mode.inputFromFileToDB(new File(filepath));
                            serverGUI.setNpcId(mode.getCurrId() + 1);
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(), "Collection");
                            break;
                        }
                        case "clear": {
                            mode.clear();
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(), "Collection");
                            serverGUI.setOperationsStatus("Collection cleared.");
                            break;
                        }
                        case "add": {
//                            try {
                            serverGUI.setOperationsStatus(mode.addElement(additionalData));
                            serverGUI.setNpcId(mode.getCurrId() + 1);
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(), "Collection");
//                            } catch (DateTimeParseException exc) {
//                                serverGUI.setOperationsStatus("Incorrect date format.");
//                            }
//                            finally {
//                                break;
//                            }
                            break;
                        }

                        case "change": {
                            try {
                                serverGUI.setOperationsStatus(mode.changeElement(additionalData));
                                serverGUI.setNpcId(mode.getCurrId() + 1);
                                serverGUI.addListeners(mode.getNpcs());
                                serverGUI.createTree(mode.getNpcs(), "Collection");
                            } catch (DateTimeParseException exc) {
                                serverGUI.setOperationsStatus("Incorrect date format.");
                            } finally {
                                break;
                            }
                        }
                        case "remove": {
                            serverGUI.setOperationsStatus(mode.removeElement(additionalData));
                            serverGUI.setNpcId(mode.getCurrId() + 1);
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(), "Collection");
                            break;
                        }
                        case "remove_first": {
                            serverGUI.setOperationsStatus(mode.removeFirst());
                            serverGUI.setNpcId(mode.getCurrId() + 1);
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(), "Collection");
                            break;
                        }
                    }
                }

//                receiver.transmitMessage(mode.commandVariants(command, additionalData));

            }).run();

        }

    }
}
