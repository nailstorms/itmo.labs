package lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Ellipse extends JComponent {
    private Integer x = 0;
    private Integer y = 0;
    private Integer width;
    private Integer height;
    private Color color;


    public Ellipse() {
        this.width = 10;
        this.height = 10;
    }

    public Ellipse(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Color getColor () {
        return color;
    }

    public void setColor (Color color) {
        this.color = color;
        this.repaint();
    }

    public void paintComponent(Graphics gr) {
        super.paintComponents(gr);
        Graphics2D g2d = (Graphics2D) gr;
        g2d.setPaint(this.color);
        g2d.fillOval(this.x, this.y, this.width, this.height);
    }
}