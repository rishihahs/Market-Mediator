package market.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.List;

public class Stock extends Graph {

    private final Dimension size = new Dimension(600, 300);
    private String[] ylabels;
    private String[] xlabels;
    private List<List<Point>> data;
    private List<Color> colors;
    private String title;
    private double interval;

    @Override
    public void paintGraph(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        Font defaultFont = g.getFont();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size.width, size.height);

        // Axis
        g.setColor(Color.BLACK);
        g.drawLine(70, 70, 70, size.height - 70);
        g.drawLine(70, size.height - 70, size.width - 70, size.height - 70);
        // Graph fill
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(71, 71, size.width - 70 - 71, size.height - 70 - 71);

        // Title
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(title, size.width / 2, 20);

        g.setFont(new Font(defaultFont.getFontName(), Font.PLAIN, 12));

        // Y Label
        String ylabel = "Price";
        g.translate(20, size.height / 2);
        g.rotate(Math.toRadians(270.0));
        g.drawString(ylabel, 0, 0);
        g.rotate(-1 * Math.toRadians(270.0));
        g.translate(-20, -1 * (size.height / 2));

        // X Label
        String xlabel = "Days";
        g.drawString(xlabel, size.width / 2, size.height - 20);

        g.setFont(defaultFont);

        // Y axis labels
        int yintlen = (size.height - 70 - 70) / (ylabels.length - 1);

        int count = 0;
        for (int i = size.height - 70; count < ylabels.length; i -= yintlen) {
            g.drawString(ylabels[count++], 40, i);
            g.drawLine(70, i, size.width - 70, i);
        }

        int xintlen = (size.width - 70 - 70) / (xlabels.length - 1);

        count = 0;
        for (int i = 70; count < xlabels.length; i += xintlen) {
            g.drawString(xlabels[count++], i, size.height - 40);
            g.drawLine(i, size.height - 70, i, size.height - 65);
        }

        // Bottom Left
        AffineTransform old = g.getTransform();
        g.translate(71, size.height - 71);
        g.scale(1, -1);

        Iterator<Color> color = colors.iterator();
        Color oldcolor = g.getColor();
        for (List<Point> points : data) {
            if (color.hasNext())
                g.setColor(color.next());
            Iterator<Point> i = points.iterator();
            Point poir = new Point(i.next());
            poir.y = (int) Math.round((poir.y - Double.parseDouble(ylabels[0]))
                    * (1.0 / interval) * yintlen);
            while (i.hasNext()) {
                Point now = new Point(i.next());
                now.y = (int) Math.round((now.y - Double
                        .parseDouble(ylabels[0])) * (1.0 / interval) * yintlen);
                g.drawLine(poir.x * xintlen, (int) (poir.y), now.x * xintlen,
                        (int) now.y);
                poir = now;
            }
        }
        g.setColor(oldcolor);
        

        g.setTransform(old);
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    public String[] getYlabels() {
        return ylabels;
    }

    public void setYlabels(String[] ylabels) {
        this.ylabels = ylabels;
    }

    public String[] getXlabels() {
        return xlabels;
    }

    public void setXlabels(String[] xlabels) {
        this.xlabels = xlabels;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getInterval() {
        return interval;
    }

    public List<List<Point>> getData() {
        return data;
    }

    public void setData(List<List<Point>> data) {
        this.data = data;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
