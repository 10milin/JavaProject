package receiverClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Receiver extends Thread{
	DataInputStream in;
	DataOutputStream out;
	String name;
	String msg;
	boolean check = false;
	boolean inputCheck = false;
	int index = 0;
	static ArrayList<String> order = new ArrayList<String>();
	
	public Receiver(Socket socket, String name) {
		this.name = name;
		try{
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		}catch(Exception e) {}
	}

	@Override
	public void run(){
		if(out != null) {
			try {
				out.writeUTF(name);
			} catch (Exception e) {
				e.printStackTrace();
			} //서버로 자신의 이름 전송
		}
		while(in != null) {
			try{
				msg = in.readUTF();
				order.add(msg); //주문번호와 주문내역을 계속 받아옴
				index = 0;
				for(int i = 0; i < order.size(); i++) {
					//버튼에 주문번호를 입력하는 작업(버튼 갯수에 맞게)
					if(order.get(i).charAt(0) >= '0' && order.get(i).charAt(0) <= '9' && index < 12) {
						ReceiverOrderList.orderBtn[index].setText(order.get(i));
						index++;
					}
				}
			}catch(Exception e) {}
		}
	}
}
