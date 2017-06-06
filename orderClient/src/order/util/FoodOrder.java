package order.util;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.*;

import order.action.Action;
import order.action.ChinaFoodAction;
import order.action.KoreaFoodAction;
import order.action.WesternFoodAction;
import order.controller.FrontController;
import order.thread.ClientReceiver;
import order.thread.ClientSender;
import order.util.ConsoleUtils;

public class FoodOrder extends JFrame{
	Container c;
	JButton[] typeBtn = new JButton[3];
	String[] type = {"한식", "중식", "양식"};
	static JTextArea orderList = new JTextArea();
	static JButton[] foodBtn = new JButton[9];
	Action action = null;
	ConsoleUtils cu = new ConsoleUtils();
	FrontController fc = new FrontController();
	JOptionPane option = new JOptionPane();
	
	public FoodOrder() {
		setTitle("Order Client");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(800, 600);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = getSize();
		
		int x = (int)(screen.getWidth() / 2 - frm.getWidth() / 2);
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

		for(int i = 0; i < typeBtn.length; i++) {
			typeBtn[i] = new JButton();
			typeBtn[i].setText(type[i]);
			typeBtn[i].addActionListener(new TypeButtonAction()); //음식 type 선택 이벤트 처리
			typeBtn[i].setFont(new Font("D2Coding", Font.PLAIN, 20));
			buttonPanel.add(typeBtn[i]);
		} //주문할 음식의 type을 선택할 수 있는 버튼을 생성(한식, 중식, 양식)
		
		for(int i = 0; i < foodBtn.length; i++) {
			foodBtn[i] = new JButton();
			foodBtn[i].addActionListener(new FoodButtonAction()); //선택한 음식을 주문내역서에 추가하는 이벤트 처리
			foodBtn[i].addMouseListener(new FoodButtonAction());
			foodBtn[i].setFont(new Font("D2Coding", Font.PLAIN, 15));
			foodBtn[i].setBackground(new Color(145,194,249));
			buttonPanel.add(foodBtn[i]);
		} //주문할 음식의 종류를 선택할 수 있는 버튼을 생성
		
		JPanel orderPanel = new JPanel();
		JPanel orderBtnPanel = new JPanel();
		JScrollPane scrollPane;
		JButton orderSendBtn = new JButton("주문"); //선택한 음식을 주문하는 버튼
		JButton orderCancelBtn = new JButton("주문취소"); //모든 주문리스트를 초기화 하는 버튼
		
		orderPanel.setLayout(new BorderLayout());
		orderBtnPanel.setLayout(new GridLayout(1, 2));
		
		orderSendBtn.addActionListener(new OrderButtonAction()); //주문 이벤트 처리
		orderCancelBtn.addActionListener(new OrderButtonAction()); //주문 취소 이벤트 처리
		orderList.setEditable(false); //주문내역 사용자 수정 불가
		orderList.setFont(new Font("D2Coding", Font.PLAIN, 25));
		scrollPane = new JScrollPane(orderList); //JTextArea에 스크롤 기능 추가
		
		orderPanel.add("Center",scrollPane);
		orderBtnPanel.add(orderSendBtn);
		orderBtnPanel.add(orderCancelBtn);
		orderPanel.add("South", orderBtnPanel);
		
		c.add("West",buttonPanel);
		c.add("East", orderPanel);
	}
	
	public void start() {
		try {
			String name = InetAddress.getLocalHost().getHostAddress(); //자신의 IP를 name으로 사용
			Socket socket = new Socket("",7700); //서버의 IP주소와 열린 포트번호
			Thread sender = new Thread(new ClientSender(socket, name)); //송신 Thread 생성
			Thread receiver = new Thread(new ClientReceiver(socket)); //수신 Thread 생성
			//Thread 시작
			sender.start();
			receiver.start();
		}catch(Exception e) {}
	}
	
	class TypeButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton check = (JButton)e.getSource();
			
			switch(check.getText()) { //음식의 Type이 적혀있는 버튼의 Text를 읽어온다
			case "한식":
				action = new KoreaFoodAction(); //한식을 선택한 경우의 액션 처리
				break;
			case "중식":
				action = new ChinaFoodAction(); //중식을 선택한 경우의 액션 처리
				break;
			case "양식":
				action = new WesternFoodAction(); //양식을 선택한 경우의 액선 처리
				break;
			}
			
			if(action != null) {
				fc.requestProcess(action); //액션이 있는 경우 액션을 실행
			}
		}
	}
	
	class FoodButtonAction extends MouseAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton check = (JButton)e.getSource();
			if(!check.getText().equals("")) {
				cu.showFoodOrder(check.getText()); //음식의 종류를 선택하여 주문내역서에 그 내용을 입력
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			JButton btn = (JButton)e.getSource();
			btn.setBackground(new Color(208, 205, 208));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JButton btn = (JButton)e.getSource();
			btn.setBackground(new Color(145,194,249));
		}
	}
	
	class OrderButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton check = (JButton)e.getSource();
			switch(check.getText()) { //주문, 주문취소의 구분
			case "주문":
				if(!orderList.getText().equals("")) {
					try {
						ClientSender.out.writeUTF(InetAddress.getLocalHost().getHostAddress()); //자신의 IP를 서버로 전송
						Thread.sleep(100); //0.1초 대기
						String list = orderList.getText();
						String[] oneOrder = list.split("\n"); //주문내역서의 내용을 구분
						
						for(int i = 0; i < oneOrder.length; i++) {
							ClientSender.out.writeUTF(oneOrder[i]); //주문내역서의 내용을 전송
						}
						
						orderList.setText(""); //주문내역서 초기화
						
						option.showMessageDialog(null, cu.showOrderNumber(ClientReceiver.orderNum)); //주문번호 팝업창으로 표시
					} catch (Exception e1) {}
				}
				break;
			case "주문취소":
				orderList.setText(""); //주문내역서 초기화
				break;
			}
		}
	}
	
	public static void main(String[] ar) {
		new FoodOrder();
	}
}
