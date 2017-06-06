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
		setTitle("�ֹ�����");
				
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
			orderBtn[i].addActionListener(new OrderListButtonAction()); //�ֹ������� ǥ���� �̺�Ʈ ó��
			orderBtn[i].setFont(new Font("D2Coding", Font.PLAIN, 20));
			buttonPanel.add(orderBtn[i]);
		} //�ֹ���ȣ�� ��Ÿ�� ��ȣ ����
		
		JPanel orderPanel = new JPanel();
		JButton orderFinishBtn = new JButton("�Ϸ�");
		
		orderPanel.setLayout(new BorderLayout());
		orderList.setEditable(false);
		orderList.setFont(new Font("D2Coding", Font.PLAIN, 25));
		scrollPane = new JScrollPane(orderList); //�ֹ������� ��ũ�� ����
		
		orderPanel.add("Center",scrollPane);
		orderFinishBtn.addActionListener(new OrderFinishButtonAction()); //������ �Ϸ��� ����� �̺�Ʈ ó��
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
			if(!check.getText().equals("")){ //�ֹ���ȣ�� �ִ� ��쿡�� ����
				orderList.setText(""); //���� �ֹ������� �ʱ�ȭ
				for(int i = 0; i < Receiver.order.size(); i++) {
					String text = check.getText();
					boolean isPrint = false;
					
					if(Receiver.order.get(i).equals(text)) {
						int index = i+1;
						orderList.append("�ֹ���ȣ : " + Receiver.order.get(i)+"\n"); //�ֹ���ȣ ǥ��
						while(!isPrint){
							if(index < Receiver.order.size()) {
								if(Receiver.order.get(index).charAt(0) >= '0' && Receiver.order.get(index).charAt(0) <= '9') { //���� �ֹ���ȣ�� ���ð��
									isPrint = true;
								}else {
									orderList.append(Receiver.order.get(index)+"\n"); //�ֹ��������� ���� �߰�
									scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()); //��ũ�ѹ� ����
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
				StringTokenizer st = new StringTokenizer(text,"�ֹ���ȣ : |\n");
				text = st.nextToken();
				ReceiverClient.output_msg.append(text + "\n"); //�ֹ���ȣ�� �����ǿ� �����
				orderList.setText(""); //�ֹ� ������ �ʱ�ȭ
				for(int i = 0; i < orderBtn.length; i++) {
					if(orderBtn[i].getText().equals(text)) {
						orderBtn[i].setText(""); //�ֹ��� �Ϸ�Ǿ����Ƿ� �ش� ��ȣ�� �ֹ���ȣ ����
						for(int j = 0; j < Receiver.order.size(); j++) {
							if(Receiver.order.get(j).equals(text)) { //�ش��ȣ�� �ֹ���ȣ�� ã��
								Receiver.order.remove(j); //�ֹ���ȣ�� �����Ѵ�
								while(true) {
									if(Receiver.order.size() == 0) { //�� �̻� �ֹ������� ���� ���
										break;
									}else {
										if(Receiver.order.size() == j) { //���� �ֹ������� ���� ���
											break;
										}
										if(Receiver.order.get(j).charAt(0) >= '0' && Receiver.order.get(j).charAt(0) <= '9') { //���� �ֹ���ȣ�� ���� ���
											break;
										}else {
											Receiver.order.remove(j); //�ֹ� ���� ����
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
					orderBtn[i].setText(""); //��� ��ư�� �ֹ����� �ʱ�ȭ
				}
				
				for(int i = 0; i < Receiver.order.size(); i++) {
					if(Receiver.order.get(i).charAt(0) >= '0' && Receiver.order.get(i).charAt(0) <= '9' && index < 12) { //�ֹ� ��ȣ�� ������
						orderBtn[index].setText(Receiver.order.get(i)); //�ֹ���ȣ�� ��ư�� �ٽ� �Է�
						index++;
					}
				}
				
				play("sound/bell.wav"); //�ֹ� �Ϸ��� ���
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
