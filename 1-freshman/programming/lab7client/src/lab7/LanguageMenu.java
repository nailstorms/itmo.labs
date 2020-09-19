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

        JMenuItem pt_PT = new JMenuItem("Português");
        pt_PT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("pt"));
            }
        });
        this.add(pt_PT);

        JMenuItem gr_GR = new JMenuItem("Ελληνικά");
        gr_GR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("gr"));
            }
        });
        this.add(gr_GR);

        JMenuItem es_NI = new JMenuItem("Español");
        es_NI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("es", "NI"));
            }
        });
        this.add(es_NI);
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

        JMenuItem pt_PT = new JMenuItem("Português");
        pt_PT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth.changeLanguage(new Locale("pt"));
            }
        });
        this.add(pt_PT);

        JMenuItem gr_GR = new JMenuItem("Ελληνικά");
        gr_GR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth.changeLanguage(new Locale("gr"));
            }
        });
        this.add(gr_GR);

        JMenuItem es_NI = new JMenuItem("Español");
        es_NI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth.changeLanguage(new Locale("es", "NI"));
            }
        });
        this.add(es_NI);
    }

    public void changeLanguage(Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new BundleControl());
        this.setText(rb.getString("language"));
    }
}
