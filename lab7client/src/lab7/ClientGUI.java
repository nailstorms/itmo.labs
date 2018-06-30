package lab7;


import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class ClientGUI extends JFrame {

    public Locale locale;

    Locale ruLocale = new Locale("ru");
    Locale eeLocale = new Locale("ee");
    Locale lvLocale = new Locale("lv");
    Locale colLocale = new Locale("es", "CO");

//    ResourceBundle ruBundle = ResourceBundle.getBundle("ClientResourceBundle", ruLocale);
//    ResourceBundle eeBundle = ResourceBundle.getBundle("ClientResourceBundle", eeLocale);
//    ResourceBundle lvBundle = ResourceBundle.getBundle("ClientResourceBundle", lvLocale);
//    ResourceBundle colBundle = ResourceBundle.getBundle("ClientResourceBundle", colLocale);

    private boolean isDataAcquirePressed = false;
    private boolean stopTimer = true;
    private boolean time = true;


    private Color color;
    private int animationIterator = 0;

    private JTextField serverConnection = new JTextField(20);
    private JTextField userMessage = new JTextField(20);
    private JButton acquireDataFromServer = new JButton("Acquire data from server");
    private JButton helpButton = new JButton("Help");
    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");


    private JComboBox colors;
    private JComboBox beautyLevels;
    private JComboBox chinSharpnessTypes;

    private JPanel startStopHelp = new JPanel();
    private JPanel currentElementPanel = new JPanel();
    private JPanel collectionPanel = new JPanel();
    private JPanel filterPanel = new JPanel();
    private JPanel nestedAcquireData = new JPanel();
    private JPanel nestedId = new JPanel();
    private JPanel nestedHeight = new JPanel();
    private JPanel nestedWeight = new JPanel();
    private JPanel nestedX = new JPanel();
    private JPanel nestedY = new JPanel();
    private JPanel nestedDateOfBirth = new JPanel();
    private JPanel nestedDateOfBirthLabels = new JPanel();
    private JPanel nestedDateOfBirthFrom = new JPanel();
    private JPanel nestedDateOfBirthTo = new JPanel();
    private JPanel nestedNames = new JPanel();
    private JPanel nestedColors = new JPanel();
    private JPanel nestedBeauties = new JPanel();
    private JPanel nestedSharpnesses = new JPanel();

    private JLabel filterLabel = new JLabel("Filters");
    private JTextField name = new JTextField(8);
    private JTextField idFrom = new JTextField(8);
    private JTextField idTo = new JTextField(8);
    private JTextField heightFrom = new JTextField(8);
    private JTextField heightTo = new JTextField(8);
    private JTextField weightFrom = new JTextField(8);
    private JTextField weightTo = new JTextField(8);
    private DateTimePicker dateOfBirthFrom = new DateTimePicker();
    private DateTimePicker dateOfBirthTo = new DateTimePicker();
    private JTextField xFrom = new JTextField(8);
    private JTextField xTo = new JTextField(8);
    private JTextField yFrom = new JTextField(8);
    private JTextField yTo = new JTextField(8);

    private JLabel highlightedObject = new JLabel("Clicked object - ");
    private JLabel xCurrent = new JLabel("x:");
    private JLabel yCurrent = new JLabel("y:");
    private JLabel idCurrent = new JLabel("id:");
    private JLabel nameCurrent = new JLabel("name:");
    private JLabel heightCurrent = new JLabel("height:");
    private JLabel weightCurrent = new JLabel("weight:");
    private JLabel dobCurrent = new JLabel("dateOfBirth:");
    private JLabel beautyCurrent = new JLabel("beautyLevel:");
    private JLabel chinCurrent = new JLabel("chinSharpness:");


    private JTextField xCurrentValue = new JTextField(5);
    private JTextField yCurrentValue = new JTextField(5);
    private JTextField idCurrentValue = new JTextField(3);
    private JTextField nameCurrentValue = new JTextField(5);
    private JTextField heightCurrentValue = new JTextField(5);
    private JTextField weightCurrentValue = new JTextField(5);
    private JTextField dobCurrentValue = new JTextField(12);
    private JTextField beautyCurrentValue = new JTextField(6);
    private JTextField chinCurrentValue = new JTextField(6);


    private JLabel introLabel = new JLabel("Here is some useful information regarding the collection viewer:");

    private JLabel labelRetrieve = new JLabel("To fetch the NPC collection from the server press the \"Acquire data from server\" button.\n");

    private JLabel labelElements = new JLabel("You view NPCs as ellipses on the XOY Cartesian coordinate system.");
    private JLabel labelElements1 = new JLabel("To learn more information about the specified object, click on it. " +
            "The information may be seen at the top of the screen.");
    private  JLabel labelElements2 = new JLabel("Hover on object to see its name.\n");

    private JLabel labelElementsFields = new JLabel("Fields are as follows: id - unique NPC identifier; name - NPC's name;");
    private JLabel labelElementsFields1 = new JLabel("height - NPC's height; weight - NPC's weight; dateOfBirth - NPC's date of birth;");
    private JLabel labelElementsFields2 = new JLabel("x,y - NPC's coordinates on the map, color -  NPC's color;");
    private JLabel labelElementsFields3 = new JLabel("beautyLevel - NPC's level of beauty; chinSharpness - NPC's chin sharpness level.\n");

    private JLabel labelFilters = new JLabel("To filter the objects tune the desired characteristics.");
    private JLabel labelFilters1 = new JLabel("All fields except \"Date of birth\" have numerical values (integer), \"Date of birth\" input format - \"yyyy-MM-dd\".\n");
    private JLabel labelFilters2 = new JLabel("If you leave field \"From\" unfilled, only elements with value lower than \"To\" will be considered.\n");
    private JLabel labelFilters3 = new JLabel("If you leave field \"To\" unfilled, only elements with value higher than \"From\" will be considered.\n");
    private JLabel labelFilters4 = new JLabel("If you leave both fields \"From\" and \"To\" unfilled, the characteristic will be not considered at all.\n");
    private JLabel labelFilters5 = new JLabel();

    private JLabel labelStart = new JLabel("\"Start\" button makes objects which correspond to the filters to disappear slowly, then reappear after some time.");
    private JLabel labelStart1 = new JLabel();
    private JLabel labelStop = new JLabel("\"Stop\" button stops the animation.");

    private String dataAcquired;
    private String unableToPaint;
    private String animStart;
    private String animStop;
    private String formatError;
    private String dateFormatError;
    private String idError;
    private String heightError;
    private String weightError;
    private String dateError;
    private String xError;
    private String yError;
    private String serverUnav;
    private String emptyColl;
    private String ioError;

    private LanguageMenu languageMenu;


    public ClientGUI() {
        super("Lab task 7 - client collection viewer");
        this.locale = Locale.getDefault();

        String[] s1 = {""};
        String[] s2 = {""};
        String[] s3 = {""};

        JMenuBar menuBar = new JMenuBar();
        languageMenu = new LanguageMenu(this);
        menuBar.add(languageMenu);
        this.setJMenuBar(menuBar);


        colors = new JComboBox(s1);
        beautyLevels = new JComboBox(s2);
        chinSharpnessTypes = new JComboBox(s3);

        collectionPanel.setBorder(LineBorder.createBlackLineBorder());
        collectionPanel.setLayout(null);
        collectionPanel.setBackground(Color.WHITE);

        currentElementPanel.setBackground(Color.LIGHT_GRAY);

        currentElementPanel.add(highlightedObject);
        currentElementPanel.add(idCurrent);
        currentElementPanel.add(idCurrentValue);
        idCurrentValue.setEditable(false);
        currentElementPanel.add(xCurrent);
        currentElementPanel.add(xCurrentValue);
        xCurrentValue.setEditable(false);
        currentElementPanel.add(yCurrent);
        currentElementPanel.add(yCurrentValue);
        yCurrentValue.setEditable(false);
        currentElementPanel.add(nameCurrent);
        currentElementPanel.add(nameCurrentValue);
        nameCurrentValue.setEditable(false);
        currentElementPanel.add(heightCurrent);
        currentElementPanel.add(heightCurrentValue);
        heightCurrentValue.setEditable(false);
        currentElementPanel.add(weightCurrent);
        currentElementPanel.add(weightCurrentValue);
        weightCurrentValue.setEditable(false);
        currentElementPanel.add(dobCurrent);
        currentElementPanel.add(dobCurrentValue);
        dobCurrentValue.setEditable(false);
        currentElementPanel.add(beautyCurrent);
        currentElementPanel.add(beautyCurrentValue);
        beautyCurrentValue.setEditable(false);
        currentElementPanel.add(chinCurrent);
        currentElementPanel.add(chinCurrentValue);
        chinCurrentValue.setEditable(false);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        filterPanel.setLayout(new GridLayout(16,1));
        this.add(filterPanel, BorderLayout.WEST);
        this.add(currentElementPanel, BorderLayout.NORTH);
        this.add(collectionPanel, BorderLayout.CENTER);


        nestedAcquireData.add(acquireDataFromServer);
        filterPanel.add(nestedAcquireData);

        serverConnection.setHorizontalAlignment(SwingConstants.CENTER);
        serverConnection.setBackground(Color.white);
        filterPanel.add(serverConnection);
        serverConnection.setEditable(false);

        userMessage.setHorizontalAlignment(SwingConstants.CENTER);
        userMessage.setBackground(Color.white);
        filterPanel.add(userMessage);
        userMessage.setEditable(false);

        JPanel nestedPanelForText = new JPanel();
        nestedPanelForText.setLayout(new FlowLayout());
        nestedPanelForText.add(filterLabel);
        filterPanel.add(nestedPanelForText);


        nestedId.setLayout(new FlowLayout(FlowLayout.CENTER,30,0));
        JLabel idLabel = new JLabel("< id <");
        nestedId.add(idFrom);
        nestedId.add(idLabel);
        nestedId.add(idTo);
        filterPanel.add(nestedId);

        nestedHeight.setLayout(new FlowLayout(FlowLayout.CENTER,17,0));
        JLabel heightLabel = new JLabel("< height <");
        nestedHeight.add(heightFrom);
        nestedHeight.add(heightLabel);
        nestedHeight.add(heightTo);
        filterPanel.add(nestedHeight);

        nestedWeight.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
        JLabel weightLabel = new JLabel("< weight <");
        nestedWeight.add(weightFrom);
        nestedWeight.add(weightLabel);
        nestedWeight.add(weightTo);
        filterPanel.add(nestedWeight);


        nestedX.setLayout(new FlowLayout(FlowLayout.CENTER,32,0));
        JLabel labelX = new JLabel("< x <");
        nestedX.add(xFrom);
        nestedX.add(labelX);
        nestedX.add(xTo);
        filterPanel.add(nestedX);

        nestedY.setLayout(new FlowLayout(FlowLayout.CENTER,32,0));
        JLabel labelY = new JLabel("< y <");
        nestedY.add(yFrom);
        nestedY.add(labelY);
        nestedY.add(yTo);
        filterPanel.add(nestedY);

        nestedDateOfBirth.setLayout(new FlowLayout(FlowLayout.CENTER,30,0));
        JLabel dobLabelFrom = new JLabel("date of birth >                 ");
        JLabel dobLabelTo = new JLabel("                 date of birth <");
        nestedDateOfBirthLabels.add(dobLabelFrom);
        nestedDateOfBirthLabels.add(dobLabelTo);
        filterPanel.add(nestedDateOfBirthLabels);
//        nestedDateOfBirth.setLayout(new FlowLayout(FlowLayout.CENTER,3,0));
        filterPanel.add(nestedDateOfBirth);


        nestedNames.setLayout(new FlowLayout(FlowLayout.CENTER,30,0));
        nestedColors.setLayout(new FlowLayout(FlowLayout.CENTER));
        nestedBeauties.setLayout(new FlowLayout(FlowLayout.CENTER));
        nestedSharpnesses.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel labelNames = new JLabel("name  ");
        nestedNames.add(labelNames);
        JLabel labelColors = new JLabel("color  ");
        nestedColors.add(labelColors);
        JLabel labelBeautyLevels = new JLabel("beautyLevel  ");
        nestedBeauties.add(labelBeautyLevels);
        JLabel labelChinSharpnesses = new JLabel("chinSharpness  ");
        nestedSharpnesses.add(labelChinSharpnesses);

        nestedNames.add(name);
        nestedColors.add(colors);
        nestedBeauties.add(beautyLevels);
        nestedSharpnesses.add(chinSharpnessTypes);

        filterPanel.add(nestedNames);
        filterPanel.add(nestedColors);
        filterPanel.add(nestedBeauties);
        filterPanel.add(nestedSharpnesses);


        startStopHelp.setLayout(new FlowLayout());
        startStopHelp.add(startButton);
        startStopHelp.add(stopButton);
        startStopHelp.add(helpButton);
        filterPanel.add(startStopHelp);

        this.changeLanguage(Locale.getDefault());

        acquireDataFromServer.addMouseListener(new MouseAdapter() {
                                 @Override
                                 public void mouseClicked(MouseEvent mouseEvent) {
                                     isDataAcquirePressed = true;
                                 }
                             }
        );

        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JDialog dialogHelp = new JDialog(new JFrame(), "Info");
                dialogHelp.setLocation(500, 100);
                dialogHelp.setSize(800, 400);
                dialogHelp.setLayout(new FlowLayout());

                introLabel.setFont(new Font("Droid Serif",Font.BOLD,16));



                dialogHelp.add(introLabel);
                dialogHelp.add(labelRetrieve);

                dialogHelp.add(labelElements);
                dialogHelp.add(labelElements1);
                dialogHelp.add(labelElements2);

                dialogHelp.add(labelElementsFields);
                dialogHelp.add(labelElementsFields1);
                dialogHelp.add(labelElementsFields2);
                dialogHelp.add(labelElementsFields3);

                dialogHelp.add(labelFilters);
                dialogHelp.add(labelFilters1);
                dialogHelp.add(labelFilters2);
                dialogHelp.add(labelFilters3);
                dialogHelp.add(labelFilters4);
                dialogHelp.add(labelFilters5);

                dialogHelp.add(labelStart);
                dialogHelp.add(labelStart1);
                dialogHelp.add(labelStop);

                dialogHelp.setVisible(true);
            }
        });
    }

    public void setStatusMessage(String s) {
        userMessage.setText(s);
        ClientGUI.this.invalidate();
    }

    public void setServerStatusMessage(String s) {
        serverConnection.setText(s);
        ClientGUI.this.invalidate();
    }


    public void setDataAcquire() {
        isDataAcquirePressed = false;
    }

    public boolean isDataAcquirePressed() {
        return isDataAcquirePressed;
    }

    public Ellipse[] drawNPCs(String npcs[]) {

        nestedColors.remove(colors);
        nestedBeauties.remove(beautyLevels);
        nestedSharpnesses.remove(chinSharpnessTypes);

        serverConnection.setText(dataAcquired);
        collectionPanel.removeAll();
        collectionPanel.repaint();



        Ellipse ellipses[] = new Ellipse[npcs.length];

        for (int i = 0; i < npcs.length; i++) {
            String fields[] = npcs[i].split(" ");
            try {
                if(!fields[9].toLowerCase().equals("null")) {
                    ellipses[i] = new Ellipse(0, 0,
                            (int) Double.parseDouble(fields[3]), (int) Double.parseDouble(fields[2]),
                            (Color) (Class.forName("java.awt.Color").getField(fields[9].toLowerCase())).get(null));
                }
                else throw new Exception("");
//                (int) Double.parseDouble(fields[5]), (int) Double.parseDouble(fields[6]),
//                        (int) Double.parseDouble(fields[3]), (int) Double.parseDouble(fields[2])
            } catch (Exception exc) {
                ellipses[i] = new Ellipse(0, 0,
                        (int) Double.parseDouble(fields[3]), (int) Double.parseDouble(fields[2]),
                        Color.black);
            }
            try {
                Field field = Class.forName("java.awt.Color").getField(fields[9].toLowerCase());
                color = (Color) field.get(null);
            } catch (Exception e) {
                color = null;
            }

            try {
                int panelX0 = collectionPanel.getWidth()/2;
                int panelY0 = collectionPanel.getHeight()/2;
                int ellipseWidth = (int) Double.parseDouble(fields[3]);
                int ellipseHeight = (int) Double.parseDouble(fields[2]);
                int ellipseX = (int) Double.parseDouble(fields[5]);
                int ellipseY = (int) Double.parseDouble(fields[6]);

                ellipses[i].setLocation(panelX0-ellipseWidth/2+ellipseX,panelY0-ellipseHeight/2-ellipseY);
                    ellipses[i].setSize(ellipseWidth, ellipseHeight);
            } catch (NullPointerException | NumberFormatException | ClassCastException exc) {
                ClientGUI.this.setStatusMessage(unableToPaint + fields[1]); ellipses[i] = null;
            }
            if (ellipses[i] != null) {
                collectionPanel.add(ellipses[i]);
                ellipses[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        xCurrentValue.setText(fields[5]);
                        yCurrentValue.setText(fields[6]);
                        dobCurrentValue.setText(fields[4]);
                        weightCurrentValue.setText(fields[3]);
                        heightCurrentValue.setText(fields[2]);
                        nameCurrentValue.setText(fields[1]);
                        idCurrentValue.setText(fields[0]);
                        beautyCurrentValue.setText(fields[7]);
                        chinCurrentValue.setText(fields[8]);
                    }});
                ellipses[i].setToolTipText(fields[1]); }
            this.repaint();
        }
        ClientGUI.this.revalidate();
        return ellipses;
    }



    public void startAnimation(Ellipse[] ellipses, String[] npcs) {
        for( MouseListener al : startButton.getMouseListeners() ) {
            startButton.removeMouseListener(al);
        }
        for( MouseListener al : stopButton.getMouseListeners() ) {
            stopButton.removeMouseListener(al);
        }

        Timer reappearTimer = new Timer(80, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean[] checker = ClientGUI.this.checkFormat(npcs);


                for (int i=0; i < npcs.length; i++) {
                    if (checker[i]) {
                        String fields[] = npcs[i].split(" ");
                        if (ellipses[i] != null) {
                            collectionPanel.repaint();

                            int redValue = ellipses[i].getColor().getRed();
                            int greenValue = ellipses[i].getColor().getGreen();
                            int blueValue = ellipses[i].getColor().getBlue();
                            int alphaValue = ellipses[i].getColor().getAlpha()+5;
                            if (alphaValue > 255) alphaValue = 255;

                            color = new Color(redValue,greenValue,blueValue,alphaValue);
                            ellipses[i].setColor(color);

                            int panelX0 = collectionPanel.getWidth()/2;
                            int panelY0 = collectionPanel.getHeight()/2;
                            int ellipseWidth = (int) Double.parseDouble(fields[3]);
                            int ellipseHeight = (int) Double.parseDouble(fields[2]);
                            int ellipseX = (int) Double.parseDouble(fields[5]);
                            int ellipseY = (int) Double.parseDouble(fields[6]);

                            ellipses[i].setLocation(panelX0-ellipseWidth/2+ellipseX,panelY0-ellipseHeight/2-ellipseY);
                            ellipses[i].setSize(ellipseWidth, ellipseHeight);


                            collectionPanel.add(ellipses[i]);

                            ellipses[i].setToolTipText(fields[1]); }
                        ClientGUI.this.repaint();
                    }
                }
                if (animationIterator < 50) {
                    animationIterator++;
                } else {
                    animationIterator = 0;
                    ((Timer)actionEvent.getSource()).stop();
                    stopTimer=true;
                }
            }
        });

        Timer disappearTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean[] checker = ClientGUI.this.checkFormat(npcs);

                for (int i = 0; i < npcs.length; i++) {
                    if (checker[i]) {
                        String fields[] = npcs[i].split(" ");
                        if (ellipses[i] != null) {
                            collectionPanel.remove(ellipses[i]);
                            ClientGUI.this.repaint();

                            int redValue = ellipses[i].getColor().getRed();
                            int greenValue = ellipses[i].getColor().getGreen();
                            int blueValue = ellipses[i].getColor().getBlue();
                            int alphaValue = ellipses[i].getColor().getAlpha()-5;
                            if (alphaValue < 0) alphaValue = 0;


                            color = new Color(redValue,greenValue,blueValue,alphaValue);

                            ellipses[i].setColor(color);

                            int panelX0 = collectionPanel.getWidth()/2;
                            int panelY0 = collectionPanel.getHeight()/2;
                            int ellipseWidth = (int) Double.parseDouble(fields[3]);
                            int ellipseHeight = (int) Double.parseDouble(fields[2]);
                            int ellipseX = (int) Double.parseDouble(fields[5]);
                            int ellipseY = (int) Double.parseDouble(fields[6]);

                            ellipses[i].setLocation(panelX0-ellipseWidth/2+ellipseX,panelY0-ellipseHeight/2-ellipseY);
                            ellipses[i].setSize(ellipseWidth, ellipseHeight);

                            collectionPanel.add(ellipses[i]);

                            ellipses[i].setToolTipText(fields[1]);

                        }
                        ClientGUI.this.repaint();
                    }
                }
                if (animationIterator < 50) {
                    animationIterator++;
                } else {
                    animationIterator = 0;
                    ((Timer)actionEvent.getSource()).stop();
                    reappearTimer.start();

                }
            }
        });

        Timer restartTimer = new Timer(9500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                disappearTimer.start();
            }});

        acquireDataFromServer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                ClientGUI.this.setStatusMessage("");
                if (restartTimer.isRunning()) { restartTimer.stop();
                    if (reappearTimer.isRunning()) reappearTimer.stop();
                    if (disappearTimer.isRunning()) disappearTimer.stop();
                    collectionPanel.removeAll(); isDataAcquirePressed = true;}
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                ClientGUI.this.setStatusMessage(animStart);
                //   for( MouseListener al : get.getMouseListeners() ) {
                //     get.removeMouseListener(al);
                //    }
                if (time) {
                    disappearTimer.start();
                    restartTimer.setInitialDelay(9500);
                    restartTimer.start();
                }

            }
        });

        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                restartTimer.stop();
                ClientGUI.this.setStatusMessage(animStop);
                acquireDataFromServer.addMouseListener(new MouseAdapter() {
                                         @Override
                                         public void mouseClicked(MouseEvent mouseEvent) {
                                             isDataAcquirePressed = true;
                                         }
                                     }
                );

            }
        });
    }

    public void filterCreate (String[] npcs) {
        int max[] = {-30000,-30000,-30000,-30000,-30000,-30000,-30000,-30000,-30000,-30000};
        int min[] = {30000,30000,30000,30000,30000,30000,30000,30000,30000,30000};

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar maxDateCal = Calendar.getInstance();
        Calendar minDateCal = Calendar.getInstance();
        maxDateCal.set(1800,Calendar.JANUARY,1);
        minDateCal.set(2030,Calendar.JANUARY,1);
        LocalDateTime maxDate = maxDateCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime minDate = minDateCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


        ArrayList<String> color = new ArrayList<>();


        color.add("");
        for (int i = 0; i< npcs.length; i++) {
            String fields[] = npcs[i].split(" ");

            if (!(color.contains(fields[9].toLowerCase()))) color.add(fields[9].toLowerCase());

            if(!fields[4].equals("null")) {
                try {

                        LocalDateTime dateOfBirthNew = format.parse(fields[4]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        if (dateOfBirthNew.isAfter(maxDate)) maxDate = dateOfBirthNew;
                        if (dateOfBirthNew.isBefore(minDate)) minDate = dateOfBirthNew;

                } catch (ParseException exc) {
                    ClientGUI.this.setStatusMessage(dateFormatError);
                }
            }

            for (int j=0; j<fields.length; j++) {
                try {
                    if (Double.parseDouble(fields[j]) > max[j]) {
                        max[j] = (int) Double.parseDouble(fields[j]);
                    }
                    if (Double.parseDouble(fields[j]) < min[j]) {
                        min[j] = (int) Double.parseDouble(fields[j]);
                    }
                } catch (NumberFormatException e) {j++; j--;}
            }
        }
        idFrom.setText(String.valueOf(min[0])); idTo.setText(String.valueOf(max[0]));
        heightFrom.setText(String.valueOf(min[2])); heightTo.setText(String.valueOf(max[2]));
        weightFrom.setText(String.valueOf(min[3])); weightTo.setText(String.valueOf(max[3]));
        dateOfBirthFrom.setDateTimePermissive(minDate); dateOfBirthTo.setDateTimePermissive(maxDate);
        xFrom.setText(String.valueOf(min[5])); xTo.setText(String.valueOf(max[5]));
        yFrom.setText(String.valueOf(min[6])); yTo.setText(String.valueOf(max[6]));

        String[] s3 = {"", "Hideous", "Normal", "Beautiful"};
        String[] s4 = {"", "Flat", "Normal", "Sharp"};


        colors = new JComboBox(color.toArray());
        beautyLevels = new JComboBox(s3);
        chinSharpnessTypes = new JComboBox(s4);


        nestedColors.add(colors);
        nestedBeauties.add(beautyLevels);
        nestedSharpnesses.add(chinSharpnessTypes);

        filterPanel.revalidate();
        ClientGUI.super.revalidate();

    }

    public boolean[] checkFormat(String[] npcs) {
        boolean[] check = new boolean[npcs.length];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        for (int i=0; i < npcs.length; i++) {
            String fields[] = npcs[i].split(" ");
            boolean fieldChecks[] = new boolean[fields.length];
            time = true;
            try {

                if (idFrom.getText().equals("") && idTo.getText().equals(""))  {fieldChecks[0] = true; } else {
                    if (!(fields[0].equals("null"))) {
                        if ((idTo.getText().equals("")) || (idFrom.getText().equals(""))) {
                            fieldChecks[0] = (idTo.getText().equals("")) && ((int) Double.parseDouble(fields[0]) >= Integer.parseInt(idFrom.getText()));
                            if (( (idFrom.getText().equals("")) && (int)Double.parseDouble(fields[0]) <= Integer.parseInt(idTo.getText())) )
                                fieldChecks[0] = true;
                        } if (!(idFrom.getText().equals("")) && !(idTo.getText().equals(""))) {
                            if (  (!(idFrom.getText().equals("")))&& (!(idTo.getText().equals(""))) && Integer.parseInt(idFrom.getText()) > Integer.parseInt(idTo.getText()))
                                ClientGUI.this.setStatusMessage(idError);
                            fieldChecks[0] = ((int) Double.parseDouble(fields[0]) >= Integer.parseInt(idFrom.getText())) && (((int) Double.parseDouble(fields[0]) <= Integer.parseInt(idTo.getText())));
                        }
                    } else {fieldChecks[0] = false;} }

                fieldChecks[1] = name.getText().equals(fields[1].toLowerCase());
                if (name.getText().equals("")) fieldChecks[1] = true;


                if (heightFrom.getText().equals("") && heightTo.getText().equals(""))  {fieldChecks[2] = true; } else {
                    if (!(fields[2].equals("null"))) {
                        if ((heightTo.getText().equals("")) || (heightFrom.getText().equals(""))) {
                            fieldChecks[2] = false;
                            if ( (heightTo.getText().equals(""))&&((int)Double.parseDouble(fields[2]) >= Integer.parseInt(heightFrom.getText())) )
                                fieldChecks[2] = true;
                            if ((heightFrom.getText().equals("")) && ((int)Double.parseDouble(fields[2]) <= Integer.parseInt(heightTo.getText())))
                                fieldChecks[2] = true;
                        }
                        if (!(heightFrom.getText().equals("")) && !(heightTo.getText().equals(""))) {
                            if (Integer.parseInt(heightFrom.getText()) > Integer.parseInt(heightTo.getText())) ClientGUI.this.setStatusMessage(heightError);
                            fieldChecks[2] = ((int) Double.parseDouble(fields[2]) >= Integer.parseInt(heightFrom.getText())) && (((int) Double.parseDouble(fields[2]) <= Integer.parseInt(heightTo.getText())));
                        }
                    } else {fieldChecks[2] = false;} }


                if (weightFrom.getText().equals("") && weightTo.getText().equals(""))  {fieldChecks[3] = true; } else {
                    if (!(fields[3].equals("null"))) {
                        if ((weightTo.getText().equals("")) || (weightFrom.getText().equals(""))) {
                            fieldChecks[3] = (weightTo.getText().equals("")) && ((int) Double.parseDouble(fields[3]) >= Integer.parseInt(weightFrom.getText()));
                            if ((weightFrom.getText().equals("")) && ((int)Double.parseDouble(fields[3]) <= Integer.parseInt(weightTo.getText())) )
                                fieldChecks[3] = true;
                        }
                        if (!(weightFrom.getText().equals("")) && !(weightTo.getText().equals(""))) {
                            if (Integer.parseInt(weightFrom.getText()) > Integer.parseInt(weightTo.getText())) ClientGUI.this.setStatusMessage(weightError);
                            fieldChecks[3] = ((int) Double.parseDouble(fields[3]) >= Integer.parseInt(weightFrom.getText())) && (((int) Double.parseDouble(fields[3]) <= Integer.parseInt(weightTo.getText())));
                        }
                    } else {fieldChecks[3] = false;} }


                    fieldChecks[4] = (dateOfBirthFrom.getDateTimePermissive().compareTo(format.parse(fields[4]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) <= 0) &&
                            (dateOfBirthTo.getDateTimePermissive().compareTo(format.parse(fields[4]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) >= 0);
//                if (dateOfBirthFrom.getText().equals("") && dateOfBirthTo.getText().equals(""))  {fieldChecks[4] =true; } else {
//                    if (!(fields[4].equals("null"))) {
//
//                        if ((dateOfBirthTo.getText().equals("")) || (dateOfBirthFrom.getText().equals(""))) {
//                            fieldChecks[4] = (dateOfBirthTo.getText().equals("")) && (format.parse(fields[4]).compareTo(format.parse((dateOfBirthFrom.getText()))) >= 0);
//                            if ((dateOfBirthFrom.getText().equals("")) && (format.parse(fields[4]).compareTo(format.parse((dateOfBirthTo.getText()))) <= 0))
//                                fieldChecks[4] = true;
//                        }
//                        if (!(dateOfBirthFrom.getText().equals("")) && !(dateOfBirthTo.getText().equals(""))) {
//                            if ((format.parse(dateOfBirthFrom.getText()).compareTo(format.parse(dateOfBirthTo.getText())) > 0)) ClientGUI.this.setStatusMessage(dateError);
//                            fieldChecks[4] = (format.parse(fields[4]).compareTo(format.parse((dateOfBirthFrom.getText()))) >= 0) && (format.parse(fields[4]).compareTo(format.parse((dateOfBirthTo.getText()))) <= 0);
//                        }
//                    } else {fieldChecks[4] = false;} }



                if (xFrom.getText().equals("") && xTo.getText().equals(""))  {fieldChecks[5] = true; } else {
                    if (!(fields[5].equals("null"))) {
                        if ((xTo.getText().equals("")) || (xFrom.getText().equals(""))) {
                            fieldChecks[5] = (xTo.getText().equals("")) && ((int) Double.parseDouble(fields[5]) >= Integer.parseInt(xFrom.getText()));
                            if ( (xFrom.getText().equals("")) && ((int)Double.parseDouble(fields[5]) <= Integer.parseInt(xTo.getText())))
                                fieldChecks[5] = true;
                        }
                        if (!(xFrom.getText().equals("")) && !(xTo.getText().equals(""))) {
                            if (Integer.parseInt(xFrom.getText()) > Integer.parseInt(xTo.getText())) ClientGUI.this.setStatusMessage(xError);
                            fieldChecks[5] = ((int) Double.parseDouble(fields[5]) >= Integer.parseInt(xFrom.getText())) && (((int) Double.parseDouble(fields[5]) <= Integer.parseInt(xTo.getText())));
                        }
                    } else {fieldChecks[5] = false;} }



                if (yFrom.getText().equals("") && yTo.getText().equals(""))  {fieldChecks[6] = true; } else {
                    if (!(fields[6].equals("null"))) {
                        if ((yTo.getText().equals("")) || (yFrom.getText().equals(""))) {
                            fieldChecks[6] = (yTo.getText().equals("")) && ((int) Double.parseDouble(fields[6]) >= Integer.parseInt(yFrom.getText()));
                            if ((yFrom.getText().equals("")) && ((int) Double.parseDouble(fields[6]) <= Integer.parseInt(yTo.getText())))
                                fieldChecks[6] = true;
                        }
                        if (!(yFrom.getText().equals("")) && !(yTo.getText().equals(""))) {
                            if (Integer.parseInt(yFrom.getText()) > Integer.parseInt(yTo.getText()) && (!(yFrom.getText().equals(""))) && (!(yTo.getText().equals(""))))
                                ClientGUI.this.setStatusMessage(yError);
                            fieldChecks[6] = ((int) Double.parseDouble(fields[6]) >= Integer.parseInt(yFrom.getText())) && (((int) Double.parseDouble(fields[6]) <= Integer.parseInt(yTo.getText())));
                        }
                    } else {fieldChecks[6] = false;} }


                if (beautyLevels.getSelectedItem().toString().toLowerCase().equals(fields[7].toLowerCase())) fieldChecks[7] = true;
                else fieldChecks[7] = false;
                if (beautyLevels.getSelectedItem().toString().equals("")) fieldChecks[7] = true;

                if (chinSharpnessTypes.getSelectedItem().toString().toLowerCase().equals(fields[8].toLowerCase()))
                    fieldChecks[8] = true;
                else fieldChecks[8] = false;
                if (chinSharpnessTypes.getSelectedItem().toString().equals("")) fieldChecks[8] = true;


                if (colors.getSelectedItem().toString().equals(fields[9].toLowerCase())) fieldChecks[9] = true;
                else fieldChecks[9] = false;
                if (colors.getSelectedItem().toString().equals("")) fieldChecks[9] = true;


            } catch (NumberFormatException e) {
                ClientGUI.this.setStatusMessage(formatError); fieldChecks[2] = false; time = false; e.printStackTrace(); }
            catch (ParseException f) {
                ClientGUI.this.setStatusMessage(dateFormatError); fieldChecks[4] = false; time = false;}

            check[i] = true;
            for (boolean fieldCheck : fieldChecks) {
                if (!fieldCheck) {
                    check[i] = false;
                }
            }
        }
        return check;
    }

    public void changeLanguage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("Resources", locale, new BundleControl());
        this.setTitle(bundle.getString("title"));
        acquireDataFromServer.setText(bundle.getString("acquireData"));
        helpButton.setText(bundle.getString("helpBtn"));
        startButton.setText(bundle.getString("startBtn"));
        stopButton.setText(bundle.getString("stopBtn"));
        highlightedObject.setText(bundle.getString("clickedObject"));
        serverConnection.setText(bundle.getString("connectionInfo"));
        userMessage.setText(bundle.getString("filtAnimInfo"));
        filterLabel.setText(bundle.getString("filtLabel"));
        dataAcquired = bundle.getString("dataAcquired");
        unableToPaint = bundle.getString("unableToPaint");
        animStart = bundle.getString("animStart");
        animStop = bundle.getString("animStop");
        formatError = bundle.getString("formatError");
        dateFormatError = bundle.getString("dateFormatError");
        idError = bundle.getString("idError");
        heightError = bundle.getString("heightError");
        weightError = bundle.getString("weightError");
        dateError = bundle.getString("dateError");
        xError = bundle.getString("xError");
        yError = bundle.getString("yError");
        serverUnav = bundle.getString("serverUnav");
        emptyColl = bundle.getString("emptyColl");
        ioError = bundle.getString("ioError");

        introLabel.setText(bundle.getString("introLabel"));
        labelRetrieve.setText(bundle.getString("labelRetrieve"));
        labelElements.setText(bundle.getString("labelElements"));
        labelElements1.setText(bundle.getString("labelElements1"));
        labelElements2.setText(bundle.getString("labelElements2"));
        labelElementsFields.setText(bundle.getString("labelFields"));
        labelElementsFields1.setText(bundle.getString("labelFields1"));
        labelElementsFields2.setText(bundle.getString("labelFields2"));
        labelElementsFields3.setText(bundle.getString("labelFields3"));
        labelFilters.setText(bundle.getString("labelFilters"));
        labelFilters1.setText(bundle.getString("labelFilters1"));
        labelFilters2.setText(bundle.getString("labelFilters2"));
        labelFilters3.setText(bundle.getString("labelFilters3"));
        labelFilters4.setText(bundle.getString("labelFilters4"));
        labelFilters5.setText(bundle.getString("labelFilters5"));
        labelStart.setText(bundle.getString("labelStart"));
        labelStart1.setText(bundle.getString("labelStart1"));
        labelStop.setText(bundle.getString("labelStop"));

        languageMenu.changeLanguage(locale);

        nestedDateOfBirth.removeAll();
        nestedDateOfBirthFrom.removeAll();
        nestedDateOfBirthTo.removeAll();
        nestedDateOfBirth.setLayout(new GridLayout(1,2));


        DatePickerSettings dps1 = new DatePickerSettings(locale);
        TimePickerSettings tps1 = new TimePickerSettings(locale);

        LocalDateTime oldTimeFrom = dateOfBirthFrom.getDateTimePermissive();
        dateOfBirthFrom = new DateTimePicker(dps1, tps1);
        dateOfBirthFrom.setDateTimePermissive(oldTimeFrom);


        DatePickerSettings dps2 = new DatePickerSettings(locale);
        TimePickerSettings tps2 = new TimePickerSettings(locale);

        LocalDateTime oldTimeTo = dateOfBirthTo.getDateTimePermissive();
        dateOfBirthTo = new DateTimePicker(dps2, tps2);
        dateOfBirthTo.setDateTimePermissive(oldTimeTo);



        nestedDateOfBirthFrom.add(dateOfBirthFrom);
        nestedDateOfBirthTo.add(dateOfBirthTo);

        nestedDateOfBirth.add(nestedDateOfBirthFrom);
        nestedDateOfBirth.add(nestedDateOfBirthTo);
    }

    public String getServerUnav () {
        return serverUnav;
    }

    public String getIoError () {
        return ioError;
    }

    public String getEmptyColl() {
        return emptyColl;
    }


}



