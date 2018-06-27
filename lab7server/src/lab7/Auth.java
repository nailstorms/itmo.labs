package lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;


public class Auth extends JFrame {
    boolean check = false;

    JLabel username = new JLabel("Username:");
    JTextField usernameInput = new JTextField("");
    JLabel password = new JLabel("Password:");
    JPasswordField passwordInput = new JPasswordField("",10);
    JButton login = new JButton("Login");
    JLabel indicator = new JLabel("");

    public Auth(Runnable callback) {
        super("Login");
        this.setBounds(420, 240, 768, 576);
        this.setSize(512, 256);
        this.setLayout(new GridLayout(6,1));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.add(username);

        this.add(usernameInput);
        this.add(password);
        this.add(passwordInput);
        this.add(login);
        this.add(indicator);
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
    }


    public boolean hasPassedTheCheck() {
        return check;
    }

    public boolean login() {
        indicator.setText("");
        try {
            InputStream fileLogPass = new FileInputStream(
                    "C://Users//nailstorm//IdeaProjects//lab5//materials//logPass.csv");
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
                indicator.setText("An exception has been caught. Please try again.");
            }

            for (int i=0; i<logins.toArray().length; i++) {
                if(usernameInput.getText().equals(logins.toArray()[i]) &&
                        (passwordInput.getText().equals(passwords.toArray()[i]))){

                    javax.swing.Timer timer = new javax.swing.Timer(2000, null);
                    timer.addActionListener((e) -> {
                        Auth.this.setVisible(false);
                        Auth.this.dispose();
                    });
                    indicator.setText("Successfully logged in!");
                    timer.start();
                    return true;
                }
                else
                    indicator.setText("Incorrect username or password. Please try again.");
            }
            return false;
        } catch (FileNotFoundException exc) {
            indicator.setText("File containing logins/passwords has not been found.");
        }
        return false;

    }
}
