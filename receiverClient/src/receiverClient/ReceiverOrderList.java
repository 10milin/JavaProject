package receiverClient;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.StringTokenizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class ReceiverOrderList extends JFrame{
	Container c;
	static JButton[] orderBtn = new JButton[12];
	static JTextArea orderList = new JTextArea();
	static JScrollPane scrollPane;
	
	public ReceiverOrderList(){
		setTitle("주문내역");
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(800, 600);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = getSize();
		
		int x = (int)(screen.getWidth() / 2 - frm.getWidth());
		int y = (int)(screen.getHeight() / 2 - frm.getHeight() / 2);
		
		setLocation(x,y);
		
		init();
		start();
		
		setResizable(false);
		setVisible(true);
	}
	
	public void init() {
		c = getContentPane();
		c.setLayout(new GridLayout(1, 2));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 3));
		
		for(int i = 0; i < orderBtn.length; i++) {
			orderBtn[i] = new JButton();
			orderBtn[i].addActionListener(new OrderListButtonAction()); //주문내역을 표시할 이벤트 처리
			orderBtn[i].setFont(new Font("D2Coding", Font.PLAIN, 20));
			buttonPanel.add(orderBtn[i]);
		} //주문번호를 나타낼 번호 생성
		
		JPanel orderPanel = new JPanel();
		JButton orderFinishBtn = new JButton("완료");
		
		orderPanel.setLayout(new BorderLayout());
		orderList.setEditable(false);
		orderList.setFont(new Font("D2Coding", Font.PLAIN, 25));
		scrollPane = new JScrollPane(orderList); //주문내역에 스크롤 생성
		
		orderPanel.add("Center",scrollPane);
		orderFinishBtn.addActionListener(new OrderFinishButtonAction()); //음식을 완료할 경우의 이벤트 처리
		orderPanel.add("South", orderFinishBtn);
		
		c.add("West",buttonPanel);
		c.add("East", orderPanel);
	}
	
	public void start() {
	}
	
	class OrderListButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton check = (JButton)e.getSource();
			if(!check.getText().equals("")){ //주문번호가 있는 경우에만 실행
				orderList.setText(""); //예전 주문내역을 초기화
				for(int i = 0; i < Receiver.order.size(); i++) {
					String text = check.getText();
					boolean isPrint = false;
					
					if(Receiver.order.get(i).equals(text)) {
						int index = i+1;
						orderList.append("주문번호 : " + Receiver.order.get(i)+"\n"); //주문번호 표시
						while(!isPrint){
							if(index < Receiver.order.size()) {
								if(Receiver.order.get(index).charAt(0) >= '0' && Receiver.order.get(index).charAt(0) <= '9') { //다음 주문번호가 나올경우
									isPrint = true;
								}else {
									orderList.append(Receiver.order.get(index)+"\n"); //주문내역서에 음식 추가
									scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()); //스크롤바 갱신
									index++;
								}
							}else {
								isPrint = true;
							}
						}
					}
				}
			}
		}
	}
	
	class OrderFinishButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!orderList.getText().equals("")) {
				String text = orderList.getText();
				StringTokenizer st = new StringTokenizer(text,"주문번호 : |\n");
				text = st.nextToken();
				ReceiverClient.output_msg.append(text + "\n"); //주문번호를 전광판에 띄우줌
				orderList.setText(""); //주문 내역서 초기화
				for(int i = 0; i < orderBtn.length; i++) {
					if(orderBtn[i].getText().equals(text)) {
						orderBtn[i].setText(""); //주문이 완료되었으므로 해당 번호의 주문번호 제거
						for(int j = 0; j < Receiver.order.size(); j++) {
							if(Receiver.order.get(j).equals(text)) { //해당번호의 주문번호를 찾아
								Receiver.order.remove(j); //주문번호를 삭제한다
								while(true) {
									if(Receiver.order.size() == 0) { //더 이상 주문내역이 없는 경우
										break;
									}else {
										if(Receiver.order.size() == j) { //다음 주문내역이 없는 경우
											break;
										}
										if(Receiver.order.get(j).charAt(0) >= '0' && Receiver.order.get(j).charAt(0) <= '9') { //다음 주문번호가 나올 경우
											break;
										}else {
											Receiver.order.remove(j); //주문 내역 삭제
										}
									}
								}
								break;
							}
						}
					}
				}
				
				int index = 0;
				
				for(int i = 0; i < orderBtn.length; i++) {
					orderBtn[i].setText(""); //모든 버튼의 주문내역 초기화
				}
				
				for(int i = 0; i < Receiver.order.size(); i++) {
					if(Receiver.order.get(i).charAt(0) >= '0' && Receiver.order.get(i).charAt(0) <= '9' && index < 12) { //주문 번호만 가져옴
						orderBtn[index].setText(Receiver.order.get(i)); //주문번호를 버튼에 다시 입력
						index++;
					}
				}
				
				play("sound/bell.wav"); //주문 완료음 재생
			}
		}
	}
	
	public void play(String fileName) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			Clip clip = AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
