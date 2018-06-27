package lab7;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageMenu extends JMenu {
    LanguageMenu (ClientGUI gui){
        super();
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new BundleControl());
        this.setText(rb.getString("language"));

        JMenuItem ru_Ru = new JMenuItem("Русский");
        ru_Ru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("ru"));
            }
        });
        this.add(ru_Ru);

        JMenuItem ee_EE = new JMenuItem("Eesti");
        ee_EE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("ee"));
            }
        });
        this.add(ee_EE);

        JMenuItem lv_LV = new JMenuItem("Latviešu");
        lv_LV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("lv"));
            }
        });
        this.add(lv_LV);

        JMenuItem es_CO = new JMenuItem("Español");
        es_CO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("es", "CO"));
            }
        });
        this.add(es_CO);
    }

    LanguageMenu (Auth auth){
        super();
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new BundleControl());
        this.setText(rb.getString("language"));

        JMenuItem ru_Ru = new JMenuItem("Русский");
        ru_Ru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth.changeLanguage(new Locale("ru"));
            }
        });
        this.add(ru_Ru);

        JMenuItem ee_EE = new JMenuItem("Eesti");
        ee_EE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth.changeLanguage(new Locale("ee"));
            }
        });
        this.add(ee_EE);

        JMenuItem lv_LV = new JMenuItem("Latviešu");
        lv_LV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth.changeLanguage(new Locale("lv"));
            }
        });
        this.add(lv_LV);

        JMenuItem es_CO = new JMenuItem("Español");
        es_CO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth.changeLanguage(new Locale("es", "CO"));
            }
        });
        this.add(es_CO);
    }

    public void changeLanguage(Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new BundleControl());
        this.setText(rb.getString("language"));
    }
}
