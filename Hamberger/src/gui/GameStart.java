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
    private int chefKirbyX = 300; // 원하는 X 좌표로 설정
    private int chefKirbyY = 300; // 원하는 Y 좌표로 설정 (고정)

    private int[] hamX = new int[8]; // 각 햄버거 재료의 X 좌표
    private int[] hamY = new int[8]; // 각 햄버거 재료의 Y 좌표
    private int[] hamSpeed = new int[8]; // 각 햄버거 재료의 떨어지는 속도

    private Random random = new Random();

    public GameStart() {
        // 배경 이미지와 메인 캐릭터 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage();
        chefKirbyImage = new ImageIcon("image/kirbychef.png").getImage();

        // 햄버거 재료 이미지 로드
        for (int i = 0; i < 8; i++) {
            hamImages[i] = new ImageIcon("image/hamImg" + (i + 1) + ".png").getImage();
            hamSpeed[i] = 4 + random.nextInt(4); // 속도를 높임 (4~7 사이)

            // 이미지 간 겹침을 방지하기 위해 X 좌표 설정
            int x;
            boolean overlap;
            do {
                x = random.nextInt(800); // X 좌표를 넓은 범위에서 랜덤 설정
                overlap = false;
                // 이전 이미지와 X 좌표 겹침 방지
                for (int j = 0; j < i; j++) {
                    if (Math.abs(x - hamX[j]) < 60) { // 이미지가 겹치지 않도록 최소 거리 유지
                        overlap = true;
                        break;
                    }
                }
            } while (overlap);

            hamX[i] = x;
            hamY[i] = random.nextInt(100) - 100; // Y 좌표를 화면 위에서 떨어지도록 설정
        }

        // KeyListener 추가
        this.setFocusable(true);  // 패널이 키 이벤트를 받을 수 있도록 설정
        this.addKeyListener(this); // KeyListener 등록

     // 타이머 설정 (애니메이션 효과)
        Timer timer = new Timer(40, new ActionListener() { // 타이머 속도를 더 빠르게 (30에서 20으로 줄임)
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
            hamY[i] += hamSpeed[i]; // Y 좌표에 속도만큼 더해 아래로 이동

            // 화면 아래로 떨어진 경우 다시 위로 이동하여 랜덤한 위치에서 떨어지도록 설정
            if (hamY[i] > getHeight()) {
                hamY[i] = -50;
                // X 좌표를 겹치지 않도록 설정
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
                hamSpeed[i] = 2 + random.nextInt(3); // 속도도 높여 설정 (4~7 사이)
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞게 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // ChefKirby 이미지를 원하는 위치에 그리기 (Y는 고정)
        g.drawImage(chefKirbyImage, chefKirbyX, chefKirbyY, 175, 170, this);

        // 햄버거 재료 이미지들을 화면에 표시
        for (int i = 0; i < hamImages.length; i++) {
            g.drawImage(hamImages[i], hamX[i], hamY[i], 50, 50, this); // 각 이미지의 X, Y 좌표에 그리기
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 화살표 키가 눌리면 캐릭터 위치 이동
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            chefKirbyX -= 10;  // 왼쪽으로 이동
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            chefKirbyX += 10;  // 오른쪽으로 이동
        }
        repaint();  // 이동 후 화면을 다시 그리기
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 키가 떼어지면 아무 동작도 하지 않음
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 키 입력이 문자일 경우 처리하는 부분, 여기서는 필요 없음
    }

    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame("Game Start");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        // GameStart 패널을 JFrame에 추가
        GameStart gameStartPanel = new GameStart();
        frame.add(gameStartPanel);

        // 창을 보이게 설정
        frame.setVisible(true);
    }
}