package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomHamburger {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            eximg exampleImage = new eximg();
            exampleImage.setVisible(true);

            Timer timer = new Timer(2000, e -> {
                startGame();
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    private static void startGame() {
        JFrame gameFrame = new JFrame("Game Start");
        GameStart gameStartPanel = new GameStart();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(900, 600);
        gameFrame.add(gameStartPanel);
        gameFrame.setVisible(true);
    }
}

class eximg extends JFrame {
    private Image[] burgerImages;
    private Random random;

    public eximg() {
        setTitle("똑같은 버거를 만들어보세요!");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        burgerImages = new Image[] {
            new ImageIcon("image/chesseburger.png").getImage(),
            new ImageIcon("image/hamberger.png").getImage(),
            new ImageIcon("image/souce.png").getImage(),
            new ImageIcon("image/chickenburger.png").getImage()
        };

        random = new Random();
        setLocation(1000, 200);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int index = random.nextInt(burgerImages.length);
        g.drawImage(burgerImages[index], 0, 0, getWidth(), getHeight(), this);
    }
}

class GameStart extends JPanel implements KeyListener {
    private Image backgroundImage;
    private Image chefKirbyImage;
    private Image[] hamImages = new Image[8];
    private int chefKirbyX = 300;
    private int chefKirbyY = 300;

    private int[] hamX = new int[8];
    private int[] hamY = new int[8];
    private double[] hamSpeed = new double[8];
    private Random random = new Random();

    private MiniStackPanel miniStackPanel;

    public GameStart() {
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage();
        chefKirbyImage = new ImageIcon("image/kirbychef.png").getImage();

        // 미니창 초기화 및 추가
        miniStackPanel = new MiniStackPanel();
        JFrame miniFrame = new JFrame("Mini Stack");
        miniFrame.setSize(200, 400);
        miniFrame.add(miniStackPanel);
        miniFrame.setLocation(1200, 200);
        miniFrame.setVisible(true);

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
        timer.start();
    }

    private void updateHamPosition() {
        for (int i = 0; i < hamImages.length; i++) {
            hamY[i] += hamSpeed[i];

            // 커비 캐릭터와 충돌 감지 (범위 조정)
            if (hamY[i] + 20 >= chefKirbyY && hamX[i] + 20 >= chefKirbyX && hamX[i] <= chefKirbyX + 155) {
                miniStackPanel.addIngredient(hamImages[i]); // 미니 창에 재료 추가
                hamY[i] = -50; // 재료를 다시 위로 초기화
                hamSpeed[i] = 4 + random.nextInt(4); // 새로운 속도 설정
            }

            if (hamY[i] > getHeight()) {
                hamY[i] = -50;
                hamSpeed[i] = 4 + random.nextInt(4);

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
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}

// 미니창에 재료를 쌓아 표시하는 클래스
class MiniStackPanel extends JPanel {
    private ArrayList<Image> ingredients;

    public MiniStackPanel() {
        ingredients = new ArrayList<>();
        setPreferredSize(new Dimension(200, 400));
    }

    public void addIngredient(Image ingredient) {
        ingredients.add(ingredient);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = getHeight() - 50; // 아래부터 쌓이도록 위치 조정
        for (Image ingredient : ingredients) {
            g.drawImage(ingredient, 50, y, 100, 50, this);
            y -= 50; // 각 재료의 높이만큼 위로 쌓음
        }
    }
}