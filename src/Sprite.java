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
    Image right;
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

        hilo1 = new Thread(this);
        bi = new BufferedImage(1600, 800, BufferedImage.TYPE_INT_RGB);
        Toolkit herramienta = Toolkit.getDefaultToolkit();

        // Carga las imágenes del sprite para cada dirección
        img = herramienta.getImage(getClass().getResource("personaje.png"));
        up = herramienta.getImage(getClass().getResource("up3.png"));
        down = herramienta.getImage(getClass().getResource("down3.png"));
        left = herramienta.getImage(getClass().getResource("left3.png"));
        right = herramienta.getImage(getClass().getResource("right3.png"));

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
        int m_X = (increment % 4) * 100;
        int m_Y = (increment /4) * 100;

        if (m_Arriba && characterY > 0) {
            characterY -= 10;
            currentImage = up;
        } else if (m_Abajo && characterY < getHeight() - 300) {
            characterY += 10;
            currentImage = down;
        } else if (m_Izquierda && characterX > 0) {
            characterX -= 10;
            currentImage = left;
        } else if (m_Derecha && characterX < getWidth() - 300) {
            characterX += 10;
            currentImage = right;
        } else {
            currentImage = img;
        }

        g2d.drawImage(currentImage, characterX, characterY, characterX + 300, characterY + 300, m_X, m_Y, m_X + 100, m_Y + 170, this);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
            }
            increment = increment + 1;
            if (increment > 3) {
                increment = 0;
            }
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                m_Arriba = true;
                break;
            case KeyEvent.VK_DOWN:
                m_Abajo = true;
                break;
            case KeyEvent.VK_LEFT:
                m_Izquierda = true;
                break;
            case KeyEvent.VK_RIGHT:
                m_Derecha = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                m_Arriba = false;
                break;
            case KeyEvent.VK_DOWN:
                m_Abajo = false;
                break;
            case KeyEvent.VK_LEFT:
                m_Izquierda = false;
                break;
            case KeyEvent.VK_RIGHT:
                m_Derecha = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        new Sprite().setVisible(true);
    }
}
