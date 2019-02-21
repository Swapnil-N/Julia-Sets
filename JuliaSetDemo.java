import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JuliaSetDemo extends JPanel implements AdjustmentListener, ActionListener{

    JFrame frame;
    JScrollBar[] scrollBar;
    int[] color;
    JCheckBox invert;
    Boolean flip = true;
    
    public JuliaSetDemo(){

        frame = new JFrame("Julia Set Program");
        frame.add(this);
        scrollBar = new JScrollBar[3];
        color = new int[3];
        invert = new JCheckBox();
        invert.addActionListener(this);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(1, 3));

        for (int x=0;x<3;x++){
            scrollBar[x] = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 255);
            scrollBar[x].addAdjustmentListener(this);
            scrollPanel.add(scrollBar[x]);
            scrollBar[x].setUnitIncrement(1);
        }

        JPanel onePanel = new JPanel();
        onePanel.setLayout(new BorderLayout());
        onePanel.add(scrollPanel, BorderLayout.CENTER);
        onePanel.add(invert, BorderLayout.SOUTH);

        frame.add(onePanel, BorderLayout.EAST);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(new Color(color[0],color[1],color[2]));
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

    }

    public void adjustmentValueChanged(AdjustmentEvent e){

        for (int i=0;i<3;i++){
            if(e.getSource() == scrollBar[i]){
                color[i] = scrollBar[i].getValue();
            }
        }

        repaint();
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == invert){
            for (int i=0;i<3;i++){
                color[i] = 255 - color[i];
            }

            /*if (flip){
                scrollBar[i].setValue(scrollBar[i]);
            }*/

        }

        repaint();
    }

    public static void main(String[] args) {
        JuliaSetDemo app = new JuliaSetDemo();
    }

}