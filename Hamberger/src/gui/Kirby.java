package gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Kirby extends JPanel {
    private Image backgroundImage;
    private Image characterImage;
    private Image kirbychar;
    private Image GhostKir; // 새 이미지
    private Image star; // 별 이미지
    private Image star2; // 별 이미지
    private int kirbyW = 80;
    private int kirbyH = 80;
    private boolean isKirbyFullyGrown = false; // 커비가 다 커졌는지 여부
    private boolean isNewImageDisplayed = false; // 새 이미지 표시 여부

    public Kirby() {
        // 배경 및 캐릭터 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage(); // 배경 이미지
        characterImage = new ImageIcon("image/char.png").getImage(); // 캐릭터 이미지
        kirbychar = new ImageIcon("image/kirbymukbang.png").getImage();
        GhostKir = new ImageIcon("image/GhostKir.png").getImage(); // 새로운 이미지 로드
        star = new ImageIcon("image/star.png").getImage(); // 별 이미지 로드
        star2 = new ImageIcon("image/star.png").getImage(); // 별 이미지 로드

        // 타이머 생성 및 커비 이미지 크기 증가 설정
        Timer kirbyTimer = new Timer(100, event -> {
            kirbyW += 10;
            kirbyH += 10;
            if (kirbyW >= 200 || kirbyH >= 200) { // 원하는 크기인 200에서 멈춤
                ((Timer) event.getSource()).stop(); // 타이머 중지
                isKirbyFullyGrown = true; // 커비가 다 커졌음을 표시

                // 3초 후 캐릭터가 바뀌고 GameStart 화면으로 전환
                Timer delayTimer = new Timer(3000, delayEvent -> { // 지연 시간을 3000밀리초(3초)로 설정
                    isNewImageDisplayed = true; // 새 이미지를 표시
                    repaint(); // 새 이미지를 그리기 위해 다시 그리기

                    // GameStart 화면 전환 1초 후 실행되도록 타이머 설정
                    Timer transitionTimer = new Timer(1000, transitionEvent -> {
                        JFrame gameStartFrame = new JFrame("Game Start");
                        gameStartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        gameStartFrame.setSize(900, 600);
                        gameStartFrame.add(new GameStart()); // GameStart 클래스를 패널로 추가
                        gameStartFrame.setVisible(true);

                        // 현재 창 닫기
                        JFrame topFrame = (JFrame) getTopLevelAncestor();
                        topFrame.dispose();  // Kirby 창 닫기
                    });
                    transitionTimer.setRepeats(false); // 한 번만 실행되도록 설정
                    transitionTimer.start(); // 타이머 시작
                });
                delayTimer.setRepeats(false); // 한 번만 실행되도록 설정
                delayTimer.start(); // 타이머 시작
            }
            repaint(); // 크기 조정 후 다시 그리기
        });
        kirbyTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞게 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        if (isNewImageDisplayed) {
            // 텀 후 새 이미지를 표시 (ChefKirby와 star 이미지를 함께 표시)
            g.drawImage(GhostKir, 320, 180, 180, 180, this); // GhostKir 위치와 크기
            g.drawImage(star, 444, 150, 100, 100, this); // GhostKir 옆에 별 이미지 표시
            g.drawImage(star2, 260, 270, 100, 100, this); // GhostKir 옆에 별2 이미지 표시
        } else if (isKirbyFullyGrown) {
            // 커비가 다 커졌을 때는 기존 캐릭터와 커비 이미지를 유지
            g.drawImage(characterImage, 300, 180, 200, 200, this);
            g.drawImage(kirbychar, 300, 180, kirbyW, kirbyH, this);
        } else {
            // 커비가 커지는 중일 때 기존 이미지들 그리기
            g.drawImage(characterImage, 300, 180, 200, 200, this);
            g.drawImage(kirbychar, 250, 150, kirbyW, kirbyH, this);
        }
    }

    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        // Kirby 패널을 JFrame에 추가
        Kirby kirbyPanel = new Kirby();
        frame.add(kirbyPanel);

        // 창을 보이게 설정
        frame.setVisible(true);
    }
}