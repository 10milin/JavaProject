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
				orderNum = in.readUTF(); //서버에서 받아온 메시지를 출력
			}catch(Exception e) {}
		}
	}
}
