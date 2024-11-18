package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;

public class HamburgerMain {
    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        
        // 배경 패널 생성
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        frame.add(backgroundPanel);

        // 버튼 이미지 로드
        ImageIcon buttonIcon = new ImageIcon("image/start.png"); // 버튼에 사용할 이미지 경로 설정

        // 버튼 생성
        JButton button = new JButton(buttonIcon);
        button.setBounds(390, 430, buttonIcon.getIconWidth(), buttonIcon.getIconHeight()); // 버튼의 위치와 크기를 이미지 크기로 설정
        
        // 버튼의 배경과 테두리 제거
        button.setContentAreaFilled(false); // 버튼의 내용 영역 배경을 투명하게 설정
        button.setBorderPainted(false);     // 버튼 테두리 제거
        button.setFocusPainted(false);      // 버튼 클릭 시 포커스 효과 제거
        
        backgroundPanel.setLayout(null);    // 레이아웃을 null로 설정해 절대 위치 사용
        backgroundPanel.add(button);        // 배경 패널에 버튼 추가

        // 버튼 클릭 이벤트 처리
        button.addActionListener(e -> {
            // 현재 창 닫기
            frame.dispose();
            
            // Kirby 클래스를 새 창에 띄우기
            JFrame kirbyFrame = new JFrame();
            kirbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            kirbyFrame.setSize(900, 600);
            kirbyFrame.add(new Kirby()); // Kirby 패널 추가
            kirbyFrame.setVisible(true);
        });

        // 창을 보이게 설정
        frame.setVisible(true);
    }
}

// 배경 패널 클래스
class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    private Image additionalImage; // 추가 이미지 필드

    public BackgroundPanel() {
        // 이미지 로드
        backgroundImage = new ImageIcon("image/main.jpg").getImage();
        additionalImage = new ImageIcon("image/kirbyham.png").getImage(); // 추가 이미지 경로 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 배경 이미지
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
        // 추가 이미지
        g.drawImage(additionalImage, 640, 17, 150, 150, this); // 추가 이미지 위치와 크기
    }
}