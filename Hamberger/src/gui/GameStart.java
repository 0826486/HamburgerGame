package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameStart extends JPanel implements KeyListener {
    private Image backgroundImage;
    private Image chefKirbyImage; // 메인 캐릭터 이미지
    private Image[] hamImages = new Image[8]; // 햄버거 재료 이미지 배열
    private int chefKirbyX = 300; // 원하는 X 좌표로 설정
    private int chefKirbyY = 300; // 원하는 Y 좌표로 설정 (고정)

    public GameStart() {
        // 배경 이미지와 메인 캐릭터 이미지 로드
        backgroundImage = new ImageIcon("image/startbackground.jpg").getImage();
        chefKirbyImage = new ImageIcon("image/kirbychef.png").getImage();

        // 햄버거 재료 이미지 로드
        for (int i = 0; i < 8; i++) {
            hamImages[i] = new ImageIcon("image/ham" + (i + 1) + ".png").getImage();
        }

        // KeyListener 추가
        this.setFocusable(true);  // 패널이 키 이벤트를 받을 수 있도록 설정
        this.addKeyListener(this); // KeyListener 등록
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞게 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // ChefKirby 이미지를 원하는 위치에 그리기 (Y는 고정)
        g.drawImage(chefKirbyImage, chefKirbyX, chefKirbyY, 175, 170, this);

        // 햄버거 재료 이미지들을 화면에 표시 (예시)
        for (int i = 0; i < hamImages.length; i++) {
            g.drawImage(hamImages[i], 50 * i, 0, 50, 50, this); // 원하는 위치와 크기로 조정 가능
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