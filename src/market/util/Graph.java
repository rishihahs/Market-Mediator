package market.util;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Graph extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGraph(g);
    }

    public abstract void paintGraph(Graphics g);

    @Override
    public abstract Dimension getPreferredSize();

}
