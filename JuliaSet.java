import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class JuliaSet extends JPanel implements AdjustmentListener, ActionListener{

    JFrame frame;
    JScrollBar[] scrollBarArr;
    JCheckBox invert;
    Boolean flip = true;

    int height;
    int width;
    double A = 0;
    double B = 0;

    float maxIter = 300;
    
    public JuliaSet(){

        frame = new JFrame("Julia Set Program");
        frame.add(this);
        scrollBarArr = new JScrollBar[5];
        invert = new JCheckBox();
        invert.addActionListener(this);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(1, 3));

        for (int x=0;x<5;x++){
            scrollBarArr[x] = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 255);
            scrollBarArr[x].addAdjustmentListener(this);
            scrollPanel.add(scrollBarArr[x]);
            scrollBarArr[x].setUnitIncrement(1);
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

        //g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        drawJuliaSet(g);

    }

    public void drawJuliaSet(Graphics g) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        width = frame.getWidth();
        height = frame.getHeight();

        for (int x=0;x<width;x++){           
            for (int y=0;y<height;y++){

                float zoom = 1f;
                float counter = maxIter;

                double zx = 1.5*((x-0.5*width)/(0.5*zoom*width));
                double zy = (y-0.5*height)/(.5*zoom*height);

                while ((Math.pow(zx,2) + Math.pow(zy,2))<6 && counter>0){
                    double temp = Math.pow(zx, 2) - Math.pow(zy, 2) + A;
                    zy = (2*zx*zy) + B;
                    zx = temp;
                    counter--;
                }

                int c;
                if(counter>0)
                    c = Color.HSBtoRGB((maxIter/counter)%1, 1, 1);
                else 
                    c = Color.HSBtoRGB(maxIter/counter,1, 0);

                image.setRGB(x, y, c);
            }
        }
        g.drawImage(image, 0, 0, null);
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {

        for (int i=0;i<3;i++){
            if(e.getSource() == scrollBarArr[i]){
                //color[i] = scrollBar[i].getValue();
            }
        }

        repaint();
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == invert){
            for (int i=0;i<3;i++){
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