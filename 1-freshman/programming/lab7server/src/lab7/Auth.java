package lab7;

import lab8.orm.DB;
import lab8.orm.SQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Auth extends JFrame {
    SQL<Users> usersSQL = new SQL<>(Users.class);
    DB db = new DB("jdbc:postgresql://localhost:5432/lab8", "nailstorm", "qwerty");
    boolean check = false;

    JLabel username = new JLabel("Username:");
    JTextField usernameInput = new JTextField("");
    JLabel password = new JLabel("Password:");
    JPasswordField passwordInput = new JPasswordField("", 10);
    JButton login = new JButton("Login");
    JLabel indicator = new JLabel("");


    JButton register = new JButton("Registration");
    JLabel registrationIndicator = new JLabel("");


    public Auth(Runnable callback) {
        super("Login");
        db.execute(usersSQL.createTable());
        this.setBounds(420, 240, 768, 576);
        this.setSize(512, 256);
        this.setLayout(new GridLayout(7, 1));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.add(username);

        this.add(usernameInput);
        this.add(password);
        this.add(passwordInput);
        this.add(login);
        this.add(register);
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

        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JDialog dialogRegister = new JDialog();
                dialogRegister.setBounds(520, 100, 768, 576);
                dialogRegister.setSize(512, 256);
                dialogRegister.setLayout(new GridLayout(6, 1));

                dialogRegister.setTitle("Registration");

                JLabel usernameRegister = new JLabel("Username:");
                JTextField usernameRegisterInput = new JTextField("");
                JLabel passwordRegister = new JLabel("Password:");
                JPasswordField passwordRegisterInput = new JPasswordField("", 10);
                JButton registerButton = new JButton("Register");
                registrationIndicator = new JLabel("");

                dialogRegister.add(usernameRegister);
                dialogRegister.add(usernameRegisterInput);
                dialogRegister.add(passwordRegister);
                dialogRegister.add(passwordRegisterInput);
                dialogRegister.add(registerButton);
                dialogRegister.add(registrationIndicator);

                dialogRegister.invalidate();

                dialogRegister.setVisible(true);

                registerButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (register(usernameRegisterInput.getText(), passwordRegisterInput.getText())) {
                            registrationIndicator.setText("Successful!");
                            javax.swing.Timer timer = new javax.swing.Timer(2000, null);
                            timer.addActionListener((b) -> {
                                timer.stop();
                                registrationIndicator.setText("");
                                usernameRegisterInput.setText("");
                                passwordRegisterInput.setText("");
                                dialogRegister.setVisible(false);
                                dialogRegister.dispose();
                            });
                            timer.start();
                        }
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
//        db.execute(usersSQL.insert(new Users(login, MD5(password))));

        ArrayList<String> logins = new ArrayList<>();

        java.util.List<Users> usersList = usersSQL.resultsToObjects(db.fetch(usersSQL.selectAll()));
        for (Users users : usersList) {
            logins.add(users.getLogin());
        }

        if (logins.contains(login)) {
            registrationIndicator.setText("This user already exists.");
            return false;
        }

        registrationIndicator.setText("Success!");
        db.execute(usersSQL.insert(new Users(login, MD5(password))));
        return true;
    }

    public boolean login() {
        indicator.setText("");

        ArrayList<String> logins = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        java.util.List<Users> usersList = usersSQL.resultsToObjects(db.fetch(usersSQL.selectAll()));
        for (Users users : usersList) {
            logins.add(users.getLogin());
            passwords.add(users.getPasswordHash());
        }


        for (int i = 0; i < logins.toArray().length; i++) {
            if (usernameInput.getText().equals(logins.toArray()[i]) &&
                    (MD5(passwordInput.getText()).equals(passwords.toArray()[i]))) {

                javax.swing.Timer timer = new javax.swing.Timer(2000, null);
                timer.addActionListener((e) -> {
                    Auth.this.setVisible(false);
                    Auth.this.dispose();
                });
                indicator.setText("Successfully logged in!");
                timer.start();
                return true;
            } else
                indicator.setText("Incorrect username or password. Please try again.");
        }
        return false;

    }

    public boolean checkClientLogin(String log, String pass) {
        String login = log;
        String passwordHash = MD5(pass);

        ArrayList<String> logins = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        java.util.List<Users> usersList = usersSQL.resultsToObjects(db.fetch(usersSQL.selectAll()));

        for (Users users : usersList) {
            logins.add(users.getLogin());
            passwords.add(users.getPasswordHash());
        }

        for (int i = 0; i < logins.toArray().length; i++) {
            if (login.equals(logins.toArray()[i]) &&
                    (passwordHash.equals(passwords.toArray()[i]))) {
                return true;
            }
        }
        return false;
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
