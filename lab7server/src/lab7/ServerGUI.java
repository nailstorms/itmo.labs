package lab7;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import lab3.*;

public class ServerGUI extends JFrame {

    NPCTree myTree = null;
    String commandToExecute = null;

    private JPanel leftElementInputPanel = new JPanel();
    private JPanel middleCollectionPanel = new JPanel();
    private JPanel rightCommandPanel = new JPanel();

    private JPanel nestedId = new JPanel();
    private JPanel nestedHeight = new JPanel();
    private JPanel nestedWeight = new JPanel();
    private JPanel nestedX = new JPanel();
    private JPanel nestedY = new JPanel();
    private JPanel nestedDateOfBirth = new JPanel();
    private JPanel nestedName = new JPanel();
    private JPanel nestedColor = new JPanel();
    private JPanel nestedBeauties = new JPanel();
    private JPanel nestedSharpnesses = new JPanel();

    private JPanel nestedAddElement = new JPanel();
    private JPanel nestedRemoveElement = new JPanel();
    private JPanel nestedRemoveFirst = new JPanel();
    private JPanel nestedClear = new JPanel();
    private JPanel nestedStatus = new JPanel();

    private JButton addElement = new JButton("Add/change element");
    private JButton removeElement = new JButton("Remove element");
    private JButton removeFirstElement = new JButton("Remove first element");
    private JButton clearCollection = new JButton("Clear collection");
    private JTextField operationsStatus = new JTextField(30);

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileHandling = new JMenu("File");
    private JMenuItem readFromFile = new JMenuItem("Read collection from file");
    private JMenuItem saveToFile = new JMenuItem("Save collection to file");
    private JMenuItem exitGui = new JMenuItem("Exit");
    private JMenu guiHelp = new JMenu("Help");

    private JTextField npcId = new JHintTextField("NPC's id");
    private JTextField npcName = new JHintTextField("NPC's name");
    private JTextField npcHeight = new JHintTextField("NPC's height");
    private JTextField npcWeight = new JHintTextField("NPC's weight");
    private JTextField npcDoB = new JHintTextField("NPC's date of birth");
    private JTextField npcX = new JHintTextField("NPC's coordinate by x-axis");
    private JTextField npcY = new JHintTextField("NPC's coordinate by y-axis");
    private JTextField npcColor = new JHintTextField("NPC's color");
    private JComboBox npcBeautyLevel;
    private JComboBox npcChinSharpness;

    JScrollPane scrollPane = new JScrollPane(middleCollectionPanel);

//    JPanel upperCommandPanel = new JPanel();
//    JPanel middleCollectionPanel = new JPanel();
//    JPanel lowerUtilityPanel = new JPanel();
//    JPanel lowerNestedPanel1 = new JPanel();
//    JPanel lowerNestedPanel2 = new JPanel();
//    JPanel lowerNestedPanel3 = new JPanel();
//
//
//    String[] commands = {"add", "add_if_min", "add_if_max", "remove", "remove_first", "clear"};
//    JComboBox<String> commandBox = new JComboBox<>(commands);
//
//    JMenuBar menuBar = new JMenuBar();
//    JMenu fileHandling = new JMenu("File");
//    JMenuItem readFromFile = new JMenuItem("Read collection from file");
//    JMenuItem saveToFile = new JMenuItem("Save collection to file");
//
//    JButton executor = new JButton("Execute");
//
//    JTextField executionResult = new JTextField("Placeholder");
//    JButton exitGUI = new JButton("Exit");
//    JButton helpButton = new JButton("?");
//
//    JScrollPane scrollPane = new JScrollPane();

    private boolean wantedToBeExecuted;
    private boolean wantedToBeClosed = false;



