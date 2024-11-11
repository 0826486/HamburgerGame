package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

public class GameStart extends JPanel implements KeyListener {
    private Image backgroundImage;
    private Image chefKirbyImage;
    private Image[] hamImages = new Image[8];
    private int chefKirbyX = 300;
    private int chefKirbyY = 300;

    private int[] hamX = new int[8];
    private int[] hamY = new int[8];
    private double[] hamSpeed = new double[8];
    private Random random = new Random();

    public GameStart() {
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage();
        chefKirbyImage = new ImageIcon("image/kirbychef.png").getImage();

        for (int i = 0; i < 8; i++) {
            hamImages[i] = new ImageIcon("image/hamImg" + (i + 1) + ".png").getImage();
            hamSpeed[i] = 4 + random.nextInt(4);

            int x;
            boolean overlap;
            do {
                x = random.nextInt(800);
                overlap = false;
                for (int j = 0; j < i; j++) {
                    if (Math.abs(x - hamX[j]) < 60) {
                        overlap = true;
                        break;
                    }
                }
            } while (overlap);
            hamX[i] = x;
            hamY[i] = random.nextInt(100) - 100;
        }

        this.setFocusable(true);
        this.addKeyListener(this);

        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHamPosition();
                repaint();
            }
        });
        timer.start(); // 애니메이션 타이머는 반복적으로 실행
    }

    private void updateHamPosition() {
        for (int i = 0; i < hamImages.length; i++) {
            hamY[i] += hamSpeed[i];

            if (hamY[i] > getHeight()) {
                hamY[i] = -50;
                hamSpeed[i] += 0.2;

                int x;
                boolean overlap;
                do {
                    x = random.nextInt(getWidth());
                    overlap = false;
                    for (int j = 0; j < hamImages.length; j++) {
                        if (i != j && Math.abs(x - hamX[j]) < 60) {
                            overlap = true;
                            break;
                        }
                    }
                } while (overlap);
                hamX[i] = x;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(chefKirbyImage, chefKirbyX, chefKirbyY, 175, 170, this);

        for (int i = 0; i < hamImages.length; i++) {
            g.drawImage(hamImages[i], hamX[i], hamY[i], 50, 50, this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && chefKirbyX > 0) {
            chefKirbyX -= 10;
        } else if (keyCode == KeyEvent.VK_RIGHT && chefKirbyX < getWidth() - 175) {
            chefKirbyX += 10;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Start");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        GameStart gameStartPanel = new GameStart();
        frame.add(gameStartPanel);

        frame.setVisible(true);
    }
}