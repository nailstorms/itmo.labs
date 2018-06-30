package com.company;

import com.google.gson.*;
import lab3.NPC;
import lab5.InteractiveMode;
import lab6.DatagramReceiver;
import lab7.Auth;
import lab7.ServerGUI;
import lab8.orm.DB;
import lab8.orm.SQL;

import java.awt.*;
import java.io.File;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.LinkedBlockingDeque;


public class Main {

    public static final String IPAddress = System.getenv("IPAddress");
    public static final int PortNumber = Integer.parseInt(System.getenv("PortNumber"));

    public static void main(String[] args) {
//        OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.parse("2009-01-01 10:35:35",
//                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),  OffsetDateTime.now(ZoneId.systemDefault()).getOffset());
//
//        System.out.println(odt.toString());
//


        SQL<NPC> sql = new SQL<>(NPC.class);
        DB db = new DB("jdbc:postgresql://localhost:5432/lab8", "nailstorm", "qwerty");
            db.execute(sql.createTable());
//
//        NPC npc = new NPC("za", 123.0, 66.1, 77.1);
//             db.executeUpdate(sql.insert(npc));
//
//        int id = db.executeUpdateGetId(sql.insert(npc));
//        System.out.println(sql.insert(npc));
//        npc.setId(id);
//
//        npc = new NPC("zhora", 11.0, 66.1, 77.1);
//        npc.setId(id);
//        System.out.println(sql.update(npc));
//
//        db.execute(sql.update(npc));
//        System.out.println(sql.delete(npc));
//        db.execute(sql.delete(npc));
//        List<NPC> listNpc = sql.resultsToObjects(db.fetch(sql.selectAll()));
//
//        for (NPC npce : listNpc)
//            System.out.println(npce);

        DatagramReceiver receiver = new DatagramReceiver();
        InteractiveMode mode = new InteractiveMode();

        ServerGUI serverGUI = new ServerGUI();
        Auth login = new Auth(() -> serverGUI.setVisible(true));
        login.setVisible(true);

        if(login.hasPassedTheCheck()) {
            EventQueue.invokeLater(() -> serverGUI.setVisible(true));
        }

//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                mode.saveToDB();
//            }
//        });

        mode.inputFromDB();
        serverGUI.setNpcId(mode.getCurrId()+1);
        serverGUI.addListeners(mode.getNpcs());


        new Thread(()-> {
            while (true) {
                String data = receiver.receiveData();
                if (data.equals("save"))
                    receiver.transmitCollection(mode.getNpcs());
                else {
                    String[] logPass = data.split("/",3);
                    for (String logPasses : logPass) {
                        System.out.println(logPasses);
                    }
                    if(logPass[0].equals("login")) {
                        if (login.checkClientLogin(logPass[1], logPass[2]))
                            receiver.transmitMessage("Success");
                        else
                            receiver.transmitMessage("Wrong");
                    }
                    else if (logPass[0].equals("register")) {
                        if(login.register(logPass[1], logPass[2]))
                            receiver.transmitMessage("Success");
                        else
                            receiver.transmitMessage("Wrong");
                    }
                }
            }
        }).start();

        while (true) {
//            String data = receiver.receiveData();

            if(serverGUI.isWantedToBeClosed()) System.exit(0);

            new Thread(() -> {
                String datax = "";
                synchronized (serverGUI) {
                    if (serverGUI.isWantedToBeExecuted()) {
                        datax = serverGUI.commandToExecute();
                    }
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
//                        case "save": {
//                            mode.saveToDB();
//                            break;
//                        }
                        case "clear": {
                            mode.clear();
                            serverGUI.addListeners(mode.getNpcs());
                            serverGUI.createTree(mode.getNpcs(),"Collection");
                            serverGUI.setOperationsStatus("Collection cleared.");
                            break;
                        }
                        case "add": {
//                            try {
                                serverGUI.setOperationsStatus(mode.addElement(additionalData));
                                serverGUI.setNpcId(mode.getCurrId()+1);
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