    public ServerGUI() {
        super("Lab task 7 - server collection manager");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.add(leftElementInputPanel, BorderLayout.WEST);
        this.add(middleCollectionPanel, BorderLayout.CENTER);
        this.add(rightCommandPanel, BorderLayout.EAST);


        leftElementInputPanel.setBackground(Color.LIGHT_GRAY);
        leftElementInputPanel.setLayout(new GridLayout(10,2));

        fileHandling.add(readFromFile);
        fileHandling.add(saveToFile);
        fileHandling.add(exitGui);
        menuBar.add(fileHandling);
        menuBar.add(guiHelp);
        this.setJMenuBar(menuBar);


        nestedId.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel idLabel = new JLabel("ID:");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(idLabel);
        nestedId.add(npcId);
        leftElementInputPanel.add(nestedId);

        nestedName.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(nameLabel);
        nestedName.add(npcName);
        leftElementInputPanel.add(nestedName);

        nestedDateOfBirth.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel dobLabel = new JLabel("Date of birth:");
        dobLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(dobLabel);
        nestedDateOfBirth.add(npcDoB);
        leftElementInputPanel.add(nestedDateOfBirth);

        nestedHeight.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(heightLabel);
        nestedHeight.add(npcHeight);
        leftElementInputPanel.add(nestedHeight);

        nestedWeight.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel weightLabel = new JLabel("Weight:");
        weightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(weightLabel);
        nestedWeight.add(npcWeight);
        leftElementInputPanel.add(nestedWeight);


        nestedX.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelX = new JLabel("X:");
        labelX.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(labelX);
        nestedX.add(npcX);
        leftElementInputPanel.add(nestedX);

        nestedY.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelY = new JLabel("Y:");
        labelY.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(labelY);
        nestedY.add(npcY);
        leftElementInputPanel.add(nestedY);

        nestedColor.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelColor = new JLabel("Color:");
        labelColor.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(labelColor);
        nestedColor.add(npcColor);
        leftElementInputPanel.add(nestedColor);

        String[] beautyLevels = {"", "Hideous", "Normal", "Beautiful"};
        String[] chinSharpnesses = {"", "Flat", "Normal", "Sharp"};
        npcBeautyLevel = new JComboBox(beautyLevels);
        npcChinSharpness = new JComboBox(chinSharpnesses);

        nestedBeauties.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelBeauties = new JLabel("Beauty level:");
        labelBeauties.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(labelBeauties);
        nestedBeauties.add(npcBeautyLevel);
        leftElementInputPanel.add(nestedBeauties);

        nestedSharpnesses.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelChinaNumberOne = new JLabel("Chin sharpness:");
        labelChinaNumberOne.setHorizontalAlignment(SwingConstants.CENTER);
        leftElementInputPanel.add(labelChinaNumberOne);
        nestedSharpnesses.add(npcChinSharpness);
        leftElementInputPanel.add(nestedSharpnesses);


        rightCommandPanel.setBackground(Color.LIGHT_GRAY);
        rightCommandPanel.setLayout(new GridLayout(15,1));

        rightCommandPanel.add(new JPanel());
        nestedAddElement.add(addElement);
        rightCommandPanel.add(nestedAddElement);
        rightCommandPanel.add(new JPanel());

        nestedRemoveElement.add(removeElement);
        rightCommandPanel.add(nestedRemoveElement);
        rightCommandPanel.add(new JPanel());

        nestedRemoveFirst.add(removeFirstElement);
        rightCommandPanel.add(nestedRemoveFirst);
        rightCommandPanel.add(new JPanel());

        nestedClear.add(clearCollection);
        rightCommandPanel.add(nestedClear);

        rightCommandPanel.add(new JPanel());
        rightCommandPanel.add(new JPanel());
        rightCommandPanel.add(new JPanel());
        rightCommandPanel.add(new JPanel());
        rightCommandPanel.add(new JPanel());

        operationsStatus.setEditable(false);
        operationsStatus.setText("Operations result");
        operationsStatus.setHorizontalAlignment(SwingConstants.CENTER);
        rightCommandPanel.add(operationsStatus);

        rightCommandPanel.add(new JPanel());



        guiHelp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDialog information = new JDialog(new JFrame(),"Info");
                information.setLocation(500, 100);
                information.setSize(850,315);
                information.setLayout(new FlowLayout());

                JLabel intro = new JLabel("Some useful information about collection manager:");
                intro.setFont(new Font("Droid Serif",Font.BOLD,16));

                JLabel collectionView = new JLabel("You view the collection as a tree. Each element is introduced by" +
                        " its fields. \n");
                JLabel collectionView1 = new JLabel("To load the collection from file, press \"File\" and " +
                        "\"Read collection from file\".\n");
                JLabel collectionSave = new JLabel("To save the collection to the file, press \"Save collection to file\".\n");

                JLabel com1 = new JLabel("To add element to collection you must first specify all the fields which you want your NPC to have.\n");

                JLabel labelElementsFields = new JLabel("Fields are as follows: id - unique NPC identifier (int); name - NPC's name (string);");
                JLabel labelElementsFields1 = new JLabel("height - NPC's height (int); weight - NPC's weight (int); dateOfBirth - NPC's date of birth (format - \"yyyy-MM-dd\");");
                JLabel labelElementsFields2 = new JLabel("x,y - NPC's coordinates on the map (int), color - NPC's color (string);");
                JLabel labelElementsFields3 = new JLabel("beautyLevel - NPC's level of beauty; chinSharpness - NPC's chin sharpness level.\n");
                JLabel labelElementsFields4 = new JLabel("Fields \"ID\" and \"Name\" are obligatory.\n");


                JPanel nestedCommandsIntro = new JPanel();
                JLabel commandsIntro = new JLabel("Information about commands: \n");
                commandsIntro.setFont(new Font("Droid Serif",Font.BOLD,16));

                JLabel labelCommands1 = new JLabel("On the right part of the screen addition/removal commands can be seen.\n");
                JLabel labelCommands2 = new JLabel("To add the element to collection, press \"Add/change element\". If there is already an element with such" +
                        " id, it will be replaced with the new data.\n");
                JLabel labelCommands3 = new JLabel("To remove the element from collection, press \"Remove element\". You only need an id to remove " +
                        "the specified element.\n");
                JLabel labelCommands4 = new JLabel("To remove the first element from collection, press \"Remove first element\".\n");
                JLabel labelCommands5 = new JLabel("To fully clear the collection, press \"Clear collection\".\n");

                information.add(intro);
                information.add(collectionView);
                information.add(collectionView1);
                information.add(collectionSave);

                information.add(com1);
                information.add(labelElementsFields);
                information.add(labelElementsFields1);
                information.add(labelElementsFields2);
                information.add(labelElementsFields3);
                information.add(labelElementsFields4);


                nestedCommandsIntro.add(commandsIntro);
                information.add(nestedCommandsIntro);

                information.add(labelCommands1);
                information.add(labelCommands2);
                information.add(labelCommands3);
                information.add(labelCommands4);
                information.add(labelCommands5);

                information.setVisible(true);
            }
        });

        exitGui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ServerGUI.this.setVisible(false);
                ServerGUI.this.dispose();
                commandToExecute = "save";
                wantedToBeExecuted = true;
                wantedToBeClosed = true;
            }
        });
    }

    public NPCTree createTree (LinkedBlockingDeque<NPC> npcs, String defaultNode) {

        List<TreePath> expandedPaths = new ArrayList<>();
        if (myTree != null) {
            for (int i = 0; i < myTree.getRowCount() - 1; i++) {
                TreePath currPath = myTree.getPathForRow(i);
                TreePath nextPath = myTree.getPathForRow(i + 1);
                if (currPath.isDescendant(nextPath)) {
                    expandedPaths.add(currPath);
                }
            }
        }
        myTree = new NPCTree(defaultNode);
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) myTree.getModel().getRoot();

        for(NPC readNPCs : npcs) {
//            rootNode.add(new DefaultMutableTreeNode("ID: " + readNPCs.getId() + ". Name: " + readNPCs.getNPCName()));
            rootNode.add(new DefaultMutableTreeNode(readNPCs));
        }

        this.middleCollectionPanel.remove(scrollPane);
        this.middleCollectionPanel.revalidate();

        myTree.setPreferredSize(new Dimension(720,300));
        scrollPane.getViewport().add(myTree);
        this.middleCollectionPanel.add(scrollPane);
        this.middleCollectionPanel.repaint();

        for (TreePath path : expandedPaths) {
            myTree.expandPath(path);
        }

        return myTree;
    }



    public void addListeners(LinkedBlockingDeque<NPC> npcs) {
        for(ActionListener listener : readFromFile.getActionListeners())
            readFromFile.removeActionListener(listener);
        for(ActionListener listener : saveToFile.getActionListeners())
            saveToFile.removeActionListener(listener);

        readFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerGUI.this.createTree(npcs, "Collection");
            }
        });

        saveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandToExecute = "save";
                wantedToBeExecuted = true;
            }
        });

        addElement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                commandToExecute = "add";
                wantedToBeExecuted = true;
            }
        });

        removeElement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                commandToExecute = "remove";
                wantedToBeExecuted = true;
            }
        });

        removeFirstElement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                commandToExecute = "remove_first";
                wantedToBeExecuted = true;
            }
        });

        clearCollection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                commandToExecute = "clear";
                wantedToBeExecuted = true;
            }
        });
    }

    public String commandToExecute() {
        this.wantedToBeExecuted = false;

        String[] characteristics = new String[10];

        characteristics[0] = npcId.getText();

        characteristics[1] = npcName.getText();

        characteristics[2] = npcHeight.getText();
        if (characteristics[2].equals("") || characteristics[2].equals("NPC's height"))
            characteristics[2] = null;
        characteristics[3] = npcWeight.getText();
        if (characteristics[3].equals("") || characteristics[3].equals("NPC's weight"))
            characteristics[3] = null;
        characteristics[4] = npcDoB.getText();
        if (characteristics[4].equals("") || characteristics[4].equals("NPC's date of birth"))
            characteristics[4] = null;
        characteristics[5] = npcX.getText();
        if (characteristics[5].equals("") || characteristics[5].equals("NPC's coordinate by x-axis"))
            characteristics[5] = null;
        characteristics[6] = npcY.getText();
        if (characteristics[6].equals("") || characteristics[6].equals("NPC's coordinate by y-axis"))
            characteristics[6] = null;
        characteristics[7] = npcBeautyLevel.getSelectedItem().toString();
        if (characteristics[7].equals(""))
            characteristics[7] = null;
        characteristics[8] = npcChinSharpness.getSelectedItem().toString();
        if (characteristics[8].equals(""))
            characteristics[8] = null;
        characteristics[9] = npcColor.getText();
        if (characteristics[9].equals("") || characteristics[9].equals("NPC's color"))
            characteristics[9] = null;

        if(commandToExecute.equals("add")) {
            if (characteristics[1].equals("") || characteristics[1].equals("NPC's name")) {
                this.setOperationsStatus("Name cannot be null!");
                return "no";
            }

        }


        ServerGUI.this.invalidate();

        if (characteristics[0].equals("") || characteristics[0].equals("NPC's id")) {
            this.setOperationsStatus("ID cannot be null!");
            return "no";
        } else {
                if (commandToExecute.equals("remove_first") || commandToExecute.equals("clear") || commandToExecute.equals("save"))
                    return commandToExecute;
                else {
                    if (commandToExecute.equals("remove")) {
                        return commandToExecute + " {\"id\":" + characteristics[0] + "}";
                    } else {
                        try {
                            int fieldsInt;
                            for (int i = 0; i < 7; i++) {
                                if (characteristics[i] != null)
                                    fieldsInt = Integer.parseInt(characteristics[i]);

                                if (i == 0 || i == 3) i++;
                            }
                            if(characteristics[4]!=null) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date dob = dateFormat.parse(characteristics[4]);
                            }
                        }   catch (NumberFormatException exc) {
                                ServerGUI.this.setOperationsStatus("Numeric fields must be integer.");
                                return "no";
                            } catch (ParseException | DateTimeParseException exc) {
                                ServerGUI.this.setOperationsStatus("Incorrect date format.");
                                return "no";
                            }
                    }

                        return commandToExecute + " " +
                                "{\"levels\":{\"beauty\":" + characteristics[7] + ",\"" + "chin\":" + characteristics[8] + "}," +
                                "\"name\":\"" + characteristics[1] + "\"," + "\"height\":" + characteristics[2] +
                                ",\"weight\":" + characteristics[3] + ",\"dateOfBirth\":" + characteristics[4] + "," +
                                "\"x\":" + characteristics[5] + ",\"y\":" + characteristics[6] + ",\"id\":" + characteristics[0] + ",\"color\":" + characteristics[9] + "}";
                    }
                }

            }


