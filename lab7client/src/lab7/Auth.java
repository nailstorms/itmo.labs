package lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class Auth extends JFrame {
    boolean check = false;
    private LanguageMenu languageMenu;

    public Locale locale;

    private String registraaa;
    private String success;
    private String fileNotFound;
    private String incorrectCombo;
    private String someException;


    JLabel username = new JLabel("Username:");
    JTextField usernameInput = new JTextField("");
    JLabel password = new JLabel("Password:");
    JPasswordField passwordInput = new JPasswordField("",10);
    JButton login = new JButton("Login");
    JLabel indicator = new JLabel("");


    JButton register = new JButton("Registration");

    JLabel usernameRegister = new JLabel();
    JTextField usernameRegisterInput = new JTextField("");
    JLabel passwordRegister = new JLabel("");
    JPasswordField passwordRegisterInput = new JPasswordField("",10);
    JButton registerButton = new JButton();
    JLabel registrationIndicator = new JLabel("");

    public Auth(Runnable callback) {
        super("Login");
        locale = Locale.getDefault();

        this.setBounds(420, 240, 768, 576);
        this.setSize(512, 256);
        this.setLayout(new GridLayout(7,1));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        languageMenu = new LanguageMenu(this);
        menuBar.add(languageMenu);
        this.setJMenuBar(menuBar);

        this.add(username);

        this.add(usernameInput);
        this.add(password);
        this.add(passwordInput);
        this.add(login);
        this.add(register);
        this.add(indicator);

        this.changeLanguage(Locale.getDefault());

        login.addMouseListener(new MouseAdapter() {
                                   @Override
                                   public void mouseClicked(MouseEvent mouseEvent) {
                                       if (Auth.this.login()) {
                                           javax.swing.Timer popupTimer = new javax.swing.Timer(2000, null);
                                           popupTimer.addActionListener((e) -> {
                                               popupTimer.stop();
                                               callback.run();
                                           });
                                           popupTimer.start();
                                       }
                                   }
                               }
        );

        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JDialog dialogRegister = new JDialog();
                dialogRegister.setBounds(520, 100, 768, 576);
                dialogRegister.setSize(512, 256);
                dialogRegister.setLayout(new GridLayout(6,1));

                dialogRegister.setTitle(registraaa);

                dialogRegister.add(usernameRegister);
                dialogRegister.add(usernameRegisterInput);
                dialogRegister.add(passwordRegister);
                dialogRegister.add(passwordRegisterInput);
                dialogRegister.add(registerButton);
                dialogRegister.add(registrationIndicator);

                dialogRegister.setVisible(true);

                registerButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(register(usernameRegisterInput.getText(), passwordRegisterInput.getText())) {
                            registrationIndicator.setText(success);
                            javax.swing.Timer timer = new javax.swing.Timer(2000, null);
                            timer.addActionListener((b) -> {
                                dialogRegister.setVisible(false);
                                dialogRegister.dispose();
                            });
                            timer.start();
                        }
                        else
                            registrationIndicator.setText(someException);
                    }
                });

            }
        });
    }


    public boolean hasPassedTheCheck() {
        return check;
    }

    public boolean register(String login, String password) {
        registrationIndicator.setText("");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter
                    ("C://Users//nailstorm//IdeaProjects//lab5//materials//logPassClients.csv",true));
            writer.write(login + "/" + MD5(password) + "\n");
            writer.flush();
            return true;
        } catch (FileNotFoundException exc) {
            indicator.setText(fileNotFound);
        } catch (IOException exc) {
            indicator.setText(someException);
        }
        return false;
    }

    public boolean login() {
        indicator.setText("");
        try {
            InputStream fileLogPass = new FileInputStream(
                    "C://Users//nailstorm//IdeaProjects//lab5//materials//logPassClients.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileLogPass));
            ArrayList<String> logins = new ArrayList<>();
            ArrayList<String> passwords = new ArrayList<>();
            String someBs;

            try {
                while ((someBs = reader.readLine()) != null && someBs.length() > 0) {
                    String[] logPass = someBs.split("/");
                    logins.add(logPass[0]);
                    passwords.add(logPass[1]);
                }

            } catch (IOException exc) {
                indicator.setText(someException);
            }

            for (int i=0; i<logins.toArray().length; i++) {
                if(usernameInput.getText().equals(logins.toArray()[i]) &&
                        (MD5(passwordInput.getText()).equals(passwords.toArray()[i]))){

                    javax.swing.Timer timer = new javax.swing.Timer(2000, null);
                    timer.addActionListener((e) -> {
                        Auth.this.setVisible(false);
                        Auth.this.dispose();
                    });
                    indicator.setText(success);
                    timer.start();
                    return true;
                }
                else
                    indicator.setText(incorrectCombo);
            }
            return false;
        } catch (FileNotFoundException exc) {
            indicator.setText(fileNotFound);
        }
        return false;

    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }


    public void changeLanguage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("Resources", locale, new BundleControl());
        this.setTitle(bundle.getString("authTitle"));
        username.setText(bundle.getString("usName"));
        password.setText(bundle.getString("pass"));
        login.setText(bundle.getString("loginn"));


        registraaa = bundle.getString("registration");
        register.setText(bundle.getString("registration"));

        usernameRegister.setText(bundle.getString("usName"));
        passwordRegister.setText(bundle.getString("pass"));
        registerButton.setText(bundle.getString("registration"));

        success = bundle.getString("success");
        fileNotFound = bundle.getString("fileNotFound");
        incorrectCombo = bundle.getString("incorrectCombo");
        someException = bundle.getString("someException");

        languageMenu.changeLanguage(locale);
    }
}
