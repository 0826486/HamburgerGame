package gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameStart {

    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame("Game Start");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        // 캐릭터 이미지 패널 생성
        CharacterPanel characterPanel = new CharacterPanel();
        frame.add(characterPanel);

        // 창을 보이게 설정
        frame.setVisible(true);
    }
}

// 캐릭터 이미지를 표시하는 패널 클래스
class CharacterPanel extends JPanel {
    private Image characterImage;

    public CharacterPanel() {
        // 캐릭터 이미지 로드 (이미지 파일 경로에 맞게 설정)
        characterImage = new ImageIcon("image/char.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞게 캐릭터 이미지 그리기
        g.drawImage(characterImage, 100, 100, 300, 300, this); // 캐릭터 위치와 크기 설정
    }
}