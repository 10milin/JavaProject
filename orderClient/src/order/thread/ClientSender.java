package order.thread;

import java.io.DataOutputStream;
import java.net.Socket;

public class ClientSender extends Thread {
	Socket socket;
	public static DataOutputStream out;
	String name;
	
	public ClientSender(Socket socket, String name) {
		try{
			out = new DataOutputStream(socket.getOutputStream());
			this.name = name;
		}catch(Exception e) {}
	}

	@Override
	public void run() {
		try {
			if(out != null) {
				out.writeUTF(name); //서버로 메시지 전송
			}
		}catch(Exception e) {}
	}
}
