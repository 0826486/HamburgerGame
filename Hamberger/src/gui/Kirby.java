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
    private int kirbyW = 80;
    private int kirbyH = 80;

    public Kirby() {
        // 배경 및 캐릭터 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage(); // 배경 이미지
        characterImage = new ImageIcon("image/char.png").getImage(); // 캐릭터 이미지
        kirbychar = new ImageIcon("image/kirbymukbang.png").getImage();
        
        // 캐릭터 크기 조정 타이머 설정
        Timer kirby = new Timer(100, e -> {
            kirbyW += 10;
            kirbyH += 10;
            if (kirbyW >= 200 || kirbyH >= 200) {  // 원하는 크기인 200에서 멈춤
                ((Timer) e.getSource()).stop(); // 타이머 중지
            }
            repaint(); // 크기 조정 후 다시 그리기
        });
        kirby.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞게 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
        // 캐릭터 이미지를 원하는 위치에 표시
        g.drawImage(characterImage, 300, 180, 200, 200, this); // 위치 및 크기 설정
        g.drawImage(kirbychar, 250, 150, kirbyW, kirbyH, this);
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