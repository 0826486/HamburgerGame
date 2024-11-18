package gui;

import javax.swing.*;
import java.awt.*;

public class GameOver {
    public static void main(String[] args) {
        // GameOver 화면을 띄운다.
        SwingUtilities.invokeLater(() -> {
            GameOverFrame gameOverFrame = new GameOverFrame();
            gameOverFrame.setVisible(true);
        });
    }
}

class GameOverFrame extends JFrame {
    private Image backgroundImage;
    private Image gameOverBoxImage;

    public GameOverFrame() {
        setTitle("Game Over");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 배경 이미지를 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage(); // 배경 이미지 설정
        gameOverBoxImage = new ImageIcon("image/GameOverBox.png").getImage(); // 게임 오버 박스 이미지 설정
        setLocation(100, 100); // 위치 설정
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // 게임 오버 박스 이미지 그리기 (중앙에 위치)
        int boxX = (getWidth() - 500) / 2;
        int boxY = (getHeight() - 400) / 2;
        int boxWidth = 400;  // 너비
        int boxHeight = 200; // 높이

        g.drawImage(gameOverBoxImage, boxX, boxY, boxWidth, boxHeight, this);
    }
}