package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomHamburger {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // eximg 창을 먼저 띄웁니다
            eximg exampleImage = new eximg();
            exampleImage.setVisible(true);

            // 타이머를 설정하여 일정 시간이 지난 후 게임을 시작하도록 설정
            Timer timer = new Timer(2000, e -> startGame()); // 게임을 시작
            timer.setRepeats(false);
            timer.start();
        });
    }

    private static void startGame() {
        // GameStart 화면을 새로운 JFrame으로 띄움
        JFrame gameFrame = new JFrame("Game Start");
        GameStart gameStartPanel = new GameStart();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(900, 600);
        gameFrame.add(gameStartPanel);
        gameFrame.setVisible(true);
    }
}

class eximg extends JFrame {
    private Image selectedBurgerImage;

    public eximg() {
        setTitle("랜덤 버거!");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image[] burgerImages = {
            new ImageIcon("image/chesseburger.png").getImage(),
            new ImageIcon("image/hamberger.png").getImage(),
            new ImageIcon("image/souce.png").getImage(),
            new ImageIcon("image/chickenburger.png").getImage()
        };

        Random random = new Random();
        selectedBurgerImage = burgerImages[random.nextInt(burgerImages.length)];

        setLocation(1000, 200); // 위치를 설정하여 화면에 띄우기
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(selectedBurgerImage, 0, 0, getWidth(), getHeight(), this);
    }

    public Image getSelectedBurgerImage() {
        return selectedBurgerImage;
    }
}

class GameStart extends JPanel implements KeyListener {
    private Image backgroundImage;
    private Image GhostKirImage;
    private Image gameOverImage; // gameover.png 이미지
    private Image[] hamImages = new Image[8];
    private int GhostKirX = 300;
    private int GhostKirY = 300;

    private int[] hamX = new int[8];
    private int[] hamY = new int[8];
    private double[] hamSpeed = new double[8];
    private Random random = new Random();

    private MiniStackPanel miniStackPanel;
    private Timer gameTimer;
    private boolean isGameOver = false; // 게임 종료 여부 체크
    private Timer gameOverTimer; // 게임 오버 타이머
    
    private eximg exampleImage;

    public GameStart() {
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage();
        GhostKirImage = new ImageIcon("image/GhostKir.png").getImage();
        gameOverImage = new ImageIcon("image/gameover.png").getImage(); // gameover.png 로드

        miniStackPanel = new MiniStackPanel();
        JFrame miniFrame = new JFrame("Mini Stack");
        miniFrame.setSize(200, 500);
        miniFrame.add(miniStackPanel);
        miniFrame.setLocation(1200, 200);
        miniFrame.setVisible(true);

        for (int i = 0; i < 8; i++) {
            hamImages[i] = new ImageIcon("image/hamImg" + (i + 1) + ".png").getImage();
            hamSpeed[i] = 2 + random.nextInt(2); // 속도를 줄여서 내려오는 속도 느리게 설정

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

        // 타이머로 게임 상태를 업데이트
        gameTimer = new Timer(40, e -> {
            if (!isGameOver) {
                updateHamPosition(miniFrame);
                repaint();
            }
        });
        gameTimer.start();
    }

    private void updateHamPosition(JFrame miniFrame) {
        for (int i = 0; i < hamImages.length; i++) {
            hamY[i] += hamSpeed[i];

            if (hamY[i] >= GhostKirY && hamX[i]  >= GhostKirX && hamX[i] <= GhostKirX + 150 && hamY[i] <= GhostKirY+120) {
                miniStackPanel.addIngredient(hamImages[i]);

                // 윗빵이 올라갔을 때 gameover.png를 표시
                if (hamImages[i] == new ImageIcon("image/hamImg1.png").getImage()) {
                    isGameOver = true; // 게임 종료
                    startGameOverTimer(miniFrame); // 게임 오버 타이머 시작
                    return;
                }

                hamY[i] = -50;
                hamSpeed[i] = 2 + random.nextInt(2); // 재료가 떨어지는 속도를 계속 줄여줍니다
            }

            if (hamY[i] > getHeight()) {
                hamY[i] = -50;
                hamSpeed[i] = 2 + random.nextInt(2); // 재료가 떨어지는 속도를 계속 줄여줍니다

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

    // 게임 오버 타이머 설정
    private void startGameOverTimer(JFrame miniFrame) {
        gameOverTimer = new Timer(2000, e -> {
            // 2초 후 게임 오버 화면 표시
//            repaint();
            // 2초 후 게임 오버 화면 표시
            GameOver.showGameOver(miniStackPanel.getIngredients());
            miniFrame.dispose();
//            eximg.closeWindow();
        });
        gameOverTimer.setRepeats(false);
        gameOverTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(GhostKirImage, GhostKirX, GhostKirY, 175, 170, this);

        if (isGameOver) {
            // Y 좌표를 기존보다 50만큼 위로 올림
            g.drawImage(gameOverImage, getWidth() / 2 - 150, getHeight() / 2 - 150, 300, 200, this);

        } else {
            for (int i = 0; i < hamImages.length; i++) {
                g.drawImage(hamImages[i], hamX[i], hamY[i], 50, 50, this);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && GhostKirX > 0) {
            GhostKirX -= 10;
        } else if (keyCode == KeyEvent.VK_RIGHT && GhostKirX < getWidth() - 175) {
            GhostKirX += 10;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}

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
        int y = getHeight() - 50;
        for (Image ingredient : ingredients) {
            g.drawImage(ingredient, 50, y, 100, 50, this);
            y -= 50;
        }
    }
    public java.util.List<Image> getIngredients() {
        return new ArrayList<>(ingredients); // 원본 리스트를 보호하기 위해 복사본 반환
    }
}