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
	String[] type = {"�ѽ�", "�߽�", "���"};
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
			typeBtn[i].addActionListener(new TypeButtonAction()); //���� type ���� �̺�Ʈ ó��
			typeBtn[i].setFont(new Font("D2Coding", Font.PLAIN, 20));
			buttonPanel.add(typeBtn[i]);
		} //�ֹ��� ������ type�� ������ �� �ִ� ��ư�� ����(�ѽ�, �߽�, ���)
		
		for(int i = 0; i < foodBtn.length; i++) {
			foodBtn[i] = new JButton();
			foodBtn[i].addActionListener(new FoodButtonAction()); //������ ������ �ֹ��������� �߰��ϴ� �̺�Ʈ ó��
			foodBtn[i].addMouseListener(new FoodButtonAction());
			foodBtn[i].setFont(new Font("D2Coding", Font.PLAIN, 15));
			foodBtn[i].setBackground(new Color(145,194,249));
			buttonPanel.add(foodBtn[i]);
		} //�ֹ��� ������ ������ ������ �� �ִ� ��ư�� ����
		
		JPanel orderPanel = new JPanel();
		JPanel orderBtnPanel = new JPanel();
		JScrollPane scrollPane;
		JButton orderSendBtn = new JButton("�ֹ�"); //������ ������ �ֹ��ϴ� ��ư
		JButton orderCancelBtn = new JButton("�ֹ����"); //��� �ֹ�����Ʈ�� �ʱ�ȭ �ϴ� ��ư
		
		orderPanel.setLayout(new BorderLayout());
		orderBtnPanel.setLayout(new GridLayout(1, 2));
		
		orderSendBtn.addActionListener(new OrderButtonAction()); //�ֹ� �̺�Ʈ ó��
		orderCancelBtn.addActionListener(new OrderButtonAction()); //�ֹ� ��� �̺�Ʈ ó��
		orderList.setEditable(false); //�ֹ����� ����� ���� �Ұ�
		orderList.setFont(new Font("D2Coding", Font.PLAIN, 25));
		scrollPane = new JScrollPane(orderList); //JTextArea�� ��ũ�� ��� �߰�
		
		orderPanel.add("Center",scrollPane);
		orderBtnPanel.add(orderSendBtn);
		orderBtnPanel.add(orderCancelBtn);
		orderPanel.add("South", orderBtnPanel);
		
		c.add("West",buttonPanel);
		c.add("East", orderPanel);
	}
	
	public void start() {
		try {
			String name = InetAddress.getLocalHost().getHostAddress(); //�ڽ��� IP�� name���� ���
			Socket socket = new Socket("59.26.102.194",7700); //������ IP�ּҿ� ���� ��Ʈ��ȣ
			Thread sender = new Thread(new ClientSender(socket, name)); //�۽� Thread ����
			Thread receiver = new Thread(new ClientReceiver(socket)); //���� Thread ����
			//Thread ����
			sender.start();
			receiver.start();
		}catch(Exception e) {}
	}
	
	class TypeButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton check = (JButton)e.getSource();
			
			switch(check.getText()) { //������ Type�� �����ִ� ��ư�� Text�� �о�´�
			case "�ѽ�":
				action = new KoreaFoodAction(); //�ѽ��� ������ ����� �׼� ó��
				break;
			case "�߽�":
				action = new ChinaFoodAction(); //�߽��� ������ ����� �׼� ó��
				break;
			case "���":
				action = new WesternFoodAction(); //����� ������ ����� �׼� ó��
				break;
			}
			
			if(action != null) {
				fc.requestProcess(action); //�׼��� �ִ� ��� �׼��� ����
			}
		}
	}
	
	class FoodButtonAction extends MouseAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton check = (JButton)e.getSource();
			if(!check.getText().equals("")) {
				cu.showFoodOrder(check.getText()); //������ ������ �����Ͽ� �ֹ��������� �� ������ �Է�
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
			switch(check.getText()) { //�ֹ�, �ֹ������ ����
			case "�ֹ�":
				if(!orderList.getText().equals("")) {
					try {
						ClientSender.out.writeUTF(InetAddress.getLocalHost().getHostAddress()); //�ڽ��� IP�� ������ ����
						Thread.sleep(100); //0.1�� ���
						String list = orderList.getText();
						String[] oneOrder = list.split("\n"); //�ֹ��������� ������ ����
						
						for(int i = 0; i < oneOrder.length; i++) {
							ClientSender.out.writeUTF(oneOrder[i]); //�ֹ��������� ������ ����
						}
						
						orderList.setText(""); //�ֹ������� �ʱ�ȭ
						
						option.showMessageDialog(null, cu.showOrderNumber(ClientReceiver.orderNum)); //�ֹ���ȣ �˾�â���� ǥ��
					} catch (Exception e1) {}
				}
				break;
			case "�ֹ����":
				orderList.setText(""); //�ֹ������� �ʱ�ȭ
				break;
			}
		}
	}
	
	public static void main(String[] ar) {
		new FoodOrder();
	}
}