//        if (commandToExecute.equals("remove_first") || commandToExecute.equals("clear") || commandToExecute.equals("save"))
//            return commandToExecute;
//        else {
//            if (commandToExecute.equals("remove")) {
//                if (characteristics[0].equals("") || characteristics[0].equals("NPC's id")) {
//                    this.setOperationsStatus("ID cannot be null!");
//                    return "no";
//                } else return commandToExecute + " {\"id\":" + characteristics[0] + "}";
//            } else {
//
//                return commandToExecute + " " +
//                        "{\"levels\":{\"beauty\":" + characteristics[7] + ",\"" + "chin\":" + characteristics[8] + "}," +
//                        "\"name\":\"" + characteristics[1] + "\"," + "\"height\":" + characteristics[2] +
//                        ",\"weight\":" + characteristics[3] + ",\"dateOfBirth\":" + characteristics[4] + "," +
//                        "\"x\":" + characteristics[5] + ",\"y\":" + characteristics[6] + ",\"id\":" + characteristics[0] + ",\"color\":" + characteristics[9] + "}";
//            }
//        }


    public boolean isWantedToBeExecuted() {
        return wantedToBeExecuted;
    }

    public boolean isWantedToBeClosed() {
        return wantedToBeClosed;
    }

    public String getCommandToExecute () {
        return commandToExecute;
    }

    public void setOperationsStatus (String status) {
        operationsStatus.setText(status);
        ServerGUI.this.invalidate();
    }

}
