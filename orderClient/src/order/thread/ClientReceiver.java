package order.thread;

import java.io.DataInputStream;
import java.net.Socket;

public class ClientReceiver extends Thread{
	Socket socket;
	DataInputStream in;
	public static String orderNum;
	public ClientReceiver(Socket socket) {
		this.socket = socket;
		try{
			in = new DataInputStream(socket.getInputStream());
		}catch(Exception e) {}
	}

	@Override
	public void run(){
		while(in != null) {
			try{
				orderNum = in.readUTF(); //�������� �޾ƿ� �޽����� ���
			}catch(Exception e) {}
		}
	}
}
