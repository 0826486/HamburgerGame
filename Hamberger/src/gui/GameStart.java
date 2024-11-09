package gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameStart extends JPanel {
    private Image backgroundImage;
    private Image chefKirbyImage; // 메인 캐릭터 이미지
    private int chefKirbyX = 200; // 원하는 X 좌표로 설정
    private int chefKirbyY = 150; // 원하는 Y 좌표로 설정

    public GameStart() {
        // 배경 이미지와 메인 캐릭터 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage();
        chefKirbyImage = new ImageIcon("image/kirbychef.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞게 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // ChefKirby 이미지를 원하는 위치에 그리기
        g.drawImage(chefKirbyImage, chefKirbyX, chefKirbyY, 175, 170, this);
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