import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Sprite extends JFrame implements Runnable, KeyListener {

Image img;
Image up;
Image down;
Image left;
Image rigth;
Thread hilo1;
int increment;
BufferedImage bi;
int characterX;
int characterY;
boolean m_Abajo;
boolean m_Arriba;
boolean m_Derecha;
boolean m_Izquierda;

    public Sprite() {
        setSize(1600, 800);
        getContentPane().setBackground(Color.ORANGE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Sprite Movimiento");
        setResizable(false);
        addKeyListener(this);

        hilo1= new Thread(this);
        bi = new BufferedImage(1600, 800, BufferedImage.TYPE_INT_RGB);
        Toolkit herramienta = Toolkit.getDefaultToolkit();

        // Carga las imágenes del sprite para cada dirección
        img =  herramienta. getImage(getClass().getResource("personaje.png"));
        up = herramienta.getImage(getClass().getResource("down3.png"));
        down = herramienta.getImage(getClass().getResource("up3.png"));
        left = herramienta.getImage(getClass().getResource("left3.png"));
        rigth= herramienta.getImage(getClass().getResource("right3.png"));

        hilo1.start();
    }

    @Override
    public void paint(Graphics g) {
        Image currentImage;
        Graphics2D g2d;
        g.drawImage(bi, 0, 0, this);
        g2d = bi.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 1600, 800);
        int m_X= (increment % 5)*100;
        int m_Y =(increment /5)*100;

        if (m_Arriba && characterY > 0) {
            characterY -=1;
            currentImage=up;
        } else if (m_Abajo && characterY < getHeight() -300){
            characterY +=1;
            currentImage = down;
        } else if (m_Izquierda && characterX > 0){
            characterX -=1;
            currentImage = left;
        } else if (m_Derecha && characterX < getWidth() - 300){
            characterX +=1;
            currentImage= rigth;
        } else{
            currentImage = img;
        }

        g2d.drawImage(currentImage, characterX, characterY, characterX +300, characterY +300, m_X,m_Y,m_X +100, m_Y +170, this);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(170);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
            }
            increment = increment +1;
            if (increment > 5){
                increment =0;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {;
        switch (e.getKeyCode()) {
            case 37:
                m_Arriba= true;
                break;
            case 38:
                m_Abajo=true;
                break;
            case 39:
                m_Izquierda=true;
                break;
            case 40:
                m_Derecha=true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
           case 37:
                m_Arriba= false;
                break;
            case 38:
                m_Abajo=false;
                break;
            case 39:
                m_Izquierda=false;
                break;
            case 40:
                m_Derecha=false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se utiliza en este ejemplo, pero se debe implementar debido a la interfaz KeyListener
    }

    public static void main(String[] args) {
        new Sprite().setVisible(true);
    }
}
