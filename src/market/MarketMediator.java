package market;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import market.methods.EMA;
import market.util.Graph;

public class MarketMediator implements Runnable {

    public static Graph stock;
    public static JTextField text;
    public static JPanel panel;

    public static final Runnable GRAPH = new Runnable() {
        @Override
        public void run() {
            panel.remove(stock);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 2;
            stock = new EMA().constructGraph(text.getText());
            panel.add(stock, c);
            panel.revalidate();
        }
    };

    public static void main(String... args) {
        SwingUtilities.invokeLater(new MarketMediator());
    }

    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Market Mediator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        text = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(text, c);
        text.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(GRAPH);
            }

        });

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        stock = new EMA().constructGraph("SIRI");
        panel.add(stock, c);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}