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
    private Image chefKirbyImage; // 메인 캐릭터 이미지
    private Image[] hamImages = new Image[8]; // 햄버거 재료 이미지 배열
    private int chefKirbyX = 300; // 캐릭터 X 좌표
    private int chefKirbyY = 300; // 캐릭터 Y 좌표 (고정)

    private int[] hamX = new int[8]; // 햄버거 재료의 X 좌표
    private int[] hamY = new int[8]; // 햄버거 재료의 Y 좌표
    private double[] hamSpeed = new double[8]; // 햄버거 재료의 속도

    private Random random = new Random();

    public GameStart() {
        // 배경 및 캐릭터 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage();
        chefKirbyImage = new ImageIcon("image/kirbychef.png").getImage();

        // 햄버거 재료 이미지 로드 및 위치 초기화
        for (int i = 0; i < 8; i++) {
            hamImages[i] = new ImageIcon("image/hamImg" + (i + 1) + ".png").getImage();
            hamSpeed[i] = 4 + random.nextInt(4); // 속도 초기화 (4~7)

            // X 좌표 겹침 방지하여 초기화
            int x;
            boolean overlap;
            do {
                x = random.nextInt(800); // X 좌표 범위 설정
                overlap = false;
                for (int j = 0; j < i; j++) {
                    if (Math.abs(x - hamX[j]) < 60) { // 최소 거리 유지
                        overlap = true;
                        break;
                    }
                }
            } while (overlap);
            hamX[i] = x;
            hamY[i] = random.nextInt(100) - 100; // Y 좌표 설정
        }

        // 키 리스너 추가
        this.setFocusable(true);
        this.addKeyListener(this);

        // 타이머 설정 (애니메이션)
        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHamPosition();
                repaint(); // 화면 다시 그리기
            }
        });
        timer.start(); // 타이머 시작
    }

    // 햄버거 재료의 위치 업데이트
    private void updateHamPosition() {
        for (int i = 0; i < hamImages.length; i++) {
            hamY[i] += hamSpeed[i]; // Y 좌표 이동

            // 화면 아래로 떨어진 경우 다시 위로 이동
            if (hamY[i] > getHeight()) {
                hamY[i] = -50;
                hamSpeed[i] += 0.2; // 속도 증가

                // X 좌표 겹침 방지하여 초기화
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
        // 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // 캐릭터 이미지 그리기
        g.drawImage(chefKirbyImage, chefKirbyX, chefKirbyY, 175, 170, this);

        // 햄버거 재료 그리기
        for (int i = 0; i < hamImages.length; i++) {
            g.drawImage(hamImages[i], hamX[i], hamY[i], 50, 50, this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 캐릭터 이동
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
        // 키가 떼어질 때 동작 없음
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 문자 입력 처리 없음
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