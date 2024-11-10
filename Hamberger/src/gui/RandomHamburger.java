package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomHamburger {
    public static void main(String[] args) {
        // 예시 이미지를 보여주는 창을 생성하고 표시
        SwingUtilities.invokeLater(() -> {
            eximg exampleImage = new eximg();
            exampleImage.setVisible(true); // 예시 이미지 창 보이기

            // 2초 후에 게임을 시작하는 메서드를 호출
            Timer timer = new Timer(2000, e -> {
                // 2초 후 예시 창은 그대로 두고 게임을 시작
                startGame();
            });
            timer.setRepeats(false); // 한 번만 실행되도록 설정
            timer.start(); // 타이머 시작
        });
    }

    // 게임 시작 창을 여는 메서드
    private static void startGame() {
        JFrame gameFrame = new JFrame("Game Start");
        GameStart gameStartPanel = new GameStart();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(900, 600);
        gameFrame.add(gameStartPanel);
        gameFrame.setVisible(true); // 게임 시작 창 보이기
    }
}

// 랜덤 햄버거 이미지를 표시하는 클래스
class eximg extends JFrame {
    private Image[] burgerImages; // 햄버거 이미지 배열
    private Random random;

    public eximg() {
        setTitle("똑같은 버거를 만들어보세요!");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 이미지 로드
        burgerImages = new Image[] {
            new ImageIcon("image/chesse.png").getImage(),
            new ImageIcon("image/hamberger.png").getImage(),
            new ImageIcon("image/souce.png").getImage(),
            new ImageIcon("image/chicken.png").getImage() // 추가된 햄버거 이미지
        };

        random = new Random();
        setLocation(1000, 200); // 예시 창 위치 조정 (게임 창 옆에 위치)
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 랜덤한 햄버거 이미지 그리기
        int index = random.nextInt(burgerImages.length);
        g.drawImage(burgerImages[index], 0, 0, getWidth(), getHeight(), this);
    }
}