package receiverClient;

import java.net.*;
import java.awt.*;
import javax.swing.*;

public class ReceiverClient extends JFrame{
	static JTextArea output_msg = new JTextArea("");
	static String name;
	static JScrollPane scrollPane;
	Container c;
	
	public ReceiverClient() {
		setTitle("������");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = getSize();
		
		int x = (int)(screen.getWidth() / 2 + frm.getWidth() / 2);
		int y = (int)(screen.getHeight() / 2 - frm.getHeight() / 2);
		
		setLocation(x,y);
		
		init();
		start();
		
		setResizable(false);
		setVisible(true);
	}
	
	private void init() {
		c = getContentPane();
		output_msg.setEditable(false);
		output_msg.setFont(new Font("D2Coding", Font.PLAIN, 100));
		scrollPane = new JScrollPane(output_msg);

		c.add(scrollPane);
	}
	
	private void start() {
		try {
			name = "receiver"; //�������� �޴� ������ �����ص� �̸�
			Socket socket = new Socket("59.26.102.194",7700); //������ IP�ּҿ� ���� ��Ʈ��ȣ
			Thread receiver = new Thread(new Receiver(socket, name));
			receiver.start();
		}catch(Exception e) {}
	}
	
	public static void main(String[] ar) {
		new ReceiverClient();
		new ReceiverOrderList();
	}
}
