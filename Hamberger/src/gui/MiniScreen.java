package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MiniScreen {
    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        
        // MiniScreen용 배경 패널 생성 및 추가
        MiniScreenBackgroundPanel backgroundPanel = new MiniScreenBackgroundPanel();
        frame.add(backgroundPanel);

        // 창을 보이게 설정
        frame.setVisible(true);

        // 게임 시작 시 랜덤으로 햄버거 이미지를 표시하는 창 생성
        RandomHamburgerWindow hamburgerWindow = new RandomHamburgerWindow();
        hamburgerWindow.setVisible(true); // 작은 창을 보이게 설정
    }
}

// MiniScreen 배경 이미지를 표시하는 패널 클래스
class MiniScreenBackgroundPanel extends JPanel {
    private Image backgroundImage;
    private Image characterImage; // 추가된 캐릭터 이미지

    public MiniScreenBackgroundPanel() {
        // 배경 및 캐릭터 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage(); // 배경 이미지
        characterImage = new ImageIcon("image/char.png").getImage(); // 캐릭터 이미지 로드
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞게 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
        // 캐릭터 이미지를 원하는 위치에 표시 (예: x=100, y=400)
        g.drawImage(characterImage, 300, 300, 200, 200, this); // 위치 및 크기 설정
    }
}

// 랜덤 햄버거 이미지를 표시하는 작은 창 클래스
class RandomHamburgerWindow extends JFrame {
    private Image[] hamburgerImages; // 햄버거 이미지 배열
    private Random random;
    private Image selectedHamburgerImage; // 선택된 햄버거 이미지를 저장할 필드

    public RandomHamburgerWindow() {
        setTitle("랜덤 햄버거");
        setSize(300, 200);
        setLocation(950, 100); // 작은 창의 위치 설정 (메인 창 오른쪽에 배치)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 햄버거 이미지 로드
        hamburgerImages = new Image[] {
            new ImageIcon("image/chicken.png").getImage(),
            new ImageIcon("image/souce.png").getImage(),
            new ImageIcon("image/chesse.png").getImage()
        };

        random = new Random();

        // 창이 처음 열릴 때 랜덤으로 햄버거 이미지 선택
        selectRandomHamburgerImage();
        
        // 이미지 표시를 위해 JPanel을 사용
        HamburgerPanel hamburgerPanel = new HamburgerPanel();
        add(hamburgerPanel);
    }

    // 랜덤으로 햄버거 이미지를 선택하는 메서드
    private void selectRandomHamburgerImage() {
        int index = random.nextInt(hamburgerImages.length);
        selectedHamburgerImage = hamburgerImages[index];
    }

    // 햄버거 이미지를 표시하는 패널 클래스
    class HamburgerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (selectedHamburgerImage != null) {
                // 선택된 햄버거 이미지를 창 크기에 맞춰 그리기
                g.drawImage(selectedHamburgerImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
