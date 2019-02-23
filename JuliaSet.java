import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class JuliaSet extends JPanel implements AdjustmentListener, ActionListener{

    JFrame frame;
    JScrollBar[] scrollBarArr;
    JCheckBox invert;
    JCheckBox reset;
    
    int height;
    int width;

    double A;
    double B;    
    Boolean fliped;
    float maxIter;
    float zoom;
    int c;
    float saturation;
    float brightness;
    
    public JuliaSet(){

        frame = new JFrame("Julia Set Program");
        frame.add(this);
        scrollBarArr = new JScrollBar[6];
        invert = new JCheckBox();
        invert.addActionListener(this);
        reset = new JCheckBox();
        reset.addActionListener(this);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(1, 3));

        scrollBarArr[0] = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 800); // for A
        scrollBarArr[1] = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 800); // for B
        scrollBarArr[2] = new JScrollBar(JScrollBar.VERTICAL, 100, 0, 10, 1000); // for zoom
        scrollBarArr[3] = new JScrollBar(JScrollBar.VERTICAL, 100, 0, 0, 100); // for saturation
        scrollBarArr[4] = new JScrollBar(JScrollBar.VERTICAL, 100, 0, 0, 100); // for brightness
        scrollBarArr[5] = new JScrollBar(JScrollBar.VERTICAL, 100, 0, 10, 1000); // for maxIters

        for (int x=0;x<6;x++){
            scrollBarArr[x].addAdjustmentListener(this);
            scrollPanel.add(scrollBarArr[x]);
            scrollBarArr[x].setUnitIncrement(1);
        }
        
        resetScreen();

        JPanel onePanel = new JPanel();
        onePanel.setLayout(new BorderLayout());
        onePanel.add(scrollPanel, BorderLayout.CENTER);
        onePanel.add(invert, BorderLayout.SOUTH);
        onePanel.add(reset, BorderLayout.NORTH);

        frame.add(onePanel, BorderLayout.EAST);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        width = frame.getWidth();
        height = frame.getHeight();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        drawJuliaSet(g);
    }

    public void drawJuliaSet(Graphics g) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        width = frame.getWidth();
        height = frame.getHeight();

        for (int x=0;x<width;x++){
            for (int y=0;y<height;y++){

                float counter = maxIter;
                double zx = 1.5*((x-0.5*width)/(0.5*zoom*width));
                double zy = (y-0.5*height)/(.5*zoom*height);

                while ((Math.pow(zx,2) + Math.pow(zy,2))<6 && counter>0){
                    double temp = Math.pow(zx, 2) - Math.pow(zy, 2) + A;
                    zy = (2*zx*zy) + B;
                    zx = temp;
                    counter--;
                }

                float hue = (maxIter/counter)%1;
                if(counter>0){
                    if (fliped)
                        c = Color.HSBtoRGB(360-hue, saturation, brightness);
                    else
                        c = Color.HSBtoRGB(hue, saturation, brightness);
                }
                else
                    c = Color.HSBtoRGB(hue, 1, 0);

                image.setRGB(x, y, c);
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    public void resetScreen(){
        A = 0;
        B = 0;    
        fliped = false;
        maxIter = 100;
        zoom = 1;
        c = 0;
        saturation = 1;
        brightness = 1;

        scrollBarArr[0].setValue(0);
        scrollBarArr[1].setValue(0);
        scrollBarArr[2].setValue(100);
        scrollBarArr[3].setValue(100);
        scrollBarArr[4].setValue(100);
        scrollBarArr[5].setValue(100);
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        if(e.getSource() == scrollBarArr[0])
            A = scrollBarArr[0].getValue()/1000.0;

        if(e.getSource() == scrollBarArr[1])
            B = scrollBarArr[1].getValue()/1000.0;
        
        if(e.getSource() == scrollBarArr[2])
            zoom = (float) (scrollBarArr[2].getValue()/100.0);
        
        if(e.getSource() == scrollBarArr[3])
            saturation = (float) (scrollBarArr[3].getValue()/100.0);
        
        if(e.getSource() == scrollBarArr[4])
            brightness = (float) (scrollBarArr[4].getValue()/100.0);
        
        if(e.getSource() == scrollBarArr[5])
            maxIter = (float) (scrollBarArr[5].getValue());

        repaint();
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == invert)
            fliped = !fliped;
    
        if (e.getSource() == reset)
            resetScreen();

        repaint();
    }

    public static void main(String[] args) {
        JuliaSet app = new JuliaSet();
    }

}