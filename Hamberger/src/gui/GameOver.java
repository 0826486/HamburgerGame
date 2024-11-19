package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import gui.RandomHamburger;

public class GameOver extends JPanel {
    private Image backgroundImage;
    private Image gameOverBoxImage;
    private List<Image> stackedBurger;

    public GameOver(List<Image> stackedBurger) {
        // 전달받은 리스트를 복사한 뒤, 원래 순서를 뒤집음
        this.stackedBurger = new ArrayList<>(stackedBurger);

        // 배경 및 박스 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage(); // 배경 이미지 설정
        gameOverBoxImage = new ImageIcon("image/GameOverBox.png").getImage(); // 게임 오버 박스 이미지 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // 게임 오버 박스 그리기
        int boxX = (getWidth() - 500) / 2;
        int boxY = (getHeight() - 300) / 2;
        int boxWidth = 400;
        int boxHeight = 200;
        g.drawImage(gameOverBoxImage, boxX, boxY, boxWidth, boxHeight, this);

        // 햄버거 거꾸로 그리기
        if (stackedBurger != null && !stackedBurger.isEmpty()) {
            int burgerX = boxX + 200; // 햄버거 시작 위치 (박스 내 중심)
            int burgerY = boxY + 300; // 햄버거는 박스의 상단부터 아래로 거꾸로 쌓기

            for (Image burger : stackedBurger) {
                g.drawImage(burger, burgerX, burgerY, 100, 50, this);
                burgerY -= 20; // 아래로 쌓아 내리기
            }
        }
    }

    public static void showGameOver(List<Image> stackedBurger) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Over");
            GameOver gameOverPanel = new GameOver(stackedBurger);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.add(gameOverPanel);
            frame.setVisible(true);
        });
    }
}
