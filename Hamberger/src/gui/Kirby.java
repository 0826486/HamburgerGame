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
    private Image newImage; // 새 이미지
    private int kirbyW = 80;
    private int kirbyH = 80;
    private boolean isKirbyFullyGrown = false; // 커비가 다 커졌는지 여부
    private boolean isNewImageDisplayed = false; // 새 이미지 표시 여부

    public Kirby() {
        // 배경 및 캐릭터 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage(); // 배경 이미지
        characterImage = new ImageIcon("image/char.png").getImage(); // 캐릭터 이미지
        kirbychar = new ImageIcon("image/kirbymukbang.png").getImage();
        newImage = new ImageIcon("image/kirbychef.png").getImage(); // 새로운 이미지 로드

        // 2초 지연 후에 타이머 시작
        new Timer(2000, e -> {
            // 타이머 생성 및 커비 이미지 크기 증가 설정
            Timer kirbyTimer = new Timer(100, event -> {
                kirbyW += 10;
                kirbyH += 10;
                if (kirbyW >= 200 || kirbyH >= 200) {  // 원하는 크기인 200에서 멈춤
                    ((Timer) event.getSource()).stop(); // 타이머 중지
                    isKirbyFullyGrown = true; // 커비가 다 커졌음을 표시
                    
                    // 2초 후 새 이미지 표시 타이머
                    new Timer(2000, delayEvent -> {
                        isNewImageDisplayed = true;
                        repaint(); // 새 이미지를 그리기 위해 다시 그리기
                    }).start();
                }
                repaint(); // 크기 조정 후 다시 그리기
            });
            kirbyTimer.start();
            ((Timer) e.getSource()).stop(); // 2초 지연 타이머 중지
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞게 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        if (isNewImageDisplayed) {
            // 2초 후 새 이미지를 표시
            g.drawImage(newImage, 300, 180, 170, 170, this);
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