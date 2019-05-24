import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends JPanel{

    private double aMax, aMin, bMin, bMax;

    private int maxLoops;

    public Main(int width, int height) {
        setSize(width, height);

        aMax = 1.5;
        aMin = -2;
        bMax = 1.5;
        bMin = -1.5;

        maxLoops = 50;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                zoomIn(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public int testValue(double a, double b){
        int count = 0;
        double az = a;
        double bz = b;

        double ac = -.4;
        double bc = .6;

        while(count < maxLoops && az*az + bz*bz <= 4){ //#?=
            count++;
//            double temp = az*az - bz*bz + a;
//            bz = 2*az*bz + b;
//            az = temp;

//            double temp = az*az - bz*bz + ac;
//            bz = 2*az*bz + bc;
//            az = temp;

//            (a + bi)^3 = (a^2 - b^2 -2abi)(a + bi)
        }
        return count;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double da = (aMax - aMin)/getWidth();
        double db = (bMax - bMin)/getHeight();

        for (double a = aMin; a <= aMax; a += da) {
            for (double b = bMin; b <= bMax; b += db) {
                int count = testValue(a, b);
                if(count == maxLoops){
                    g2.setColor(Color.BLACK);
                    plotPoint(a, b, g2);
                }else{
                    g2.setColor(Color.getHSBColor(1.0f*count/maxLoops, 1-1.0f*count/maxLoops, 1));
                    plotPoint(a, b, g2);
                }
            }
        }
    }

    //plot (a+bi) on our panel.
    public void plotPoint(double a, double b, Graphics2D g2){
        double x = (a - aMin) * (getWidth() / (aMax - aMin));
        double y = getHeight() - (b - bMin) * (getHeight() / (bMax - bMin));
        g2.drawRect((int)x, (int)y, 1, 1);
    }

    public void zoomIn(int x, int y){

        double a = x / (getWidth() / (aMax - aMin)) + aMin;
        double b = - ((y - getHeight()) / (getHeight() / (bMax - bMin)) - bMin);

        double currentWidth = aMax - aMin;
        double currentHeight = bMax - bMin;

        aMin = a - currentWidth/4;
        aMax = a + currentWidth/4;
        bMin = b - currentHeight/4;
        bMax = b + currentHeight/4;

        maxLoops *= 1.25;

        repaint();

    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Fractals!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1000, 800 + 22); //(x, y, w, h) 22 due to title bar.

        Main panel = new Main(1000, 800);

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }

}