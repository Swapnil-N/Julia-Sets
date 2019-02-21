import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class JuliaSet extends JPanel implements AdjustmentListener, ActionListener{

    JFrame frame;
    JScrollBar[] scrollBar;
    int[] color;
    JCheckBox invert;
    Boolean flip = true;
    int height;
    int width;

    float maxInter = 300;
    
    public JuliaSet(){

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

        width = frame.getWidth();
        height = frame.getHeight();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(new Color(color[0],color[1],color[2]));
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        drawJuliaSet(g);

    }

    public void drawJuliaSet(Graphics g) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x=0;x<width;x++){
            for (int y=0;y<height;y++){
                float zoom = 1;
                float itFloat = maxInter;

                double zx = 1.5*((x-0.5*x)/(0.5*zoom*width));
                double zy = (y-.5*y)/(.5*zoom*height);

                //while ((Math.pow(zx, 2)+Math.pow(zy, 2))<6 && itFloat>0){

                //}

            }
        }
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {

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
        JuliaSet app = new JuliaSet();
    }

}