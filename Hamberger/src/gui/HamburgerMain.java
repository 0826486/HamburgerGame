package gui;

import javax.swing.*;
import java.awt.*;

public class HamburgerMain {
    public static void main(String[] args) {
        // JFrame 생성 ( 메인 게임 시작 화면 )ㄴ
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

        // 버튼 클릭 시 실행 할 코드 정의
        button.addActionListener(e -> {
            // 랜덤 미니 창 표시를 5초(5000ms) 후에 실행
        	// Time Timer : 버튼 클릭 후 5초 뒤에 화면 전환
            Timer timer = new Timer(5000, event -> {
                new eximg().setVisible(true); // 랜덤 미니 창 표시
            });
            timer.setRepeats(false); // 타이머가 한 번만 실행
            timer.start();

            // Kirby 화면으로 전환
            frame.getContentPane().removeAll(); // 기존 화면 제거
            frame.add(new Kirby());             // Kirby.java 화면 추가
            frame.revalidate();                 // 변경 사항 적용
            frame.repaint();                    // 화면 다시 그리기
            // UI를 새로고침하여 변경 사항을 적용
        });

        // 화면을 보이게 설정
        frame.setVisible(true);
    }
}

// 배경 패널 클래스
// Swing 패널을 확장하여 커스텀 배경을 설정
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
        // 배경 이미지를 창 크기에 맞춰서 그림
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // 추가 이미지를 지정된 위치에서 보여지게 만들음
        g.drawImage(additionalImage, 640, 17, 150, 150, this); // 추가 이미지 위치와 크기
    }
}
